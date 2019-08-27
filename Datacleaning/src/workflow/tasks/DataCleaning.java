package workflow.tasks;

public class DataCleaning {

    public static void main(String[] args) throws Exception {
        DataCleaningJob job = new DataCleaningJob();
        job.init(args);
        job.execute(new Object[0]);
        job.output(new Object[0]);
    }
}
