package workflow.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

public class MeanJob implements Job
{
    private Logger logger;
    private SparkSession spark;
    private Dataset<Row> dfr;
    private Optional<Dataset<Row>> dfr2;
    private List<String> labelNames = new ArrayList();
    private List<String> lnames = new ArrayList();
    private String lsnames;
    private Dataset<Row> df;
    private Dataset<Row> dfResult;
    private String debugString;
    private Map<String, String> parameters;


    public void init(Object... args) throws Exception {
        this.logger = LogManager.getLogger(MeanJob.class);
        this.parameters = (Map)Arrays.stream(args).map(x -> x.toString().split("=", 2)).collect(Collectors.toMap(x -> x[0], x -> x[1]));

        try {
            this.spark = SparkSession.builder().appName("Mean").getOrCreate();
        }
        catch (Exception e) {
            this.spark = SparkSession.builder().master("local[2]").appName("Mean").getOrCreate();
        }
    }

    public void execute(Object... params) throws Exception {
        int i;
        this.df = this.spark.read().format("csv").option("header", "true").option("inferSchema", "true").load((String)this.parameters.get("csvData"));
        this.lnames = (List)this.labelNames.stream().map(s -> s + "_mean").collect(Collectors.toList());
        this.lsnames = (String)this.lnames.stream().map(Object::toString).collect(Collectors.joining(","));
        this.labelNames = (List)Arrays.stream(((String)this.parameters.get("labelName")).split(",")).collect(Collectors.toList());
        this.df = this.df.withColumn("tr", functions.regexp_replace(this.df.col((String)this.parameters.get("grbyLabel")), "'", ""));
        this.df = this.df.withColumn("TimeRecord", this.df.col("tr").cast("timestamp")).drop((String)this.parameters.get("grbyLabel")).drop("tr");

        ArrayList<Dataset<Row>> a = new ArrayList<Dataset<Row>>();
        switch ((String)this.parameters.get("aggPar")) {
            case "Month":
                for (i = 0; i < this.labelNames.size(); i++) {
                    this
                            .dfr = this.df.groupBy(new Column[] { functions.window(this.df.col("TimeRecord"), "30 days").alias("Time") }).agg(functions.avg(this.df.col(String.format("%s", new Object[] { this.labelNames.get(i) }))).alias(String.format("%s_monthly_mean", new Object[] { this.labelNames.get(i) })), new Column[0]);
                    a.add(this.dfr.sort("Time", new String[0]));
                }
                break;
            case "Year":
                for (i = 0; i < this.labelNames.size(); i++) {
                    this
                            .dfr = this.df.groupBy(new Column[] { functions.window(this.df.col("TimeRecord"), "365 days").alias("Time") }).agg(functions.avg(this.df.col(String.format("%s", new Object[] { this.labelNames.get(i) }))).alias(String.format("%s_annual_mean", new Object[] { this.labelNames.get(i) })), new Column[0]);
                    a.add(this.dfr.sort("Time", new String[0]));
                }
                break;
            case "Day":
                for (i = 0; i < this.labelNames.size(); i++) {
                    this
                            .dfr = this.df.groupBy(new Column[] { functions.window(this.df.col("TimeRecord"), "1 day").alias("Time") }).agg(functions.avg(this.df.col(String.format("%s", new Object[] { this.labelNames.get(i) }))).alias(String.format("%s_daily_mean", new Object[] { this.labelNames.get(i) })), new Column[0]);
                    a.add(this.dfr.sort("Time", new String[0]));
                }
                break;
            case "Minute":
                for (i = 0; i < this.labelNames.size(); i++) {
                    this
                            .dfr = this.df.groupBy(new Column[] { functions.window(this.df.col("TimeRecord"), "1 minute").alias("Time") }).agg(functions.avg(this.df.col(String.format("%s", new Object[] { this.labelNames.get(i) }))).alias(String.format("%s_minutely_mean", new Object[] { this.labelNames.get(i) })), new Column[0]);
                    a.add(this.dfr.sort("Time", new String[0]));
                }
                break;
            case "Week":
                for (i = 0; i < this.labelNames.size(); i++) {
                    this
                            .dfr = this.df.groupBy(new Column[] { functions.window(this.df.col("TimeRecord"), "7 days").alias("Time") }).agg(functions.avg(this.df.col(String.format("%s", new Object[] { this.labelNames.get(i) }))).alias(String.format("%s_weekly_mean", new Object[] { this.labelNames.get(i) })), new Column[0]);
                    a.add(this.dfr.sort("Time", new String[0]));
                }
                break;
            case "Hour":
                for (i = 0; i < this.labelNames.size(); i++) {
                    this
                            .dfr = this.df.groupBy(new Column[] { functions.window(this.df.col("TimeRecord"), "1 hour").alias("Time") }).agg(functions.avg(this.df.col(String.format("%s", new Object[] { this.labelNames.get(i) }))).alias(String.format("%s_hourly_mean", new Object[] { this.labelNames.get(i) })), new Column[0]);
                    a.add(this.dfr.sort("Time", new String[0]));
                }
                break;
            default:
                System.out.println("Wrong parameter input."); break;
        }
        this.dfr2 = a.stream().reduce((x, y) ->
                x.join(y, "Time"));

        this.dfResult = ((Dataset)this.dfr2.get()).withColumn("Start_Time", ((Dataset)this.dfr2.get()).col("Time.start")).withColumn("End_Time", ((Dataset)this.dfr2.get()).col("Time.end")).drop("Time");
        this.dfResult.show();
    }

    public void output(Object... params) throws Exception {
        this.dfResult.repartition(1).write()
                .format("csv")
                .mode(SaveMode.Overwrite)
                .option("header", "true")

                .option("timestampFormat", "yyyy-MM-dd HH:mm:ss.SSSSSS")
                .save((String)this.parameters.get("resultPath"));
        this.logger.info(this.debugString);
        this.spark.stop();
        this.spark.close();
    }
}