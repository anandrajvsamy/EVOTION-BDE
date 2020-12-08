<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>Evotion</title>
<link href="css/bootstrap.min.css" rel="stylesheet"/>
<link href="css/stile.css" rel="stylesheet" type="text/css" />
<link href='https://fonts.googleapis.com/css?family=Roboto:400,300italic,700,700italic' rel='stylesheet' type='text/css'/>
<link rel="stylesheet" href="font-awesome/css/font-awesome.min.css"/>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">

var dawTaskId="";

function addTask(){
	
	window.location.href = "task_add.jsp?daw_task_id="+dawTaskId;
}

function searchTasks(){
	$('#error').hide();
	$('#error').html();
dawTaskId=$("#dawTaskId").val();
var url="./api/tasks/list";
if(dawTaskId!=''){
	url="./api/tasks/findByModel/"+dawTaskId;
}
$.ajax({
	  url: url,
	  cache: false,
	  dataType: 'json',
	  async:false,
	  type:'GET',
	  error: function(e) {
			$('#error').show();
			$('#error').html(e.responseText);
		  }

	 
	}).done(function( data ) {	
		
		$('#tasks-table tr').remove();
		
		if(data.length>0){
			
        	var intes='<tr><th>ID</th><th>NAME</th><th>DAW TASK ID</th><th>LIB</th><th>LANGUAGE</th><th>PATH</th></tr>';
			$("#tasks-table").append(intes);	
			var p_rows='';
			for(var i=0;i<data.length;i++){
			
				var item=data[i];
				
				p_rows=p_rows+'<tr onclick="javascript:location=\'task_detail.jsp?id_task='+item.idTask+'&daw_task_id='+dawTaskId+'\'"><td>'+item.idTask+'</td><<td class="voci_tabella" >'+item.name+'</td><td class="voci_tabella">'+item.dawTaskId+'</td><td class="voci_tabella">'+item.lib+'</td><td class="voci_tabella">'+item.language+'</td><td class="voci_tabella">'+item.path+'</td></tr>';
			
			
			
			}
			$("#tasks-table").append(p_rows);	
		}	
		
	});

}

$( document ).ready(function() {
	
	<%
	if(session.getAttribute( "logined" )==null){
		
		%>
		window.location.href = "login.jsp";
		<%
	}
	%>
	
	<%
	String dawTaskId = request.getParameter("daw_task_id");
	if (dawTaskId != null && !dawTaskId.equals("")) {%>
		dawTaskId = "<%=dawTaskId%>";
	<%}%>
	if(dawTaskId!=null && dawTaskId!=""){
		$("#dawTaskId").val(dawTaskId);
		$("#searchTasksButton").click();
		searchTasks();
	}
	
	$('#contenitore_menu').css("height", $(document).height()-60);
	
	
	
	$("#searchTasksButton").on("click", function (e) {
		
		searchTasks();
		
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
        	<h1>Tasks Catalogue</h1>
            <div class="row">
                <div class="col-md-12"><button type="button" class="btn btn-success pull-right" id="bottone_inserisci" onclick="return addTask();"><span class="glyphicon glyphicon-asterisk " aria-hidden="true"></span> ADD TASK</button></div>
            </div>
            <br />
             <div class="contenitore_filtri" id="error" style="display:none;color:red">
             </div>
            <div class="collapse in" id="ContFiltro">
            <div class="contenitore_filtri">
            	<form class="form-inline" action="" method="post">
                  <div class="form-group">
                    <input type="text" class="form-control" placeholder="DAW Task ID" name="dawTaskId" id="dawTaskId" value=""/>
                  </div>
                  <div class="form-group">
                 	 <button type="button" class="btn btn-primary" name="searchTasksButton" id="searchTasksButton"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> SEARCH</button>
                 </div>
                </form>
            </div>
            <br />
            <div class="contenitore_tabella">
            <table class="table table-hover" id="tasks-table">
            	
            </table>
            </div>
        </div>
    </div>
</div>


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
