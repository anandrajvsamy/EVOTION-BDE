package com.oozie.source;

import java.util.List;

public class JobInfo {

	private String workflowName;
	private int dataId;
	private String startId;
	public int getDatId() {
		return dataId;
	}

	public void setDatId(int dataId) {
		this.dataId = dataId;
	}

	private String actionName;
	private String jobTracker;
	private String nameNode;
	private String SparkMaster;
	private String jobName;
	private String jobClass;
	private String jobJar;
	private List<String> args;
	private String nextJob;
	private String endId;
	private String ok;
	private String end;
	private List<String> forklist;
	public List<String> getForklist() {
		return forklist;
	}
	private String fail;

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public void setForklist(List<String> forklist) {
		this.forklist = forklist;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public String getStartId() {
		return startId;
	}

	public void setStartId(String startId) {
		this.startId = startId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getJobTracker() {
		return jobTracker;
	}

	public void setJobTracker(String jobTracker) {
		this.jobTracker = jobTracker;
	}

	public String getNameNode() {
		return nameNode;
	}

	public void setNameNode(String nameNode) {
		this.nameNode = nameNode;
	}

	public String getSparkMaster() {
		return SparkMaster;
	}

	public void setSparkMaster(String sparkMaster) {
		SparkMaster = sparkMaster;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobJar() {
		return jobJar;
	}

	public void setJobJar(String jobJar) {
		this.jobJar = jobJar;
	}

	public List<String> getArgs() {
		return args;
	}

	public void setArgs(List<String> args) {
		this.args = args;
	}

	public String getNextJob() {
		return nextJob;
	}

	public void setNextJob(String nextJob) {
		this.nextJob = nextJob;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

}
