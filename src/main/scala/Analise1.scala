import org.apache.spark.{SparkConf, SparkContext}
import play.api.libs.json._
import service.{MyDomainToUsefulDomainConverter, MyParser}

/**
  * Faço o parse dos jsons da fundamentus, calculo a media de
  * algum indicador que eu não lembro por setor, e faz o print
  * da lista da empresa, com a media do setor, e o valor do indicador
  */
object Analise1 {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))

    val minhasEmpresas = sc
      .textFile("./input/all.txt")
      .map(x => Json.parse(x))
      .map(x => MyParser.indicadoresReads.reads(x).get)
      .map(x => MyDomainToUsefulDomainConverter.convert(x))

    val mediaPorSetor = minhasEmpresas
      .map(x => ((x.setor, x.subsetor), (x.pSobreVp, 1)))
      .reduceByKey((e1, e2) => (e1._1 + e2._1, e1._2 + e2._2))
      .map {
        case (key, value) => (key, value._1 / value._2)
      }
//      .sortBy(x => x._2, ascending = false)

    // agora, vou tentar fazer um join para mostrar a emresa, seu pSobreVp e pode compara-lo com
    // a media do setor. depois, vou fazer um intervalo de confianca bem massa.

    minhasEmpresas
      .filter(x => x.pSobreVp > 0)
      .map(x => ((x.setor, x.subsetor), x))
      .join(mediaPorSetor)
      .map{
        case(key, value) =>
          val emp = value._1
          (emp.siglaEmpresa, emp.setor, emp.subsetor, emp.pSobreVp, value._2)
      }
      .collect()
      .foreach(x => println(x))





//    mediaPorSetor
//      .take(10)
//      .foreach(println)

  }

}