import domain.MyUsefulDomain
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import play.api.libs.json.Json
import service.{MyDomainToUsefulDomainConverter, MyParser}

/**
  * Não sei ainda.
  *
  * O que eu meio que quero fazer, mas que ainda não tenho certeza.
  *
  * Calcular a media e desvio padrão do P/VP em cada setor. Daí vou saber, para
  * cada empresa, como ela está em relação ao P/VP do seu setor.
  *
  * Após isso, quero ver se eu consigo relacionar isso com o indicador RPL, porque eles
  * parecem bem atrelados. RPL = lucroLiquido / patrimonioLiquido.
  *
  * http://www.valor.com.br/valor-investe/o-estrategista/3230168/o-charme-enganoso-do-multiplo-pvpa
  * http://iniciantenabolsa.com/serie-analise-fundamentalista-3-rpl-e-pl/
  *
  * Rodrigo.
  */
object Analise5 {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))

    val minhasEmpresas = sc
      .textFile("./input/all.txt")
      .map(x => Json.parse(x))
      .map(x => MyParser.indicadoresReads.reads(x).get)
      .map(x => MyDomainToUsefulDomainConverter.convert(x))
      .persist()

//    minhasEmpresas
//      .map(x => (x.siglaEmpresa, x.nomeEmpresa, x.setor, x.subsetor, x.pSobreVp, x.lucroLiquido/x.patrimonioLiquido))
//      .collect()
//      .foreach(println)

    minhasEmpresas
      .map(x => ((x.setor, x.subsetor), (x.pSobreVp, x.lucroLiquido/x.patrimonioLiquido*100, 1)))
      .reduceByKey((x1, x2) => (x1._1 + x2._1, x1._2 + x2._2, x1._3 + x2._3))
      .mapValues(x => (x._1/x._3, x._2/x._3, x._3))
      .filter(x => x._2._1 > 0 && x._2._2 > 0)
      .sortBy(x => x._2._2, ascending = false)
      .collect()
      .foreach(x => println(x))

  }

}
