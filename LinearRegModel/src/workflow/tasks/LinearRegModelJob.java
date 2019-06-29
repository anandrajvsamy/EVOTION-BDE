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
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.ml.regression.LinearRegressionTrainingSummary;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;


public class LinearRegModelJob
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

    private Dataset<Row> dfResult3;

    private Dataset<Row> dfResult4;

    private LinearRegressionModel model;

    private static final boolean fitIntercept = true;

    private static final boolean standardization = true;

    private static final int aggDepth = 2;

    private static final double tol = 1.0E-6D;

    private static final double elasticNetParam = 0.0D;

    private static final double regParam = 0.0D;

    private static final int maxIter = 100;

    private Vector featureImportances;

    private String debugString;


    public void init(Object... args) throws Exception {
        this.logger = LogManager.getLogger(LinearRegModelJob.class);
        this.parameters = (Map)Arrays.stream(args).map(x -> x.toString().split("=", 2)).collect(Collectors.toMap(x -> x[0], x -> x[1]));
        try {
            this.spark = SparkSession.builder().appName("LRModelTask").getOrCreate();
        }
        catch (Exception e) {
            this.spark = SparkSession.builder().master("local[2]").appName("LRModelTask").getOrCreate();
        }
    }

    public void execute(Object... params) throws Exception {
        this.df = this.spark.read().format("csv").option("header", "true").option("inferSchema", "true").load((String)this.parameters.get("csvData"));
        formatData();
        LinearRegression();
    }


    public void output(Object... params) throws Exception {
        this.model.write().overwrite().save((String)this.parameters.get("resultPath"));

        this.dfResult.coalesce(1).write()
                .format("csv")
                .mode(SaveMode.Append)
                .option("header", "true")
                .save((String)this.parameters.get("resultPath"));

        this.dfResult2.coalesce(1).write()
                .format("csv")
                .mode(SaveMode.Append)
                .option("header", "true")
                .save((String)this.parameters.get("resultPath"));

        this.dfResult3.coalesce(1).write()
                .format("csv")
                .mode(SaveMode.Append)
                .option("header", "true")
                .save((String)this.parameters.get("resultPath"));

        this.dfResult4.coalesce(1).write()
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

    private void LinearRegression() {
        LinearRegression lr = (new LinearRegression()).setMaxIter(Integer.parseInt((String)this.parameters.getOrDefault("maxIter", String.valueOf(100)))).setRegParam(Double.parseDouble((String)this.parameters.getOrDefault("regParam", String.valueOf(0.0D)))).setElasticNetParam(Double.parseDouble((String)this.parameters.getOrDefault("elasticNetParam", String.valueOf(0.0D)))).setFitIntercept(Boolean.parseBoolean((String)this.parameters.getOrDefault("fitIntercept", String.valueOf(true)))).setAggregationDepth(Integer.parseInt((String)this.parameters.getOrDefault("aggDepth", String.valueOf(2)))).setTol(Double.parseDouble((String)this.parameters.getOrDefault("tol", String.valueOf(1.0E-6D)))).setStandardization(Boolean.parseBoolean((String)this.parameters.getOrDefault("standardization", String.valueOf(true))));

        this.model = (LinearRegressionModel)lr.fit(this.df);

        LinearRegressionTrainingSummary trainingSummary = this.model.summary();

        List<Row> rowsn = Arrays.asList(new Row[] {
                RowFactory.create(new Object[] { Double.valueOf(this.model.intercept()), Integer.valueOf(trainingSummary.totalIterations()),
                        Double.valueOf(trainingSummary.rootMeanSquaredError()), Double.valueOf(trainingSummary.r2()) })
        });

        StructType scheman = new StructType(new StructField[] { new StructField("Intercept", DataTypes.DoubleType, true, Metadata.empty()), new StructField("Number_Of_Iterations", DataTypes.IntegerType, true, Metadata.empty()), new StructField("RMSE", DataTypes.DoubleType, true, Metadata.empty()), new StructField("r2", DataTypes.DoubleType, true, Metadata.empty()) });

        this.dfResult = this.spark.createDataFrame(rowsn, scheman);
        this.dfResult2 = trainingSummary.residuals();

        StructType schema3 = new StructType(new StructField[] { new StructField("Coefficients", DataTypes.DoubleType, true, Metadata.empty()) });

        List<Row> rows3 = new ArrayList<Row>();
        String s = this.model.coefficients().toString();
        s = s.replace("[", "").replace("]", "");

        List<String> sl = (List)Arrays.stream(s.split(",")).collect(Collectors.toList());
        List<Double> sli = new ArrayList<Double>(sl.size());

        for (String str : sl) {
            sli.add(Double.valueOf(str));
        }

        Double[] sla = (Double[])sli.stream().toArray(x$0 -> new Double[x$0]);

        for (int i = 0; i < sla.length; i++) {
            rows3.add(RowFactory.create(new Object[] { sla[i] }));
        }

        this.dfResult4 = this.spark.createDataFrame(rows3, schema3);


        StructType schema2 = new StructType(new StructField[] { new StructField("Objective_History", DataTypes.DoubleType, true, Metadata.empty()) });


        List<Row> r = new ArrayList<Row>();
        for (int i = 0; i < trainingSummary.objectiveHistory().length; i++) {
            r.add(RowFactory.create(new Object[] { Double.valueOf(trainingSummary.objectiveHistory()[i]) }));
        }

        this.dfResult3 = this.spark.createDataFrame(r, schema2);
    }
}
