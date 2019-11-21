package com.oozie.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Prepare", propOrder = {
    "delete",
    "mkdir"
})

public class Prepare {
	   protected List<Delete> delete;
	    protected List<Mkdir> mkdir;

	    /**
	     * Gets the value of the delete property.
	     * 
	     * <p>
	     * This accessor method returns a reference to the live list,
	     * not a snapshot. Therefore any modification you make to the
	     * returned list will be present inside the JAXB object.
	     * This is why there is not a <CODE>set</CODE> method for the delete property.
	     * 
	     * <p>
	     * For example, to add a new item, do as follows:
	     * <pre>
	     *    getDelete().add(newItem);
	     * </pre>
	     * 
	     * 
	     * <p>
	     * Objects of the following type(s) are allowed in the list
	     * {@link Delete }
	     * 
	     * 
	     */
	    public List<Delete> getDelete() {
	        if (delete == null) {
	            delete = new ArrayList<Delete>();
	        }
	        return this.delete;
	    }

	    /**
	     * Gets the value of the mkdir property.
	     * 
	     * <p>
	     * This accessor method returns a reference to the live list,
	     * not a snapshot. Therefore any modification you make to the
	     * returned list will be present inside the JAXB object.
	     * This is why there is not a <CODE>set</CODE> method for the mkdir property.
	     * 
	     * <p>
	     * For example, to add a new item, do as follows:
	     * <pre>
	     *    getMkdir().add(newItem);
	     * </pre>
	     * 
	     * 
	     * <p>
	     * Objects of the following type(s) are allowed in the list
	     * {@link Mkdir}
	     * 
	     * 
	     */
	    public List<Mkdir> getMkdir() {
	        if (mkdir == null) {
	            mkdir = new ArrayList<Mkdir>();
	        }
	        return this.mkdir;
	    }

	}
