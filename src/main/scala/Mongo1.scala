import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object Mongo1 {

  def main(args: Array[String]): Unit = {

    val sc = SparkSession.builder()
      .master("local[*]")
      .appName("hello-mongo")
      .config("spark.mongodb.input.uri", "mongodb://10.240.64.103:20000/sherlock.events")
      //.config("spark.mongodb.output.uri", "mongodb://127.0.0.1:27017/articles.articles")
      .getOrCreate()
      .sparkContext

    sc.parallelize(List(1, 2, 3)).collect().foreach(println)

    val rdd = MongoSpark.load(sc)
    println("ae")

    //val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))

    //val sqlContext = SQLContext.getOrCreate(sc)
    //val readConfig = ReadConfig(Map("uri" -> "mongodb://localhost:27017/articles.articles"))
    //val readConfig = ReadConfig(Map("uri" -> "mongodb://127.0.0.1/articles.articles?readPreference=primaryPreferred\""))
    //val movieRatings = MongoSpark.load(sc, readConfig) //.takeOrdered(2)

    //val writeConfig = WriteConfig(Map("uri" -> "mongodb://127.0.0.1/movies.user_recommendations"))
    //val movieRatings = MongoSpark.load(sc, readConfig)// .toDF[UserMovieRating]

    println("ae")
  }

}

