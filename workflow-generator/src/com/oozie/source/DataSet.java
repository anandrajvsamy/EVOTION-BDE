
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
    "dsetID",
    "datID",
    "avparamID",
    "inOutput"
})
public class DataSet {

    @Override
	public String toString() {
		return "DataSet [dsetID=" + dsetID + ", datID=" + datID + ", avparamID=" + avparamID + ", inOutput=" + inOutput
				+ ", additionalProperties=" + additionalProperties + "]";
	}

	@JsonProperty("dsetID")
    private Integer dsetID;
    @JsonProperty("datID")
    private Integer datID;
    @JsonProperty("avparamID")
    private Integer avparamID;
    @JsonProperty("inOutput")
    private Boolean inOutput;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("dsetID")
    public Integer getDsetID() {
        return dsetID;
    }

    @JsonProperty("dsetID")
    public void setDsetID(Integer dsetID) {
        this.dsetID = dsetID;
    }

    @JsonProperty("datID")
    public Integer getDatID() {
        return datID;
    }

    @JsonProperty("datID")
    public void setDatID(Integer datID) {
        this.datID = datID;
    }

    @JsonProperty("avparamID")
    public Integer getAvparamID() {
        return avparamID;
    }

    @JsonProperty("avparamID")
    public void setAvparamID(Integer avparamID) {
        this.avparamID = avparamID;
    }

    @JsonProperty("inOutput")
    public Boolean getInOutput() {
        return inOutput;
    }

    @JsonProperty("inOutput")
    public void setInOutput(Boolean inOutput) {
        this.inOutput = inOutput;
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
