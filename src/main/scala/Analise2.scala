import org.apache.spark.{SparkConf, SparkContext}
import play.api.libs.json.Json
import service.{MyDomainToUsefulDomainConverter, MyParser}

/**
  * Isso aqui vai ser um teste para calcular media e desvio padrão.
  * O objetivo é que eu aprenda a calcular media e desvio padrão por
  * materia escoalr e então que eu possa fazer um join para que eu saiba
  * quantos desvios padroes cada aluno está em relação à média.
  */
object Analise2 {

  private def fromTextToTuples(x: String) = {
    val splitted = x.split(" ")
    (splitted(0), splitted(1), splitted(2).toDouble)
  }

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))

    val myData = sc
      .textFile("./input/teste.txt")
      .map(x => fromTextToTuples(x))
      .persist()

    val medias = myData
      .map(x => (x._1, (x._3, 1)))
      .reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
      .mapValues(x => (x._1 / x._2, x._2))
      .persist()

    val desvioPadrao = sc
      .textFile("./input/teste.txt")
      .map(x => fromTextToTuples(x))
      .map(x => (x._1, (x._2, x._3)))
      .join(medias)
      .map(x => {
        val disciplina = x._1
        val tupleValue = x._2
        val nomePessoa = tupleValue._1._1
        val notaPessoa = tupleValue._1._2
        val mediaDisciplina = tupleValue._2._1
        val quantidadePessoasNaDisciplina = tupleValue._2._2
        val difference = notaPessoa - mediaDisciplina
        val quadrados= difference * difference
        (disciplina, (nomePessoa, notaPessoa, mediaDisciplina, quantidadePessoasNaDisciplina, quadrados))
      })
      .combineByKey(
        previousTuple => (previousTuple._5, previousTuple._4),
        (c: (Double, Int), previousTuple) => (c._1 + previousTuple._5, c._2),
        (c1: (Double, Int), c2: (Double, Int)) => (c1._1 + c2._1, c1._2)
      )
      .mapValues(x => Math.sqrt(x._1/(x._2-1)))
      .persist()

    desvioPadrao
      .collect()
      .foreach(x => println(x))

    myData
      .map(x => (x._1, (x._2, x._3)))
      .join(medias)
      .join(desvioPadrao)
      .map(x => {
        val nomePessoa = x._2._1._1._1
        val materia = x._1
        val notaPessoa = x._2._1._1._2
        val mediaMateria = x._2._1._2._1
        val dpMateria = x._2._2
        val qtdDesviosPadroesDaPessoa = (notaPessoa - mediaMateria)/dpMateria
        (nomePessoa, qtdDesviosPadroesDaPessoa, materia, notaPessoa, mediaMateria, dpMateria)
      })
      .collect()
      .foreach(x => println(x))

  }

}
