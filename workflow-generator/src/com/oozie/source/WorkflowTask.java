
package com.oozie.source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "datID", "workflowID", "dattID", "methodID", "depvarID", "datName", "created", "modified",
		"datOutput", "dataSet", "avDataSetList", "methodParams", "taskParams" })
public class WorkflowTask {

	@JsonProperty("datID")
	private Integer datID;
	@JsonProperty("workflowID")
	private Integer workflowID;
	@JsonProperty("dattID")
	private Integer dattID;
	@JsonProperty("methodID")
	private Integer methodID;
	@JsonProperty("depvarID")
	private Integer depvarID;
	@JsonProperty("datName")
	private String datName;
	@JsonProperty("created")
	private Long created;
	@JsonProperty("modified")
	private Object modified;
	@JsonProperty("datOutput")
	private String datOutput;
	@JsonProperty("dataSet")
	private List<DataSet> dataSet = null;
	@JsonProperty("avDataSetList")
	private List<AvDataSetList> avDataSetList = null;
	@JsonProperty("methodParams")
	private List<MethodParam> methodParams = null;
	@JsonProperty("taskParams")
	private List<TaskParam> taskParams = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("datID")
	public Integer getDatID() {
		return datID;
	}

	@JsonProperty("datID")
	public void setDatID(Integer datID) {
		this.datID = datID;
	}

	@JsonProperty("workflowID")
	public Integer getWorkflowID() {
		return workflowID;
	}

	@JsonProperty("workflowID")
	public void setWorkflowID(Integer workflowID) {
		this.workflowID = workflowID;
	}

	@JsonProperty("dattID")
	public Integer getDattID() {
		return dattID;
	}

	@JsonProperty("dattID")
	public void setDattID(Integer dattID) {
		this.dattID = dattID;
	}

	@JsonProperty("methodID")
	public Integer getMethodID() {
		return methodID;
	}

	@JsonProperty("methodID")
	public void setMethodID(Integer methodID) {
		this.methodID = methodID;
	}

	@JsonProperty("depvarID")
	public Integer getDepvarID() {
		return depvarID;
	}

	@JsonProperty("depvarID")
	public void setDepvarID(Integer depvarID) {
		this.depvarID = depvarID;
	}

	@JsonProperty("datName")
	public String getDatName() {
		return datName;
	}

	@JsonProperty("datName")
	public void setDatName(String datName) {
		this.datName = datName;
	}

	@JsonProperty("created")
	public Long getCreated() {
		return created;
	}

	@JsonProperty("created")
	public void setCreated(Long created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "WorkflowTask [datID=" + datID + ", workflowID=" + workflowID + ", dattID=" + dattID + ", methodID="
				+ methodID + ", depvarID=" + depvarID + ", datName=" + datName + ", created=" + created + ", modified="
				+ modified + ", datOutput=" + datOutput + ", dataSet=" + dataSet + ", avDataSetList=" + avDataSetList
				+ ", methodParams=" + methodParams + ", taskParams=" + taskParams + ", additionalProperties="
				+ additionalProperties + "]";
	}

	@JsonProperty("modified")
	public Object getModified() {
		return modified;
	}

	@JsonProperty("modified")
	public void setModified(Object modified) {
		this.modified = modified;
	}

	@JsonProperty("datOutput")
	public String getDatOutput() {
		return datOutput;
	}

	@JsonProperty("datOutput")
	public void setDatOutput(String datOutput) {
		this.datOutput = datOutput;
	}

	@JsonProperty("dataSet")
	public List<DataSet> getDataSet() {
		return dataSet;
	}

	@JsonProperty("dataSet")
	public void setDataSet(List<DataSet> dataSet) {
		this.dataSet = dataSet;
	}

	@JsonProperty("avDataSetList")
	public List<AvDataSetList> getAvDataSetList() {
		return avDataSetList;
	}

	@JsonProperty("avDataSetList")
	public void setAvDataSetList(List<AvDataSetList> avDataSetList) {
		this.avDataSetList = avDataSetList;
	}

	@JsonProperty("methodParams")
	public List<MethodParam> getMethodParams() {
		return methodParams;
	}

	@JsonProperty("methodParams")
	public void setMethodParams(List<MethodParam> methodParams) {
		this.methodParams = methodParams;
	}

	@JsonProperty("taskParams")
	public List<TaskParam> getTaskParams() {
		return taskParams;
	}

	@JsonProperty("taskParams")
	public void setTaskParams(List<TaskParam> taskParams) {
		this.taskParams = taskParams;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public boolean equals(Object o) {

		/*
		 * if(this == o) { return true; }
		 */

		WorkflowTask workflowTask = (WorkflowTask) o;

		if (datOutput == null || workflowTask.datName == null) {
			return false;
		}

		if (datOutput.equals(workflowTask.datName)) {
			return true;
		}
		return false;
	}
}
