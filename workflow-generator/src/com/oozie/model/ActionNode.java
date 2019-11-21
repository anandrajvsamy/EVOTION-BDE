package com.oozie.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Action", propOrder = {
     "java",
     "spark",
    "ok",
    "error"
})

public class ActionNode {
	   
	    protected JavaAction java;
	    
	    protected SparkAction spark;
	    
	    @XmlElement(required = true)
	    protected ActionTransition ok;
	    
	    @XmlElement(required = true)
	    protected ActionTransition error;
	    
	    @XmlAttribute(name = "name", required = true)
	    protected String name;
	
	  
	    /**
	     * Gets the value of the JavaAction property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link JavaAction }
	     *     
	     */
	    public JavaAction getJava() {
	        return java;
	    }

	    /**
	     * Sets the value of the JavaAction property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link JavaAction }
	     *     
	     */
	    public void setJava(JavaAction value) {
	        this.java = value;
	    }

	    /**
	     * Gets the value of the ok property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link ActionTransition }
	     *     
	     */
	    public ActionTransition getOk() {
	        return ok;
	    }

	    /**
	     * Sets the value of the ok property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link ActionTransition }
	     *     
	     */
	    public void setOk(ActionTransition value) {
	        this.ok = value;
	    }

	    /**
	     * Gets the value of the error property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link ActionTransition }
	     *     
	     */
	    public ActionTransition getError() {
	        return error;
	    }

	    /**
	     * Sets the value of the error property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link ActionTransition }
	     *     
	     */
	    public void setError(ActionTransition value) {
	        this.error = value;
	    }

	    /**
	     * Gets the value of the any property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link Object }
	     *     
	     */
	   

	    /**
	     * Sets the value of the any property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link Object }
	     *     
	     */
	   

	    /**
	     * Gets the value of the name property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link String }
	     *     
	     */
	  
	    /**
	     * Sets the value of the name property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link String }
	     *     
	  

	    /**
	     * Gets the value of the cred property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link String }
	     *     
	     */
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

		/**
		 * @return the spark
		 */
		public SparkAction getSpark() {
			return spark;
		}

		/**
		 * @param spark the spark to set
		 */
		public void setSpark(SparkAction spark) {
			this.spark = spark;
		}
	  
	  
}

