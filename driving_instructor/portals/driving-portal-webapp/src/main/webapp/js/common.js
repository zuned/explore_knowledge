/**
 * Remove Ajax cachng by default
 */
$.ajaxSetup({ cache: false });


(function($) {

	// jQuery on an empty object, we are going to use this as our Queue
	var ajaxQueue = $({});

	$.ajaxQueue = function( ajaxOpts ) {
	    var jqXHR,
	        dfd = $.Deferred(),
	        promise = dfd.promise();

	    // queue our ajax request
	    ajaxQueue.queue( doRequest );

	    // add the abort method
	    promise.abort = function( statusText ) {

	        // proxy abort to the jqXHR if it is active
	        if ( jqXHR ) {
	            return jqXHR.abort( statusText );
	        }

	        // if there wasn't already a jqXHR we need to remove from queue
	        var queue = ajaxQueue.queue(),
	            index = $.inArray( doRequest, queue );

	        if ( index > -1 ) {
	            queue.splice( index, 1 );
	        }

	        // and then reject the deferred
	        dfd.rejectWith( ajaxOpts.context || ajaxOpts,
	            [ promise, statusText, "" ] );

	        return promise;
	    };

	    // run the actual query
	    function doRequest( next ) {
	        jqXHR = $.ajax( ajaxOpts )
	            .done( dfd.resolve )
	            .fail( dfd.reject )
	            .then( next, next );
	    }

	    return promise;
	};

})(jQuery);



/**
 * function to be called from each jsp to render view of header for mobile version
 * @returns locale-specific message
 */

$(function(){

	$(".btn-navbar").live("click", function(){
		var o = $(this).next();
		o.slideToggle(function(){
			if(o.is(":hidden")){
				o.removeAttr("style");
			}
		});
	}).siblings('ul').find('li').bind('click', function(e){
		var o = $('ul',this);
		o.parents('ul').find('li ul').slideUp();
		o.slideToggle();
		if(o.is(":hidden")){
			o.removeAttr("style");
		}
	});
	$(document).on('click', function(event){
		if(!($(event.target).is($("nav.nav ul, ul.topLinks, .btn-navbar, div.options"))) && $('.btn-navbar').is(':visible')){
			var o = $("nav.nav ul, ul.topLinks, .options");
			o.slideUp(function(){
				o.removeAttr("style");
			});
		}
	});
});

/**
 * Internationalization utilizing the messages array formed using messageCodes
 * @returns locale-specific message
 */
$.i18nMessage = function(message) {
	if(typeof messages !== "undefined" && messages[message]) {
		message = messages[message];
	}
	return message;
};

/** 
 * Jquery Validator Default Message 
 * Overridden the defaultMessage function of Jquery Validator to 
 * return localized messages using $.fn.i18nMessage
 * 
 * */

/*var validationDefaultMessage = $.validator.prototype.defaultMessage;
$.validator.prototype.defaultMessage = function(element, method) {
	var message = validationDefaultMessage.call(this, element, method);
	return $.i18nMessage(message);
};*/


/** 
 * Jquery Validator Show Label 
 * Overridden the showLabel function of Jquery Validator to 
 * show localized messages using $.i18nMessage
 * 
 * */
var validationShowLabel = $.validator.prototype.showLabel;
$.validator.prototype.showLabel = function(element, message) {
	message = $.i18nMessage(message);
	validationShowLabel.call(this, element, message);
};


/**
 * Setting the default behavior of jQuery Validator
 */
$.validator.setDefaults({
	errorElement: "div",
	unhighlight: function(element, errorClass) {
		$(element).removeClass(errorClass);
		$(element).siblings('.icon-error').fadeOut();
	},
	highlight: function(element, errorClass) {
		$(element).addClass(errorClass);
		$(element).siblings('.icon-error').eq('0').fadeIn();
	}
});

$.validator.addMethod("customEmail", function(value, element, regexp) {
	var re = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
	return this.optional(element) || re.test(value);
}, "Please enter valid Email");

/** 
 * Jquery Validator Additional methods start
 * */

$.validator.addMethod("greaterThanToday", function(value, element, params) {
	if(this.optional(element)) {
		return true;
	}
	return !isDateGreaterThanTodaysDate(value);
}, jQuery.format("Please enter a date in the past"));

function isDateGreaterThanTodaysDate(dateStr) {
	var month, day, year;
	month = dateStr.substring(0, 2);
	day = dateStr.substring(3, 5);
	year = dateStr.substring(6, 10);
	var date = new Date();
	date.setFullYear(year, month - 1, day);
	return(new Date() < date);
}

$.validator.addMethod("alpha", function(value, element) {
	var reg=/^[a-zA-Z\_]+$/g;

	if(value==""){
		return true;
	}else{
		if(reg.test(value)){
			return true;
		}else{
			return false;
		}
	}
	
}, "please enter alphabets only.");

$.validator.addMethod("noOfHours", function(value, element) {
	if(value == ''){
		return true;
	}
	var reg = /^\d+$/;;
	var val=parseInt(value);
	if(reg.test(value) && !(val>24)){
		return true;
	}else{
		return false;
	}
}, "please enter valid No of working Hours.");

$.validator.addMethod("annualSalary", function(value, element) {
	var reg = /^\d+(\.?\d+)?$/;
	var val=parseFloat(value);
	if(!(val>0)){
		return false;
	}
	if(reg.test(value)){
		return true;
	}else{
		return false;
	}
}, "please enter valid Annual Salary");

jQuery.validator.addMethod("customPassword", function(value, element) {
	var passwordPattern = (/(?!.*[\s])+^.*(?=.{8,})(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).*$/);
	return this.optional(element) || passwordPattern.test(value);
}, "Please enter valid password");

jQuery.validator.addMethod("pastYear", function(value, element) {
	var date = new Date();
    return this.optional(element) || (parseFloat(value) <= date.getFullYear());
}, "Year must be in the past");

jQuery.validator.addMethod("sumLessThan", function(value, element, params) {
	var sumOfVals = 0;
	var selector = $(element).attr("class").split(' ')[0];
    $('.' + selector).each(function () {
    	var val = parseInt($(this).val(), 10);
    	if(!isNaN(val)) {
    		sumOfVals += val;
    	}
    });
    return sumOfVals <= params;
}, jQuery.format("Total should not exceed {0}"));

jQuery.validator.addMethod("sumGreaterThan", function(value, element, params) {
	var sumOfVals = 0;
	var selector = $(element).attr("class").split(' ')[0];
    $('.' + selector).each(function () {
    	var val = parseInt($(this).val(), 10);
    	if(!isNaN(val)) {
    		sumOfVals += val;
    	}
    });
    return sumOfVals > params;
}, jQuery.format("Total should exceed {0}"));

/** 
 * Jquery Validator Additional methods end
 * */


/**
 * Used for CSRF checks for AJAX requests
 * @returns hidden input jQuery element containing CSRF token
 */
function getCSRFField(target) {
	var csrfTokenName = 'CSRFToken';
	var csrfTokenHidden= false;
	if(target) {
		csrfTokenHidden = $('input[name="' + csrfTokenName + '"]', target);
	} else {
		csrfTokenHidden = $('input[name="' + csrfTokenName + '"]');
	}
	if(csrfTokenHidden.length) {
		return $('<input type="hidden" name="' + csrfTokenName + '" value="' + csrfTokenHidden.val() + '" />');
	}
	return false;
}

/**
 * Capitalizes the first character
 */
String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
};

/**
 * Appends CSRF Field data to ajax settings object before fetching
 */
$(document).ajaxSend(function(e, jqxhr, settings) {
	if(settings.type.toLowerCase() === 'post') {
		var target = e.target || e.srcElement;
		var csrfHidden = getCSRFField(target);
		if(csrfHidden) {
			var csrfHiddenName = csrfHidden.attr('name'),
				csrfHiddenValue = csrfHidden.val();
			if(!settings.data) {
				settings.data = '';
				if(jqxhr !== null && !($.isEmptyObject(jqxhr))) {
					jqxhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
				}
			}
			if(typeof settings.data === 'string' && settings.data.indexOf(csrfHiddenValue) === -1) {
				settings.data = settings.data + ((settings.data.lastIndexOf('&') === settings.data.length - 1) ? '' : '&') + csrfHiddenName + '=' + csrfHiddenValue;
			}
		}
	}
});

/**
 * Prevents Duplicate submissions of forms
 * @usage $('form').preventDoubleSubmission();
 */
$.fn.preventDuplicateSubmission = function() {
	var doc = $(document);
	var self = $(this);
	self.submit(function(e) {
		if (!e.isDefaultPrevented()) {
			if (self.data('submitted') === true || doc.data('submissionInProgress') === true) {
				return false;	// Form already submitted or another form is being submitted
			} else {
				self.data('submitted', true);
				doc.data('submissionInProgress', true);
			}
		}
	});
};
// Remove submitted state on ajax calls as forms can be resubmitted
$(document).ajaxComplete(function(e, jqxhr, settings) {
	var target = e.target || e.srcElement;
	$(target).removeData('submitted');	// Form submission allowed again via ajax
	$(document).removeData('submissionInProgress');	// Forms submission opened again as no other forms are being submitted
});

/* *************** For All Pages *************** */
function common() {	
	var accordion = new accordionModule();
	accordion.init($('.js-accordion'));
	
	// Adding stripes for IE browsers less then or equal to 8
	if($.browser.msie && $.browser.version < 9) {
		$('table.table-striped').each(function() {
			$(this).find('tr:even').find('th, td').addClass('odd');
		});
	}
}

/* *************** Document Ready Function *************** */
$(function() {
	
	/* =============>> Module to call page dependend functions <<============= */
	(function() {
		var url, pageNameWithExtension, pageName, initPage;
		
		url = window.location.pathname; // Getting complete URL 
		pageNameWithExtension = url.split('/'); // Getting current page name with extension
		pageName = pageNameWithExtension[pageNameWithExtension.length - 1].split('.')[0].replace(/-/g, '_'); // Page name without extension and after replacing '-' with '_'
		initPage = window[pageName]; // Setting page name as function name
		
		// If initial function exist for page
		if (typeof initPage == 'function') { 
		  initPage(); // Calling current page Initial function
		}
		
		common(); // Calling global functions (functions for )
		
	}());
	
});

/* =============>> Tabing module <<============= */
var tabingModule = (function($) {
	var init, tabing;
	
	init = function(tabGroup, contentGroup) {
		
		// Event fire on click of tabs
		tabGroup.on('click', function() {	
			var self, tab;
			
			self = $(this);
			tabGroup.removeClass('active');
			self.addClass('active');
			
			tab = $(this).data('tabName');
			contentGroup.find('[id$="-link"]').hide();			
			contentGroup.find('#' + tab).show();
			
			return false;
		});
	}
	
	tabing = function() {
			this.init = init;
	}
	
	return tabing;
		
}(jQuery));


/* =============>> Accordion module <<============= */
var accordionModule = (function($) {
	var init, accordion;
	
	init = function(className) {		
		className.on('click', function(e) {	
		
			// If user click in any input do nothing
			if($(e.target).is('input')) {
				return;
			}
			
			var self = $(this);
			self.find('span[class*="icon-arrow"]').toggleClass('icon-arrow-down icon-arrow-right');
			self.next().stop().slideToggle();
		});
	}
	
	accordion = function() {
		this.init = init;
	}
	
	return accordion;
		
}(jQuery));

/* =============>> Security Question Function Module <<============= */
function changeQuestion(questionAnswerParentID, questionID, answerID, customQuestionAnswerParentID ){
	if($('#'+questionID).find('option:selected').val() == 'custom'){
		$('#'+customQuestionAnswerParentID).slideDown('slow');
		//$('#'+answerID).hide();
		$('#'+customQuestionAnswerParentID).append($('#'+answerID).remove());
	}else{
		$('#'+customQuestionAnswerParentID).slideUp('slow');	
		//$('#'+answerID).show();
		$('#'+questionAnswerParentID).append($('#'+answerID).remove());
	};
}

//Equal Height JS
$.fn.setAllToMaxHeight = function(){
	return this.height( Math.max.apply(this, $.map(this,function(e){return $(e).height()})));
};

$.fn.ssnControl = function(options) {
	var defaults = {
		separator: "",
		firstInputClass: "input-miniAlt marginR10",
		secondInputClass: "input-miniAlt marginR10",
		thirdInputClass: "input-miniAlt"
	};
	
	function jqCompliantId(id) {
		return '#' + id.replace(/(:|\.|\[|\])/g, '\\$1');
	}
	
	function extractValueArray(value, separator) {
		/* if(separator) {
			return value.split(separator);
		} */
		return [value.substr(0, 3), value.substr(3, 2), value.substr(5)];
	}
	$.extend(defaults, options);
	return this.each(function() {
		var othis = $(this),
			parent = othis.parent(),
			id = this.id || this.name,
			value = this.value,
			firstInputVal,
			secondInputVal,
			thirdInputVal;
			firstInputVal = secondInputVal = thirdInputVal = "";
		if(value) {
			var inputValArray = extractValueArray(value, defaults.separator);
			secondInputVal = inputValArray[1];
			thirdInputVal = inputValArray[2];
			firstInputVal = inputValArray[0];
			var newControl = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' name='" + id + "_0' maxLength='3' class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' name='" + id + "_1' maxLength='2' class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' name='" + id + "_2' id='" + id + "_2' maxLength='4'  class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "'>";
		}else{
			var newControl = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' maxLength='3'  class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' maxLength='2'  class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' id='" + id + "_2' maxLength='4'   class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "'>";
		}
		
		var newControl1 = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' name='" + id + "_0' maxLength='3' class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' name='" + id + "_1' maxLength='2' class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' id='" + id + "_2' name='" + id + "_2' maxLength='4' class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "'>";
		if(this.id.indexOf('ssn')>0) {
			othis.replaceWith(newControl1);
		} else {
			othis.replaceWith(newControl);
		}

	});

};

$.fn.phoneControl = function(options) {
	var defaults = {
		separator: "",
		firstInputClass: "input-miniAlt marginR10",
		secondInputClass: "input-miniAlt marginR10",
		thirdInputClass: "input-miniAlt"
	};
	
	function jqCompliantId(id) {
		return '#' + id.replace(/(:|\.|\[|\])/g, '\\$1');
	}
	
	function extractValueArray(value, separator) {
		/* if(separator) {
			return value.split(separator);
		} */
		return [value.substr(0, 3), value.substr(3, 3), value.substr(6)];
	}
	$.extend(defaults, options);
	return this.each(function() {
		var othis = $(this),
			parent = othis.parent(),
			id = this.id || this.name,
			value = this.value,
			firstInputVal,
			secondInputVal,
			thirdInputVal;
			firstInputVal = secondInputVal = thirdInputVal = "";
		if(value) {
			var inputValArray = extractValueArray(value, defaults.separator);
			secondInputVal = inputValArray[1];
			thirdInputVal = inputValArray[2];
			firstInputVal = inputValArray[0];
			var newControl = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' maxLength='3' class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' maxLength='3' class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' id='" + id + "_2' maxLength='4'  class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "'>";
		}else{
			var newControl = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' maxLength='3' disabled='true' class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' maxLength='3' disabled='true' class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' id='" + id + "_2' maxLength='4' disabled='true'  class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "'>";
		}
		
		var newControl1 = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' name='" + id + "_0' maxLength='3' class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' name='" + id + "_1' maxLength='3' class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' id='" + id + "_2' name='" + id + "_2' maxLength='4' class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "'>";
		if(this.id == 'employee.contactDetails.phoneNumber' || this.id == 'employee.contactDetails.cellPhoneNumber' || this.id == 'employee.contactDetails.workPhoneNumber') {
			othis.replaceWith(newControl1);
		} else {
			othis.replaceWith(newControl);
		}

	});

};


$.fn.errorControls = function(options) {
	var id=options.id.substring(0,options.id.length-7);
	if(id.contains("familyMembers")){
		var arr=id.split('.');
		id="familyMembers["+arr[0].substring(13,arr[0].length)+"]."+arr[1];
	}
	$("div[id^='"+options.id+"']").addClass('error errorAlt');
	$('[name="'+id+'"]').addClass('error errorAlt');
	$('[name="'+id+'"]').siblings('.icon-error').attr('style','display:block');
	if($('[name="'+id+'"]').hasClass('checkbox')){
		$('[name="'+id+'"]').siblings('.icon-error').css('right', -($('[name="'+id+'"]').parent().next().width()+21));
		$('[name="'+id+'"]').siblings('.icon-error').addClass('checkboxClass');
	}else if($('[name="'+id+'"]').hasClass('radio')){
		$('[name="'+id+'"]').siblings('.icon-error').css('right', -($('[name="'+id+'"]').parent().next().width()+21));
		$('[name="'+id+'"]').siblings('.icon-error').addClass('radioClass');
	}else if($('[name="'+id+'"]').hasClass('select')){
		$('[name="'+id+'"]').siblings('.icon-error').css('right', -($('[name="'+id+'"]').parent().next().width()+18));
		$('[name="'+id+'"]').siblings('.icon-error').addClass('selectClass');
	}else if($('[name="'+id+'"]').hasClass('dob')){
		$('[name="'+id+'"]').siblings('.icon-error').css('right', -20);
		$('[name="'+id+'"]').siblings('.icon-error').addClass('selectClass');
	}
	else {
		$('[name="'+id+'"]').siblings('.icon-error').attr('style','display:block');
	}
	
	return ;

};

Common = {
		 enableErrorIcon : function() {
				$('input, select, textarea').wrap('<span class="errorWarp"></span>').parent().append('<span class="icon icon-error"></span>');
				$('input.dob').siblings('.icon-error').css('right', -20);
				$('input[type=radio],input[type=checkbox]').each(function(index, element) {
					$(this).siblings('.icon-error').css('right', -($(this).parent().next().width()+21));
				});
				$('select').each(function(index, element) {
					$(this).siblings('.icon-error').css('right', -($(this).parent().next().width()+18));
				});
			},
			enableErrorDisplay : function(){
				$(".icon-error").live("mouseover",function(){
					var parent =  $(this).parent('.errorWarp'),
						pos = $(this).position().left + 20;
					
				  	parent.find('div.error').eq('0').css({
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
					$(this).parent('.errorWarp').find('div.error').fadeOut('slow', function() {
						$(this).removeAttr('style');
					});
				});
			}
}


/**************** Custom Checkbox and radio button ****************/

//Module to create customize controls for cross browsers
var customControl = (function($) {		    
	
	var applySkin, bindEvents, customizeControl;

	// Function to hide original control
	function hideControl(control, controlWidth, controlHeight) {
		
		control.css({
			opacity: 0,
			width: controlWidth,
			height: controlHeight,
			margin: 0,
			padding: 0
		});
	}
	
	// Object to apply skin in radiobuttons and checkboxes
	applySkin = {
		
		// Object to apply skin in checkboxes
		fancyCheckbox: function(target, className) {
			
			var fancyCheckbox, checkBoxSkin, checkBoxWidth, checkBoxHeight;
			
			fancyCheckbox = target.find('input[type="checkbox"]');
			
			fancyCheckbox.each(function() {
				var self = $(this);				
				self.wrap('<span />').parent().addClass(className);
				applySkin.addGenericParts(self);
			});
			
			checkBoxSkin = fancyCheckbox.parent();
			checkBoxWidth = checkBoxSkin.width();
			checkBoxHeight = checkBoxSkin.height();
			
			hideControl(fancyCheckbox, checkBoxWidth, checkBoxHeight);
			bindEvents.fancyCheckbox(checkBoxSkin);
		},
		
		// Object to apply skin in radio buttons
		radioBtn: function(target, className) {
			
			var radioBtn, radioBtnSkin, radioBtnWidth, radioBtnHeight;
			
			radioBtn = target.find('input[type="radio"]');
			
			radioBtn.each(function() {
				var self = $(this);
				self.wrap('<span />').parent().addClass(className);
				applySkin.addGenericParts(self);
			});
			
			radioBtnSkin = radioBtn.parent();
			radioBtnWidth = radioBtnSkin.width();
			radioBtnHeight = radioBtnSkin.height();
			
			hideControl(radioBtn, radioBtnWidth, radioBtnHeight);
			bindEvents.radioBtn(radioBtnSkin, className);
			
		},
		
		// Object to add generic parts in skin of controls
		addGenericParts: function(self) {
			
			// If control is enabled
			if(self.is(':enabled')) {
				
				// If control is checked
				if(self.is(':checked')) {
					self.parent().addClass('checked');
				}
			}
			
			// If control is disabled
			else {
				self.parent().addClass('disabled');
				
				// If control is disabled and checked
				if(self.is(':checked')) {
					self.parent().addClass('checked');
				}
			}
		}
	}
	
	// Object to bind event
	bindEvents = {
		
		// Object to bind checkboxes events
		fancyCheckbox: function(target) {
						
			this.focusEvent(target, 'fancyCheckbox'); // Binding focusin and focusout events
			
			// Event trigger on change of checkbox value
			target.on('change', 'input[type="checkbox"]', function() {
				$(this).parent().toggleClass('checked');
			});
		},
		
		// Object to bind radio buttons event
		radioBtn: function(target, className) {
			
			this.focusEvent(target, 'radio'); // Binding focusin and focusout events
			
			// Event trigger on change of radio button value
			target.on('change', 'input[type="radio"]', function() {
				
				var self = $(this);
				var name = self.attr('name');
				
				// Condition to check if radio button is inside a form
				if(self.parents('form').length) {
					self.parents('form').find('span.'+ className +' input[name="' + name + '"]').parent().removeClass('checked');
				}
				
				// If radio button is not inside a form
				else {
					target.find('span.'+ className +' input[name="' + name + '"]').parent().removeClass('checked');
				}
				
				self.parent().addClass('checked');
			});
		},
		
		// Object to bind event trigger in focus and focus out from control
		focusEvent: function(target, controlType) {
			
			// Event trigger on focus in control
			target.on('focusin', 'input[type="' + controlType + '"]', function() {
				$(this).parent().addClass('focus');
			});
			
			// Event trigger on focus out from control
			target.on('focusout', 'input[type="' + controlType + '"]', function() {
				$(this).parent().removeClass('focus');
			});
		}
	}

 // Constructor Object
 customizeControl = function (target) {		
		
		this.fancyCheckbox  = function(className) {
			applySkin.fancyCheckbox(target, className);
		}
		
		this.radioBtn  = function(className) {
			applySkin.radioBtn(target, className);
		}		
 };	

	return customizeControl;
	 
}(jQuery));

var customize = {
	radioBtn: false,
	fancyCheckbox: false,
	uploadFile: false
}

$.fn.extend({
	customControl: function(options) {
		
		var target, defaultClasses, classes, customizeControl;
				
		target = $(this);
		
		// Object to define default classes for controls
		defaultOptions = {
			customizeRadioButton: true,
			customizeCheckBox: true,
			radioButtonClass: 'radioBtn',
			checkBoxClass: 'fancyCheckbox'
		};
		
		// Replace default classes with defined
		options = $.extend(defaultOptions, options);
		
		customizeControl = new customControl(target);
		
		if(options.customizeCheckBox) {
			customizeControl.fancyCheckbox(options.checkBoxClass);
		}
		
		if(options.customizeRadioButton) {
			customizeControl.radioBtn(options.radioButtonClass);
		}
				
	}
});


$.fn.ssnControlExtended = function(options) {
	var defaults = {
		separator: "",
		readOnly: false,
		firstInputClass: "input-miniAlt marginR10",
		secondInputClass: "input-miniAlt marginR10",
		thirdInputClass: "input-miniAlt"
	};
	
	function jqCompliantId(id) {
		return '#' + id.replace(/(:|\.|\[|\])/g, '\\$1');
	}
	
	function extractValueArray(value, separator) {
		/* if(separator) {
			return value.split(separator);
		} */
		return [value.substr(0, 3), value.substr(3, 2), value.substr(5)];
	}
	$.extend(defaults, options);
	return this.each(function() {
		var othis = $(this),
			parent = othis.parent(),
			id = this.id || this.name,
			value = this.value,
			firstInputVal,
			secondInputVal,
			thirdInputVal;
			firstInputVal = secondInputVal = thirdInputVal = "";
			readOnlyElem = this.readOnly? 'readOnly' : 'false';
			disabledElem = this.disabled; 
		if(value) {
			var inputValArray = extractValueArray(value, defaults.separator);
			secondInputVal = inputValArray[1];
			thirdInputVal = inputValArray[2];
			firstInputVal = inputValArray[0];
			if(readOnlyElem=='readOnly' || disabledElem){
				var newControl = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' name='" + id + "_0' maxLength='3' class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "' readOnly='readOnly' />" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' name='" + id + "_1' maxLength='2' class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "' readOnly='readOnly' />" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' name='" + id + "_2' id='" + id + "_2' maxLength='4'  class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "' readOnly='readOnly' />" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "' data-field-value='" + value + "'/>";
			}else{
				var newControl = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' name='" + id + "_0' maxLength='3' class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' name='" + id + "_1' maxLength='2' class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' name='" + id + "_2' id='" + id + "_2' maxLength='4'  class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "' data-field-value='" + value + "'>";
			}
		}else{
			var newControl = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' maxLength='3'  class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' maxLength='2'  class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' id='" + id + "_2' maxLength='4'   class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "' data-field-value='" + value + "'>";
		}
		
		//var newControl1 = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' name='" + id + "_0' maxLength='3' class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' name='" + id + "_1' maxLength='2' class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' id='" + id + "_2' name='" + id + "_2' maxLength='4' class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "'>";
		
			othis.replaceWith(newControl);

	});

};

$.fn.phoneControlExtended = function(options) {
	var defaults = {
		separator: "",
		firstInputClass: "input-miniAlt marginR10",
		secondInputClass: "input-miniAlt marginR10",
		thirdInputClass: "input-miniAlt"
	};
	
	function jqCompliantId(id) {
		return '#' + id.replace(/(:|\.|\[|\])/g, '\\$1');
	}
	
	function extractValueArray(value, separator) {
		/* if(separator) {
			return value.split(separator);
		} */
		return [value.substr(0, 3), value.substr(3, 3), value.substr(6)];
	}
	$.extend(defaults, options);
	
	return this.each(function() {
		var othis = $(this),
			parent = othis.parent(),
			id = this.id || this.name,
			value = this.value,
			firstInputVal,
			secondInputVal,
			thirdInputVal;
			firstInputVal = secondInputVal = thirdInputVal = "";
			readOnlyElem = this.readOnly=='readOnly'? 'readOnly' : 'false';
		if(value) {
			var inputValArray = extractValueArray(value, defaults.separator);
			secondInputVal = inputValArray[1];
			thirdInputVal = inputValArray[2];
			firstInputVal = inputValArray[0];
			if(readOnlyElem=='readOnly'){
				var newControl = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' name='" + id + "_0' maxLength='3' class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "' readOnly='readOnly'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' name='" + id + "_1' maxLength='3' class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "' readOnly='readOnly'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' id='" + id + "_2' name='" + id + "_2' maxLength='4'  class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "' readOnly='readOnly'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "' data-field-value='" + value + "'>";
			}else{
				var newControl = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' name='" + id + "_0' maxLength='3' class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1'  class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' name='" + id + "_1' maxLength='3' class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' id='" + id + "_2' name='" + id + "_2' maxLength='4'  class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "' data-field-value='" + value + "'>";
			}
		}else{
			var newControl = "<label for='" + id + "_0' class='hide'>SSN1</label>" + "<input type='text' id='" + id + "_0' name='" + id + "_0' maxLength='3'  class='" + defaults.firstInputClass + " posWholeNumeric' value='" + firstInputVal + "'/>" + "<label for='" + id + "_1' class='hide'>SSN2</label>" + "<input type='text' id='" + id + "_1' name='" + id + "_1' maxLength='3'  class='" + defaults.secondInputClass + " posWholeNumeric' value='" + secondInputVal + "'/>" + "<label for='" + id + "_2' class='hide'>SSN3</label>" + "<input type='text' id='" + id + "_2' name='" + id + "_2' maxLength='4'   class='" + defaults.thirdInputClass + " posWholeNumeric' value='" + thirdInputVal + "'/>" + "<input type='hidden' id='" + this.id + "' name='" + this.name + "' value='" + value + "' data-field-value='" + value + "'>";
		}
		
			othis.replaceWith(newControl);

	});

};

$('#homePage').customControl();

$('.notImplemented').live('click', function(e) {
	alert('Feature Not Implemented Yet!');
	return false;
});

$('.nonNegativeInteger').live('keydown', function(event){
    // Allow: backspace, delete, tab, escape, and enter
    if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
         // Allow: Ctrl+A
        (event.keyCode == 65 && event.ctrlKey === true) || 
         // Allow: home, end, left, right
        (event.keyCode >= 35 && event.keyCode <= 39) ||
        (((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105 )) 
        		&& event.ctrlKey === false && event.altKey === false && event.shiftKey === false)
        ) {
             // let it happen, don't do anything
             return;
    }
	event.preventDefault(); 
});
