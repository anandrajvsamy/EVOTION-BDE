package workflow.tasks;

public interface Job {
    void init(Object... paramVarArgs) throws Exception;

    void execute(Object... paramVarArgs) throws Exception;

    void output(Object... paramVarArgs) throws Exception;
}
