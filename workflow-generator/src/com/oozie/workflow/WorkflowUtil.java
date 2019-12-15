package com.oozie.workflow;

import java.util.Map.Entry;

import com.oozie.source.WorkflowTask;

public class WorkflowUtil {

	public static String getDatAndIdCombo(Entry<String, WorkflowTask> entry) {
		return entry.getValue().getDatName() + "_" + entry.getValue().getDatID().toString();
	}

	public static int getDatId(Entry<String, WorkflowTask> entry) {
		return entry.getValue().getDatID();
	}

	public static String getSequentialSuffix() {
		return "_" + "seq";
	}

	public static String getForkSuffix() {
		return "_" + "fork";
	}

	public static String getJoinSuffix() {
		return "_" + "join";
	}

}
