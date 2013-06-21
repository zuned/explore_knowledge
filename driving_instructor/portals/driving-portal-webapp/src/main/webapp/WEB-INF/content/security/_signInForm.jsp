<%@ include file="../../../includes/_tagLibs.jsp" %>	     
	          <div class="loginSection">
	          	<h2 class="getQuote"><s:message code="label.signin.signIn" text="Sign In"/></h2>
	            <div class="loginForm">
	            <c:set var="errMessage" value="" />
	            <c:if test="${not empty param.login_error}">
	            <%-- <h3>${SPRING_SECURITY_LAST_EXCEPTION.message}</h3> --%>
	            <%-- ${SPRING_SECURITY_FORM_USERNAME_KEY} fdfg --%>
	            <c:choose>
	            	<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'Bad credentials'}">
	            		<c:set var="errMessage" value="The username or password you entered is incorrect"/>
	            	</c:when>
	            	<c:otherwise>
	            		<c:set var="errMessage" value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
	            	</c:otherwise>
	            </c:choose>	            	
	            </c:if>
	            	<form id="loginForm" name="login" method="post" action="${pageContext.request.contextPath}/admin/login/authenticate">
	            		<c:if test="${errMessage != '' }">
								<div class="error errorAlt" style="display: block;font-size:10px;" ><strong>${errMessage}</strong></div>
							</c:if>
	                	<div class="marginT10 posRel clearfix">
	                    	<label class="hide" for="j_username"><s:message code="label.signin.enterYourUserName" text="User Name"/></label>
	                    	<input type="text" class="userInput" name="j_username" id="j_username" value="${(sessionScope.username != '') ? sessionScope.username : '' }" placeholder="Enter your Username" />
	                      <span class="icon-user userIcon"></span>
	                    </div>	                    
	                    <div class="marginT10 posRel clearfix">
	                    	<label class="hide" for="j_password"><s:message code="label.signin.enterPassword" text="Password" /></label>
	                    	<input type="password" class="userInput" name="j_password" id="j_password" value="" placeholder="Password" />
	                      <span class="icon-lock userIcon"></span>
	                    <div class="form-inline clearfix marginB5">
	                    	<input type="checkbox" name="remember" id="remember" class="check" /> <label for="remember" class="checkbox loginLabel boldText"><s:message code="label.signin.rememberMyUsernameAndPassword" text="Remember My UserName And Passowrd" /></label>
	                        
	                    </div>
	                    <a href="#" class="marginT5"><s:message code="label.signin.forgotUsernameOrPassword" text="Forgot Your Password ?" /></a>
	                    <div class="form-inline clearfix marginT15"><input type="submit" id="submit" class="btn btn-medium colorBlue" value="<s:message code="label.signin.SUBMIT" text="Sign In" />" /> <span class="icon-arrow-left-blue margin0"></span></div>
	                    </div>
	                </form>
	            </div>
	            <div class="register marginT25">
	            	<h3 class="reg"><s:message code="label.signin.newusersSignUpNow" text="Sign Up Now" /></h3>
	                <p><s:message code="label.signin.youNeedToRegisterWithUsToAccessTheInformation" text="You Need To Register With Us To Access The Information"/></p>
	                <a href="${pageContext.request.contextPath}/admin/register" class="btn btn-medium"><s:message code="label.signin.registerNow" text="Rgister"/> <span class="icon-arrow-left-blue margin0"></span></a>
	                </div>          
	            </div>
	            
<script type="text/javascript">

 /* $.validator.setDefaults({
	submitHandler: function() { 
		$("#loginForm").submit();
	}
});  */
$(document).ready(function() {
	$('[placeholder]').placeholder();
	
	// validate the comment form when it is submitted
	//$("#commentForm").validate();

	// validate signup form on keyup and submit
	$("#loginForm").validate({
		rules: {
			username: {
				required: true,
			},
			password: {
				required: true,
				minlength: 5
			}
		},
		messages: {
			username: "NotBlank.login.username",
			password: {
				required: "NotBlank.login.password",
				minlength: "MinLength.login.password"
			}
		},
		errorClass: "errorLogin",
		errorElement: "div"/* ,
		highlight: function(element, errorClass) {
			if($(element).attr('type') == 'password'){
				$(element).next('.error').not(':first').remove();
			}
	   } */
	});	
});

</script>	            