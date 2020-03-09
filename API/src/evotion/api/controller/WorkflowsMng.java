package evotion.api.controller;

import evotion.api.model.Workflow;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WorkflowsMng {

	private String dbUrl;
	private String dbUsr;
	private String dbPwd;
	
	public WorkflowsMng() {
			
			Properties prop = new Properties();
			InputStream input = null;
			ClassLoader classLoader = getClass().getClassLoader();
			try {
				
				input = classLoader.getResourceAsStream("evotion.properties");
				prop.load(input);
				dbUrl=prop.getProperty("db-url");
				dbUsr=prop.getProperty("db-usr");
				dbPwd=prop.getProperty("db-pwd");
				
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		
	}
	
	public long insert(Workflow workflow) {

		long result = 0;

		try {
			Connection conn = openConnection();

			String query = " insert into workflow (name,daw_id,language,path,execution_type,description,tasks,parameters,vis_query)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, workflow.getName());
			preparedStmt.setString(2, workflow.getDawId());
			preparedStmt.setString(3, workflow.getLanguage());
			preparedStmt.setString(4, workflow.getPath());
			preparedStmt.setString(5, workflow.getExecutionType());
			preparedStmt.setString(6, workflow.getDescription());
			preparedStmt.setString(7, workflow.getTasks());
			preparedStmt.setString(8, workflow.getParameters());
			preparedStmt.setString(9, workflow.getVisQuery());

			int affectedRows = preparedStmt.executeUpdate();
			if (affectedRows == 0) {
				result = -1;
			} else {

				try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						result = generatedKeys.getLong(1);
					} else {
						throw new SQLException(
								"Creating workflow failed, no ID obtained.");
					}
				}
			}
			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return result;

	}

	public boolean update(Workflow workflow) {

		boolean result = false;

		try {
			Connection conn = openConnection();

			String query = " update workflow set name = ?,daw_id=?,language=?,path=?,execution_type=?,description=?,tasks=?,parameters=?,vis_query=? where id=?";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, workflow.getName());
			preparedStmt.setString(2, workflow.getDawId());
			preparedStmt.setString(3, workflow.getLanguage());
			preparedStmt.setString(4, workflow.getPath());
			preparedStmt.setString(5, workflow.getExecutionType());
			preparedStmt.setString(6, workflow.getDescription());
			preparedStmt.setString(7, workflow.getTasks());
			preparedStmt.setString(8, workflow.getParameters());
			preparedStmt.setString(9, workflow.getVisQuery());
			preparedStmt.setLong(10, workflow.getIdEdaw());

			int affectedRows = preparedStmt.executeUpdate();
			if (affectedRows != 0) {
				result = true;
			}

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return result;

	}
	
	public boolean updateJobId(long id, String jobId) {

		boolean result = false;

		try {
			Connection conn = openConnection();

			String query = " update workflow set job_id = ? where id=?";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, jobId);
			preparedStmt.setLong(2, id);

			int affectedRows = preparedStmt.executeUpdate();
			if (affectedRows != 0) {
				result = true;
			}

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return result;

	}

	public Workflow findById(long id) {

		Workflow workflow = null;

		try {
			Connection conn = openConnection();

			String query = " select name,daw_id,language,path,execution_type,description,tasks,parameters,job_id,vis_query from workflow where id=?";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setLong(1, id);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				workflow = new Workflow();
				workflow.setIdEdaw(id);
				workflow.setName(rs.getString("name"));
				workflow.setDawId(rs.getString("daw_id"));
				workflow.setLanguage(rs.getString("language"));
				workflow.setPath(rs.getString("path"));
				workflow.setExecutionType(rs.getString("execution_type"));
				workflow.setDescription(rs.getString("description"));
				workflow.setTasks(rs.getString("tasks"));
				workflow.setParameters(rs.getString("parameters"));
				workflow.setJobId(rs.getString("job_id"));
				workflow.setVisQuery(rs.getString("vis_query"));
			}

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return workflow;

	}

	public List<Workflow> findByModel(String dawId) {

		List<Workflow> workflows = new ArrayList<Workflow>();

		try {
			Connection conn = openConnection();

			String query = "select id,name,language,path,execution_type,description,tasks,parameters,vis_query,job_id from workflow where daw_id=?";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, dawId);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				Workflow workflow = new Workflow();
				workflow.setIdEdaw(rs.getLong("id"));
				workflow.setName(rs.getString("name"));
				workflow.setDawId(dawId);
				workflow.setLanguage(rs.getString("language"));
				workflow.setPath(rs.getString("path"));
				workflow.setExecutionType(rs.getString("execution_type"));
				workflow.setDescription(rs.getString("description"));
				workflow.setTasks(rs.getString("tasks"));
				workflow.setParameters(rs.getString("parameters"));
				workflow.setVisQuery(rs.getString("vis_query"));
				workflow.setJobId(rs.getString("job_id"));
				workflows.add(workflow);
			}

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return workflows;

	}
	
	public List<Workflow> list() {

		List<Workflow> workflows = new ArrayList<Workflow>();

		try {
			Connection conn = openConnection();

			String query = "select id,name,language,path,execution_type,description,tasks,parameters,vis_query,job_id,daw_id from workflow ";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				Workflow workflow = new Workflow();
				workflow.setIdEdaw(rs.getLong("id"));
				workflow.setName(rs.getString("name"));
				workflow.setDawId(rs.getString("daw_id"));
				workflow.setLanguage(rs.getString("language"));
				workflow.setPath(rs.getString("path"));
				workflow.setExecutionType(rs.getString("execution_type"));
				workflow.setDescription(rs.getString("description"));
				workflow.setTasks(rs.getString("tasks"));
				workflow.setParameters(rs.getString("parameters"));
				workflow.setVisQuery(rs.getString("vis_query"));
				workflow.setJobId(rs.getString("job_id"));
				workflows.add(workflow);
			}

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return workflows;

	}

	public boolean deleteById(long id) {

		boolean result = false;

		try {
			
			Connection conn = openConnection();

			String query = " delete from workflow where id=?";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setLong(1, id);

			int affectedRows = preparedStmt.executeUpdate();
			if (affectedRows != 0) {
				result = true;
			}

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return result;

	}

	public boolean deleteByModel(String dawId) {

		boolean result = false;

		try {
			Connection conn = openConnection();

			String query = " delete from workflow where daw_id=?";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, dawId);

			int affectedRows = preparedStmt.executeUpdate();
			if (affectedRows != 0) {
				result = true;
			}

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return result;

	}
	
	public List<Workflow> findByJobIds(List<String> jobIds){
		

		List<Workflow> workflows = new ArrayList<Workflow>();

		try {
			Connection conn = openConnection();

			String query = "select id,name,language,path,execution_type,description,tasks,parameters,vis_query,daw_id from workflow where ";
			for(int i=0;i<jobIds.size();i++){
				if(i>0) query=query+" or ";
				query=query+" job_id like ? ";
			}

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			for(int i=0;i<jobIds.size();i++){
				preparedStmt.setString(i+1, jobIds.get(i));
			}
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				Workflow workflow = new Workflow();
				workflow.setIdEdaw(rs.getLong("id"));
				workflow.setName(rs.getString("name"));
				workflow.setLanguage(rs.getString("language"));
				workflow.setPath(rs.getString("path"));
				workflow.setExecutionType(rs.getString("execution_type"));
				workflow.setDescription(rs.getString("description"));
				workflow.setTasks(rs.getString("tasks"));
				workflow.setParameters(rs.getString("parameters"));
				workflow.setVisQuery(rs.getString("vis_query"));
				workflow.setDawId(rs.getString("daw_id"));
				workflows.add(workflow);
			}

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return workflows;
		
	}
	
private Connection openConnection() throws Exception{
		
		String driver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://"+dbUrl;
		Class.forName(driver);
		return DriverManager.getConnection(myUrl, dbUsr,dbPwd);
	}
}
