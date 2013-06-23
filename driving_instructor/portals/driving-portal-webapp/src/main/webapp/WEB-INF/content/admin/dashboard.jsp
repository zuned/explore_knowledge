<%@ include file="../../../includes/_tagLibs.jsp" %>

<article class="mainArticle">
<!-- Beginning of Main Article -->
        <div class="form-section">
          <div class="content">
            <fieldset class="noBorder">
              <!-- Beginning of Main Heading -->
              <header class="sectionHeader">
                <h1 class="pageHeading">Manage Service Providers</h1>
                <h2 class="hide">Heading 2</h2>
              </header>
              <!-- End of Main Heading -->
              <div class="contentBlock">
                <div class="editDetailsBox">
                  <div class="control-group marginB20 marginT10">
                    <input type="button" id="js-add_service_provider" value="Add Service Provider" class="btn btn-primary marginR10 deviceBottom">
                  </div>
                  <h3>Search Service Provider</h3>
                  <form:form id="serviceProviderTableSearchForm" method="get" >
                  <div class="control-group searchBy gutterB20 gutterT10">
                    <div class="pull-left marginR40">
                      <label for="search_by">Search By:</label>
                      <select class="select-large" name="search_by" id="search_by">
                        <option value="-"  <c:if test="${param.search_by ne 'name' or param.search_by eq 'sac' }">selected="selected"</c:if> >Select</option>
                        <option value="name" <c:if test="${param.search_by eq 'name'}">selected="selected"</c:if> >Service Provider Name</option>
                     <%--    <option value="sac" <c:if test="${param.search_by eq 'sac'}">selected="selected"</c:if> >Service Provider AreaCode</option> --%>
                      </select>
                    </div>
                    <div class="pull-left marginR20">
                      <label for="keyword">Keyword:</label>
                      <input type="text" name="keyword" id="keyword" class="input-large" value="${param.keyword }">
                    </div>
                    <div class="pull-left">
                      <label class="hide-text" for="go">Go:</label>
                      <input type="submit" name="go" id="go" value="GO" class="btn">
                    </div>
                  </div>
                  </form:form>
                  <h3>Service Provider List</h3>
					<div id="drivingInstructorListing" class="marginB20">
						<table class="employeeListTable table table-condensed" id="serviceProviderTable">
							<thead>
								<tr class="first-row">
									<th>Service Provider Id</th>
									<th>Service Provider Name</th>
									<th>Membership Type</th>
									<th>Date Of Registration</th>
									<th>Contact Preference</th>
									<th>License Number</th>
									<th>Area Codes</th>
									<th></th>
								</tr>
							</thead>
						</table>
                </div>
              </div>
              </div>
            </fieldset>
          </div>
        </div>
<!-- End of Main Article --> 
</article>

	<div class="popupBox alertBox" id="popup">
     	<div class="popupContent">
        	<span class="icon-close-popup closePopUp pull-right"><span class="hide-text">Close</span></span>
        	<p>Are you sure you want to delete this Service Provider ?</p>
            <div class="control-group">
           		 <input type="hidden" id="serviceProviderId" value=""/>
            	<input type="button" name="" class="btn marginL50 closePopUp" value="Yes" id="yesRemove">
                <input type="button" name="" class="btn marginL10 closePopUp" value="No">                
            </div>
        </div>
     </div>
    <div class="popupBox alertBox" id="popupSuccess">
     	<div class="popupContent">
        	<p>Service Provider Successfully deleted.</p>
        </div>
     </div>

<script type="text/javascript">
		$(document).ready(function(){
			var documentObj = $(document);
			$.ajaxSetup({ cache: false });
			$("#popup, #popupSuccess").dialog({
				autoOpen : false,
				modal : true,
				position : [ 'center', '40' ],
				width : 500
			});
			$(".closePopUp ").click(function() {
				$("#popup").dialog('close');
			});
			ManageDrivingInstructor.createDataTable(documentObj);	
			$('#yesRemove').live(
					"click",
					function() {
						var serviceProviderId = $("#serviceProviderId").val();
						var deleteUrl = GlobalVars['app_url']+ "/admin/sp/"+serviceProviderId+"/remove";
						var request = $.ajax({
							url : deleteUrl,
							async : false,
							type : "POST",
							success : function(data, status, jqXHR) {
								$('#popup').jQpopup({
									show : false
								});
								 $('#popupSuccess').dialog('open');
								setTimeout(function(){
									 $('#popupSuccess').jQpopup({ show : false });
									window.location = GlobalVars["app_url"] +"/admin/dashboard";
									},3000);
							},
							error : function(data, status, jqXHR) {
								response = false;
								$('#popup').jQpopup({
									show : false
								});
							}
						});
					});
		});
	</script>