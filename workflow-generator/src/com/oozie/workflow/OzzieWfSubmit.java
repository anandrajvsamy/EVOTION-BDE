package com.oozie.workflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.WorkflowJob;

import com.oozie.util.Constants;
import com.oozie.util.OozieProp;

public class OzzieWfSubmit implements Constants {

	public String submitOozieWorkflow() {

		OozieClient oozieClient = new OozieClient(OozieProp.Getproperties().getProperty(Constants.OOZIE_CLIENT));
		Properties conf = oozieClient.createConfiguration();
		conf.setProperty(OozieClient.APP_PATH, OozieProp.Getproperties().getProperty(Constants.OUTPUT_WORKFLOW_PATH));
		conf.setProperty(OozieClient.LIBPATH, OozieProp.Getproperties().getProperty(Constants.OOZIE_LIB_PATH));
		conf.setProperty(OozieClient.USE_SYSTEM_LIBPATH, "true");
		String jobId = null;
		try {
			// submit and start the workflow job
			jobId = oozieClient.run(conf);
			// wait until the workflow job finishes printing the status every 10
			// seconds
			while (oozieClient.getJobInfo(jobId).getStatus() == WorkflowJob.Status.RUNNING) {
				Thread.sleep(10 * 1000);
			}
			// print the final status o the workflow job
			// return jobId;
		} catch (Exception e) {
			System.out.println(e);

		}
		return jobId;

	}

	public void writeToHdfs(String inputPathLocation, String outputLoaction) throws IOException {

		BufferedReader inputReader = null;
		FileSystem fileSystem = null;
		OutputStream opStream = null;
		BufferedWriter bufferWriter = null;

		try {

			Configuration conf = new Configuration();
			conf.addResource(
					new org.apache.hadoop.fs.Path(OozieProp.Getproperties().getProperty(Constants.HADOOP_CORE_XML)));
			conf.addResource(
					new org.apache.hadoop.fs.Path(OozieProp.Getproperties().getProperty(Constants.HDFS_CORE_XML)));
			conf.setBoolean("fs.hdfs.impl.disable.cache", true);

			Path inputPath = new Path(OozieProp.Getproperties().getProperty(Constants.INPUT_LOCAL_PATH));
			Path outputPath = new Path(OozieProp.Getproperties().getProperty(Constants.OUTPUT_WORKFLOW_PATH));

			FileSystem fsHsdfs = FileSystem.get(conf);

			fsHsdfs.copyFromLocalFile(inputPath, outputPath);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		} finally {
			if (null != inputReader) {
				inputReader.close();
			}
			if (null != bufferWriter) {
				bufferWriter.close();
			}
			if (null != fileSystem) {
				fileSystem.close();
			}
			if (null != opStream) {
				opStream.close();
			}
		}
	}

	/**
	 * Method to delete file from HDFS
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void DeleteFromHdfs(String fileName) throws IOException {
		Configuration conf = new Configuration();
		conf.addResource(new org.apache.hadoop.fs.Path(HADOOP_CORE_SITE_PATH));
		conf.addResource(new org.apache.hadoop.fs.Path(HADOOP_HDFS_SITE_PATH));
		conf.setBoolean("fs.hdfs.impl.disable.cache", true);
		FileSystem fileSystem = FileSystem.get(conf);
		fileSystem.delete(new Path(fileName), true);
	}

	public static void main(String[] args) {
		String inputfile = OozieProp.Getproperties().getProperty(Constants.INPUT_FILE);
		boolean exists = new WorkflowXMLGenerator().getXML(inputfile);
		String localInputLoc = OozieProp.Getproperties().getProperty(Constants.INPUT_LOCAL_PATH);
		String hdfsOutputLoc = OozieProp.Getproperties().getProperty(Constants.OUTPUT_WORKFLOW_PATH);
		if (exists) {
			OzzieWfSubmit wfSubmit = new OzzieWfSubmit();
			try {
				wfSubmit.writeToHdfs(localInputLoc, hdfsOutputLoc);
			} catch (Exception e) {
				System.out.println(e);
			}
			String jobId =wfSubmit.submitOozieWorkflow();
			System.out.println("application has been successfully submited your JobId"+jobId);
		}

	}

}
