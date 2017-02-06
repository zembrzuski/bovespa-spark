import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object StreamingIntro {

  def main(args: Array[String]): Unit = {
    // antes de executrar, devo escrever o seguitne no terminal:
    // nc -lk 8088

    // copia do livro: mostra as linhas que possuem error.
//    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))
//    val ssc = new StreamingContext(sc, Seconds(2))
//    val lines = ssc.socketTextStream("localhost", 8088)
//    val errorLines = lines.filter(_.contains("error"))
//    errorLines.print()
//    ssc.start()
//    ssc.awaitTermination()

    // stateless processing: contar as palavras
//    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))
//    val ssc = new StreamingContext(sc, Seconds(5))
//    val lines = ssc.socketTextStream("localhost", 8088)
//    val summed = lines.map(line => (line, 1)).reduceByKey((x, y) => x+y)
//    summed.foreachRDD(x => x.collect().foreach(println))
//    ssc.start()
//    ssc.awaitTermination()

//    // stateful processing: contar palavras ao longo do tempo.
    // nao funcionou direito, mas peguei o espirito. na segunda tentativa, vai funcionar muito bem.
//    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))
//    val ssc = new StreamingContext(sc, Seconds(5))
//    ssc.checkpoint("/home/nozes/working-area/spark-checkpoint")
//    val lines = ssc.socketTextStream("localhost", 8088)
//    lines.window(Seconds(10), Seconds(5))
//    val summed = lines.map(line => (line, 1)).reduceByKeyAndWindow({(x, y) => x+y}, {(x, y) => x+y}, Seconds(30), Seconds(10))
//    summed.foreachRDD(x => x.collect().foreach(println))
//    ssc.start()
//    ssc.awaitTermination()


    // update state by key. esse é o cara.
    // entao esse cara vou fazer com calma :)


  }
}
