import org.apache.spark.{SparkConf, SparkContext}

/**
  * Calculating jaccard coefficient index.
  */
object JaccardCoeficient {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))

    val noticiaA = "umaoutra"
    val noticiaB = "politica"

    val myTuples = sc
      .textFile("./input/jaccard.txt")
      .map(x => (x.split(" ")(1), x.split(" ")(0)))
      .persist()

    val viewsNoticiaA = myTuples
      .filter(x => x._1.equals(noticiaA))
      .map(x => x._2)
      .persist()

    // it could be extracted for another method.
    val viewsNoticiaB = myTuples
      .filter(x => x._1.equals(noticiaB))
      .map(x => x._2)
      .persist()

    val joined = viewsNoticiaA
      .cartesian(viewsNoticiaB)
      .persist()


    val and = joined
      .filter(x => x._1.equals(x._2))


    val or = joined
      .filter(x => !x._1.equals(x._2))


    val jaccardSimilarity = and.count().toDouble / or.count()

    println(jaccardSimilarity)

  }


}
