
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
    "mparamID",
    "methodID",
    "paramLabel",
    "paramName",
    "paramTypeID",
    "parammin",
    "parammax",
    "paramDefaultValue",
    "paramArrayValues"
})
public class MethodParam {

    @Override
	public String toString() {
		return "MethodParam [mparamID=" + mparamID + ", methodID=" + methodID + ", paramLabel=" + paramLabel
				+ ", paramName=" + paramName + ", paramTypeID=" + paramTypeID + ", parammin=" + parammin + ", parammax="
				+ parammax + ", paramDefaultValue=" + paramDefaultValue + ", paramArrayValues=" + paramArrayValues
				+ ", additionalProperties=" + additionalProperties + "]";
	}

	@JsonProperty("mparamID")
    private Integer mparamID;
    @JsonProperty("methodID")
    private Integer methodID;
    @JsonProperty("paramLabel")
    private String paramLabel;
    @JsonProperty("paramName")
    private String paramName;
    @JsonProperty("paramTypeID")
    private Integer paramTypeID;
    @JsonProperty("parammin")
    private Integer parammin;
    @JsonProperty("parammax")
    private Integer parammax;
    @JsonProperty("paramDefaultValue")
    private Object paramDefaultValue;
    @JsonProperty("paramArrayValues")
    private Object paramArrayValues;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("mparamID")
    public Integer getMparamID() {
        return mparamID;
    }

    @JsonProperty("mparamID")
    public void setMparamID(Integer mparamID) {
        this.mparamID = mparamID;
    }

    @JsonProperty("methodID")
    public Integer getMethodID() {
        return methodID;
    }

    @JsonProperty("methodID")
    public void setMethodID(Integer methodID) {
        this.methodID = methodID;
    }

    @JsonProperty("paramLabel")
    public String getParamLabel() {
        return paramLabel;
    }

    @JsonProperty("paramLabel")
    public void setParamLabel(String paramLabel) {
        this.paramLabel = paramLabel;
    }

    @JsonProperty("paramName")
    public String getParamName() {
        return paramName;
    }

    @JsonProperty("paramName")
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @JsonProperty("paramTypeID")
    public Integer getParamTypeID() {
        return paramTypeID;
    }

    @JsonProperty("paramTypeID")
    public void setParamTypeID(Integer paramTypeID) {
        this.paramTypeID = paramTypeID;
    }

    @JsonProperty("parammin")
    public Integer getParammin() {
        return parammin;
    }

    @JsonProperty("parammin")
    public void setParammin(Integer parammin) {
        this.parammin = parammin;
    }

    @JsonProperty("parammax")
    public Integer getParammax() {
        return parammax;
    }

    @JsonProperty("parammax")
    public void setParammax(Integer parammax) {
        this.parammax = parammax;
    }

    @JsonProperty("paramDefaultValue")
    public Object getParamDefaultValue() {
        return paramDefaultValue;
    }

    @JsonProperty("paramDefaultValue")
    public void setParamDefaultValue(Object paramDefaultValue) {
        this.paramDefaultValue = paramDefaultValue;
    }

    @JsonProperty("paramArrayValues")
    public Object getParamArrayValues() {
        return paramArrayValues;
    }

    @JsonProperty("paramArrayValues")
    public void setParamArrayValues(Object paramArrayValues) {
        this.paramArrayValues = paramArrayValues;
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
