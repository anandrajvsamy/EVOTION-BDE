package workflow.tasks;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.ml.clustering.BisectingKMeansModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.feature.VectorSlicer;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;


public class KmeansPredictJob
        implements Job
{
    private Logger logger;
    private SparkSession spark;
    private List<String> lcn = new ArrayList();


    private Map<String, String> parameters;


    private static final int FIRST_OCCURRENCE = 2;


    private static final int KEY = 0;


    private static final int VALUE = 1;


    private Dataset<Row> df;

    private Dataset<Row> dfResult;

    private Dataset<Row> dfResult2;

    private Vector featureImportances;

    private String debugString;


    public void init(Object... args) throws Exception {
        this.logger = LogManager.getLogger(KmeansPredictJob.class);
        this.parameters = (Map)Arrays.stream(args).map(x -> x.toString().split("=", 2)).collect(Collectors.toMap(x -> x[0], x -> x[1]));
        try {
            this.spark = SparkSession.builder().appName("KmeansPredictJob").getOrCreate();
        }
        catch (Exception e) {
            this.spark = SparkSession.builder().master("local[2]").appName("KmeansPredictJob").getOrCreate();
        }
    }



    public void execute(Object... params) throws Exception {
        this.df = this.spark.read().format("csv").option("header", "true").option("inferSchema", "true").load((String)this.parameters.get("csvData"));
        formatData();
        BisectingKMeansModel model = BisectingKMeansModel.load((String)this.parameters.get("modelData"));
        Dataset<Row> predictions = model.transform(this.df);
        this.dfResult = predictions.drop("features");

    }

    public void output(Object... params) throws Exception {
        this.dfResult.coalesce(1).write()
                .format("csv")
                .mode(SaveMode.Overwrite)
                .option("header", "true")
                .save((String)this.parameters.get("resultPath"));
    }


    void formatData() {
        this.df.createOrReplaceTempView("dft");
        this.df = this.spark.sql(String.format("SELECT * FROM dft", new Object[0]));
        String[] names = this.df.columns();
        VectorAssembler assembler1 = (new VectorAssembler()).setInputCols(names).setOutputCol("features");
        this.df = assembler1.transform(this.df);
    }
}