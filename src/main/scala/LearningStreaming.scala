import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object LearningStreaming {

  def main(args: Array[String]): Unit = {
    // nc -lk 7777
    // materia1 - 12:35

    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))
    val ssc = new StreamingContext(sc, Seconds(5))
    ssc.checkpoint("/home/rodrigoz/studying-area/spark-checkpoint")

    val lines = ssc.socketTextStream("localhost", 7777)
    val responseCodeDStream = lines.map { x =>
      val splitted = x.split(" - ")
      val nomeMateria = splitted(0)
      val hora = splitted(1).split(":")(0)
      ((nomeMateria, hora), 1L)
    }
    val responseCodeCountDStream = responseCodeDStream.updateStateByKey(updateRunningSum _)
    responseCodeCountDStream.foreachRDD(x => x.foreach(x => println(x)))

    ssc.start()
    ssc.awaitTermination()
  }


  def updateRunningSum(values: Seq[Long], state: Option[Long]) = {
    Some(state.getOrElse(0L) + values.size)
  }

}
