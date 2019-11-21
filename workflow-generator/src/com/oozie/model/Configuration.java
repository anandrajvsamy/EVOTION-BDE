package com.oozie.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "configuration", propOrder = {
	    "property"
	})
	public class Configuration {

	    @XmlElement(required = true)
	    protected List<Configuration.Property> property;

	    /**
	     * Gets the value of the property property.
	     * 
	     * <p>
	     * This accessor method returns a reference to the live list,
	     * not a snapshot. Therefore any modification you make to the
	     * returned list will be present inside the JAXB object.
	     * This is why there is not a <CODE>set</CODE> method for the property property.
	     * 
	     * <p>
	     * For example, to add a new item, do as follows:
	     * <pre>
	     *    getProperty().add(newItem);
	     * </pre>
	     * 
	     * 
	     * <p>
	     * Objects of the following type(s) are allowed in the list
	     * {@link CONFIGURATION.Property }
	     * 
	     * 
	     */
	    public List<Configuration.Property> getProperty() {
	        if (property == null) {
	            property = new ArrayList<Configuration.Property>();
	        }
	        return this.property;
	    }


	    /**
	     * <p>Java class for anonymous complex type.
	     * 
	     * <p>The following schema fragment specifies the expected content contained within this class.
	     * 
	     * <pre>
	     * &lt;complexType>
	     *   &lt;complexContent>
	     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	     *       &lt;sequence>
	     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
	     *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
	     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
	     *       &lt;/sequence>
	     *     &lt;/restriction>
	     *   &lt;/complexContent>
	     * &lt;/complexType>
	     * </pre>
	     * 
	     * 
	     */
	    @XmlAccessorType(XmlAccessType.FIELD)
	    @XmlType(name = "", propOrder = {
	        "name",
	        "description",
	        "value"
	    })
	    public static class Property {

	        @XmlElement(required = true)
	        protected String name;
	        @XmlElement(required = true)
	        protected String value;
	        protected String description;

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
	         * Gets the value of the value property.
	         * 
	         * @return
	         *     possible object is
	         *     {@link String }
	         *     
	         */
	        public String getValue() {
	            return value;
	        }

	        /**
	         * Sets the value of the value property.
	         * 
	         * @param value
	         *     allowed object is
	         *     {@link String }
	         *     
	         */
	        public void setValue(String value) {
	            this.value = value;
	        }

	        /**
	         * Gets the value of the description property.
	         * 
	         * @return
	         *     possible object is
	         *     {@link String }
	         *     
	         */
	        public String getDescription() {
	            return description;
	        }

	        /**
	         * Sets the value of the description property.
	         * 
	         * @param value
	         *     allowed object is
	         *     {@link String }
	         *     
	         */
	        public void setDescription(String value) {
	            this.description = value;
	        }

	    }
}
