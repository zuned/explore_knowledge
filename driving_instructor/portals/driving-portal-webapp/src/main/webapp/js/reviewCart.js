ReviewCart = {
		addEvents : function() {
			$(document).ajaxSend(function(event, jqxhr, settings) {
    			if(settings.url.search('\\?') != -1){ 
    				settings.url += "&sessionToken=" + $("#sessionToken").val(); 
    			}else{
    				settings.url += "?sessionToken=" + $("#sessionToken").val(); 
    			}
    	});
		$( "#js-saveQuote" ).dialog({
				autoOpen: false,
				modal: true,
				position: ['center','center'],
				width: 500
		});
		$( "#js-removeFromCart" ).dialog({
			autoOpen: false,
			modal: true,
			position: ['center','center'],
			width: 500
		});
		$( "#js-removeRiderFromCart" ).dialog({
			autoOpen: false,
			modal: true,
			position: ['center','center'],
			width: 500
		});
		$( "#quoteSavedPopup" ).dialog({
			autoOpen: false,
			modal: true,
			position: ['center','center'],
			width: 500
		});
		
		$('.riderHead').next('.riders').show();
		$( ".planCategory" ).accordion({ 
			header: "h1",
			icons: { "header": "ui-icon-plusthickwhite", "headerSelected": "ui-icon-minusthick" },
			autoHeight: false,
			//icons: { "header": "icon-arrow-right pull-right marginT15", "headerSelected": "icon-arrow-down pull-right marginT15" }
			collapsible: true
		});
		$( ".accoraccordionBorder" ).accordion({ 
			header: "h3",
			icons: { "header": "icon-plus-black", "headerSelected": "icon-minus-black" },
			autoHeight: false,
			collapsible: true
		});
		$( ".segmentCategoryInner" ).accordion({ 
			header: ".planHead",
			icons: { "header": "icon-plus-black", "headerSelected": "icon-minus-black"},
			autoHeight: false,
			collapsible: true
		});
		
		// Add Rider 
		$('.riderHead').click(function(e) {
			e.preventDefault();
			$(this).next('.riders').slideToggle();
			$(this).find('.icon-arrow-right').toggleClass('icon-arrow-down');
        });
		
		/*// Remove Rider
		$('.js-removeRider').click(function(e) {
            $(this).parents('.riderAdded').remove();
        });*/
		
		// Remove Plan
		$('.js-removePlan').on('click', function(e) {
			e.stopPropagation();
			var planCode = $(this).data('planCode');
			$('#planCode').val(planCode);
			var issuerCode = $(this).data('issuerCode');
			$('#issuerCode').val(issuerCode);
			var cartId = $(this).data('cartId');
			$('#cartId').val(cartId);
			$('#js-removeFromCart').dialog('open');
		});
		
		$('#yesRemovePlan').click(function(event) {
			var planCode = $('#planCode').val();
			var issuerCode = $('#issuerCode').val();
			var cartId = $('#cartId').val();
			$.ajax({
						type: "GET",
						url : GlobalVars['app_url'] + "/web/cart/removeFromCart/" + cartId + "?planCode=" + planCode + "&issuerCode=" + issuerCode,
						cache : false,
						success : function(data) {	
							window.location.reload(true);
						},
					    error : function(data) {
							alert(data.statusText);
						}
			});
			return false;
		});
		
		/*$('h1 span.ui-icon-closethick').click(function(e) {
			var objDum =  $(this).parents('h1');
            objDum.next('div').remove();
			objDum.remove();
        });*/
		
		$('.js-viewAll').on('click', function(){
			var productCategories = $(this).data('productCategories');
			var offerIdentifier = $(this).data('offerIdentifier');
			var sessionToken = $(this).data('sessionToken');
			document.location.href = GlobalVars['app_url'] + '/employee/planlist?offerIdentifier=' + offerIdentifier + '&productCategories=' + productCategories + '&b=1&sessionToken=' + sessionToken;
		});
		
		$('.addRider').on('click', function() {
			var riderCode = $(this).data('riderCode');
			var parentPlanCode = $(this).data('parentPlanCode');
			var offerIdentifier = $(this).data('offerIdentifier');
			var productCategoryCode = $(this).data('productCategoryCode');
			var issuerCode = $(this).data('issuerCode');
			var effectiveFromDate = $(this).data('effectiveFromDate');
			var cartId = $(this).data('cartId');
		
			$.ajax({
				type: "POST",
				data : {
			    	'offerIdentifier': offerIdentifier,
			    	'issuerCode': issuerCode,
			    	'productCategoryCode': productCategoryCode,
			    	'effectiveFromDate': effectiveFromDate,
			    	'parentPlanCode' : parentPlanCode,
			    	'planCode' : riderCode
				},
				url : GlobalVars['app_url'] + "/web/cart/addRiderToCartPlan/" + cartId,
				cache : false,
				success : function(data) {	
					window.location.reload(true);
					$(this).hide();
					$(this).next('.removeRider').show();
				},
			    error : function(data) {
					alert(data.statusText);
				}
			});
			
			return false;
		});
		
		$('.removeRider').on('click', function() {
			var riderCode = $(this).data('riderCode');
			$("#riderCode").val(riderCode);
			var cartId = $(this).data('cartId');
			$('#cartId').val(cartId);
			
			$("js-removeRiderFromCart").dialog('open');
		});
		
		$("#yesRemoveRider").on('click', function() {
			var riderCode = $("#riderCode").val();
			var cartId = $('#cartId').val();
			    	
				$.ajax({
				type: "GET",
				url : GlobalVars['app_url'] + "/web/cart/removeRiderFromCartPlan/" + cartId + "?riderCode=" + riderCode,
				cache : false,
				success : function(data) {
					window.location.reload(true);
					$(this).hide();
					$(this).siblings('.addRider').show();
				},
			    error : function(data) {
					alert(data.statusText);
				}
				});
			return false;
		});
		
		$('#saveQuote').click(function(){
			$('#js-saveQuote').dialog('open');
		});
		
		/*$('.saveQuote').on('click', function(){
			document.location.href = 'my-quotes.html';
		});*/
		
		$('.closePopUp, #cancelQuote').click(function(){
			$('#js-saveQuote, #js-removeFromCart, js-removeRiderFromCart').dialog('close');
		});
		
		$('.planDetails').click(function(event) {
			event.stopPropagation();
			var planCode = $(this).data('planCode');
			var offerIdentifier = $(this).data('offerIdentifier');
			var sessionToken = $(this).data('sessionToken');
			document.location.href=href=GlobalVars['app_url'] + '/employee/planDetails?planId=' + planCode + '&offerIdentifier=' + offerIdentifier + '&sessionToken=' + sessionToken + '&fromCart=true';
		});
		
		$('#start-eapp').click(function() {
			$.ajax({
				type: "GET",
				url : GlobalVars['app_url'] + "/web/cart/starEnrollment",
				cache : false,
				success : function(data) {
					 window.location.href=GlobalVars['app_url'] + "/employee/enrollment?enrollmentId="+data.enrollmentId;
				},
			    error : function(data) {
					alert(data.statusText);
				}
				});
		});
		},
	
	addValidation : function() {
		var validationRules = [];
		var validationMessages = [];
		
		validationRules['quoteName'] = {
				required : true,
				maxlength : 50
		};
		validationMessages['quoteName'] = {
				required : "NotNull.quote.name",
				maxlength : 'Size.quote.name'
		};
		
		$('#saveQuoteForm').validate(
				{
					rules: validationRules,
					messages: validationMessages,
					errorElement : 'div',
					submitHandler : function(form) {						
						$.ajax({
							type: "GET",
							url : GlobalVars['app_url'] + "/web/cart/saveQuote?quoteName=" + $("#quoteName").val(),
							cache : false,
							success : function(data) {	
								$('#saveQuoteForm').find('input:text, div.serverError').html('');
								$("#js-saveQuote").dialog( 'close' );
								$('#quoteSavedPopup').dialog('open');
							},
							error : function(data, status, jqXHR){
								if(data.responseText) {
									$("#errors").html($.parseJSON(data.responseText).errors);
								}
							}
						});
						
						return false;
					}
				});
	}
};