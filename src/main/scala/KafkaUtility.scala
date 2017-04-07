import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

/**
  * Created by rodrigoz on 4/7/17.
  */
object KafkaUtility {

  //TODO It should read from config
  private val kafkaParams = Map(
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "auto.offset.reset" -> "earliest",
    "group.id" -> "use_a_separate_group_id_for_each_stream"
  )

  private val preferredHosts = LocationStrategies.PreferConsistent

  def createDStreamFromKafka(ssc: StreamingContext, topics: List[String]): InputDStream[ConsumerRecord[String, String]] =
    KafkaUtils.createDirectStream[String, String](
      ssc,
      preferredHosts,
      ConsumerStrategies.Subscribe[String, String](topics.distinct, kafkaParams)
    )

}
