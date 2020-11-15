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

var dawTaskId="";
var taskId="0";

function deleteTask(){
	
	$('#error').hide();
	$('#error').html();
	
	$.ajax({
		url : "./api/tasks/remove/" + taskId,
		cache : false,
		dataType : 'json',
		type : 'DELETE',
		error: function(e) {
			$('#error').show();
			$('#error').html(e.responseText);
		  }

	}).done(function(data) {
		
		if(data)
			window.location.href = "tasks_list.jsp?daw_task_id="+dawTaskId;

	});
	
}




	$(document)
			.ready(function() {
					
		
<%if (session.getAttribute("logined") == null) {%>
	window.location.href = "login.jsp";
<%}
			String taskId = request.getParameter("id_task");
			if (taskId == null) {
				taskId = "0";
			}
			String dawTaskId = request.getParameter("daw_task_id");
			if (dawTaskId != null && !dawTaskId.equals("")) {%>
			dawTaskId = "<%=dawTaskId%>";
		<%}%>
		
			taskId =<%=taskId%>;
	$('#contenitore_menu').css("height",
								$(document).height() - 60);

						

						if (taskId > 0) {
							$('#error').hide();
							$('#error').html();
							$.ajax({
								url : "./api/tasks/find/" + taskId,
								cache : false,
								dataType : 'json',
								type : 'GET',
								error: function(e) {
									$('#error').show();
									$('#error').html(e.responseText);
								  }

							}).done(function(data) {

								$('#idTask').html(data.idTask);
								$('#name').html(data.name);
								$('#dawTaskId').html(data.dawTaskId);
								$('#lib').html(data.lib);
								$('#language').html(data.language);
								$('#path').html(data.path);
								$('#dependencies').html(data.dependencies);
								$('#description').html(data.description);

							});

						} else {

							window.location.href = "tasks_list.jsp?daw_task_id="+dawTaskId;
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
				<h1>Task details</h1>
				<br/>
        	<div class="contenitore_filtri" id="error" style="display:none;color:red">
             </div>   
				<div class="contenitore_tabella">
					<form class="form-horizontal">

						<div class="form-group">
							<label class="col-sm-2 control-label">Id</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="idTask"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="name"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">DAW Task Id</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="dawTaskId"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Lib</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="lib"></p>
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
							<label class="col-sm-2 control-label">Dependencies</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="dependencies"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Description</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="description"></p>
							</div>
						</div>
						

						<div class="form-group">
                			<div class="col-sm-offset-2 col-sm-10">
                  				<button type="button" class="btn btn-primary" onclick="javascript:location='tasks_list.jsp?daw_task_id=<%=dawTaskId%>';">BACK</button>&nbsp;&nbsp;&nbsp;
                  				<button type="button" class="btn btn-success" onclick="document.location.href='task_add.jsp?id_task=<%=taskId%>&daw_task_id=<%=dawTaskId%>'">MODIFY</button>&nbsp;&nbsp;&nbsp;
                  				<button type="button" class="btn btn-danger" data-toggle="collapse" data-target="#alert">DELETE</button>
                			</div>
              			</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div id="alert" class="alert alert-danger collapse" role="alert">
									Are you sure?
									<button type="button" class="btn btn-danger" onclick="return deleteTask()">YES</button>
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
