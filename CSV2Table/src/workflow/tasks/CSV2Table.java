package workflow.tasks;

public class CSV2Table extends CSV2TableJob {
    public static void main(String[] args) throws Exception {
        CSV2TableJob job = new CSV2TableJob();
        job.init(args);
        job.execute(new Object[0]);
        job.output(new Object[0]);
    }
}

