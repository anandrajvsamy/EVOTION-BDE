package evotion.api;

import java.util.HashMap;
import java.util.List;

import io.swagger.annotations.*;
import it.unimi.evotion.api.controller.OozieMng;
import it.unimi.evotion.api.controller.WorkflowsMng;
import it.unimi.evotion.api.model.Workflow;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/workflows")
@Api(value = "/workflows")
public class WorkflowService {

	@POST
	@Path("add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@ApiOperation(value = "Add an EDAW into the Workflow Catalogue.")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "One or more required fields are missing") })
	public Response add(
			@ApiParam(value = "Edaw object that needs to be added with the list of Tasks' actual parameters", required = true) Workflow workflow) {

		if (workflow.getName() == null || workflow.getName().equals("")
				|| workflow.getDawId() == null
				|| workflow.getDawId().equals("")
				|| workflow.getTasks() == null
				|| workflow.getTasks().equals("")
				|| workflow.getParameters() == null
				|| workflow.getParameters().equals("")
				|| workflow.getLanguage() == null
				|| workflow.getLanguage().equals("")
				|| workflow.getPath() == null || workflow.getPath().equals("")
				|| workflow.getDescription() == null
				|| workflow.getDescription().equals("")) {
			return Response.status(400)
					.entity("One or more required fields are missing").build();
		}

		if (workflow.getExecutionType() == null)
			workflow.setExecutionType("");
		long id = new WorkflowsMng().insert(workflow);
		return Response.ok().entity(id).build();
	}

	@POST
	@Path("modify")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@ApiOperation(value = "Modify any of the Edaw related attributes.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "One or more required fields are missing"),
			@ApiResponse(code = 404, message = "Workflow to update not found") })
	public Response modify(
			@ApiParam(value = "Workflow that needs to be modified", required = true) Workflow workflow) {

		if (workflow.getName() == null || workflow.getName().equals("")
				|| workflow.getDawId() == null
				|| workflow.getDawId().equals("")
				|| workflow.getTasks() == null
				|| workflow.getTasks().equals("")
				|| workflow.getParameters() == null
				|| workflow.getParameters().equals("")
				|| workflow.getLanguage() == null
				|| workflow.getLanguage().equals("")
				|| workflow.getPath() == null || workflow.getPath().equals("")
				|| workflow.getDescription() == null
				|| workflow.getDescription().equals("")) {
			return Response.status(400)
					.entity("One or more required fields are missing").build();
		}

		if (workflow.getExecutionType() == null)
			workflow.setExecutionType("");

		boolean result = new WorkflowsMng().update(workflow);
		if (result)
			return Response.ok().entity(result).build();
		else
			return Response.status(404).entity("Workflow to update not found")
					.build();
	}

	@DELETE
	@Path("removeByModel/{dawId}")
	@ApiOperation(value = "Remove all the implementation of a given DAW Model Instance")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid dawId supplied"),
			@ApiResponse(code = 404, message = "Workflows to remove not found") })
	public Response removeByModel(
			@ApiParam(value = "String representing a DAW Model Instance", required = true) @PathParam("dawId") String dawId) {

		if (dawId == null || dawId.equals("")) {
			return Response.status(400).entity("Invalid dawId supplied")
					.build();
		}

		boolean result = new WorkflowsMng().deleteByModel(dawId);
		if (result)
			return Response.ok().entity(result).build();
		else
			return Response.status(404).entity("Workflows to remove not found")
					.build();
	}

	@DELETE
	@Path("remove/{edawId}")
	@ApiOperation(value = "Remove a given Edaw from the Catalogue")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid edawId supplied"),
			@ApiResponse(code = 404, message = "Workflow to remove not found") })
	public Response remove(
			@ApiParam(value = "Edaw Id to remove", required = true) @PathParam("edawId") Long edawId) {

		if (edawId == null || edawId <= 0) {
			return Response.status(400).entity("Invalid edawId supplied")
					.build();
		}

		boolean result = new WorkflowsMng().deleteById(edawId);
		if (result)
			return Response.ok().entity(result).build();
		else
			return Response.status(404).entity("Workflow to remove not found")
					.build();
	}

	@GET
	@Path("list")
	@ApiOperation(value = "Return list of all Edaw")
	@ApiResponses(value = {
			
			@ApiResponse(code = 404, message = "Workflows not found") })
	public Response findByModel() {

	
		List<Workflow> workflows = new WorkflowsMng().list();
		if (workflows == null || workflows.size() == 0)
			return Response.status(404).entity("Workflows not found").build();
		return Response.ok().entity(workflows).build();
	}

	
	@GET
	@Path("findByModel/{dawId}")
	@ApiOperation(value = "Return list of Edaw for a given DAW ID")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid dawId supplied"),
			@ApiResponse(code = 404, message = "Workflows not found") })
	public Response findByModel(
			@ApiParam(value = "Daw Id of Edaw to return") @PathParam("dawId") String dawId) {

		if (dawId == null || dawId.equals("")) {
			return Response.status(400).entity("Invalid dawTaskId supplied")
					.build();
		}

		List<Workflow> workflows = new WorkflowsMng().findByModel(dawId);
		if (workflows == null || workflows.size() == 0)
			return Response.status(404).entity("Workflows not found").build();
		return Response.ok().entity(workflows).build();
	}

	@GET
	@Path("find/{edawId}")
	@ApiOperation(value = "Find Edaw by id")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid edawId supplied"),
			@ApiResponse(code = 404, message = "Workflow not found") })
	public Response find(
			@ApiParam(value = "Id of Edaw to return") @PathParam("edawId") Long edawId) {

		if (edawId == null || edawId <= 0) {
			return Response.status(400).entity("Invalid edawId supplied")
					.build();
		}

		Workflow workflow = new WorkflowsMng().findById(edawId);
		if (workflow == null)
			return Response.status(404).entity("Workflow not found").build();
		return Response.ok().entity(workflow).build();
	}

	/* Gstione workflow in esecuzione */

	@GET
	@Path("submit/{edawId}")
	@ApiOperation(value = "Submit a specific Edaw to the scheduler with its parameters.")
	public Response submit(
			@ApiParam(value = "Edaw Id to submit", required = true) @PathParam("edawId") Long edawId) {

		WorkflowsMng mng = new WorkflowsMng();
		Workflow workflow = mng.findById(edawId);
		String jobId = "";
		System.out.println("SUBMIT"+workflow.getJobId());
		if (workflow.getJobId() == null || workflow.getJobId().equals("")) {

			HashMap<String, String>properties=formatWorkflowParameter(workflow.getExecutionType(),workflow.getPath(), workflow.getParameters());
			System.out.println("OTTENGO HASHMAP");
			try {
				jobId = new OozieMng().submit(properties);

				if (jobId != null && !jobId.equals("")) {
					System.out.println("salvo");
					workflow.setJobId(jobId);
					if (mng.updateJobId(edawId, jobId)) {
						return Response.ok().entity(jobId).build();
					}else
						throw new Exception("JobId not saved.");
				} else {
					throw new Exception("Workflow not submitted.");
				}
			} catch (Exception ex) {
				return Response.status(500).entity(ex.getMessage()).build();
			}
		} else {
			return Response.status(400).entity("Workflow in use.").build();
		}
	}
	
	@GET
	@Path("start/{edawId}")
	@ApiOperation(value = "Start a specific Edaw according to the scheduler preference.")
	public Response start(
			@ApiParam(value = "Edaw Id to start", required = true) @PathParam("edawId") Long edawId) {

		WorkflowsMng mng = new WorkflowsMng();
		Workflow workflow = mng.findById(edawId);
		
		if (workflow.getJobId() != null && !workflow.getJobId().equals("")) {

			try {
				HashMap<String, String>properties=formatWorkflowParameter(workflow.getExecutionType(),workflow.getPath(), workflow.getParameters());
				 new OozieMng().start(workflow.getJobId(),properties.get("user.name"));
			} catch (Exception ex) {
				return Response.status(500).entity(ex.getMessage()).build();
			}
		} else {
			return Response.status(400).entity("Workflow not submitted.").build();
		}
		return Response.ok().build();
	}

	@GET
	@Path("run/{edawId}")
	@ApiOperation(value = "Run a specific Edaw according to the scheduler preference and its parameters.")
	public Response run(
			@ApiParam(value = "Edaw Id to run", required = true) @PathParam("edawId") Long edawId) {

		WorkflowsMng mng = new WorkflowsMng();
		Workflow workflow = mng.findById(edawId);
		String jobId = "";
		if (workflow.getJobId() == null || workflow.getJobId().equals("")) {
			HashMap<String, String>properties=formatWorkflowParameter(workflow.getExecutionType(),workflow.getPath(), workflow.getParameters());
			try {
				jobId = new OozieMng().run(properties);

				if (jobId != null && !jobId.equals("")) {
					System.out.println("salvo");
					workflow.setJobId(jobId);
					if (mng.updateJobId(edawId, jobId)) {
						return Response.ok().entity(jobId).build();
					}else
						throw new Exception("JobId not saved.");
				} else {
					throw new Exception("Operation failed.");
				}
			} catch (Exception ex) {
				return Response.status(500).entity(ex.getMessage()).build();
			}
		} else {
			return Response.status(400).entity("Workflow in use.").build();
		}
	}

	
	@GET
	@Path("rerun/{edawId}")
	@ApiOperation(value = "Re-run a specific Edaw according to the scheduler preference and its parameters.")
	public Response rerun(
			@ApiParam(value = "Edaw Id to rerun.", required = true) @PathParam("edawId") Long edawId) {

		WorkflowsMng mng = new WorkflowsMng();
		Workflow workflow = mng.findById(edawId);
		
		if (workflow.getJobId() != null && !workflow.getJobId().equals("")) {
			HashMap<String, String>properties=formatWorkflowParameter(workflow.getExecutionType(),workflow.getPath(), workflow.getParameters());
			
			try {
				 new OozieMng().rerun(workflow.getJobId(),properties);
			} catch (Exception ex) {
				return Response.status(500).entity(ex.getMessage()).build();
			}
		} else {
			return Response.status(400).entity("Workflow not submitted.").build();
		}
		return Response.ok().entity("").build();
	}

	@GET
	@Path("resume/{edawId}")
	@ApiOperation(value = "Resume a specific Edaw.")
	public Response resume(
			@ApiParam(value = "Edaw Id to resume.", required = true) @PathParam("edawId") Long edawId) {

		WorkflowsMng mng = new WorkflowsMng();
		Workflow workflow = mng.findById(edawId);
		
		if (workflow.getJobId() != null && !workflow.getJobId().equals("")) {

			try {
				HashMap<String, String>properties=formatWorkflowParameter(workflow.getExecutionType(),workflow.getPath(), workflow.getParameters());
				
				 new OozieMng().resume(workflow.getJobId(),properties.get("user.name"));
			} catch (Exception ex) {
				return Response.status(500).entity(ex.getMessage()).build();
			}
		} else {
			return Response.status(400).entity("Workflow not submitted.").build();
		}
		return Response.ok().entity("").build();
	}
	
	@GET
	@Path("kill/{edawId}")
	@ApiOperation(value = "Kill a specific Edaw.")
	public Response stop(
			@ApiParam(value = "Edaw Id to kill.", required = true) @PathParam("edawId") Long edawId) {

		WorkflowsMng mng = new WorkflowsMng();
		Workflow workflow = mng.findById(edawId);
		
		if (workflow.getJobId() != null && !workflow.getJobId().equals("")) {

			try {
				HashMap<String, String>properties=formatWorkflowParameter(workflow.getExecutionType(),workflow.getPath(), workflow.getParameters());
				
				 new OozieMng().kill(workflow.getJobId(),properties.get("user.name"));
			} catch (Exception ex) {
				return Response.status(500).entity(ex.getMessage()).build();
			}
		} else {
			return Response.status(400).entity("Workflow not submitted.").build();
		}
		return Response.ok().entity("").build();
	}
	
	@GET
	@Path("suspend/{edawId}")
	@ApiOperation(value = "Suspend a specific Edaw.")
	public Response suspend(
			@ApiParam(value = "Edaw Id to suspend.", required = true) @PathParam("edawId") Long edawId) {

		WorkflowsMng mng = new WorkflowsMng();
		Workflow workflow = mng.findById(edawId);
		
		if (workflow.getJobId() != null && !workflow.getJobId().equals("")) {

			try {
				HashMap<String, String>properties=formatWorkflowParameter(workflow.getExecutionType(),workflow.getPath(), workflow.getParameters());
				
				 new OozieMng().suspend(workflow.getJobId(),properties.get("user.name"));
			} catch (Exception ex) {
				return Response.status(500).entity(ex.getMessage()).build();
			}
		} else {
			return Response.status(400).entity("Workflow not submitted.").build();
		}
		return Response.ok().entity("").build();
	}
	
	
	@GET
	@Path("status/{edawId}")
	@ApiOperation(value = "Status of a specific submitted Edaw.")
	public Response info(
			@ApiParam(value = "Edaw Id.", required = true) @PathParam("edawId") Long edawId) {

		WorkflowsMng mng = new WorkflowsMng();
		Workflow workflow = mng.findById(edawId);
		
		if (workflow.getJobId() != null && !workflow.getJobId().equals("")) {

			try {
				HashMap<String, String>properties=formatWorkflowParameter(workflow.getExecutionType(),workflow.getPath(), workflow.getParameters());
				boolean wf=false;
				if(properties.get("oozie.wf.application.path")!=null){
					wf=true;
				}
				 String status=new OozieMng().getStatus(workflow.getJobId(),properties.get("user.name"),wf);
				 return Response.ok().entity(status).build();
			} catch (Exception ex) {
				return Response.status(500).entity(ex.getMessage()).build();
			}
		} else {
			return Response.status(400).entity("Workflow not submitted.").build();
		}
		
	}
	
	
	@GET
	@Path("list/{status}")
	@ApiOperation(value = "Status of a specific submitted Edaw.")
	public Response list(
			@ApiParam(value = "Status of Edaw submitted to scheduler", required = true) @PathParam("status") String status) {

		try {
			
			List<String> jobIds=new OozieMng().getInfoList(status.toUpperCase());
			List<Workflow> wfs=new WorkflowsMng().findByJobIds(jobIds);
				 return Response.ok().entity(wfs).build();
			} catch (Exception ex) {
				return Response.status(500).entity(ex.getMessage()).build();
			}
		
	}
	
	
	
	private  HashMap<String, String> formatWorkflowParameter(String executionType,String path,String paramenters){
		HashMap<String, String> properties = new HashMap<String, String>();
		if (executionType != null
				&& !executionType.trim().equals("")) {
			properties.put("oozie.coord.application.path",
					path);
			System.out.println("Sched "+path);
		} else {
			properties.put("oozie.wf.application.path", path);
			System.out.println("wf "+path);
		}
		String wp = paramenters;
		if (wp != null && !wp.equals("")) {
			String last = wp.substring(wp.length() - 1);
			if (last.equals(";")) {
				wp = wp.substring(0, wp.length() - 1);
			}

			String[] params = wp.split(";");
			for (int i = 0; i < params.length; i++) {
				String[] p = params[i].split("=");
				properties.put(p[0], p[1]);
				System.out.println(p[0]+" "+p[1]);
			}

		}
		
		return properties;
	}
}
