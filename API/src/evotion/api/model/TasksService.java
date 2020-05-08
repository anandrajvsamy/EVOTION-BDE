package evotion.api;

import java.util.List;

import io.swagger.annotations.*;
import it.unimi.evotion.api.controller.TasksMng;
import it.unimi.evotion.api.model.Task;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tasks")
@Api(value = "/tasks")
public class TasksService {

	@GET
	@Path("list")
	@ApiOperation(value = "Return list of all Tasks")
	@ApiResponses(value = {
			
			@ApiResponse(code = 404, message = "Taks not found") })
	public Response findByModel() {

	
		List<Task> tasks = new TasksMng().list();
		if (tasks == null || tasks.size() == 0)
			return Response.status(404).entity("Tasks not found").build();
		return Response.ok().entity(tasks).build();
	}
	
	@POST
	@Path("add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@ApiOperation(value = "Add an implementation of a given DAW Task into the Task Catalogue.")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "All fields are required")})
	public Response add(
			@ApiParam(value = "Task that needs to be added to the catalogue", required = true) Task task) {

		if(task.getName()==null || task.getName().equals("") || 
				task.getDawTaskId()==null || task.getDawTaskId().equals("") ||
				task.getLib()==null || task.getLib().equals("") ||
				task.getLanguage()==null || task.getLanguage().equals("") ||
				task.getPath()==null || task.getPath().equals("") || 
				task.getDescription()==null || task.getDescription().equals("")){
			return Response.status(400).entity("All fields are required").build();
		}
		
		long id = new TasksMng().insert(task);
		return Response.ok().entity(id).build();
	}

	@POST
	@Path("modify")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@ApiOperation(value = "Modify any of the Task related attributes.")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "All fields are required"),
			@ApiResponse(code = 404, message = "Task to update not found")})
	public Response modify(
			@ApiParam(value = "Task that needs to be modified", required = true) Task task) {

		if(task.getName()==null || task.getName().equals("") || 
				task.getDawTaskId()==null || task.getDawTaskId().equals("") ||
				task.getLib()==null || task.getLib().equals("") ||
				task.getLanguage()==null || task.getLanguage().equals("") ||
				task.getPath()==null || task.getPath().equals("") || 
				task.getDescription()==null || task.getDescription().equals("")){
			return Response.status(400).entity("All fields are required").build();
		}
		
		boolean result = new TasksMng().update(task);
		if(result)
			return Response.ok().entity(result).build();
		else 
			return Response.status(404).entity("Task to update not found").build();
	}

	@DELETE
	@Path("removeByModel/{dawTaskId}")
	@ApiOperation(value = "Remove all the implementation of a given DAW task model")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid dawId supplied"),
			@ApiResponse(code = 404, message = "Workflows to remove not found")})
	public Response removeByModel(
			@ApiParam(value = "String representing a DAW Task ID in the Ontology Manager", required = true) @PathParam("dawTaskId") String dawTaskId) {

		if(dawTaskId==null || dawTaskId.equals("")){
			return Response.status(400).entity("Invalid dawTaskId supplied").build();
		}
		
		boolean result=new TasksMng().deleteByModel(dawTaskId);
		if(result)
			return Response.ok().entity(result).build();
		else return Response.status(404).entity("Tasks to remove not found").build();
	}

	@DELETE
	@Path("remove/{taskId}")
	@ApiOperation(value = "Remove a specific implementation of a given Task")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid taskId supplied"),
			@ApiResponse(code = 404, message = "Task to remove not found")})
	
	public Response remove(
			@ApiParam(value = "Task Id to remove", required = true) @PathParam("taskId") Long taskId) {

		if(taskId==null || taskId<=0){
			return Response.status(400).entity("Invalid taskId supplied").build();
		}
		
		boolean result=new TasksMng().deleteById(taskId);
		if(result)
			return Response.ok().entity(result).build();
		else 
			return Response.status(404).entity("Task to remove not found").build();
	}

	@GET
	@Path("findByModel/{dawTaskId}")
	@ApiOperation(value = "Return list of the tasks for an implementation of a DAW Task ID")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid dawTaskId supplied"),
			@ApiResponse(code = 404, message = "Workflows not found")})
	public Response findByModel(
			@ApiParam(value = "Daw Task Id of tasks to return") @PathParam("dawTaskId") String dawTaskId) {

		if(dawTaskId==null || dawTaskId.equals("")){
			return Response.status(400).entity("Invalid dawTaskId supplied").build();
		}
		List<Task> tasks = new TasksMng().findByModel(dawTaskId);
		if(tasks==null || tasks.size()==0)
			return Response.status(404).entity("Tasks not found").build();
		return Response.ok().entity(tasks).build();
	}

	@GET
	@Path("find/{taskId}")
	@ApiOperation(value = "Find task by id")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid taskId supplied"),
			@ApiResponse(code = 404, message = "Task not found")})
	public Response find(
			@ApiParam(value = "ID of Task to return") @PathParam("taskId") Long taskId) {

		if(taskId==null || taskId<=0){
			return Response.status(400).entity("Invalid taskId supplied").build();
		}
		
		Task task = new TasksMng().findById(taskId);
		if(task==null)
			return Response.status(404).entity("Task not found").build();
		return Response.ok().entity(task).build();
	}
}
