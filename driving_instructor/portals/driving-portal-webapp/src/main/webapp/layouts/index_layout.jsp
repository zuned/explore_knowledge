<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">	
		<%@ include file="../includes/_style.jsp" %>
		<%@ include file="../includes/_scripts.jsp" %>
		<%@ include file="../includes/_tagLibs.jsp" %>
		<decorator:usePage id="indexLayoutPage" />
		<title>:: <s:message code="label.portal.name" text="British School ofDriving" /> ::</title>
		<decorator:head />
	</head>
	<body>
		<%@ include file="../includes/_header.jsp" %>
		
		<!-- Beginning of Main Article -->
		<article class="mainArticle">
		  <div class="container"> 		    
		    <!-- Beginning of Breadcrum -->
		    <div class="breadcrumbWrapper">
		      <div class="clearfix">
		        <ul class="breadcrumb pull-left">
		          <li><a href="${pageContext.request.contextPath}/admin/"><s:message code="label.layout.home" text="Driving School Instructor"/></a></li> 				
	 				<c:if test="${not empty breadcrum}">
	 					<li> &gt; </li>		       	
						<decorator:getProperty property="page.breadcrum"/>
	 				</c:if> 				
		        </ul>
		        <div class="welcomeMessage pull-right">
		        	<security:authorize access="isAuthenticated()">
		        		<s:message code="label.layout.welcome" text="Welcome"/>, 
		        		<a href="#"><security:authentication property="principal.username" /></a> | <a href="${pageContext.request.contextPath}/admin/logout">Sign Out</a>
		        	</security:authorize>
		        </div>
		      </div>
		    </div>
		    <!-- End of Breadcrum -->	
				    <!--  Dynamic content -->
					<decorator:body />
					<!--  END Dynamic content -->
			</div>
		</article>
		<%@ include file="../includes/_footer.jsp" %>
	</body>
</html>