package evotion.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.oozie.client.AuthOozieClient;
import org.apache.oozie.client.CoordinatorJob;
import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.WorkflowJob;

public class OozieMng {

	private String oozieUrl=null;
	
	public OozieMng() {
		
		Properties prop = new Properties();
		InputStream input = null;
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			
			input = classLoader.getResourceAsStream("evotion.properties");
			prop.load(input);
			oozieUrl=prop.getProperty("oozie-url");
			
		}catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public String submit(Map<String,String> properties) throws Exception{
		
		String jobId =null;
		OozieClient wc = new OozieClient(oozieUrl);
		Properties conf = wc.createConfiguration();
		List<String> keys = new ArrayList<String>(properties.keySet());
		for(int i=0;i<keys.size();i++){
			conf.setProperty(keys.get(i), properties.get(keys.get(i)));
		}
		
		jobId = wc.submit(conf);
		System.out.println("Job"+jobId);
		return jobId;
	}
	
	public void start(String jobId,String user) throws Exception{
		
		
		OozieClient wc = new AuthOozieClient(oozieUrl);
		System.setProperty("user.name", user);
		wc.start(jobId);
		
	}
	
	public String run(Map<String,String> properties) throws Exception{
		
		String jobId =null;
		OozieClient wc = new OozieClient(oozieUrl);
		Properties conf = wc.createConfiguration();
		List<String> keys = new ArrayList<String>(properties.keySet());
		for(int i=0;i<keys.size();i++){
			conf.setProperty(keys.get(i), properties.get(keys.get(i)));
		}
		
		jobId = wc.run(conf);
		return jobId;
	}
	
	public void suspend(String jobId,String user) throws Exception{
		
		
		OozieClient wc = new AuthOozieClient(oozieUrl);
		System.setProperty("user.name", user);
		wc.suspend(jobId);
		
	}
	
	public String rerun(String jobId,Map<String,String> properties) throws Exception{
		
		OozieClient wc = new AuthOozieClient(oozieUrl);
		System.setProperty("user.name", properties.get("user.name"));
		Properties conf = wc.createConfiguration();
		List<String> keys = new ArrayList<String>(properties.keySet());
		for(int i=0;i<keys.size();i++){
			conf.setProperty(keys.get(i), properties.get(keys.get(i)));
		}
		
		wc.reRun(jobId,conf);
		return jobId;
	}

	public void resume(String jobId,String user) throws Exception{
		
		
		OozieClient wc = new AuthOozieClient(oozieUrl);
		System.setProperty("user.name", user);
		wc.resume(jobId);
		
	}
	
	public void kill(String jobId,String user) throws Exception{
		
		
		OozieClient wc = new AuthOozieClient(oozieUrl);
		System.setProperty("user.name", user);
		wc.kill(jobId);
		
	}
	
	public String getStatus(String jobId,String user,boolean wf) throws Exception{
		
		
		OozieClient wc = new AuthOozieClient(oozieUrl);
		System.setProperty("user.name", user);
		String statusDesc=null;
		if(wf){
			WorkflowJob job=wc.getJobInfo(jobId);
			if(job!=null)
				statusDesc= job.getStatus().toString();
		}else{
			CoordinatorJob job=wc.getCoordJobInfo(jobId);
			if(job!=null)
				statusDesc=job.getStatus().toString();
		}
		return statusDesc;
		
	}
	
	public List<String>  getInfoList(String status) throws Exception{
		
		
		OozieClient wc = new 
				OozieClient(oozieUrl);
		StringBuilder filter = new StringBuilder();
		filter.append(OozieClient.FILTER_STATUS).append('=')
				.append(status);
		//List<WorkflowInfo> infos=new ArrayList<WorkflowInfo>();
		List<String> infos=new ArrayList<String>();
		if(jobStatus(status)){
			List<WorkflowJob> wfs=wc.getJobsInfo(filter.toString(),0,10000);
		
			System.out.println("wf"+wfs.size());
			for(int i=0;i<wfs.size();i++){
				WorkflowJob j=wfs.get(i);
				/*WorkflowInfo wi=new WorkflowInfo();
				wi.setJobId(j.getId());
				wi.setStatus(j.getStatus().toString());*/
				infos.add(j.getId());
			}
		}
		if(coordStatus(status)){
			List<CoordinatorJob> cs=wc.getCoordJobsInfo(filter.toString(),0,10000);
			System.out.println("coord"+cs.size());
			for(int i=0;i<cs.size();i++){
				CoordinatorJob j=cs.get(i);
				/*WorkflowInfo wi=new WorkflowInfo();
				wi.setJobId(j.getId());
				wi.setStatus(j.getStatus().toString());*/
				infos.add(j.getId());
			}
		}
		return infos;
		
	}
	
	private boolean jobStatus(String status){
		
		
		if(status.equals("DONEWITHERROR") || status.equals("IGNORED") || status.equals("PREMATER") 
				|| status.equals("PAUSED") || status.equals("PAUSEDWITHERROR") || status.equals("PREPPAUSED")
				|| status.equals("PREPSUSPENDED") || status.equals("RUNNINGWITHERROR")
				|| status.equals("SUSPENDEDWITHERROR"))			
			return false;
		return true;
	}
	
	private boolean coordStatus(String status){
		
		boolean b=true;
		return b;
	}
	
		
	
}
