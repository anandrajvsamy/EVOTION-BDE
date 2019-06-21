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

public class Table2CSVJob
        implements Job {
    private Logger logger;
    private String urldb;
    private String queryInputData;
    private Dataset<Row> df;
    private SparkSession spark;
    private Map<String, String> parameters;
    private static final int FIRST_OCCURRENCE = 2;
    private static final int KEY = 0;
    private static final int VALUE = 1;

    public void init(Object... args) throws Exception {
        this.logger = LogManager.getLogger(Table2CSVJob.class);
        this.parameters = (Map) Arrays.stream(args).map(x -> x.toString().split("=", 2)).collect(Collectors.toMap(x -> x[0], x -> x[1]));
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        this.urldb = ((String) this.parameters.get("urldb"));

        if (((String) this.parameters.get("queryInputData")).toUpperCase().startsWith("SELECT")) {
            this.queryInputData = String.format("(%s) AS TMP", new Object[]{this.parameters.get("queryInputData")});
        }
        this.spark = SparkSession.builder().appName("Hbase2csv").getOrCreate();
    }

    public void execute(Object... params) throws Exception {
        this.df = this.spark.read().format("jdbc").option("url", this.urldb).option("driver", "org.apache.phoenix.jdbc.PhoenixDriver").
                option("dbtable", this.queryInputData).load();
    }

    public void output(Object... params) throws Exception {
        String urltmp = (String) this.parameters.get("urlcsv") + ".tmp";
        urltmp = (String) this.parameters.get("urlcsv");

        this.df.write()
                .format("csv")
                .mode(SaveMode.Overwrite)
                .option("header", "true")
                .save(urltmp);

        this.spark.stop();
        this.spark.close();
    }
}