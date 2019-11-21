
package com.oozie.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="workflow-app")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "start", "decisionOrForkOrJoin", "end",
})
public class WorkFlowApp {
	// protected Credentials credentials;
	@XmlElement(required = true)

	protected Start start;

	@XmlElements({ @XmlElement(name = "decision", type = Decision.class), @XmlElement(name = "fork", type = Fork.class),
			@XmlElement(name = "join", type = Join.class), @XmlElement(name = "kill", type = Kill.class),
			@XmlElement(name = "action", type = ActionNode.class) })
	protected List<Object> decisionOrForkOrJoin;

	@XmlElement(required = true)
	protected End end;

	@XmlAttribute(name = "name", required = true)
	protected String name;

	/**
	 * Gets the value of the credentials property.
	 * 
	 * @return possible object is {@link Credentials }
	 * 
	 */
	/*
	 * public Credentials getCredentials() { return credentials; }
	 */

	/**
	 * Sets the value of the credentials property.
	 * 
	 * @param value allowed object is {@link CREDENTIALS }
	 * 
	 */
	/*
	 * public void setCredentials(Credentials value) { this.credentials = value; }
	 */

	/**
	 * Gets the value of the start property.
	 * 
	 * @return possible object is {@link Start }
	 * 
	 */
	public Start getStart() {
		return start;
	}

	/**
	 * Sets the value of the start property.
	 * 
	 * @param value allowed object is {@link Start }
	 * 
	 */
	public void setStart(Start value) {
		this.start = value;
	}

	/**
	 * Gets the value of the decisionOrForkOrJoin property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the decisionOrForkOrJoin property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getDecisionOrForkOrJoin().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Decision }
	 * {@link Fork } {@link Join } {@link Kill } {@link Action }
	 * 
	 * 
	 */
//	    public List<Object> getDecisionOrForkOrJoin() {
//	        if (decisionOrForkOrJoin == null) {
//	            decisionOrForkOrJoin = new ArrayList<Object>();
//	        }
//	        return this.decisionOrForkOrJoin;
//	    }

	/**
	 * Gets the value of the end property.
	 * 
	 * @return possible object is {@link End }
	 * 
	 */
	public End getEnd() {
		return end;
	}

	/**
	 * Sets the value of the end property.
	 * 
	 * @param value allowed object is {@link End }
	 * 
	 */
	public void setEnd(End value) {
		this.end = value;
	}

	/**
	 * Gets the value of the any property.
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	/*
	 * public Object getAny() { return any; }
	 */

	/**
	 * Sets the value of the any property.
	 * 
	 * @param value allowed object is {@link Object }
	 * 
	 */
	/*
	 * public void setAny(Object value) { this.any = value; }
	 */

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the decisionOrForkOrJoin property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the decisionOrForkOrJoin property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getDecisionOrForkOrJoin().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Decision }
	 * {@link Fork } {@link Join } {@link Kill } {@link Action }
	 * 
	 * 
	 */
	public List<Object> getDecisionOrForkOrJoin() {
		if (decisionOrForkOrJoin == null) {
			decisionOrForkOrJoin = new ArrayList<Object>();
		}
		return this.decisionOrForkOrJoin;
	}

}
