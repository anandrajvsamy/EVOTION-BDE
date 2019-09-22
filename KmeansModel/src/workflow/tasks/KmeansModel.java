package workflow.tasks;

public class KmeansModel {
    public static void main(String[] args) throws Exception {
        KmeansModelJob job = new KmeansModelJob();
        job.init(args);
        job.execute(new Object[0]);
        job.output(new Object[0]);
    }
}