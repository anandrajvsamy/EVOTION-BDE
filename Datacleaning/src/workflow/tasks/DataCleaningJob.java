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

public class DataCleaningJob implements Job {

    private Logger logger;
    private SparkSession spark;
    private String csvData;
    private int labelIndex;
    private String resultPath;
    private Dataset<Row> df;
    private Dataset<Row> dfResult;
    private String debugString;
    private Map<String, String> parameters;
    private static final int FIRST_OCCURRENCE = 2;
    private static final int KEY = 0;
    private static final int VALUE = 1;

    public void init(Object... args) throws Exception {
        this.logger = LogManager.getLogger(DataCleaningJob.class);
        this.parameters = (Map)Arrays.stream(args).map(x -> x.toString().split("=", 2)).collect(Collectors.toMap(x -> x[0], x -> x[1]));
        try {
            this.spark = SparkSession.builder().appName("DataCleaningJob").getOrCreate();
        }
        catch (Exception e) {
            this.spark = SparkSession.builder().master("local[2]").appName("DataCleaningJob").getOrCreate();
        }
    }


    public void execute(Object... params) throws Exception {
        this.df = this.spark.read().format("csv").option("header", "true").option("inferSchema", "true").load((String)this.parameters.get("csvData"));

        String[] strArray = ((String)this.parameters.get("labels")).split(",");
        this.dfResult = this.df.na().drop(strArray);
    }

    public void output(Object... params) throws Exception {
        this.dfResult.repartition(1).write()
                .format("csv")
                .mode(SaveMode.Overwrite)
                .option("header", "true")
                .save((String)this.parameters.get("resultPath"));
        this.logger.info(this.debugString);
        this.spark.stop();
        this.spark.close();
    }
}