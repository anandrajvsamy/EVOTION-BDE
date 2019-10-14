package workflow.tasks;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

public class CSV2TableJob
        implements Job
{
    private Logger logger;
    private SparkSession spark;
    private static String primarykey = "AUTO";
    private String urldb;
    private Dataset<Row> df;
    private Map<String, String> parameters;
    private static final int FIRST_OCCURRENCE = 2;
    private static final int KEY = 0;
    private static final int VALUE = 1;

    public void init(Object... args) throws Exception {
        this.logger = LogManager.getLogger(CSV2TableJob.class);
        this.parameters = (Map)Arrays.stream(args).map(x -> x.toString().split("=", 2)).collect(Collectors.toMap(x -> x[0], x -> x[1]));
        this.spark = SparkSession.builder().appName("CSV2Table").getOrCreate();

        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        this.urldb = ((String)this.parameters.get("urldb"));
    }

    public void execute(Object... params) throws Exception {
        this.df = this.spark.read().format("csv").option("header", "true").option("inferSchema", "true").load((String)this.parameters.get("urlcsv"));

        if ("AUTO".equalsIgnoreCase((String)this.parameters.get("primarykey"))) {
            this.primarykey = "ROW_ID";
            this.df = this.df.withColumn("ROW_ID", functions.monotonically_increasing_id());
        }
    }

    public void output(Object... params) throws Exception {
        String tableName = ((String)this.parameters.get("tablename")).replaceAll("-", "");

        this.df.write()
                .format("jdbc")
                .mode(SaveMode.Overwrite)
                .option("url", this.urldb)
                .option("driver", "org.apache.phoenix.jdbc.PhoenixDriver")
                .option("dbtable", tableName)
                .option("primarykey", primarykey)
                .option("phoenix.mutate.maxSize", "10000000")
                .option("phoenix.mutate.immutableRows", "10000000")
                .save();
        this.spark.stop();
        this.spark.close();
    }
}