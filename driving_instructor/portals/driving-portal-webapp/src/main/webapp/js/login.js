$(document).ready(function(e) {
	$( "#js-forgetUserPassPop" ).dialog({ 
		autoOpen: false,
		modal: true,
		position: ['center',100],
		draggable: true,
		width: 500
	});
		
	$( ".closePopUp" ).click(function() {
		$( "#js-forgetUserPassPop" ).dialog('close');
	});
	
	$('#js-forgetUserPass').click(function(){
		$('#js-forgetUserPassPop').dialog('open');
		return false;
	});
	$('#forgetPass_step1').click(function(){
		$('#emailpop').hide();
		$('#emailpop2').show();
	});
	
	$('#backToEmailpop').click(function(){
		$('#emailpop').show();
		$('#emailpop2').hide();
	});
	$('#forget_step2').click(function(){
		$('#emailpop2').hide();
		$('#emailpop3').show();			
	});

	// prepare the page object
	var loginPage = {
			formId: 'loginForm',
			validationRules: {
					username: {
						required: true,
					},
					password: {
						required: true,
						minlength: 5
					}
				},
			validationMessages: {
					username: "Please enter your username",
					password: {
						required: "Please provide a password",
						minlength: "Your password must be at least 5 characters long"
					}
				} 
	};
	setupFormValidation(loginPage);
});	
