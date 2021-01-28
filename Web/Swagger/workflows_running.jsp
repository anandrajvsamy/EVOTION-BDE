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

var dawId="";

function addWorkflow(){
	
	window.location.href = "workflow_add.jsp?daw_id="+dawId;
}

function searchWorkflows(){
	
	$('#error').hide();
	$('#error').html();
	
	
	var url="./api/workflows/list/RUNNING";
	
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
		
		$('#workflows-table tr').remove();
		
		if(data.length>0){
			
        	var intes='<tr><th>ID</th><th>NAME</th><th>DAW ID</th><th>LANGUAGE</th><th>PATH</th></tr>';
			$("#workflows-table").append(intes);	
			var p_rows='';
			for(var i=0;i<data.length;i++){
			
				var item=data[i];
				
				p_rows=p_rows+'<tr onclick="javascript:location=\'workflow_detail.jsp?id_workflow='+item.idEdaw+'&daw_id='+dawId+'\'"><td>'+item.idEdaw+'</td><<td class="voci_tabella" >'+item.name+'</td><td class="voci_tabella">'+item.dawId+'</td><td class="voci_tabella">'+item.language+'</td><td class="voci_tabella">'+item.path+'</td></tr>';
			
			
			
			}
			$("#workflows-table").append(p_rows);	
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
	String dawId = request.getParameter("daw_id");
	if (dawId != null && !dawId.equals("")) {%>
		dawId = "<%=dawId%>";
	<%}%>
	if(dawId!=null && dawId!=""){
		$("#dawId").val(dawId);
		$("#searchWorkflowsButton").click();
		searchWorkflows();
	}
	
	$('#contenitore_menu').css("height", $(document).height()-60);
	
	
	
	
		
		searchWorkflows();
		
	
	
});
</script>

</head>


<body>
<div class="container-fluid" id="contenitore">
    <div class="row">
        <div class="col-md-2" id="logo"><a href="workflows_running.jsp"><img src="images/logo_interno.png" alt="Evotion" /></a></div>
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
        	<h1>Running Workflows</h1>
              <br />
              <div class="contenitore_filtri" id="error" style="display:none;color:red">
             </div> 
            <div class="contenitore_tabella">
            <table class="table table-hover" id="workflows-table">
            	
            </table>
            </div>
        </div>
    </div>
</div>


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
