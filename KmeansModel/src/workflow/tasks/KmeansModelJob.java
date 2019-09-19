package workflow.tasks;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.ml.clustering.BisectingKMeans;
import org.apache.spark.ml.clustering.BisectingKMeansModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class KmeansModelJob
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


        private BisectingKMeansModel model;


        private String debugString;

        private static final int maxIter = 5;

        private static final double MinDivisibleClusterSize = 1.0D;


        public void init(Object... args) throws Exception {
            this.logger = LogManager.getLogger( KmeansModelJob.class);
            this.parameters = (Map)Arrays.stream(args).map(x -> x.toString().split("=", 2)).collect(Collectors.toMap(x -> x[0], x -> x[1]));
            try {
                this.spark = SparkSession.builder().appName("KmeansModelJob").getOrCreate();
            }
            catch (Exception e) {
                this.spark = SparkSession.builder().master("local[2]").appName("KmeansModelJob").getOrCreate();
            }
        }

        public void execute(Object... params) throws Exception {
            this.df = this.spark.read().format("csv").option("header", "true").option("inferSchema", "true").load((String)this.parameters.get("csvData"));
            formatData();
            BisectingKMeans bkm = (new BisectingKMeans()).setK(Integer.parseInt((String)this.parameters.get("k"))).setMaxIter(Integer.parseInt((String)this.parameters.getOrDefault("maxIter", String.valueOf(5)))).setMinDivisibleClusterSize(Double.parseDouble((String)this.parameters.getOrDefault("MinDivisibleClusterSize", String.valueOf(1.0D)))).setSeed(1L);
            this.model = bkm.fit(this.df);
        }



        public void output(Object... params) throws Exception {
            this.model.write().overwrite().save((String)this.parameters.get("resultPath"));
            this.logger.info(this.debugString);
            this.spark.stop();
            this.spark.close();
        }


        void formatData() {
            this.df.createOrReplaceTempView("dft");
            this.df = this.spark.sql(String.format("SELECT * FROM dft", new Object[0]));
            String[] names = this.df.columns();
            VectorAssembler assembler1 = (new VectorAssembler()).setInputCols(names).setOutputCol("features");

            this.df = assembler1.transform(this.df);
        }
    }

