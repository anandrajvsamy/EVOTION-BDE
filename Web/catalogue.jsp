<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Evotion</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/stile.css" rel="stylesheet" type="text/css" />
<link href='https://fonts.googleapis.com/css?family=Roboto:400,300italic,700,700italic' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
$( document ).ready(function() {
	
	<%
	if(session.getAttribute( "logined" )==null){
		%>
		window.location.href = "login.jsp";
		<%
	}
	%>
	
	$('#contenitore_menu').css("height", $(document).height()-60);
	
	
	
	$.ajax({
		  url: "./api/analytics/evotionList",
		  cache: false,
		  dataType: 'json',
		  async:false,
		  type:'GET'
		 
		}).done(function( data ) {	
		 
			var htmlList="";
			for (var i = 0; i < data.length; i++) {
				var name=data[i].name;
				if(name.startsWith("Evotion/")){
					htmlList=htmlList+"<tr><td>"+data[i].id+"</td><td>"+name.replace("Evotion/","")+"</td></tr>";
				}
			}
			$("#catalogue_list").html(htmlList);
		});
	
});	
</script>

</head>

<body>
<div class="container-fluid" id="contenitore">
    <div class="row">
        <div class="col-md-2" id="logo"><a href="policies_list"><img src="images/logo_interno.png" alt="Evotion" /></a></div>
        <div class="col-md-10" id="top">
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
        	<h1>Catalogue</h1>
            
            <br />
            <div class="contenitore_tabella">
            <table class="table table-hover">
            	<thead>
                    <tr>
                    	<th>ID</th>        
                        <th>ANALYTICS</th>                        
                    </tr>
                </thead>
                <tbody id="catalogue_list">
                	
                </tbody>
            </table>
            </div>
        </div>
    </div>
</div>


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
