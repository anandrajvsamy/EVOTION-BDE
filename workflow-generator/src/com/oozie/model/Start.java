package com.oozie.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "start")
public class Start {	

	/**
	 * 
	 */
	@XmlAttribute(name="to",required = true)
	protected String to;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	} 
	
	
}
