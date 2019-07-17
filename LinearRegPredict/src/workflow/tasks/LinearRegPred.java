package workflow.tasks;

public class LinearRegPred {
    public static void main(String[] args) throws Exception {
        LinearRegPredJob job = new LinearRegPredJob();
        job.init(args);
        job.execute(new Object[0]);
        job.output(new Object[0]);
    }
}

