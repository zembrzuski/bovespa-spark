import org.apache.spark.{SparkConf, SparkContext}
import play.api.libs.json.Json
import service.{MyDomainToUsefulDomainConverter, MyParser}

/**
  * Essa análise, acredito, vai ser bem simples:
  * vou simplesmente ver o perco sobre valor patrimonial de cada empresa e listar.
  *
  */
object Analise2 {
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))


    val minhasEmpresas = sc
      .textFile("./input/all.txt")
      .map(x => Json.parse(x))
      .map(x => MyParser.indicadoresReads.reads(x).get)
      .map(x => MyDomainToUsefulDomainConverter.convert(x))

    minhasEmpresas
      .map(x => (x.pSobreVp, (x.siglaEmpresa, x.nomeEmpresa)))
      .sortBy(x => x._1, ascending = true)
      .collect()
      .foreach(x => println(x))

  }
}
