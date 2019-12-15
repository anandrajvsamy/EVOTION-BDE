package com.oozie.workflow;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.oozie.model.ActionNode;
import com.oozie.model.WorkFlowApp;
import com.oozie.source.JobInfo;
import com.oozie.source.TaskList;
import com.oozie.source.WorkflowTask;
import com.oozie.util.Constants;
import com.oozie.util.CustomSort;
import com.oozie.util.OozieProp;

public class WorkflowXMLGenerator {

	
	static String dependent = Constants.DEPENDENT;
	static String independent = Constants.INDEPENDENT;
	static String independent_1 = Constants.INDEPENDENT1;
	static String independent_2 = Constants.INDEPENDENT2;

	public static LinkedHashMap<String, JobInfo> getJobInfoList(String filename) {
		InputReader reader = new InputReader(filename);
		LinkedHashMap<Integer, String> tasklist = reader.getTaskList();
		tasklist.entrySet().forEach(entry -> {
			System.out.println("tasklist:-" + entry);
		});
		List<WorkflowTask> workflowTask = reader.input.getWorkflowTasks();
		LinkedHashMap<String, WorkflowTask> list = new LinkedHashMap<>();
		int counter = 0;
		for (Map.Entry<Integer, String> element : tasklist.entrySet()) {
			for (WorkflowTask work : workflowTask) {
				if (work.getDatID() == element.getKey()) {
					list.put(counter + "," + element.getValue().toString(), work);
					counter++;
				}
			}

		}
		System.out.println(list.size());
		LinkedHashMap<String, JobInfo> job = new LinkedHashMap<>();
		for (int index = 0; index < list.size(); index++) {
			Entry<String, WorkflowTask> current = CustomSort.getMapValueAtJobInfo(list, index);
			Entry<String, WorkflowTask> previous = CustomSort.getMapValueAtJobInfo(list, index - 1);
			Entry<String, WorkflowTask> next = CustomSort.getMapValueAtJobInfo(list, index + 1);
			String current_depedency = current.getKey().split(",")[1];
			String next_depedency = null;
			String previous_dependency = null;
			int current_index = Integer.parseInt(current.getKey().split(",")[0]);
			try {

				next_depedency = next.getKey().split(",")[1];
			} catch (Exception e) {
				System.out.println("last value");
			}

			try {
				previous_dependency = previous.getKey().split(",")[1];
			} catch (Exception e) {
				System.out.println("first value");
			}

			// correct //previous needs to be check

			if (current_depedency.equals(independent) && next_depedency == null) {
				JobInfo info = new JobInfo();
				info.setActionName(current.getValue().getDatName() + "_" + current.getValue().getDatID().toString());
				info.setOk("end");
				info.setDatId(current.getValue().getDatID());
				info.setFail("end");
				job.put(Integer.toString(index) + "_" + "seq", info);
			}

			else if (current_depedency.equals(independent) && next_depedency.equals(independent)) {
				JobInfo info = new JobInfo();
				info.setActionName(current.getValue().getDatName() + "_" + current.getValue().getDatID().toString());
				info.setOk(next.getValue().getDatName() + "_" + next.getValue().getDatID().toString());
				info.setDatId(current.getValue().getDatID());
				info.setFail("end");
				job.put(Integer.toString(index) + "_" + "seq", info);

			}

			//
			else if (current_depedency.equals(independent) && next_depedency.equals(dependent)) {
				JobInfo info = new JobInfo();
				info.setActionName(current.getValue().getDatName() + "_" + current.getValue().getDatID().toString());
				info.setOk(next.getValue().getDatName() + "_" + next.getValue().getDatID().toString() + "_" + "fork");
				info.setDatId(current.getValue().getDatID());
				info.setFail("end");
				job.put(Integer.toString(index) + "_" + "seq", info);
			}

			// stating of fork and join
			else if (current_depedency.equals(dependent) && previous_dependency.equals(independent)) {
				Integer startfork = Integer.parseInt(current.getKey().split(",")[0]);
				int endfork = 0;

				// ending our fork at next independent value
				for (int i = startfork; i < list.size(); i++) {
					if (CustomSort.getMapValueAtJobInfo(list, i).getKey().split(",")[1].equals(independent)) {
						endfork = i;
						break;
					}
				}

				// creating fork tag
				JobInfo forkjob = new JobInfo();
				List<String> forklist = new ArrayList<String>();
				forkjob.setActionName(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatName() + "_"
						+ current.getValue().getDatID().toString() + "_" + "fork");
				for (int i = startfork; i < endfork; i++) {
					forklist.add(CustomSort.getMapValueAtJobInfo(list, i).getValue().getDatName() + "_"
							+ CustomSort.getMapValueAtJobInfo(list, i).getValue().getDatID().toString());
				}
				forkjob.setForklist(forklist);
				job.put(Integer.toString(index) + "_" + "fork_job", forkjob);

				// getting list of jobs needs to be executed in fork tag and end is the join
				for (int i = startfork; i < endfork; i++) {
					JobInfo info = new JobInfo();
					info.setActionName(CustomSort.getMapValueAtJobInfo(list, i).getValue().getDatName() + "_"
							+ CustomSort.getMapValueAtJobInfo(list, i).getValue().getDatID().toString());
					info.setOk(CustomSort.getMapValueAtJobInfo(list, endfork).getValue().getDatName() + "_"
							+ CustomSort.getMapValueAtJobInfo(list, endfork).getValue().getDatID().toString() + "_"
							+ "join");
					info.setFail("fail");
					info.setDatId(CustomSort.getMapValueAtJobInfo(list, i).getValue().getDatID());
					job.put(Integer.toString(i) + "_" + "seq", info);
				}

			}

			else if (current_depedency.equals(Constants.INDEPENDENT) && previous_dependency.equals(Constants.DEPENDENT)
					&& next_depedency.equals(Constants.INDEPENDENT)) {
				JobInfo join = new JobInfo();
				join.setActionName(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatName() + "_"
						+ CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID().toString() + "_" + "join");
				join.setOk(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatName() + "_"
						+ CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID().toString());
				job.put(Integer.toString(index) + "_" + "join", join);
				JobInfo info = new JobInfo();
				info.setActionName(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatName() + "_"
						+ CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID().toString());
				info.setOk(CustomSort.getMapValueAtJobInfo(list, index + 1).getValue().getDatName() + "_"
						+ CustomSort.getMapValueAtJobInfo(list, index + 1).getValue().getDatID().toString());
				info.setFail("end");
				info.setDatId(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID());
				job.put(Integer.toString(index) + "_" + "seq", info);

			}

			else if (current_depedency.equals(Constants.INDEPENDENT) && previous_dependency.equals(Constants.DEPENDENT)
					&& next_depedency.equals(Constants.INDEPENDENT1)) {
				JobInfo join = new JobInfo();
				join.setActionName(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatName() + "_"
						+ CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID().toString() + "_" + "join");
				join.setOk(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatName() + "_"
						+ CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID().toString());
				job.put(Integer.toString(index) + "_" + "join", join);
				JobInfo info = new JobInfo();
				info.setActionName(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatName() + "_"
						+ CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID().toString());
				info.setOk(CustomSort.getMapValueAtJobInfo(list, index + 1).getValue().getDatName() + "_"
						+ CustomSort.getMapValueAtJobInfo(list, index + 1).getValue().getDatID().toString() + "_"
						+ "fork");
				info.setFail("end");
				info.setDatId(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID());
				job.put(Integer.toString(index) + "_" + "seq", info);

			}

			else if (current_depedency.equals(independent_1) && next_depedency.equals(independent_2)
					&& index + 3 < list.size() && previous_dependency != null) {
				JobInfo fork = new JobInfo();
				fork.setActionName(WorkflowXMLGenerator.getCombineIdAndName(list, index) + "_" + "fork");
				List<String> forklist = new ArrayList<String>();
				forklist.add(WorkflowXMLGenerator.getCombineIdAndName(list, index));
				forklist.add(WorkflowXMLGenerator.getCombineIdAndName(list, index + 1));
				fork.setForklist(forklist);
				job.put(Integer.toString(index) + "_" + "fork", fork);

				JobInfo info = new JobInfo();
				info.setActionName(WorkflowXMLGenerator.getCombineIdAndName(list, index));
				info.setOk(WorkflowXMLGenerator.getCombineIdAndName(list, index + 2) + "_" + "join");
				info.setDatId(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID());
				info.setFail("fail");
				job.put(Integer.toString(index) + "_" + "seq", info);

				JobInfo info1 = new JobInfo();
				info1.setActionName(WorkflowXMLGenerator.getCombineIdAndName(list, index + 1));
				info1.setOk(WorkflowXMLGenerator.getCombineIdAndName(list, index + 2) + "_" + "join");
				info1.setDatId(CustomSort.getMapValueAtJobInfo(list, index + 1).getValue().getDatID());
				info1.setFail("fail");
				job.put(Integer.toString(current_index + 1) + "_" + "seq", info1);

				try {
					WorkflowXMLGenerator.getCombineIdAndName(list, index + 5);
					JobInfo join = new JobInfo();
					join.setActionName(WorkflowXMLGenerator.getCombineIdAndName(list, index + 2) + "_" + "join");
					join.setOk(WorkflowXMLGenerator.getCombineIdAndName(list, index + 2) + "_" + "fork");
					job.put(Integer.toString(index + 2) + "_" + "join", join);
				} catch (Exception e) {
					JobInfo end_join = new JobInfo();
					end_join.setActionName(WorkflowXMLGenerator.getCombineIdAndName(list, index + 2) + "_" + "join");
					end_join.setOk(WorkflowXMLGenerator.getCombineIdAndName(list, index + 2));
					job.put(Integer.toString(index + 2) + "_" + "join", end_join);

				}

			}

			else if (current_depedency.equals(independent_1) && next_depedency.equals(independent_2)
					&& index + 2 == list.size()) {
				JobInfo info = new JobInfo();
				info.setActionName(WorkflowXMLGenerator.getCombineIdAndName(list, index));
				info.setOk("end");
				info.setFail("end");
				info.setDatId(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID());
				job.put(Integer.toString(index) + "_" + "seq", info);

				JobInfo info1 = new JobInfo();
				info1.setActionName(WorkflowXMLGenerator.getCombineIdAndName(list, index + 1));
				info1.setDatId(CustomSort.getMapValueAtJobInfo(list, index + 1).getValue().getDatID());
				info1.setOk("end");
				info1.setFail("end");
				info1.setEnd("end");
				job.put(Integer.toString(index + 1) + "_" + "seq", info1);

				/*
				*/
			}

			else if (current_depedency.equals(independent_1) && next_depedency.equals(null)) {

				JobInfo info = new JobInfo();
				info.setActionName(WorkflowXMLGenerator.getCombineIdAndName(list, index));
				info.setOk("end");
				info.setFail("fail");
				info.setDatId(CustomSort.getMapValueAtJobInfo(list, index).getValue().getDatID());
				job.put(Integer.toString(index) + "_" + "seq", info);

			}

		}

		job.entrySet().forEach(entry -> {
			System.out.println("key" + entry.getKey() + "       action =" + entry.getValue().getActionName()
					+ "        nextstep =" + entry.getValue().getOk() + "  datID== " + entry.getValue().getDatId());
			if (entry.getKey().split("_")[1].equals("fork")) {
				System.out.println(entry.getValue().getForklist());
			}

		});
		return job;

	}

	private static String getCombineIdAndName(LinkedHashMap<String, WorkflowTask> workflow, int location) {
		return CustomSort.getMapValueAtJobInfo(workflow, location).getValue().getDatName() + "_"
				+ String.valueOf(CustomSort.getMapValueAtJobInfo(workflow, location).getValue().getDatID());
	}

	public boolean getXML(String inputFile) {
		WorkFlowGenerator wfgen = new WorkFlowGenerator();
		int counter = 0;
		LinkedHashMap<String, JobInfo> workflowJobInfo = WorkflowXMLGenerator.getJobInfoList(inputFile);
		List<WorkflowTask> list = new InputReader(inputFile).input.getWorkflowTasks().stream()
				.collect(Collectors.toList());
		String workflowName = new InputReader(inputFile).input.getWorkflowname();

		for (Map.Entry<String, JobInfo> entry : workflowJobInfo.entrySet()) {
			TaskList tasklist = new TaskList();

			for (WorkflowTask workflow : list) {
				int datId;
				try {
					String[] action = entry.getValue().getActionName().split("_");
					datId = Integer.parseInt(action[action.length - 1]);

				} catch (Exception e) {
					String[] action = entry.getValue().getActionName().split("_");
					datId = Integer.parseInt(action[action.length - 2]);

				}
				if (datId == workflow.getDatID()) {
					String key = entry.getKey().split("_")[1];
					String value_action = entry.getValue().getActionName();

					if (key.equals("seq") && counter == 0) {
						wfgen.addStartNode(value_action);
						counter++;
					}

					if (key.equals("seq")) {
						tasklist.setApplicationName(entry.getValue().getActionName());
						tasklist.setNodeName(entry.getValue().getActionName());

						tasklist.setSparkOpts(OozieProp.Getproperties().getProperty(Constants.SPARK_OPTS));
						try {
						tasklist.setClassName(
								InputReader.getjarsfromid().get(workflow.getMethodID()).toString().split(",")[1]);
						tasklist.setJar(
								InputReader.getjarsfromid().get(workflow.getMethodID()).toString().split(",")[0]);
						}
						catch(Exception e) {
							System.out.println("please add class and jar info into InputReader.getjarsfromid() with respect to method Id");
							tasklist.setClassName("dummy class");
							tasklist.setJar("duumy jar");
								
						}
						tasklist.setDatID(workflow.getDatID());
						tasklist.setEnd(entry.getValue().getOk());
						
						List<String> argument = new InputReader(inputFile).getMethodArgs(workflow);
						tasklist.setFail(entry.getValue().getFail());
						tasklist.setArgs(argument);
						wfgen.addSparkAction(tasklist);

					} else if (key.equals("fork")) {
						wfgen.addFork(entry.getValue().getActionName(), entry.getValue().getForklist());
					}

					else if (key.equals("join")) {
						wfgen.addJoin(entry.getValue().getActionName(), entry.getValue().getOk());
					}

				}

			}
			wfgen.addEndNode("end");

		}
		wfgen.addKill();
		JAXBContext jc;
		boolean exists = false;
		try {
			jc = JAXBContext.newInstance(WorkFlowApp.class, ActionNode.class);
			Marshaller marshaller;
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			WorkFlowApp workflowapp = wfgen.getWorkFlowApp();
			JAXBElement<WorkFlowApp> workflowxml = new OozieNodeFactory().createWorkflowApp(workflowapp);
			workflowapp.setName(workflowName);
			File file = new File(OozieProp.Getproperties().getProperty(Constants.INPUT_LOCAL_PATH)); // :file:///app/data/abcd/spark/workflow.xml
			System.out.println("xml generated");
			marshaller.marshal(workflowxml, file);
			exists = file.exists();
			return exists;

		} catch (JAXBException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;

	}

}
