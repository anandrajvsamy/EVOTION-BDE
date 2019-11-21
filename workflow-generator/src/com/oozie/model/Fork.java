package com.oozie.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FORK", propOrder = {
    "path"
})

public class Fork {
	   @XmlElement(required = true)
	    protected List<ForkTransition> path;
	    @XmlAttribute(name = "name", required = true)
	    protected String name;

	    /**
		 * @param path the path to set
		 */
		public void setPath(List<ForkTransition> path) {
			this.path = path;
		}

		/**
	     * Gets the value of the path property.
	     * 
	     * <p>
	     * This accessor method returns a reference to the live list,
	     * not a snapshot. Therefore any modification you make to the
	     * returned list will be present inside the JAXB object.
	     * This is why there is not a <CODE>set</CODE> method for the path property.
	     * 
	     * <p>
	     * For example, to add a new item, do as follows:
	     * <pre>
	     *    getPath().add(newItem);
	     * </pre>
	     * 
	     * 
	     * <p>
	     * Objects of the following type(s) are allowed in the list
	     * {@link FORKTRANSITION }
	     * 
	     * 
	     */
	    public List<ForkTransition> getPath() {
	        if (path == null) {
	            path = new ArrayList<ForkTransition>();
	        }
	        return this.path;
	    }

	    /**
	     * Gets the value of the name property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link String }
	     *     
	     */
	    public String getName() {
	        return name;
	    }

	    /**
	     * Sets the value of the name property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link String }
	     *     
	     */
	    public void setName(String value) {
	        this.name = value;
	    }

	}
