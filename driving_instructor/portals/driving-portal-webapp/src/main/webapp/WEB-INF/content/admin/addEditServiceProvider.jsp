<%@ include file="../../../includes/_tagLibs.jsp" %>

<!-- Beginning of Main Article -->
<article class="mainArticle">
  <div class="container"> 
    <section class="manageEmployee row-fluid">
        <form:form id="serviceProviderForm" class="form-horizontal" method="post" modelAttribute="serviceProviderForm"  enctype="multipart/form-data">
          <div class="form-section">
            <div class="content">
              <fieldset class="noBorder">
                <legend class="hide">Add Service Provider Details</legend>
                <!-- Beginning of Main Heading -->
                <header class="sectionHeader">
                  <h1 class="pageHeading">Add Service Provider Details</h1>
                  <h2 class="hide">Heading 2</h2>
                  <span class="font11 requiredAlt">Required <span class="required">*</span></span>
                </header>
                <!-- End of Main Heading -->
                <div class="contentBlock">
                  <div class="editDetailsBox">
                    <h3>Personal Details</h3>
                    <div class="control-group row-fluid">
                      <div class="span12">
                        <label for="name">Name: <span class="required">*</span></label>
                      </div>
                      <div class="span3" style="margin-left:0px;">
                        <label class="hide" for="first_name">First Name: <span class="required"></span></label>
                        <form:input type="text" path="firstName" name="firstName" id="first_name" placeholder="First Name"  class="input-medium no-label" />
                      </div>
                      <div class="span3">
                        <label class="hide" for="middle_name">Middle Name: <span class="required"></span></label>
                        <form:input type="text" path="middleName" name="middle_name" id="middle_name" placeholder="Middle Name (optional)" class="input-medium  no-label" />
                      </div>
                      <div class="span3">
                        <label class="hide" for="last_name">Last Name: <span class="required"></span></label>
                        <form:input type="text" path="lastName" name="lastName" id="lastName" placeholder="Last Name" class="input-medium no-label" />
                      </div>
                    </div>
                    <div class="control-group row-fluid">
                      <div class="span3">
                        <label for="date_of_birth">Date of Birth: <span class="required">*</span></label>
                        <form:input  path="dateOfBirth" name="dateOfBirth" placeholder="MM/DD/YYYY" id="dateOfBirth" class="input-medium dob marginR10" />
                      </div>
                     <div class="span3">
                        <label for="license_number">License Number: <span class="required">*</span></label>
                        <form:input type="text" path="licenceNumber" name="licenceNumber" placeholder="License Number" id="licenceNumber" class="input-medium marginR10" />
                      </div>
                     <div class="span3">
                        <label for="emailId">Email:</label>
                        <form:input type="text" path="emailId" placeholder="Email Id" name="emailId" id="emailId" class="input-large" />
                      </div>
                    </div>
                    </div>
                     <div class="control-group row-fluid">
                      <div class="span6">
                        <label for="upload_photo">Upload Photo:</label>
                        <form:input type="file" path="photo" name="image" placeholder="Upload Photo" id="upload_photo" class="input-medium marginR10" />
                      </div>
                      <div class="span6">
                      		<c:if test="${ not empty id }">
	                      	 <img alt="my image" src="${pageContext.request.contextPath}/admin/sp/${id }/image" width="250px" height="250px" border="1px" />
	                       </c:if>
                      </div>
                    </div>
                    <div class="control-group row-fluid inline-form"> <span class="pull-left labelAlt">Gender: <span class="required">*</span></span>
                      <div class="pull-left marginR10">
                        
                        <label for="male" class="checkbox inline"><form:radiobutton path="gender"  name="gender" id="male" value="MALE" /> Male</label>
                      </div>
                      <div class="pull-left marginR10">
                        
                        <label for="female" class="checkbox inline"><form:radiobutton path="gender"  name="gender" id="female" value="FEMALE" /> Female</label>
                      </div>
                    </div>
                    <div class="control-group row-fluid inline-form">
                      <div class="span3"> <span class="fixed-widthAlt">Preferred Contact Method:</span> </div>
                      <div class="span2">
                        <label for="email" class="checkbox inline"> <form:radiobutton path="contactPrefrence" name="contactPrefrence" id="email" checked = "checked" value="EMAIL" /> Email</label>
                      </div>
                      <div class="span2">
                        <label for="call" class="checkbox inline"><form:radiobutton  path="contactPrefrence" name="contactPrefrence" id="call"  value="CALL"/> Call</label>
                      </div>
                      <div class="span2">
                        <label for="sms" class="checkbox inline"><form:radiobutton  path="contactPrefrence" name="contactPrefrence" id="sms"  value="SMS"/> SMS</label>
                      </div>
                      <div class="span2">
                        <label for="mail" class="checkbox inline"><form:radiobutton  path="contactPrefrence" name="contactPrefrence" id="mail"  value="MAIL" /> Mail</label>
                      </div>
                    </div>
                    <h3>Contact Details</h3>
                    <div class="control-group row-fluid">
                      <div class="span6">
                        <label for="primaryPhoneNumber">Phone:<span class="required">*</span></label>
                        <form:input path="address[0].primaryPhoneNumber" placeholder="Phone"  name="address[0].primaryPhoneNumber" id="primaryPhoneNumber" class="input-large marginR10 nonNegativeInteger" maxlength="10" value=""/>
                      </div>
                       <div class="span6">
                        <label for="phone">Alternate Phone:</label>
                        <form:input path="address[0].secondaryPhoneNumber" placeholder="Phone(Optional)" name="phone" id="phone" class="input-large marginR10 nonNegativeInteger" value="" maxlength="10" />
                      </div>
                    </div>
                    <div class="control-group row-fluid">
                      <div class="span6">
                        <label for="buildingName">Building Name: <span class="required">*</span></label>
                        <form:input path="address[0].buildingName" name="address[0].buildingName" id="buildingName" placeholder="Building Name" class="input-large" />
                      </div>
                      <div class="span6">
                        <label for="streetAddress">Street Address:</label>
                        <form:input path="address[0].streetAddress" name="streetAddress" id="streetAddress" placeholder="Street Address" class="input-large" />
                      </div>
                    </div>
                    <div class="control-group row-fluid">
                      <div class="span6">
                        <label for="city">City: <span class="required">*</span></label>
                        <form:input path="address[0].city" name="city" id="city" placeholder="City" class="input-large" />
                      </div>
                      <div class="span6">
                      	<label for="county">County: <span class="required">*</span></label>
                        <form:input path="address[0].county" name="county" id="county" placeholder="Country" class="input-large" />
                      </div>
                    </div>
                    <div class="control-group row-fluid">
                      <div class="span6">
                        <label for="postCode">Post Code: <span class="required">*</span></label>
                        <form:input path="address[0].postCode" name="postCode" id="postCode"  placeholder="Post Code" class="input-large" />
                      </div>
                      <div class="span6">
                        <label for="country">Country:</label>
                        <form:input path="address[0].country" name="country" id="country"  placeholder="Country" class="input-large" />
                      </div>
                    </div>
                    <h3>Service Details</h3>
                    <div class="control-group row-fluid">
	                      <div class="span3">
	                        <label for="companyName">Company Name:</label>
	                        <form:input path="companyName" placeholder="Company Name" name="companyName" id="companyName" class="input-large" />
	                      </div>
	                     <div class="span3">
	                        <label for="qualification">Qualification: <span class="required">*</span></label>
	                        <form:input path="serviceProviderServiceDetail.qualification" placeholder="Qualification" name="serviceProviderServiceDetail.qualification" id="qualification" class="input-large" />
	                      </div>
	                       <div class="span3">
	                        <label for="speciality">Speciality : <span class="required">*</span></label>
	                        <form:input path="serviceProviderServiceDetail.speciality" placeholder="Speciality" name="serviceProviderServiceDetail.speciality" id="speciality" class="input-large" />
	                      </div>
                    </div>
                     <div class="control-group row-fluid">
	                     <div class="span3">
	                        <label for="serviceName">Service: <span class="required">*</span></label>
	                        <form:select path="serviceName" name="serviceName" placeholder="Service" id="serviceName" class="input-medium marginR10" >
	                        	<form:option value="DRIVING INSTRUCTOR">Driving Instructor</form:option>
	                        	<form:option value="PLUMBER">Plumber</form:option>
	                        </form:select>
	                      </div>
	                       <div class="span3">
	                        <label for="timing">Timing: <span class="required">*</span></label>
	                         <form:input path="serviceProviderServiceDetail.timing" placeholder="Timing" name="serviceProviderServiceDetail.timing" id="timing" class="input-large" />
	                      </div>
	                       <div class="span3">
	                        <label for="member_type">MemberShip Type: <span class="required">*</span></label>
	                        <form:select   path="memberType" name="member_type" placeholder="Member Type" id="member_type" class="input-medium marginR10" >
	                        	<form:option value="FREE">Free</form:option>
	                        	<form:option value="PAID">Paid</form:option>
	                        </form:select>
	                      </div>
	                      <div class="span3">
	                        <label for="packageEnrolled">Select Packages: <span class="required">*</span></label>
	                        <form:select   path="packageEnrolled.packages.name" name="packageEnrolled.packages.name" placeholder="Package" id="packageEnrolled" class="input-medium marginR10" >
	                        	<form:option value="MPKG">Monthly</form:option>
	                        	<form:option value="QPKG">Quarterly</form:option>
	                        	<form:option value="HYPKG">SEMI YEARLY</form:option>
	                        	<form:option value="YPKG">YEARLY</form:option>
	                        </form:select>
	                      </div>
                    </div>
                     <div class="control-group row-fluid inline-form">
                      <div class="span3"> <span class="fixed-widthAlt">Is Nervous Driver:</span> </div>
                      <div class="span2">
                        <label for="email" class="checkbox inline"><input type="radio"  name="isNervousDriver" id="isNervousDriverN" checked = "checked" value="FALSE" />No</label>
                      </div>
                      <div class="span2">
                        <label for="call" class="checkbox inline"><input type="radio"  name="isNervousDriver" id="isNervousDriverY"  value="TRUE"/>Yes</label>
                      </div>
                    </div>
                    <div class="control-group row-fluid inline-form">
                      <div class="span3"> <span class="fixed-widthAlt">Vehicle Type</span> </div>
                      <div class="span2">
                        <label for="email" class="checkbox inline"><input type="radio"  name="vehicleType" id="vehicleTypeA" checked ="checked" value="AUTOMATIC" />Automatic</label>
                      </div>
                      <div class="span2">
                        <label for="call" class="checkbox inline"><input type="radio"  name="vehicleType" id="vehicleTypeSA"  value="SEMI_AUTOMATIC"/>Semi Automatic</label>
                      </div>
                      <div class="span2">
                        <label for="call" class="checkbox inline"><input type="radio"  name="vehicleType" id="vehicleTypeM"  value="MANUAL"/>Manual</label>
                      </div>
                    </div>
                    <div class="control-group row-fluid">
	                       <div class="span12">
	                        <label for="service_area">Service Area: <span class="required">*</span></label>
	                        <form:select  multiple="multiple" path="selectedServiceArea"  name="selectedServiceArea"  id="serviceAreas" class="input-large marginR10" >
	                        	<form:options items="${serviceAreas}" itemValue="areaName" itemLabel="areaName" /> 
	                        </form:select>
	                      </div>
                    </div>
					<div id="specialOfferDiv">
					
					<c:set var="offerCount" value="${fn:length(serviceProviderForm.specialOffers)}"></c:set>
					<c:choose>
						<c:when test="${offerCount gt 0 }">
							<c:set var="startIndex" value="${offerCount-1}"></c:set>
							<c:forEach items="${serviceProviderForm.specialOffers}" var="offer" varStatus="index">
 <div class="js-addRow" id="offer_${index.index }">
	<div class="control-group row-fluid posRel">
		<div class="span6">
			<label for="special_offer">Special Offer</label> <a id="${index.index}" href="#" class="js-remove hide-text icon-minus-sign removeIcon">Remove</a>
		</div>
	</div>
	<div class="control-group row-fluid">
		<div class="span3">
			<label for="name">Name:</label> 
			<input type="hidden" name="specialOffers[${index.index}].id" id="name_${index.index }"  value="${offer.id}" />
			<input type="text" placeholder="Name" name="specialOffers[${index.index}].name" id="name_${index.index }"  value="${offer.name }" class="input-large" />
		</div>
		<div class="span3">
			<label for="description">Description: <span class="required">*</span></label> 
			<input type="text" placeholder="description" name="specialOffers[${index.index }].description" id="description_${index.index }" value="${offer.description }"  class="input-large" />
		</div>
		<div class="span3">
			<label for="status">Status: <span class="required">*</span></label>
			<select name="specialOffers[${index.index }].status" id="status_${index.index }" class="input-large">
				<option value="ACTIVE">Active</option>
				<option value="INACTIVE">InActive</option>
			</select>
		</div>
	</div>
	<div class="control-group row-fluid">
		<div class="span6">
			<label for="startDate">Active From:</label> 
			<input type="text" placeholder="Active From" name="specialOffers[${index.index }].startDate" id="startDate_${index.index }" value='<fmt:formatDate pattern="MM/dd/yyyy"   value="${offer.startDate}" />'  class="input-large dateOptional" />
		</div>
		<div class="span6">
			<label for="endDate">Active To: <span class="required">*</span></label> 
			
			<input type="text" placeholder="Active To" name="specialOffers[${index.index }].endDate" id="endDate_${index.index }" value='<fmt:formatDate pattern="MM/dd/yyyy"   value="${offer.endDate}" />'  class="input-large dateOptional" />
		</div>
	</div>
</div>
							</c:forEach>

						</c:when>
						<c:otherwise>
							<c:set var="startIndex" value="0"></c:set>
						</c:otherwise>
					</c:choose>
					</div>
					<input id="offerIndex" type="hidden" value="${startIndex}">
					<div class="control-group row-fluid marginB20"> <a id="js-addOffer" href="#" class="link-underline">Add Special Offers</a> </div>
				</div>
              </fieldset>
              <div class="control-group row-fluid">
              	<div class="buttons">
                	<input type="button" id="cancelAdd" class="btn btn-secondary marginR10" value="Cancel">
                    <input type="submit" id="js-save" class="btn btn-primary" value="Save">                    
                </div>
              </div>
            </div>
          </div>
        </form:form>
    </section>
  </div>
</article>
<jsp:include page="_offerTemplate.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/handlebars.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	$("#cancelAdd").click(function(){
		window.location = "${pageContext.request.contextPath}/admin/dashboard";
	});
	// validate signup form on keyup and submit
	$("#serviceProviderForm").validate({
		rules: {
			firstName: { required: true },
			lastName: { required: true },
			dateOfBirth: {
				required: true,
				date: true
			},
			gender: { required: true },
			licenceNumber: { required: true },
			emailId:{ required: true ,email:true },
			'address[0].primaryPhoneNumber' : { required: true },
			'address[0].buildingName' :{ required: true },
			'address[0].city' :{ required: true },
			'address[0].county' :{ required: true },
			'address[0].postCode' :{ required: true },
			'serviceProviderServiceDetail.speciality':{ required: true },
			'serviceProviderServiceDetail.qualification':{ required: true },
			'serviceProviderServiceDetail.timing':{ required: true }
		},
		messages: {
			firstName: "Please enter your firstname",			
			lastName: "Please enter your lastname",
			dateOfBirth: "Please enter a valid Date of Birth",
			gender: "Please select your Gender",
			licenceNumber: "Please Enter your Licence Number",
			emailId: "Please Enter Valid Email Id",
			'address[0].primaryPhoneNumber' : "Please Enter Valid Phone Number",
			'address[0].buildingName' :"Please Enter Building Name",
			'address[0].city' :"Please Enter City",
			'address[0].county' :"Please Enter County",
			'address[0].postCode' :"Please Enter PostCode",
			'serviceProviderServiceDetail.speciality':"Please Enter Speciality",
			'serviceProviderServiceDetail.qualification':"Please Enter Qualification",
			'serviceProviderServiceDetail.timing' :"Please Provide Timing"
		},
		errorClass: "error errorAlt",
		errorElement: "div"	,
		highlight: function(element, errorClass) {
			$(element).addClass(errorClass);
			$(element).siblings('.icon-error').fadeIn();
			$('input[type=text], input[type=date], input[type=radio], input[type=checkbox], select, textarea').tooltip({
				title : function(){
					return $(this).siblings('.error').text();
				},
				placement: 'mouseX'
			});
	   },
	   unhighlight: function(element, errorClass) {
		 $(element).removeClass(errorClass);
		 $(element).siblings('.icon-error').fadeOut();
	  }
	});	
	$(".icon-error").live("mouseover",function(){
		var parent =  $(this).parent('.errorWarp'),
			pos = $(this).position().left + 20;
	  	parent.find('div.error').css({
			position: 'absolute',
			left: pos,
			top: 0,
			width: 'auto',
			height: 24,
			lineHeight: '24px',
			whiteSpace: 'nowrap',
			zIndex: 999999,
			border: '1px solid #DDDDDD'
		}).fadeIn('slow');
	});
	$(".icon-error").live("mouseout",function(){
		var parent =  $(this).parent('.errorWarp');
	  	parent.find('div.error').fadeOut('slow',function(){
			parent.find('div.error').removeAttr('style');
		});
	});
	errorIcon();
	
});
function errorIcon(){
	$('input, select, textarea').wrap('<span class="errorWarp"></span>').parent().append('<span class="icon icon-error"></span>');
	$('input.dob').siblings('.icon-error').css('right', -20);
	$('input[type=radio],input[type=checkbox]').each(function(index, element) {
		$(this).siblings('.icon-error').css('right', -($(this).parent().next().width()+21))
	});
	$('select').each(function(index, element) {
		$(this).siblings('.icon-error').css('right', -18);
	});
}
</script> 
<script type="text/javascript">
	$(document).ready(function(){
		
		$('#js-addOffer').on('click', function(){
			var offerIndex = parseInt($("#offerIndex").val());
			var template = Handlebars.compile($("#offer_template").html());
			$("#specialOfferDiv").append(template( {"index" : offerIndex }));
			$("#offerIndex").attr("value", offerIndex+1);
			
			$( ".dateOptional" ).datepicker({
	            showOn: "button",
	            buttonImage: "${pageContext.request.contextPath}/images/calender.png",
	            buttonImageOnly: true,
				buttonText: "Calender",
				changeMonth: true,
	      		changeYear: true,
				dateFormat: "mm/dd/yy"
			});
			
			$('.js-remove').on('click', function(){
				var divId = '#offer_'+this.id;
				$(divId).remove();
				var offerIndex = parseInt($("#offerIndex").val());
				$("#offerIndex").attr("value", offerIndex-1);
				return false;
			});
			return false;
		});
		
		$( ".dob, .doh" ).datepicker({
            showOn: "button",
            buttonImage: "${pageContext.request.contextPath}/images/calender.png",
            buttonImageOnly: true,
			buttonText: "Calender",
			changeMonth: true,
      		changeYear: true,
			dateFormat: "mm/dd/yy",
			minDate: '-100Y',
			maxDate: new Date(), 
			yearRange: '-100'
        });
		
	
	});
</script>