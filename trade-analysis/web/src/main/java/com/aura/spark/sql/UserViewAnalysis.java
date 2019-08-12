package com.aura.spark.sql;

import com.aura.model.User_view;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;

public class UserViewAnalysis extends BaseTradeAnalysis implements Serializable {
    private SparkSession spark;
    private JavaSparkContext jsc;
    private String userView_path = "hdfs://ze:9000/bdp22/dataset/user_view.txt";
    public UserViewAnalysis() {
        SparkConf conf = new SparkConf();
        conf.setIfMissing("spark.app.name", getClass().getSimpleName());
        conf.setMaster("local[0]"); //本地测试使用
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        spark = SparkSession.builder().config(conf).getOrCreate();
        jsc = new JavaSparkContext(spark.sparkContext());
    }

    @Override
    protected void tradeAnalysis() {
        //User_view user_view = new User_view();
        JavaRDD<User_view> userViewJavaRDD = toUserViewRDD(userView_path);
        Dataset<Row> shopViewDF = toUserViewDF(userViewJavaRDD);
        shopInfoDF.show();

    }

    public static void main(String[] args) {
        UserViewAnalysis userViewAnalysis = new UserViewAnalysis();
        userViewAnalysis.tradeAnalysis();
    }
}
