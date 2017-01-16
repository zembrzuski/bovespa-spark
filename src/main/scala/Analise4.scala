import org.apache.spark.{SparkConf, SparkContext}
import play.api.libs.json.Json
import service.{MyDomainToUsefulDomainConverter, MyParser}

/**
  * What am I going to do
  * For a given indicator -- I'll start with p/lpa --, I'll do the following
  * computations for each sector:
  *
  * Mean
  * Standard deviation
  * Max
  * Min
  */
object Analise4 {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))

    val minhasEmpresas = sc
      .textFile("./input/all.txt")
      .map(x => Json.parse(x))
      .map(x => MyParser.indicadoresReads.reads(x).get)
      .map(x => MyDomainToUsefulDomainConverter.convert(x))
      .map(x => ((x.setor, x.subsetor), (x.nomeEmpresa, x.pSobreL)))
      .persist()

    val mediasPorSetor = minhasEmpresas
      .combineByKey(
        (v: (String, Double)) => (v._2, 1),
        (v: (Double, Int), v2: (String, Double)) => (v._1 + v2._2, v._2 + 1),
        (v1: (Double, Int), v2: (Double, Int)) => (v1._1 + v2._1, v1._2 + v2._2)
      )
      .mapValues(x => (x._1/x._2, x._2))
      .persist()

    val desvioPadraoDeCadaSetor = minhasEmpresas
      .join(mediasPorSetor)
      .mapValues(x => {
        val indicadorEmpresa = x._1._2
        val mediaIndicadorSetor = x._2._1
        val qtdEmpresasSetor = x._2._2
        val squared = Math.pow(indicadorEmpresa-mediaIndicadorSetor, 2)
        val empresa = x._1._1

        (empresa, squared, qtdEmpresasSetor)
      })
      .combineByKey(
        (v: (String, Double, Int)) => (v._2, v._3),
        (v1: (Double, Int), v2: (String, Double, Int)) => (v1._1 + v2._2, v1._2),
        (v1: (Double, Int), v2: (Double, Int)) => (v1._1 + v2._1, v1._2)
      )
      .mapValues(x => Math.sqrt(x._1/(x._2-1)))
      .persist()

    val minMaxDeCadaSetor = minhasEmpresas
        .combineByKey(
          (v: (String, Double)) => (v._2, v._2),
          (v1: (Double, Double), v2: (String, Double)) => {
            val min = if (v1._1 < v2._2) v1._1 else v2._2
            val max = if (v1._2 > v2._2) v1._2 else v2._2
            (min, max)
          },
          (v1: (Double, Double), v2: (Double, Double)) => {
            val min = if (v1._1 < v2._1) v1._1 else v2._1
            val max = if (v1._2 < v2._2) v1._2 else v2._2
            (min, max)
          }
        )
        .persist()

    val conclusion = minhasEmpresas
      .join(mediasPorSetor)
      .join(desvioPadraoDeCadaSetor)
      .join(minMaxDeCadaSetor)
      .map(x => computeStandardDeviation(x))
      .filter(x => !x._1.equals(""))
      .sortBy(x => x._9, ascending = false)
      .take(20)
      .foreach(println)
  }

  private def computeStandardDeviation(x: ((String, String), ((((String, Double), (Double, Int)), Double), (Double, Double)))) = {
    val setor = x._1._1
    val subsetor = x._1._2
    val values = x._2
    val empresa = values._1._1._1._1
    val indicadorEmpresa = values._1._1._1._2
    val mediaSetor = values._1._1._2._1
    val desvioPadraoSetor = values._1._2
    val minSetor = values._2._1
    val maxSetor = values._2._2
    val numeroDesviosPadroesDaEmpresaEmRelacaoAoSetor = (indicadorEmpresa - mediaSetor) / desvioPadraoSetor

    (setor, subsetor, empresa, indicadorEmpresa, mediaSetor,
      desvioPadraoSetor, minSetor, maxSetor,
      numeroDesviosPadroesDaEmpresaEmRelacaoAoSetor)
  }

}
