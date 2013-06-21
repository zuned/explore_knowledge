<%@ include file="../../../includes/_tagLibs.jsp" %>

<article class="mainArticle">
  <div class="form-section">
  	 <div class="content">
  	 	<fieldset class="noBorder">
  	 	 <header class="sectionHeader">
            <h1 class="pageHeading">Summary Reports</h1>
            <h2 class="hide">Heading 2</h2>
          </header>
			<div class="contentBlock">
				<div id="containerBox"></div>
				<div id="summaryReportsDiv" class="content-container-inner posRel">
					<div class="holderBox2 ">
						<div  style="padding: 8px 10px; margin: 0;">
							<div id="exceptionBox">
								<c:forEach items="${errorList}" var="exception" begin="0" step="1">
									<div class="error-message">${exception}</div>
								</c:forEach>
							</div>
							<div class="error-message" id="error-message1"
								style="display: none;">
								<p id="err_fName"></p>
								<p id="err_lName"></p>
								<p id="err_email"></p>
								<p id="err_phone"></p>
							</div>
		
							<form class="reportForm adminReportsForm" name="reportsForm" method="get" id="adminReportsSearchForm">
								Select Report 
								<select name="reportName" id="dropDown" style="margin-bottom: 20px;">
									<option value="0">-- Please Select--</option>
									<option value="popular_service_provider">Popular Service provider</option>
									<option value="least_popular_service_provider">Least Popular Service provider</option>
									<option value="areas_need_promotion">Areas Need Promotion</option>
									<option value="most_popular_area">Most Popular Areas</option>
									<option value="least_popular_areas">Least Popular Areas</option>
									<option value="service_providers_report">Service providers Report</option>
								</select>
								
								<div id="completeBox">
									<div id="filterDivNew" class="hide" >
										<h3>Select Filter Criteria </h3>
										<div id="serviceProviderFilter" class="hide">
											<p><label for="serviceName" style="width:80px;color:#000000" >Service Name</label>
												<select name="serviceName" class="input-2  margin-right-5" id="serviceName" >
													<option value="0">-- Please Select--</option>
													<option value="DRIVING INSTRUCTOR">Driving Instructor</option>
													<option value="PLUMBER">Plumber</option>
													<option value="CARPAINTER">Carpainter</option>
												</select>
												<!-- <label for="numberOfRecords" style="width:80px;color:#000000" >Item Counts</label>
												<input name="numberOfRecords" type="text" class="input-2 margin-right-5" id="numberOfRecords"/> -->
											</p>
										</div>
										<div id="serviceProviderIdFilter" class="hide">
											<p><label for="serviceProviderId" style="width:80px;color:#000000" >Service Provider Id</label>
												<input name="serviceProviderId" type="text" class="input-2 margin-right-5" id="serviceProviderId"/>
											</p>
										</div>
										<div id="dateRangeFilter" class="hide">
											<span>Requested Effective Date Range</span>
											<table>
												<tr>
													<td style="width:80px;"><label for="startDate" style="width:30px;color:#000000">From</label></td>
													<td>&nbsp;</td>
													<td width="250px;"><input name="startDate" type="text" class="input-2 dateOptional  margin-right-5" id="startDate" readonly="true"/></td>
													<td>&nbsp;</td>
													<td><label for="EffectiveEndDate" style="width:30px;color:#000000">To</label></td>
													<td>&nbsp;</td>
													<td width="250px;"><input name="endDate" type="text" class="input-2 dateOptional  margin-right-5" id="EffectiveEndDate" readonly="true"/></td>
												</tr>
												<tr>
													<td></td>
													<td></td>
													<td><div class="error" id="err_startDate"></div></td>
													<td></td>
													<td></td>
													<td></td>
													<td><div class="error" id="err_endDate"></div></td>
												</tr>
											</table>
										</div>
										<br/><br/>
									</div>
								
									<div id="datesToShow">
										<input type="button" id="searchReport" value="Search Report" class="btn btn-primary marginR10 deviceBottom" /> 
										<input type="button" value="Cancel" class="btn btn-primary marginR10 deviceBottom" style="color: #FFF; height: 27px;"
											onclick="location.href='${pageContext.request.contextPath}/admin/dashboard'" />
									</div>
								</div>
									
								<div id="tableDiv" class="hide">
									<h3>Search Result</h3>
									<jsp:include page="_reportTables.jsp"></jsp:include>
								</div>
								
								<div class="F-right">
									<span id="exportButtons" class="hide">
										<input type="button" id="exportPDF" value="Export To PDF" class="btn btn-primary marginR10 deviceBottom" /> 
										<input type="button" id="exportXLS" value="Export To XLS" class="btn btn-primary marginR10 deviceBottom" /> 
									</span>
								</div>
							</form>
					</div>
				</div>
			</div>
			
				</div>
			</fieldset>
		</div><!-- content -->
	</div>
</article>
	<script>
	$(document).ready(function() {
		$.ajaxSetup({ cache: false });
		Report.initReport();
		$(".dateOptional").each(function() {
			var howerText = $('label[for='+this.id+ ']').text().trim();
			$(this).datepicker({
				showOn : "button",
				buttonImage : GlobalVars["app_url"] + "/images/calender.png",
				buttonImageOnly : true,
				buttonText: howerText,
				prevText : "Previous",
				onSelect: function(dateText) {
					setMaxMinDat($(this).attr("id"),dateText);
				  }
			});
			$(this).click(function() {
				$(this).datepicker("show");
			});
		});
		

		
		$(".dateOptionalFrom").each(function() {
			var howerText = $('label[for='+this.id+ ']').text().trim();
			$(this).datepicker({
				showOn : "button",
				buttonImage : GlobalVars["app_url"] + "/images/calender.png",
				buttonImageOnly : true,
				buttonText: howerText,
				prevText : "Previous",
				onSelect: function(dateText) {
					setMaxMinDat($(this).attr("id"),dateText);
				  }
			});
			$(this).click(function() {
				$(this).datepicker("show");
				
			});
		});
		
		$(".dateOptionalTo").each(function() {
			var howerText = $('label[for='+this.id+ ']').text().trim();
			$(this).datepicker({
				showOn : "button",
				buttonImage : GlobalVars["app_url"] + "/images/calender.png",
				buttonImageOnly : true,
				buttonText: howerText,
				prevText : "Previous",
				onSelect: function(dateText) {
					setMaxMinDat($(this).attr("id"),dateText);
				  }
			});
			$(this).click(function() {
				$(this).datepicker("show");
			});
		});
		
		$('div.listBoxRow input.crossbtn').live("click",function(){
			$(this).parent().remove();
		});
	});

	function setMaxMinDat(id,dateText) {
		//Summary Reports : 3) Effective
		if(id == 'EffectiveEndDate') {
			var effectiveEndDate = $("input[id='EffectiveEndDate']").val();
			var endDate = effectiveEndDate.split("/");
			var month = endDate[0];
			var date = endDate[1];
			var year = endDate[2];
			var newDate = new Date(year,month-1,date,0,0,0);
			$("input[id='EffectiveStartDate']").datepicker('option',{maxDate:newDate});
		}
		if(id == 'EffectiveStartDate') {
			var startdate = $("input[id='EffectiveStartDate']").val().split("/");
			var month = startdate[0];
			var date = startdate[1];
			var year = startdate[2];
			var newDate = new Date(year,month-1,date,0,0,0);
			$("input[id='EffectiveEndDate']").datepicker('option',{minDate:newDate});
		}
	}
		function clear_form_elements(ele) {
			$(ele).find(':input').each(function() {
				switch (this.type) {
				case 'password':
				case 'select-multiple':
				case 'select-one':
				case 'text':
				case 'textarea':
				case 'hidden':
				$(this).val('');
					break;
				case 'checkbox':
				case 'radio':
					this.checked = false;
				}
			});
			$('.genericTable').hide();
		}
		
		
		
		
		
		
	</script>
