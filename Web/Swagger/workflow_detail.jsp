<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Evotion</title>
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/stile.css" rel="stylesheet" type="text/css" />
<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,300italic,700,700italic'
	rel='stylesheet' type='text/css' />
<link rel="stylesheet" href="font-awesome/css/font-awesome.min.css" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">

var dawId="";
var workflowId="0";

function visualizazionLink(){
	
	var jobId=$('#jobId').html();
	var settings = {
			  "async": true,
			  "crossDomain": true,
			  "url": "http://master.localdomain:9995/api/login",
			  "method": "POST",
			  "headers": {
			    
			    "content-type": "application/x-www-form-urlencoded"
			  },
			  "data": {
			    "userName": "admin",
			    "password": "admin"
			  }
			}

			$.ajax(settings).done(function (response) {
			  console.log(response);
			});
	
	
	$.ajax({
		
		url : "http://master.localdomain:9995/api/notebook/",
		cache : false,
		dataType : 'json',
		type : 'GET',
		data: JSON.stringify("userName=admin&password=admin"),
		
	}).done(function(data) {
		alert(JSON.stringfy(data));

	});
	
		
	/*
	$.ajax({
		  url: "./api/workflows/test/",
		  cache: false,
		  async:false,
		  type:'GET',
		  error: function(e) {
				console.log(e);
			  }

		
	}).done(function(data) {
		
		alert(JSON.stringify(data));

	});*/
}

function findZeppelinUrl(){
	
	$.ajax({
		url : "http://172.25.41.13:9995/api/notebook/",
		cache : false,
		dataType : 'json',
		type : 'GET',
		error: function(e) {
			$('#error').show();
			$('#error').html(e.responseText);
		  }

	}).done(function(data) {
	
		var nb=data.body;
		var notebook="";
		var name=$('#jobId').html();
		name=name.replace(/-/g,"");
		
		for(i=0;i<nb.length;i++){
			if(nb[i].name==name){
				notebook=nb[i].id;
				
			}
		}
		
		if(notebook==""){
			var query=$('#visQuery').html();
			query=query.replace("{table}",name);
			alert(query);
			var data='{"name": "'+name+'","paragraphs": [{"title": "Data Visualzazion","text": "%jdbc(phoenix)\n\n'+query+'"}]}';
			alert(data);
			$.ajax({
				url : "http://172.25.41.13:9995/api/notebook",
				cache : false,
				dataType : 'json',
				data: data,
				type : 'POST',
				error: function(e) {
					$('#error').show();
					$('#error').html(e.responseText);
				  }

			}).done(function(data) {
				
				if(data.status=="CREATED"){
					$('#visLink').html('<a href="http://172.25.41.13:9995/#/notebook/'+data.body+'" target="blank">View</a>');
				}
			});
			
		}else{
			$('#visLink').html('<a href="http://172.25.41.13:9995/#/notebook/'+notebook+'" target="blank">View</a>');
		}
	
		
	
		
	});
}


function updateWorkflowStatus(){
	
	$.ajax({
		url : "./api/workflows/status/" + workflowId,
		cache : false,
		dataType : 'json',
		type : 'GET',
		error: function(e) {
			$('#status').html(e.responseText);
			
			if(e.responseText=='SUCCEEDED'){
				findZeppelinUrl();
				
				
			}
			
			$.ajax({
				url : "./api/workflows/find/" + workflowId,
				cache : false,
				dataType : 'json',
				type : 'GET',
				error: function(e) {
					$('#error').show();
					$('#error').html(e.responseText);
				  }

			}).done(function(data) {

				
				$('#jobId').html(data.jobId);
				
			});

	  	}
	});
	
	
	
}

function executeAction(){
	$('#error').hide();
	$('#error').html();
	
	if($('#workflowAction').val()!=''){
		$.ajax({
			url : "./api/workflows/"+$('#workflowAction').val()+"/" + workflowId,
			cache : false,
			dataType : 'json',
			type : 'GET',
			error: function(e) {
				if(e.status!=200){
					$('#error').show();
					$('#error').html(e.responseText);
				}
				updateWorkflowStatus();
		  	}

		}).done(function(data) {
		
			

		});
	}
}

function stopWorkflow(){
	$('#error').hide();
	$('#error').html();
	$.ajax({
		url : "./api/workflows/stop/" + workflowId,
		cache : false,
		dataType : 'json',
		type : 'GET',
		error: function(e) {
			$('#error').show();
			$('#error').html(e.responseText);
		  }

	}).done(function(data) {
		
		

	});
	
}



function deleteWorkflow(){
	$('#error').hide();
	$('#error').html();
	
	$.ajax({
		url : "./api/workflows/remove/" + workflowId,
		cache : false,
		dataType : 'json',
		type : 'DELETE',
		error: function(e) {
			$('#error').show();
			$('#error').html(e.responseText);
		}
	}).done(function(data) {
		
		if(data)
			window.location.href = "workflows_list.jsp?daw_id="+dawId;

	});
	
}




	$(document)
			.ready(function() {
					
		
<%if (session.getAttribute("logined") == null) {%>
	window.location.href = "login.jsp";
<%}
			String workflowId = request.getParameter("id_workflow");
			if (workflowId == null) {
				workflowId = "0";
			}
			String dawId = request.getParameter("daw_id");
			if (dawId != null && !dawId.equals("")) {%>
			dawId = "<%=dawId%>";
		<%}%>
		
		workflowId =<%=workflowId%>;
	$('#contenitore_menu').css("height",
								$(document).height() - 60);

						

						if (workflowId > 0) {
							$('#error').hide();
							$('#error').html();
							
							$.ajax({
								url : "./api/workflows/find/" + workflowId,
								cache : false,
								dataType : 'json',
								type : 'GET',
								error: function(e) {
									$('#error').show();
									$('#error').html(e.responseText);
								  }

							}).done(function(data) {

								$('#idEdaw').html(data.idEdaw);
								$('#name').html(data.name);
								$('#dawId').html(data.dawId);
								$('#tasks').html(data.tasks);
								$('#parameters').html(data.parameters);
								$('#language').html(data.language);
								$('#path').html(data.path);
								$('#executionType').html(data.executionType);
								$('#description').html(data.description);
								$('#jobId').html(data.jobId);
								$('#visQuery').html(data.visQuery);
								
								updateWorkflowStatus();
							});

						} else {

							window.location.href = "workflows_list.jsp?daw_id="+dawId;
						}

						/*
						$("#runPolicy").on("click", function (e) {
							
						
							$.ajax({
								  url: "./api/analytics/run?taskId="+policyId,
								  cache: false,
								  dataType: 'json',
								  data:'policyId='+policyId,
								  async:false,
								  type:'GET'
								 
								}).done(function( data ) {	
								
									updatePolicyDatails(policyId);
								});
							
						});
						

						$("#refreshPolicy").on("click", function (e) {
						
							updatePolicyDatails(policyId);
						});

						$("#stopPolicy").on("click", function (e) {
						
						
						$.ajax({
							  url: "./api/analytics/stop?policyId="+policyId,
							  cache: false,
							  dataType: 'json',
							  async:false,
							  type:'GET'
							 
							}).done(function( data ) {	
							
								updatePolicyDatails(policyId);
							});
						
						});

						updatePolicyDatails(policyId);
						 */

					});

	/*
	 function getDate() {
	 function pad(s) { return (s < 10) ? '0' + s : s; }
	 var d = new Date();
	
	 return pad(d.getDate())+"/"+pad(d.getMonth()+1)+"/"+d.getFullYear()+" "+pad(d.getHours())+":"+pad(d.getMinutes());
	 }

	 */
</script>

</head>

<body>
	<div class="container-fluid" id="contenitore">
		<div class="row">
			<div class="col-md-2" id="logo">
				<a href="polocies_list.jsp"><img src="images/logo_interno.png"
					alt="Evotion" /></a>
			</div>
			<div class="col-md-10" id="top">
			 <h2>Administration Backend Dashboard</h2>
				<button type="button" class="btn btn-primary" id="logout"
					onclick="javascript:location='logout.jsp';">
					<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
					Logout
				</button>
			</div>
		</div>
		<div class="row">
			<div class="col-md-2" id="contenitore_menu">
				<jsp:include page="menu.jsp" />
			</div>
			<div class="col-md-10" id="contenitore_centrale">
				<h1>Workflow details</h1>
				<br/>
        	<div class="contenitore_filtri" id="error" style="display:none;color:red">
             </div>   
				<div class="contenitore_tabella">
					<form class="form-horizontal">

						<div class="form-group">
							<label class="col-sm-2 control-label">Id</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="idEdaw"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="name"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">DAW Id</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="dawId"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Tasks</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="tasks"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Parameters</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="parameters"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Language</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="language"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Path</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="path"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Execution Type</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="executionType"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Description</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="description"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Vis. Query</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="visQuery"></p>
								&nbsp;&nbsp;&nbsp;<span id="visLink"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Job Id</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="jobId"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Status</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="status"></p>
							</div>
						</div>
						
						<div class="form-group">
                			<div class="col-sm-offset-2 col-sm-10">
                  				<button type="button" class="btn btn-primary" onclick="javascript:location='workflows_list.jsp?daw_id=<%=dawId%>';">BACK</button>&nbsp;&nbsp;&nbsp;
                  				<button type="button" class="btn btn-success" onclick="document.location.href='workflow_add.jsp?id_workflow=<%=workflowId%>&daw_id=<%=dawId%>'">MODIFY</button>&nbsp;&nbsp;&nbsp;
                  				<button type="button" class="btn btn-danger" data-toggle="collapse" data-target="#alert">DELETE</button>
                  				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  				<select id="workflowAction" class="form-control" style="width:100px;display:inline-block">
                  					<option value="">Action...</option>
                  					<option value="submit">Submit</option>
                  					<option value="start">Start</option>
                  					<option value="run">Run</option>
                  					<option value="rerun">Re-run</option>
                  					<option value="suspend">Suspend</option>
                  					<option value="resume">Resume</option>
                  					<option value="kill">Kill</option>
                  				</select>
                  				<button type="button" class="btn btn-warning" onclick="return executeAction();">GO</button>
                			</div>
              			</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div id="alert" class="alert alert-danger collapse" role="alert">
									Are you sure?
									<button type="button" class="btn btn-danger" onclick="return deleteWorkflow()">YES</button>
									&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-danger"
										data-toggle="collapse" data-target="#alert">NO</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
