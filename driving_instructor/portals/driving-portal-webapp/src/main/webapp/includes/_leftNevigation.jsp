  <%@ include file="_tagLibs.jsp" %>
  <security:authorize access="isAuthenticated()">    
    <aside class="span3 marginL0">
      <ul class="sidenav-list">
        <li id="nevigation_userdetails" class="${'nevigation_userdetails' == selectedLeftMenu ? 'active': '' }"><a href="#"><s:message code="label.layout.userdetails" /></a></li>
        <li id="nevigation_employerdetails" class="${'nevigation_employerdetails' == selectedLeftMenu ? 'active': '' }"><a href="#"><s:message code="label.layout.employerdetails" /></a></li>
        <li id="nevigation_manageemployee" class="${'nevigation_manageemployee' == selectedLeftMenu ? 'active': '' }"><a href="#"><s:message code="label.layout.manageemployee" /></a></li>
        <li id="nevigation_manageenrollment" class="${'nevigation_manageenrollment' == selectedLeftMenu ? 'active': '' }"><a href="#"><s:message code="label.layout.manageenrollment" /></a></li>
        <li id="nevigation_managebroker" class="${'nevigation_managebroker' == selectedLeftMenu ? 'active': '' }"><a href="#"><s:message code="label.layout.managebroker" /></a></li>
        <li id="nevigation_plansearchhistory" class="${'nevigation_plansearchhistory' == selectedLeftMenu ? 'active': '' }"><a href="#"><s:message code="label.layout.plansearchhistory" /></a></li>
        <li id="nevigation_searchcriteria" class="${'nevigation_searchcriteria' == selectedLeftMenu ? 'active': '' }"><a href="#"><s:message code="label.layout.searchcriteria" /></a></li>
        <li id="nevigation_managemails" class="${'nevigation_managemails' == selectedLeftMenu ? 'active': '' }"><a href="#"><s:message code="label.layout.managemails" /></a></li>
        <li id="nevigation_managefaq" class="${'nevigation_managefaq' == selectedLeftMenu ? 'active': '' }"><a href="#"><s:message code="label.layout.managefaq" /></a></li>
      </ul>
    </aside>
  </security:authorize>