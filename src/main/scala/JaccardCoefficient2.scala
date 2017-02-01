import org.apache.spark.{SparkConf, SparkContext}

/**
  * Let me see if I can do it better!
  */
object JaccardCoefficient2 {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(
      new SparkConf()
        .setAppName("hello-spark")
        .setMaster("local[*]")
    )

    val myTuples = sc
      .textFile("./input/jaccard.txt")
      .map(x => (x.split(" ")(1), x.split(" ")(0)))
      .persist()

    myTuples
      .cartesian(myTuples)
      .filter(x => x._1._1.equals("esporte")) // I don't need it. Just to organize my mind.
      .collect()
      .foreach(println)

  }

}
