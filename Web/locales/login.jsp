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
<link href='https://fonts.googleapis.com/css?family=Roboto:400,300italic,700,700italic' rel='stylesheet' type='text/css'/>
<link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	
	<%
	if(session.getAttribute( "logined" )!=null){
		
		%>
		window.location.href = "tasks_list.jsp";
		<%
	}
	%>
	$("#errore").hide();
	$("#errore_ex").hide();
	
	$("#login").on("click", function (e) {
		
		$("#errore").hide();
		$("#errore_ex").hide();
		
		$("#loading").css("visibility","visible");
		$.ajax({
			  url: "./api/users/login",
			  cache: false,
			  dataType: 'json',
			  async:false,
			  type:'GET'
			 
			}).done(function( data ) {	
			
				
				setTimeout(
			 			  function() 
			 			  {
			 				 $("#loading").css("visibility","hidden");
			 			  }, 500);
				
				if(data.token!="123456"){
					$("#errore").show();
				}else{
					window.location.href = "tasks_list.jsp";
				}
			});
		
	});
	
	
	
});
</script>
</head>

<body class="sfondo_azzurro">
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <div class="account-wall">
                <img class="profile-img" src="images/logo_evotion.png" alt="">
                <form id="loginForm" name="loginForm" action="login" method="post" class="form-signin" >
                <input type="text" class="form-control" placeholder="Utente" required autofocus name="username" id="username">
                <input type="password" class="form-control" placeholder="Password" required name="password" id="password">
                <button class="btn btn-lg btn-primary btn-block" type="button" name="login" id="login">
                    ACCEDI</button>
                <label class="checkbox pull-left">
                </form>
            </div>
        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
