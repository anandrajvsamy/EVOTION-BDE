package com.oozie.source;

import java.util.List;

public class TaskList {

String nodeName;
String applicationName;
String jar;
String sparkOpts;
List<String> args;
String className;
Integer datID;
String end;
String fail;
public String getFail() {
	return fail;
}
public void setFail(String fail) {
	this.fail = fail;
}
public String getEnd() {
	return end;
}
public void setEnd(String end) {
	this.end = end;
}
public Integer getDatID() {
	return datID;
}
public void setDatID(Integer datID) {
	this.datID = datID;
}
public String getNodeName() {
	return nodeName;
}
public void setNodeName(String nodeName) {
	this.nodeName = nodeName;
}
public String getApplicationName() {
	return applicationName;
}
public void setApplicationName(String applicationName) {
	this.applicationName = applicationName;
}
public String getJar() {
	return jar;
}
public void setJar(String jar) {
	this.jar = jar;
}
public String getSparkOpts() {
	return sparkOpts;
}
public void setSparkOpts(String sparkOpts) {
	this.sparkOpts = sparkOpts;
}
public List<String> getArgs() {
	return args;
}
public void setArgs(List<String> args) {
	this.args = args;
}

public String getClassName() {
	return className;
}

public void setClassName(String className) {
	this.className=className;
}

}
