package workflow.tasks;

public class Mean {
    public static void main(String[] args) throws Exception {
        MeanJob job = new MeanJob();
        job.init(args);
        job.execute(new Object[0]);
        job.output(new Object[0]);
    }
}