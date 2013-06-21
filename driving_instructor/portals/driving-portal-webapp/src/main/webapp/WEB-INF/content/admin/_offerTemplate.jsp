<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script id="offer_template" type="text/x-handlebars-template">
<div class="js-addRow" id="offer_{{index}}">
	<div class="control-group row-fluid posRel">
		<div class="span6">
			<label for="special_offer">Special Offer</label> <a id="{{index}}" href="#" class="js-remove hide-text icon-minus-sign removeIcon">Remove</a>
		</div>
	</div>
	<div class="control-group row-fluid">
		<div class="span3">
			<label for="name">Name:</label> 
			<input type="text" placeholder="Name" name="specialOffers[{{index}}].name" id="name_{{index}}" class="input-large" />
		</div>
		<div class="span3">
			<label for="description">Description: <span class="required">*</span></label> 
			<input type="text" placeholder="description" name="specialOffers[{{index}}].description" id="description_{{index}}" class="input-large" />
		</div>
		<div class="span3">
			<label for="status">Status: <span class="required">*</span></label>
			<select name="specialOffers[{{index}}].status" id="status_{{index}}" class="input-large">
				<option value="ACTIVE">Active</option>
				<option value="INACTIVE">InActive</option>
			</select>
		</div>
	</div>
	<div class="control-group row-fluid">
		<div class="span6">
			<label for="startDate">Active From:</label> 
			<input type="text" placeholder="Active From" name="specialOffers[{{index}}].startDate" id="startDate_{{index}}" class="input-large dateOptional" />
		</div>
		<div class="span6">
			<label for="endDate">Active To: <span class="required">*</span></label> 
			<input type="text" placeholder="Active To" name="specialOffers[{{index}}].endDate" id="endDate_{{index}}" class="input-large dateOptional" />
		</div>
	</div>
</div>
</script>