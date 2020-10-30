<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Evotion</title>
<link href="css/bootstrap.min.css" rel="stylesheet"/>
<link href="css/stile.css" rel="stylesheet" type="text/css" />
<link href='https://fonts.googleapis.com/css?family=Roboto:400,300italic,700,700italic' rel='stylesheet' type='text/css'/>
<link rel="stylesheet" href="font-awesome/css/font-awesome.min.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">

var dawTaskId="";
$( document ).ready(function() {
	<%
	if(session.getAttribute( "logined" )==null){
		%>
		window.location.href = "login.jsp";
		<%
	}
	String taskId = request.getParameter("id_task");
	if (taskId == null) {
		taskId = "0";
	}
	String dawTaskId = request.getParameter("daw_task_id");
	if (dawTaskId != null && !dawTaskId.equals("")) {%>
	dawTaskId = "<%=dawTaskId%>";
	<%}%>
	
	var taskId =<%=taskId%>;
	
	(function ($) {
	    $.fn.serializeFormJSON = function () {

	        var o = {};
	        var a = this.serializeArray();
	        $.each(a, function () {
	            if (o[this.name]) {
	                if (!o[this.name].push) {
	                    o[this.name] = [o[this.name]];
	                }
	                o[this.name].push(this.value || '');
	            } else {
	                o[this.name] = this.value || '';
	            }
	        });
	        return o;
	    };
	})(jQuery);
	
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
		
			$('#idTask').val(data.idTask);
			$('#name').val(data.name);
			$('#dawTaskId').val(data.dawTaskId);
			$('#lib').val(data.lib);
			$('#language').val(data.language);
			$('#path').val(data.path);
			$('#dependencies').val(data.dependencies);
			$('#description').val(data.description);

		});

	} 
	
	$('#contenitore_menu').css("height", $(document).height()-60);
	
	$("#saveTaskButton").on("click", function (e) {
		
		$('#error').hide();
		$('#error').html();
		
		var dataString=$('#taskForm').serializeFormJSON();
		var url="./api/tasks/add";
		console.log(JSON.stringify(dataString));
		if($('#idTask').val()!="0"){
			url="./api/tasks/modify";
		}
		$.ajax({
			 headers: { 
			        'Accept': 'application/json',
			        'Content-Type': 'application/json' 
			    },
			  url: url,
			  cache: false,
			  dataType: 'json',
			  data: JSON.stringify(dataString),
			  async:false,
			  type:'POST',
			  error: function(e) {
					$('#error').show();
					$('#error').html(e.responseText);
				  }
			 
			}).done(function( data ) {	
				
					
					window.location.href = "tasks_list.jsp?daw_task_id="+dawTaskId;
				
				
			});
		
	});
	
			
});	

</script>

</head>

<body>
<div class="container-fluid" id="contenitore">
    <div class="row">
        <div class="col-md-2" id="logo"><a href="tasks_list.jsp"><img src="images/logo_interno.png" alt="Evotion" /></a></div>
        <div class="col-md-10" id="top">
         <h2>Administration Backend Dashboard</h2>
        	<button type="button" class="btn btn-primary" id="logout" onclick="javascript:location='logout.jsp';">
              <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Logout
            </button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2" id="contenitore_menu">
        	<jsp:include page="menu.jsp" />
        </div>
        <div class="col-md-10" id="contenitore_centrale">
        	<h1>Task</h1>      
        	<br/>
        	<div class="contenitore_filtri" id="error" style="display:none;color:red">
             </div>          
            <div class="contenitore_tabella">
           
           <form id="taskForm" name="taskForm" action="" method="post" class="form-horizontal" >
           <input type="hidden" name="idTask" id="idTask" value="0"/>
              <div class="form-group">
                <label class="col-sm-2 control-label">Name</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" name="name" id="name" value="" required/>
                </div>
              </div>
               <div class="form-group">
                <label class="col-sm-2 control-label">Daw Task ID</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" name="dawTaskId" id="dawTaskId" value="" required/>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">Lib</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" name="lib" id="lib" value="" required/>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">Language</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" name="language" id="language" value="" required/>
                </div>
              </div>
               <div class="form-group">
                <label class="col-sm-2 control-label">Path</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" name="path" id="path" value="" required/>
                </div>
              </div>
               <div class="form-group">
                <label class="col-sm-2 control-label">Dependencies</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" name="dependencies" id="dependencies" value="" required/>
                </div>
              </div>
             
              <div class="form-group">
                <label class="col-sm-2 control-label">Description</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" name="description" id="description" value="" required/>
                </div>
              </div>
              
             
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <button type="button" class="btn btn-primary" onclick="document.location.href='tasks_list.jsp?daw_task_id=<%=dawTaskId%>'">CANCEL</button>&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success" name="saveTaskButton" id="saveTaskButton">SAVE</button>
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
