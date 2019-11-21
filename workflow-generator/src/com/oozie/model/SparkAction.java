package com.oozie.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "spark", propOrder = { "jobTracker", "nameNode", "prepare",
"master", "name", "className", "jarName","sparkOpts","argument"})
public class SparkAction {
	@XmlElement(name = "job-tracker", required = true)
	protected String jobTracker;
	@XmlElement(name = "name-node", required = true)
	protected String nameNode;
	@XmlElement(name = "prepare")
	protected Prepare prepare;
	@XmlAttribute(name = "xmlns", required = true)
	protected String nameSpace;

	@XmlElement(name = "master")
	protected String master;

	@XmlElement(name = "name")
	protected String name;

	@XmlElement(name = "class")
	protected String className;

	@XmlElement(name = "jar")
	protected String jarName;
	
	@XmlElement(name = "spark-opts")
	protected String sparkOpts;
	
	@XmlElement(name = "arg")
	protected List<String> argument;	
	
	
	

	/**
	 * Gets the value of the jobTracker property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getJobTracker() {
		return jobTracker;
	}

	/**
	 * Sets the value of the jobTracker property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setJobTracker(String value) {
		this.jobTracker = value;
	}

	/**
	 * Gets the value of the nameNode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNameNode() {
		return nameNode;
	}

	/**
	 * Sets the value of the nameNode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNameNode(String value) {
		this.nameNode = value;
	}

	/**
	 * Gets the value of the prepare property.
	 * 
	 * @return possible object is {@link Prepare }
	 * 
	 */
	public Prepare getPrepare() {
		return prepare;
	}

	/**
	 * Sets the value of the prepare property.
	 * 
	 * @param value
	 *            allowed object is {@link Prepare }
	 * 
	 */
	public void setPrepare(Prepare value) {
		this.prepare = value;
	}

	/**
	 * @return the master
	 */
	public String getMaster() {
		return master;
	}

	/**
	 * @param master the master to set
	 */
	public void setMaster(String master) {
		this.master = master;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the jarName
	 */
	public String getJarName() {
		return jarName;
	}

	/**
	 * @param jarName the jarName to set
	 */
	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	/**
	 * @return the nameSpace
	 */
	public String getNameSpace() {
		return nameSpace;
	}

	/**
	 * @param nameSpace the nameSpace to set
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	/**
	 * @return the argument
	 */
	public List<String> getArgument() {
		return argument;
	}

	/**
	 * @param argument the argument to set
	 */
	public void setArgument(List<String> argument) {
		this.argument = argument;
	}

	/**
	 * @return the sparkOpts
	 */
	public String getSparkOpts() {
		return sparkOpts;
	}

	/**
	 * @param sparkOpts the sparkOpts to set
	 */
	public void setSparkOpts(String sparkOpts) {
		this.sparkOpts = sparkOpts;
	}	
	
}
