package workflow.tasks;

public class LinearRegModel {
    public static void main(String[] args) throws Exception {
        LinearRegModelJob job = new LinearRegModelJob();
        job.init(args);
        job.execute(new Object[0]);
        job.output(new Object[0]);
    }
}
