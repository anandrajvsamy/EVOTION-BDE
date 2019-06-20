package workflow.tasks;

public interface Job {
    void init(Object... varArgs) throws Exception;

    void execute(Object... varArgs) throws Exception;

    void output(Object... varArgs) throws Exception;
}


