import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer
import scala.util.Random

object NearestNeighbors {

  /**
    * This is a very stupid way to generate random numbers. It is not parallelizable and
    * it is not 'functional'.
    *
    * Fore sure, I can do it better, but, by now, I'll do in this way because
    * it is not the focus now. The focus is concerned in implementing knn.
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

  def computeEuclidianDistance(x: (Int, Int), y: (Int, Int)): Double = {
    Math.sqrt(Math.pow(x._1 - y._1, 2) + Math.pow(x._2 - y._2, 2))
  }

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))

    val myTuples = sc.parallelize(generateRandomTuples(20, 20))
    val referencePoint = generateRandomTuples(20, 1).head

    myTuples
      .map(x => (x, computeEuclidianDistance(x, referencePoint)))
      .sortBy(x => x._2, ascending = true)
      .collect()
      .foreach(x => println(x))

  }

}
