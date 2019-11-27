
package com.oozie.source;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tParamID",
    "datID",
    "paramID",
    "paramValue",
    "operator",
    "paramName"
})
public class TaskParam {

    @Override
	public String toString() {
		return "TaskParam [tParamID=" + tParamID + ", datID=" + datID + ", paramID=" + paramID + ", paramValue="
				+ paramValue + ", operator=" + operator + ", paramName=" + paramName + ", additionalProperties="
				+ additionalProperties + "]";
	}

	@JsonProperty("tParamID")
    private Integer tParamID;
    @JsonProperty("datID")
    private Integer datID;
    @JsonProperty("paramID")
    private Integer paramID;
    @JsonProperty("paramValue")
    private String paramValue;
    @JsonProperty("operator")
    private String operator;
    @JsonProperty("paramName")
    private String paramName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("tParamID")
    public Integer getTParamID() {
        return tParamID;
    }

    @JsonProperty("tParamID")
    public void setTParamID(Integer tParamID) {
        this.tParamID = tParamID;
    }

    @JsonProperty("datID")
    public Integer getDatID() {
        return datID;
    }

    @JsonProperty("datID")
    public void setDatID(Integer datID) {
        this.datID = datID;
    }

    @JsonProperty("paramID")
    public Integer getParamID() {
        return paramID;
    }

    @JsonProperty("paramID")
    public void setParamID(Integer paramID) {
        this.paramID = paramID;
    }

    @JsonProperty("paramValue")
    public String getParamValue() {
        return paramValue;
    }

    @JsonProperty("paramValue")
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @JsonProperty("operator")
    public String getOperator() {
        return operator;
    }

    @JsonProperty("operator")
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @JsonProperty("paramName")
    public String getParamName() {
        return paramName;
    }

    @JsonProperty("paramName")
    public void setParamName(String paramName) {
        this.paramName = paramName;
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
