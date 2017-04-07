import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

/**
  * It is an exemple from Learning Spark book.
  */
object HelloStreaming {


  def updateRunningSum(values: Seq[Long], state: Option[Long]) = {
    Some(state.getOrElse(0L) + values.size)
  }

  def main(args: Array[String]): Unit = {
    val sparkContext = new SparkContext(new SparkConf().setAppName("hello-kakfa").setMaster("local[*]"))
    val streamingContext = new StreamingContext(sparkContext, Seconds(5))

    streamingContext.checkpoint(".")

//    val lines = streamingContext.socketTextStream("localhost", 7777)
//    val responseCodeStream = lines.map(log => (log, 1L))
//    val responseCodeCountStream = responseCodeStream.updateStateByKey(updateRunningSum)
//    responseCodeCountStream.print()


    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "use_a_separate_group_id_for_each_stream",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("test")

    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

//    stream.map(record => (record.key, record.value))
    stream.print()

    //responseCodeCountStream.print()
    //responseCodeCountStream.foreachRDD(x => x.keys.collect().foreach(println))


    /*
    STATELESS TRANSFORMATION
    val lines = streamingContext.socketTextStream("localhost", 7777)
    val mapped = lines.map(x => {
      val splitted = x.split(",")
      splitted(0).toUpperCase() + splitted(1).toUpperCase()
    })
    mapped.print()
    */



    streamingContext.start()
    streamingContext.awaitTermination()

  }

}
