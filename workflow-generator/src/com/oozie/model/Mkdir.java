package com.oozie.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mkdir")

public class Mkdir {
	  @XmlAttribute(name = "path", required = true)
	    protected String path;

	    /**
	     * Gets the value of the path property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link String }
	     *     
	     */
	    public String getPath() {
	        return path;
	    }

	    /**
	     * Sets the value of the path property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link String }
	     *     
	     */
	    public void setPath(String value) {
	        this.path = value;
	    }

	}