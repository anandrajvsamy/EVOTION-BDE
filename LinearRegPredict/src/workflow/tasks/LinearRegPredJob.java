package workflow.tasks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
public class LinearRegPredJob implements Job
{
    private Logger logger;
    private SparkSession spark;
    private List<String> lcn = new ArrayList();



    private Dataset<Row> df;



    private Dataset<Row> predictions;


    private Map<String, String> parameters;


    private static final int FIRST_OCCURRENCE = 2;


    private static final int KEY = 0;


    private static final int VALUE = 1;


    private Vector featureImportances;


    private String debugString;



    public void init(Object... args) throws Exception {
        this.logger = LogManager.getLogger(LinearRegPredJob.class);
        this.parameters = (Map)Arrays.stream(args).map(x -> x.toString().split("=", 2)).collect(Collectors.toMap(x -> x[0], x -> x[1]));
        try {
            this.spark = SparkSession.builder().appName("LRPredictTask").getOrCreate();
        }
        catch (Exception e) {
            this.spark = SparkSession.builder().master("local[2]").appName("LRPredictTask").getOrCreate();
        }
    }

    public void execute(Object... params) throws Exception {
        this.df = this.spark.read().format("csv").option("header", "true").option("inferSchema", "true").load((String)this.parameters.get("csvData"));
        formatData();
        LinearRegressionModel model = LinearRegressionModel.load((String)this.parameters.get("modelData"));
        this.predictions = model.transform(this.df).drop("features");
        this.predictions.show();
    }


    public void output(Object... params) throws Exception {
        this.predictions.coalesce(1).write()
                .format("csv")
                .mode(SaveMode.Append)
                .option("header", "true")
                .save((String)this.parameters.get("resultPath"));

        this.logger.info(this.debugString);
        this.spark.stop();
        this.spark.close();
    }

    void formatData() {
        StringIndexer indexer = (new StringIndexer()).setInputCol(String.valueOf(this.df.col((String)this.parameters.get("label")))).setOutputCol("label");

        this.df = indexer.fit(this.df).transform(this.df);
        String processedLabelNames = ((String)this.parameters.get("labelName")).replaceAll("'", "");
        for (String c : this.df.columns()) {
            this.df = this.df.withColumnRenamed(c, c.replaceAll("'", ""));
        }
        this.df.createOrReplaceTempView("dft");
        this.df = this.spark.sql(String.format("SELECT %s, label FROM dft", new Object[] { processedLabelNames }));
        this.lcn = (List)Arrays.stream(processedLabelNames.split(",")).collect(Collectors.toList());
        String[] names = (String[])this.lcn.stream().toArray(x$0 -> new String[x$0]);

        VectorAssembler assembler1 = (new VectorAssembler()).setInputCols(names).setOutputCol("features");

        this.df = assembler1.transform(this.df);
        this.df.show();
    }
}
