$(document).ready(function(e){
$("#formsubmit").click(function(e){
       e.preventDefault();
       var email =$("#email").val();
       var password =$("#password").val();
            console.log(email+" "+password);
            var path = $("#container").attr("path");
            console.log(path)
            $.ajax({
            url: path,
            type:"GET",
            data:{
                email:email,
                password:password,
                 },
            dataType:"text",
            success:function(resultdata ,textStatus, jqXHR)
            {
              if(jqXHR.status==200)
                {
                    if(email == "" || password =="")
                       {
                           alert("Enter credentials");
                           return;

                       }

                    alert("User Found");
					window.location.href = "/content/techcombank/logincomponent/welcome-to-business.html";
                }
            },
            error:function(resultdata ,textStatus, jqXHR){

                if (jqXHR=="Unauthorized") {
					 alert("Invalid Credentials");
                    window.location.href = "/content/techcombank/logincomponent.html";
				 } 
				else if (jqXHR == "Not Found") {
					alert("user not found");
                    window.location.href = "/content/techcombank/logincomponent/home-page.html";
				}
				else {
					alert("Site is under maintainance");
				}

          }
      })

     }
  );}
 );



