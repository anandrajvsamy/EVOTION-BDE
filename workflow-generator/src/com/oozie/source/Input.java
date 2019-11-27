
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
@JsonPropertyOrder({
    "workflowID",
    "userID",
    "created",
    "modified",
    "statusID",
    "etype",
    "workflowname",
    "start",
    "duration",
    "durationperiodID",
    "repeat",
    "repeatperiodID",
    "workflowTasks"
})
public class Input {

    @JsonProperty("workflowID")
    private Integer workflowID;
    @JsonProperty("userID")
    private Integer userID;
    @JsonProperty("created")
    private Long created;
    @JsonProperty("modified")
    private Object modified;
    @JsonProperty("statusID")
    private Integer statusID;
    @JsonProperty("etype")
    private Integer etype;
    @JsonProperty("workflowname")
    private String workflowname;
    @JsonProperty("start")
    private Object start;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("durationperiodID")
    private Integer durationperiodID;
    @JsonProperty("repeat")
    private Integer repeat;
    @JsonProperty("repeatperiodID")
    private Integer repeatperiodID;
    @JsonProperty("workflowTasks")
    private List<WorkflowTask> workflowTasks = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("workflowID")
    public Integer getWorkflowID() {
        return workflowID;
    }

    @JsonProperty("workflowID")
    public void setWorkflowID(Integer workflowID) {
        this.workflowID = workflowID;
    }

    @JsonProperty("userID")
    public Integer getUserID() {
        return userID;
    }

    @JsonProperty("userID")
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @JsonProperty("created")
    public Long getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(Long created) {
        this.created = created;
    }

    @JsonProperty("modified")
    public Object getModified() {
        return modified;
    }

    @JsonProperty("modified")
    public void setModified(Object modified) {
        this.modified = modified;
    }

    @JsonProperty("statusID")
    public Integer getStatusID() {
        return statusID;
    }

    @JsonProperty("statusID")
    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    @JsonProperty("etype")
    public Integer getEtype() {
        return etype;
    }

    @JsonProperty("etype")
    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    @JsonProperty("workflowname")
    public String getWorkflowname() {
        return workflowname;
    }

    @JsonProperty("workflowname")
    public void setWorkflowname(String workflowname) {
        this.workflowname = workflowname;
    }

    @JsonProperty("start")
    public Object getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Object start) {
        this.start = start;
    }

    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @JsonProperty("durationperiodID")
    public Integer getDurationperiodID() {
        return durationperiodID;
    }

    @JsonProperty("durationperiodID")
    public void setDurationperiodID(Integer durationperiodID) {
        this.durationperiodID = durationperiodID;
    }

    @JsonProperty("repeat")
    public Integer getRepeat() {
        return repeat;
    }

    @JsonProperty("repeat")
    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    @JsonProperty("repeatperiodID")
    public Integer getRepeatperiodID() {
        return repeatperiodID;
    }

    @JsonProperty("repeatperiodID")
    public void setRepeatperiodID(Integer repeatperiodID) {
        this.repeatperiodID = repeatperiodID;
    }

    @JsonProperty("workflowTasks")
    public List<WorkflowTask> getWorkflowTasks() {
        return workflowTasks;
    }

    @JsonProperty("workflowTasks")
    public void setWorkflowTasks(List<WorkflowTask> workflowTasks) {
        this.workflowTasks = workflowTasks;
    }

    @Override
	public String toString() {
		return "Input [workflowID=" + workflowID + ", userID=" + userID + ", created=" + created + ", modified="
				+ modified + ", statusID=" + statusID + ", etype=" + etype + ", workflowname=" + workflowname
				+ ", start=" + start + ", duration=" + duration + ", durationperiodID=" + durationperiodID + ", repeat="
				+ repeat + ", repeatperiodID=" + repeatperiodID + ", workflowTasks=" + workflowTasks
				+ ", additionalProperties=" + additionalProperties + "]";
	}

	@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
