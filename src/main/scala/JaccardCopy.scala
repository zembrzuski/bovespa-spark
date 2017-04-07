import org.apache.spark.{SparkConf, SparkContext}

object JaccardCopy {

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))

    // extract (userid, movieid, rating) from ratings data
    val ratings= sc
      .textFile("./input/ml-100k/zem.base")
      .map(x => {
        val splitted = x.split("\t")
        (splitted(0).toInt, splitted(1).toInt, splitted(2).toInt)
      })
      .persist()

    val numRatersPerMovie = ratings
      .map(x => (x._2, 1))
      .reduceByKey((x1, x2) => x1+x2)
      .persist()

    //(user, movie, rating, numRaters).
    val ratingsWithSize = ratings
      .keyBy(x => x._2)
      .join(numRatersPerMovie)
      .map(x => (x._2._1._1, x._2._1._2, x._2._1._3, x._2._2))
      .persist()

    val ratings2 = ratingsWithSize
      .keyBy(tup => tup._1)
      .persist()


    /**
      * NAO ENTENDI DIREITO ESSE CARA AINDA.
      * DEVO PROSSEGUIR A PARTIR DAQUI.
      */
    // join on userid and filter movie pairs such that we don't double-count and exclude self-pairs
    val ratingPairs = ratingsWithSize
        .keyBy(tup => tup._1)
        .join(ratings2)
        .filter(f => f._2._1._2 < f._2._2._2)
        .collect()
        .foreach(println)


  }

}
