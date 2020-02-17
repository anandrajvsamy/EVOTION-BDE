package evotion.api.controller;

import it.unimi.evotion.api.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TasksMng {

	private String dbUrl;
	private String dbUsr;
	private String dbPwd;
	
	public TasksMng() {
			
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
	public long insert(Task task) {

		long result = 0;

		try {
			Connection conn = openConnection();

			// the mysql insert statement
			String query = " insert into task (name,daw_task_id,lib,language,path,dependencies,description)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, task.getName());
			preparedStmt.setString(2, task.getDawTaskId());
			preparedStmt.setString(3, task.getLib());
			preparedStmt.setString(4, task.getLanguage());
			preparedStmt.setString(5, task.getPath());
			preparedStmt.setString(6, task.getDependencies());
			preparedStmt.setString(7, task.getDescription());

			// execute the preparedstatement
			int affectedRows = preparedStmt.executeUpdate();
			if (affectedRows == 0) {
				result = -1;
			} else {

				try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						result = generatedKeys.getLong(1);
					} else {
						throw new SQLException(
								"Creating task failed, no ID obtained.");
					}
				}
			}
			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return result;

	}

	public boolean update(Task task) {

		boolean result = false;

		try {
			Connection conn = openConnection();

			// the mysql insert statement
			String query = " update task set name = ?,daw_task_id=?,lib=?,language=?,path=?,dependencies=?,description=? where id=?";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, task.getName());
			preparedStmt.setString(2, task.getDawTaskId());
			preparedStmt.setString(3, task.getLib());
			preparedStmt.setString(4, task.getLanguage());
			preparedStmt.setString(5, task.getPath());
			preparedStmt.setString(6, task.getDependencies());
			preparedStmt.setString(7, task.getDescription());
			preparedStmt.setLong(8, task.getIdTask());

			// execute the preparedstatement
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
	
	
	
	
	public Task findById(long id) {

		Task task = null;

		try {
			Connection conn = openConnection();
			// the mysql insert statement
			String query = " select name,daw_task_id,lib,language,path,dependencies,description from task where id=?";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setLong(1, id);
			ResultSet rs = preparedStmt.executeQuery();
			if(rs.next()){
				task=new Task();
				task.setIdTask(id);
				task.setName(rs.getString("name"));
				task.setDawTaskId(rs.getString("daw_task_id"));
				task.setLib(rs.getString("lib"));
				task.setLanguage(rs.getString("language"));
				task.setPath(rs.getString("path"));
				task.setDependencies(rs.getString("dependencies"));
				task.setDescription(rs.getString("description"));
			}
			
			

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return task;

	}
	
	public List<Task> list() {

		List<Task> tasks= new ArrayList<Task>();

		try {
			Connection conn = openConnection();

			// the mysql insert statement
			String query = " select id,name,lib,language,path,dependencies,description,daw_task_id from task";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();
			while(rs.next()){
				Task task=new Task();
				task.setIdTask(rs.getLong("id"));
				task.setName(rs.getString("name"));
				task.setDawTaskId(rs.getString("daw_task_id"));
				task.setLib(rs.getString("lib"));
				task.setLanguage(rs.getString("language"));
				task.setPath(rs.getString("path"));
				task.setDependencies(rs.getString("dependencies"));
				task.setDescription(rs.getString("description"));
				task.setDawTaskId(rs.getString("daw_task_id"));
				tasks.add(task);
			}
			
			

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return tasks;

	}
	
	public List<Task> findByModel(String dawTaskId) {

		List<Task> tasks= new ArrayList<Task>();

		try {
			Connection conn = openConnection();

			// the mysql insert statement
			String query = " select id,name,lib,language,path,dependencies,description from task where daw_task_id like ?";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, dawTaskId);
			ResultSet rs = preparedStmt.executeQuery();
			while(rs.next()){
				Task task=new Task();
				task.setIdTask(rs.getLong("id"));
				task.setName(rs.getString("name"));
				task.setDawTaskId(dawTaskId);
				task.setLib(rs.getString("lib"));
				task.setLanguage(rs.getString("language"));
				task.setPath(rs.getString("path"));
				task.setDependencies(rs.getString("dependencies"));
				task.setDescription(rs.getString("description"));
				tasks.add(task);
			}
			
			

			conn.close();
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}

		return tasks;

	}



	public boolean deleteById(long id) {

		boolean result = false;

		try {
			Connection conn = openConnection();

			// the mysql insert statement
			String query = " delete from task where id=?";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setLong(1, id);
			

			// execute the preparedstatement
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
	
	
	public boolean deleteByModel(String dawTaskId) {

		boolean result = false;

		try {
			
			Connection conn = openConnection();

			// the mysql insert statement
			String query = " delete from task where daw_task_id=?";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, dawTaskId);
			

			// execute the preparedstatement
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
	
	private Connection openConnection() throws Exception{
		
		String driver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://"+dbUrl;
		Class.forName(driver);
		return DriverManager.getConnection(myUrl, dbUsr,dbPwd);
	}
}
