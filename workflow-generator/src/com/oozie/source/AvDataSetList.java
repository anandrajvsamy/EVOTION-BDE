
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
    "avparamid",
    "avdsetid",
    "paramname",
    "tableName",
    "createdByDataskID",
    "description",
    "typeID"
})
public class AvDataSetList {

    @Override
	public String toString() {
		return "AvDataSetList [avparamid=" + avparamid + ", avdsetid=" + avdsetid + ", paramname=" + paramname
				+ ", tableName=" + tableName + ", createdByDataskID=" + createdByDataskID + ", description="
				+ description + ", typeID=" + typeID + ", additionalProperties=" + additionalProperties + "]";
	}

	@JsonProperty("avparamid")
    private Integer avparamid;
    @JsonProperty("avdsetid")
    private Integer avdsetid;
    @JsonProperty("paramname")
    private String paramname;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("createdByDataskID")
    private Integer createdByDataskID;
    @JsonProperty("description")
    private String description;
    @JsonProperty("typeID")
    private Integer typeID;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("avparamid")
    public Integer getAvparamid() {
        return avparamid;
    }

    @JsonProperty("avparamid")
    public void setAvparamid(Integer avparamid) {
        this.avparamid = avparamid;
    }

    @JsonProperty("avdsetid")
    public Integer getAvdsetid() {
        return avdsetid;
    }

    @JsonProperty("avdsetid")
    public void setAvdsetid(Integer avdsetid) {
        this.avdsetid = avdsetid;
    }

    @JsonProperty("paramname")
    public String getParamname() {
        return paramname;
    }

    @JsonProperty("paramname")
    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    @JsonProperty("tableName")
    public String getTableName() {
        return tableName;
    }

    @JsonProperty("tableName")
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @JsonProperty("createdByDataskID")
    public Integer getCreatedByDataskID() {
        return createdByDataskID;
    }

    @JsonProperty("createdByDataskID")
    public void setCreatedByDataskID(Integer createdByDataskID) {
        this.createdByDataskID = createdByDataskID;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("typeID")
    public Integer getTypeID() {
        return typeID;
    }

    @JsonProperty("typeID")
    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
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
