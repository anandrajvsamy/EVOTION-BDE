package com.oozie.workflow;

import java.util.ArrayList;
import java.util.List;

import com.oozie.model.ActionNode;
import com.oozie.model.ActionTransition;
import com.oozie.model.End;
import com.oozie.model.Fork;
import com.oozie.model.ForkTransition;
import com.oozie.model.JavaAction;
import com.oozie.model.Join;
import com.oozie.model.Kill;
import com.oozie.model.SparkAction;
import com.oozie.model.Start;
import com.oozie.model.WorkFlowApp;
import com.oozie.source.TaskList;
import com.oozie.util.Constants;
import com.oozie.util.OozieProp;

public class WorkFlowGenerator {

	OozieNodeFactory oozieNodeFactory;
	WorkFlowApp workFlowApp;

	public WorkFlowGenerator() {
		oozieNodeFactory = new OozieNodeFactory();
		workFlowApp = new OozieNodeFactory().createWorkFlowApp();
	}

	public SparkAction getOzzieWorkFazctory() {
		if (oozieNodeFactory == null) {
			return new OozieNodeFactory().createSparkAction();
		} else
			return oozieNodeFactory.createSparkAction();
	}

	public WorkFlowApp getWorkFlowApp() {
		return workFlowApp;
	}

	public void addFork(List<String> jobNames) {
		Fork fork = oozieNodeFactory.createFork();

		fork.setName("forking");
		List<ForkTransition> forkTransitions = new ArrayList<ForkTransition>();

		for (String jobName : jobNames) {
			ForkTransition forkTransition = new ForkTransition();
			forkTransition.setStart(jobName);
			forkTransitions.add(forkTransition);
		}
		fork.setPath(forkTransitions);
		workFlowApp.getDecisionOrForkOrJoin().add(fork);
	}

	public void addFork(String jobName, List<String> jobs) {
		Fork fork = oozieNodeFactory.createFork();

		fork.setName(jobName);
		List<ForkTransition> forkTransitions = new ArrayList<ForkTransition>();

		for (String forkjobs : jobs) {
			ForkTransition forktransition = new ForkTransition();
			forktransition.setStart(forkjobs);
			forkTransitions.add(forktransition);
		}

		ForkTransition forkTransition = new ForkTransition();
		forkTransition.setStart(jobName);
		fork.setPath(forkTransitions);
		workFlowApp.getDecisionOrForkOrJoin().add(fork);
	}

	/**
	 * Add join node
	 */
	public void addJoin(String JobName, String end) {
		Join join = oozieNodeFactory.createJoin();
		join.setName(JobName);
		join.setTo(end);
		workFlowApp.getDecisionOrForkOrJoin().add(join);
	}

	/**
	 * Kill node
	 */
	public void addKill() {
		Kill kill = oozieNodeFactory.createKill();
		kill.setName("fail");
		kill.setMessage("Java failed, error message[${wf:errorMessage(wf:lastErrorNode())}]");
		workFlowApp.getDecisionOrForkOrJoin().add(kill);
	}

	/**
	 * Method to add java action
	 */
	public void addJavaAction() {
		ActionNode action = oozieNodeFactory.createActionNode();
		action.setName("java");
		JavaAction javaAction = oozieNodeFactory.createJavaAction();
		javaAction.setNameSpace(OozieProp.Getproperties().getProperty(Constants.OOZIE_NAMESPACE));
		javaAction.setJobTracker(OozieProp.Getproperties().getProperty(Constants.JOB_TRACKER));
		javaAction.setNameNode(OozieProp.Getproperties().getProperty(Constants.NAME_NODE));
		setOkTransition(action, "new");
		setFailTransition(action, "Kill");
		action.setJava(javaAction);
		workFlowApp.getDecisionOrForkOrJoin().add(action);
	}

	/**
	 * Method to add spark actions
	 * 
	 * @param jobNames
	 */
	public void addSparkAction(List<String> jobNames) {
		for (String jobName : jobNames) {
			ActionNode action = oozieNodeFactory.createActionNode();
			action.setName(jobName);
			SparkAction sparkAction = oozieNodeFactory.createSparkAction();

			sparkAction.setJobTracker(OozieProp.Getproperties().getProperty(Constants.JOB_TRACKER));
			sparkAction.setNameNode(OozieProp.Getproperties().getProperty(Constants.NAME_NODE));
			sparkAction.setMaster(OozieProp.Getproperties().getProperty(Constants.SPARK_MASTER));

			sparkAction.setName("SparkDynamicOozieWorkFlow");
			sparkAction.setClassName("<spark main class name >");
			sparkAction.setJarName("<Spark main class jar name>");
			sparkAction.setSparkOpts("--jars <hdfs jar location> --num-executors 384");
			List<String> arguments = new ArrayList<String>();
			arguments.add("<argument one>");
			arguments.add("<argument two>");
			sparkAction.setArgument(arguments);
			setOkTransition(action, "joining");
			setFailTransition(action, "fail");
			action.setSpark(sparkAction);
			workFlowApp.getDecisionOrForkOrJoin().add(action);
		}

	}

	/**
	 * Method to add spark action
	 * 
	 * @param jobName
	 */
	public void addSparkAction(TaskList tasklist) {
		try {
			ActionNode action = oozieNodeFactory.createActionNode();
			action.setName(tasklist.getNodeName());
			SparkAction sparkAction = oozieNodeFactory.createSparkAction();
			sparkAction.setJobTracker(OozieProp.Getproperties().getProperty(Constants.JOB_TRACKER));
			sparkAction.setNameNode(OozieProp.Getproperties().getProperty(Constants.NAME_NODE));
			sparkAction.setMaster(OozieProp.Getproperties().getProperty(Constants.SPARK_MASTER));
			sparkAction.setNameSpace("uri:oozie:spark-action:0.1");
			sparkAction.setName(tasklist.getNodeName());
			sparkAction.setClassName(tasklist.getClassName());
			sparkAction.setJarName(tasklist.getJar());
			sparkAction.setSparkOpts(tasklist.getSparkOpts());
			sparkAction.setArgument(tasklist.getArgs());
			setOkTransition(action, tasklist.getEnd());
			setFailTransition(action, tasklist.getFail());
			action.setSpark(sparkAction);
			workFlowApp.getDecisionOrForkOrJoin().add(action);
		} catch (Exception e) {
			System.out.println("issue in addSpark");
		}

	}

	public void addEndNode(String name) {
		End endNode = oozieNodeFactory.createEnd();
		endNode.setName(name);
		workFlowApp.setEnd(endNode);
	}

	/**
	 * Method top add start node
	 * 
 * @param startNodeName
	 */
	public void addStartNode(String startNodeName) {
		Start start = oozieNodeFactory.createStart();
		start.setTo(startNodeName);
		workFlowApp.setStart(start);
	}

	/**
	 * 
	 * @param act
	 * @param nodeName
	 */
	public void setOkTransition(ActionNode act, String nodeName) {
		ActionTransition okCallWfTrans = oozieNodeFactory.createActionTransition();
		okCallWfTrans.setTo(nodeName);
		act.setOk(okCallWfTrans);
	}

	/**
	 * 
	 * @param act
	 * @param nodeName
	 */
	public void setFailTransition(ActionNode act, String nodeName) {
		ActionTransition errorCallWfTrans = oozieNodeFactory.createActionTransition();
		errorCallWfTrans.setTo(nodeName);
		act.setError(errorCallWfTrans);
	}

}
