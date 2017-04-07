import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.{SparkConf, SparkContext}


/**
  *
  *
  *
  * url base para eu fazer o codigo funcionar com kafka.
  * mas antes eu vou revisar o streaming simples. desse modo, vou conseguir prosseguir.
  *
  * https://github.com/knoldus/real-time-stream-processing-engine
  *
  *
  *
  */
object HelloKakfa {

  def main(args: Array[String]): Unit = {

    val sparkContext = new SparkContext(new SparkConf().setAppName("hello-kakfa").setMaster("local[*]"))

    val streamingContext = new StreamingContext(sparkContext, Seconds(2))

    // I could use it if a wanted to.
    //  "bootstrap.servers" -> "localhost:9092,anotherhost:9092"

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "use_a_separate_id_for_each_stream",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("test")

    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    stream.map(record => (record.key, record.value))

  }

}
