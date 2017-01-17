import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import org.apache.spark.{SparkConf, SparkContext}

object Mongo1 {

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("hello-spark").setMaster("local[*]"))

    //val sqlContext = SQLContext.getOrCreate(sc)
    val readConfig = ReadConfig(Map("uri" -> "mongodb://localhost:27017/articles.articles"))
    val movieRatings = MongoSpark.load(sc, readConfig) //.takeOrdered(2)

    //val writeConfig = WriteConfig(Map("uri" -> "mongodb://127.0.0.1/movies.user_recommendations"))
    //val movieRatings = MongoSpark.load(sc, readConfig)// .toDF[UserMovieRating]

    println("ae")
  }

}

