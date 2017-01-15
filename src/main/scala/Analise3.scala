import org.apache.spark.{SparkConf, SparkContext}

/**
  * Isso aqui serve somente para computar o menor e o maior valor por
  * chave em uma unica passada.
  */
object Analise3 {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))

    val minhasEmpresas = sc
      .textFile("./input/teste2.txt")
      .map(x => fromTextToTuple(x))
      .combineByKey(
        v => (v, v),
        (acc: (Int, Int), comb: Int) => {
          val minimo = if (acc._1 < comb) acc._1 else comb
          val maximo = if (acc._2 > comb) acc._2 else comb
          (minimo, maximo)
        },
        (acc1: (Int, Int), acc2: (Int, Int)) => {
          val minimo = if (acc1._1 < acc2._1) acc1._1 else acc2._1
          val maximo = if (acc1._2 > acc2._2) acc1._2 else acc2._2
          (minimo, maximo)
        }
      )
      .collect()
      .foreach(println)

  }

  private def fromTextToTuple(x: String) = {
    val splitted = x.split(" ")
    (splitted(0), splitted(1).toInt)
  }

  private def findMaxValue(v1: String, v2: String) = {
    if (v1 > v2) v1 else v2
  }
}
