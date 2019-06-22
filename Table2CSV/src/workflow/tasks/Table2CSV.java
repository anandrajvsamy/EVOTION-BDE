package workflow.tasks;

public class Table2CSV extends Table2CSVJob
{
    public static void main(String[] args) throws Exception {
        Table2CSVJob job = new Table2CSVJob();
        job.init(args);
        job.execute(new Object[0]);
        job.output(new Object[0]);
    }
}
