package com.oozie.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oozie.source.Input;
import com.oozie.source.TaskList;
import com.oozie.source.WorkflowTask;
import com.oozie.util.Constants;
import com.oozie.util.CustomSort;
import com.oozie.util.OozieProp;

public class InputReader {

	public Input input;
	public WorkFlowGenerator xml;

	public InputReader(String fileName) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Input input = mapper.readValue(OozieProp.getFileDataAsString(fileName), Input.class);
			this.input = input;
		} catch (Exception ex) {
			System.out.println("Unable to parse Json file:-" + ex);
		}

	}

	public LinkedHashMap<Integer, String> getTaskList() {
		LinkedHashMap<Integer, List<Integer>> dependentWorkflow = new LinkedHashMap<>();
		List<WorkflowTask> list = input.getWorkflowTasks().stream().collect(Collectors.toList());
		LinkedHashMap<Integer, String> finalList = new LinkedHashMap<>();
		for (WorkflowTask task : list) {
			for (WorkflowTask task2 : list) {
				if (task.equals(task2)) {
					if (!dependentWorkflow.containsKey(task.getDatID())) {
						List<Integer> l = new ArrayList<>();
						l.add(task2.getDatID());
						dependentWorkflow.put(task.getDatID(), l);
					} else {
						List<Integer> existingValues = dependentWorkflow.get(task.getDatID());
						existingValues.add(task2.getDatID());
						dependentWorkflow.put(task.getDatID(), existingValues);
					}
				}
			}

		}
		CustomSort.bubbleSort(dependentWorkflow);

		dependentWorkflow.entrySet().forEach(entry -> {
			if (!finalList.containsKey(entry.getKey())) {
				finalList.put(entry.getKey(), Constants.INDEPENDENT);
			}
			entry.getValue().forEach(value -> {
				if (entry.getValue().size() == 1) {
					finalList.put(value, Constants.INDEPENDENT);
				}
				if (entry.getValue().size() > 1) {
					finalList.put(value, Constants.DEPENDENT);
				}
			});
		});

		int counter = 1;
		for (WorkflowTask task : list) {
			if (!finalList.containsKey(task.getDatID()) && counter == 1) {
				finalList.put(task.getDatID(), Constants.INDEPENDENT1);
				System.out.println("in task 1");
				counter++;
			}
			if (!finalList.containsKey(task.getDatID()) && counter == 2) {
				finalList.put(task.getDatID(), Constants.INDEPENDENT2);
				System.out.println("in task 2");
				counter = counter - 1;
			}

		}
		return finalList;

	}

	public static HashMap<Integer, String> getjarsfromid() {
		String path = OozieProp.Getproperties().getProperty(Constants.JARS_PATH);
		HashMap<Integer, String> jarslist = new HashMap<>();

		jarslist.put(6002, path + "anova.jar" + "," + "com.anova.jar");
		jarslist.put(6003, path + "associationrules.jar" + "," + "com.associationrules");
		jarslist.put(6009, path + "bkmeans-model.jar" + "," + "com.bkmeans-model");
		jarslist.put(6010, path + "bkmeans-predict.jar" + "," + "bkmeans-predic");
		jarslist.put(6007, path + "bptest.jar" + "," + "bptest.com");
		jarslist.put(6008, path + "chisqrd.jar" + "," + "chisqrd.com");
		jarslist.put(6015, path + "dtc-model.jar" + "," + "dtcmodel.com");
		jarslist.put(6016, path + "dtc-predict.jar" + "dtc-predict.com");
		jarslist.put(6020, path + "dtr-model.jar" + "," + "dtr-model.com");
		jarslist.put(6021, path + "dtr-predict.jar" + "," + "dtr-predict.com");
		jarslist.put(5000, path + "hbase2csv.jar" + "," + "com.hbase.csv");
		jarslist.put(5001, path + "cleaning.jar" + "," + "com.cleaning.jar");
		jarslist.put(6011, path + "model.jar" + "," + "com.model.jar");
		jarslist.put(6012, path + "preditct" + "," + "com.predict.jar");
		jarslist.put(6013, path + "csv2hbase" + "," + "com.csv2hbase.jar");
		return jarslist;
	}

	public String getQuery(WorkflowTask workFlow) {
		String query = null;
		List<String> columnsList = new ArrayList<>();

		String table = workFlow.getAvDataSetList().get(0).getTableName();

		workFlow.getAvDataSetList().forEach(dataSet -> {
			if (dataSet.getParamname() != null && !dataSet.getParamname().equals("")) {
				columnsList.add(dataSet.getParamname());

			}
		});
		String columns = String.join(",", columnsList);

		System.out.println(columns);
		query = "SELECT (" + columns + ") " + " FROM " + table + ";";
		return query;
	}

	public List<String> getParameters(WorkflowTask workFlow) {
		List<String> argsList = new ArrayList<>();
		workFlow.getTaskParams().forEach(taskParam -> {
			if (taskParam.getParamName() != null && !taskParam.getParamName().equals("")
					& taskParam.getParamValue() != null & !taskParam.getParamValue().equals("")
					& taskParam.getOperator() != null & !taskParam.getOperator().equals("")) {
				String arg = taskParam.getParamName() + taskParam.getOperator() + taskParam.getParamValue();
				argsList.add(arg);

			}
		});

		return argsList;
	}

	static String flag;

	public List<String> getMethodArgs(WorkflowTask workflow) {
		int methodId = workflow.getMethodID();
		String inputPath = OozieProp.Getproperties().getProperty(Constants.INPUT);
		String workflowName = input.getWorkflowname();
		String outputPath = OozieProp.Getproperties().getProperty(Constants.OUTPUT);

		if (workflowName.contains(" ")) {
			workflowName.replaceAll(" ", "");
		}
		if (!inputPath.endsWith("/")) {
			inputPath = inputPath + "/";
		}
		if (!outputPath.endsWith("/")) {
			outputPath = outputPath + "/";
		}
		if (!workflowName.endsWith("/")) {
			workflowName = workflowName + "/";
		}

		// method id for Hbase to csv
		if (methodId == 5000) {
			List<String> taskArgs = new ArrayList<>();
			taskArgs.add("urldb=" + OozieProp.Getproperties().getProperty(Constants.HBASE_URL));
			taskArgs.add("queryInputData=" + getQuery(workflow));
			taskArgs.add("urlcsv=" + inputPath + workflowName);
			return taskArgs;
		}

		// method id for data cleaning
		if (methodId == 5001) {
			List<String> taskArgs = new ArrayList<>();
			taskArgs.add("csvData=" + inputPath + workflowName + "part-*");
			List<String> argslist = getParameters(workflow);
			for (String arg : argslist) {
				taskArgs.add(arg);
			}
			taskArgs.add("resultPath=" + outputPath + workflowName + "cleaned");
			return taskArgs;
		}

		if (methodId == 6011) {
			List<String> taskArgs = new ArrayList<>();
			taskArgs.add("csvData=" + inputPath + workflowName + "part-*");
			List<String> argslist = getParameters(workflow);
			for (String arg : argslist) {
				taskArgs.add(arg);
			}
			taskArgs.add("resultPath=" + inputPath + workflowName + "linear_regression_model");
			return taskArgs;
		}
		if (methodId == 6012) {
			List<String> taskArgs = new ArrayList<>();
			taskArgs.add("csvData=" + outputPath + workflowName + "cleaned/part-*");
			taskArgs.add("modelData=" + outputPath + workflowName + "linear_regression_model/");
			taskArgs.add("resultPath=" + inputPath + workflowName + "linear_regression_predict");
			List<String> argslist = getParameters(workflow);
			for (String arg : argslist) {
				taskArgs.add(arg);
			}
			return taskArgs;

		}
		if (methodId == 6013) {
			List<String> taskArgs = new ArrayList<>();
			taskArgs.add("urlcsv=" + outputPath + workflowName + "/linear_regression_predict/part-*");
			taskArgs.add("tablename=" + workflow.getDatOutput());
			taskArgs.add("primarykey=AUTO");
			taskArgs.add("urldb=" + OozieProp.Getproperties().getProperty(Constants.HBASE_URL));

			return taskArgs;
		}

		// this will execute for all method id's
		else {
			List<String> taskArgs = new ArrayList<>();
			List<String> argslist = getParameters(workflow);
			for (String arg : argslist) {
				taskArgs.add(arg);
			}

			return taskArgs;
		}

	}

}
