import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
  * The goal of this class is perform k-means.
  */
object KMeans {

  def main(args: Array[String]): Unit = {

    val numberOfPoints = 10
    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))
    val myDataset = sc.parallelize(generateRandomTuples(20, numberOfPoints)).zipWithIndex()
    var mycentroids = myDataset.takeSample(false, 2).map(x => x._1)

    for (i <- 1 to 10) {
      mycentroids.foreach(println)
      val myCentroidsSparkContext = sc.parallelize(mycentroids)

      mycentroids = myDataset
        .cartesian(myCentroidsSparkContext)
        .map(x => (x._1, (x._2, computeEuclidianDistance(x._1._1, x._2))))
        .filter(x => !mycentroids.contains(x._1._1))
        .reduceByKey((x1, x2) => if (x1._2 < x2._2) x1 else x2)
        .map(x => (x._2._1, x._1._1))
        .combineByKey(
          (x: (Int, Int)) => (x._1, x._2, 1),
          (x1: (Int, Int, Int), x2: (Int, Int)) => (x1._1 + x2._1, x1._2 + x2._2, x1._3 + 1),
          (x1: (Int, Int, Int), x2: (Int, Int, Int)) => (x1._1 + x2._1, x1._2 + x2._2, x1._3 + x2._3)
        )
        .mapValues(x => (x._1.toDouble / x._3, x._2.toDouble / x._3)) //step 7
        .map(x => (x._2._1.toInt, x._2._2.toInt)) //step 8
        .collect() //step 9
    }

  }


  /**
    * This is a very stupid way to generate random numbers. It is not parallelizable.
    *
    * Fore sure, I can do it better, but, by now, I'll do in this way because
    * it is not the focus now. The focus is concerned in implementing k-means.
    */
  def generateRandomTuples(maxValue: Int, numberOfPoints: Int): List[(Int, Int)] = {
    var tuples = new ListBuffer[(Int, Int)]()

    for (i <- 1 to numberOfPoints) {
      val x = Random.nextInt(maxValue)
      val y = Random.nextInt(maxValue)

      tuples += ((x, y))
    }

    tuples.toList
  }

  /**
    * Just two dimensions. If you feel motivated, you can expand it to support
    * many dimensions.
    */
  def computeEuclidianDistance(x: (Int, Int), y: (Int, Int)): Double = {
    Math.sqrt(Math.pow(x._1 - y._1, 2) + Math.pow(x._2 - y._2, 2))
  }

}
