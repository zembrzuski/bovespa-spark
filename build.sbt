name := "hello"
version := "1.0"
//scalaVersion := "2.11.6"
scalaVersion := "2.10.4"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "2.1.0"

//libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "2.1.0"


// estou usando esse cara para fazer parse de json
libraryDependencies += "com.typesafe.play" % "play-json_2.10" % "2.4.8"

//libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.1.0"
// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka_2.10
//libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.3"



// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka-0-10_2.10
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.10" % "2.0.0"

// https://mvnrepository.com/artifact/org.json4s/json4s-native_2.11
//libraryDependencies += "org.json4s" % "json4s-native_2.11" % "3.5.1"
// https://mvnrepository.com/artifact/org.json4s/json4s-native_2.10
libraryDependencies += "org.json4s" % "json4s-native_2.10" % "3.5.1"
