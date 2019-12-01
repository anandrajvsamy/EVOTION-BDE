package com.oozie.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.oozie.source.WorkflowTask;

public class CustomSort {

	public static void bubbleSort(LinkedHashMap<Integer, List<Integer>> data) {
		boolean sorted = false;

		while (!sorted) {
			sorted = true;
			for (int i = 0; i < data.size() - 1; i++) {
				Entry<Integer, ArrayList<Integer>> first = getMapValueAt(data, i);
				Entry<Integer, ArrayList<Integer>> second = getMapValueAt(data, i + 1);

				if (second.getValue().contains(first.getKey())) {
					Entry<Integer, ArrayList<Integer>> temp = first;
					first = second;
					second = temp;
					sorted  = false;
				}


			}
		}
	}

	public static Entry<Integer, ArrayList<Integer>> getMapValueAt(LinkedHashMap<Integer, List<Integer>>  list,
			int index) {
		Map.Entry<Integer, ArrayList<Integer>> entry = (Map.Entry<Integer, ArrayList<Integer>>) list.entrySet()
				.toArray()[index];
		return entry;
	}
	
	public static Entry<String, WorkflowTask> getMapValueAtJobInfo(LinkedHashMap<String, WorkflowTask>  list,
			int index) {
		Map.Entry<String, WorkflowTask> entry;
		try {
		 entry = (Map.Entry<String, WorkflowTask>) list.entrySet()
				.toArray()[index];
		return entry;
		}
		catch(Exception e) {
			return null; 
		}
		
	}

}
