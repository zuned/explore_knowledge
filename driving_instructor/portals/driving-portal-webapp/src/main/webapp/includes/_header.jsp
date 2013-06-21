<%@ include file="_tagLibs.jsp" %>
<%@ include file="_messages.jsp" %>

<header class="pageHeader">
    <div class="container">
        <div class="pull-left marginT10 marginB5">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="hide-text">British Driving School</a>
        </div>
        <aside class="pull-right">
            <ul class="topLinks align-right marginB5">
                <li><a href="${pageContext.request.contextPath}/admin/dashboard"><s:message code="label.layout.home" text="Home"/></a></li>
                <li>|</li>
                <li><a href="#"><s:message code="label.layout.whychooseus" text="Why Choose US"/></a></li>
                <li>|</li>
                <li><a href="#"><s:message code="label.layout.help" text="Hep" /></a></li>
                <li>|</li>
                <li><a href="#"><s:message code="label.layout.contactus" text="Contact Us"/></a></li>
            </ul>
            <div class="tollFree align-right"><s:message code="label.layout.callustollfree" text="Call Us Toll Free" /> <span>1800-3010-3333</span>
                <a href="#"><span><s:message code="label.layout.livechat" text="Live Chat"/></span></a>
            </div>
        </aside>
    </div>
</header>

<!-- Beginning of Navigation Bar -->
<div class="navigation">
  <div class="container navbar">
    <div class="navbar-inner"> <a href="#" class="brand"><span class="icon-home"></span>British Driving School <c:set var="selectTab" value="${pageContext.request.requestURI}"></c:set> </a>
      <nav class="nav pull-right">
	      <ul class="clearfix">
	       <security:authorize access="isAuthenticated()">
	       <c:choose>
	       	<c:when test="${fn:endsWith(pageContext.request.requestURI, 'dashboard') }">
	       		<li><a id="header_dashBoard" href="${pageContext.request.contextPath}/admin/dashboard" class="active"><s:message code="label.layout.dashBoard" text="Dashboard" /></a></li>
	       	</c:when>
	       	<c:otherwise>
	       		<li><a id="header_dashBoard" href="${pageContext.request.contextPath}/admin/dashboard" ><s:message code="label.layout.dashBoard" text="Dashboard" /></a></li>
	       	</c:otherwise>
	       </c:choose>
	       <c:choose>
	       	<c:when test="${fn:endsWith(pageContext.request.requestURI, 'reports') }">
	       		<li><a id="header_report" href="${pageContext.request.contextPath}/admin/reports" class="active"><s:message code="label.layout.reports" text="Report" /></a></li>
	       	</c:when>
	       	<c:otherwise>
	       		<li><a id="header_report" href="${pageContext.request.contextPath}/admin/reports""><s:message code="label.layout.reports" text="Report" /></a></li>
	       	</c:otherwise>
	       </c:choose>
	        </security:authorize> 
	      </ul>
      </nav>
    </div>
  </div>
</div>
<!-- End of Navigation Bar -->  