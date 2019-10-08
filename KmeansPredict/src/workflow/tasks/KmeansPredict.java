package workflow.tasks;

public class KmeansPredict {
    public static void main(String[] args) throws Exception {
            KmeansPredictJob job = new KmeansPredictJob();
            job.init(args);
            job.execute(new Object[0]);
            job.output(new Object[0]);
        }
    }

