(function($) {
	
window.hasPlaceholderSupport = false;
var test = document.createElement('input');
if('placeholder' in test) window.hasPlaceholderSupport = true;

if(!window.hasPlaceholderSupport){
	$.fn.placeholder = function placeholder(options) {
		var defaults = {css_class: "placeholder"};
		var options = $.extend(defaults, options);
		this.each(function() {
			var phvalue = $(this).attr("placeholder");
			var currvalue = $(this).val();
			if (!currvalue) {
				$(this).val(phvalue);
				$(this).addClass(options.css_class);
			}
	//		$(this).focusin(function(event){
	//			var ph = $(this).attr("placeholder");
	//			if (ph == $(this).val()) {
	//				$(this).val("").removeClass(options.css_class);
	//			}
	//		});
	//
	//		$(this).focusout(function(event){
	//			var ph = $(this).attr("placeholder");
	//			if ($(this).val() == "") {
	//				$(this).val(ph).addClass(options.css_class);
	//			}
	//		});
			
		});
	
		$("form").on("focusin", "input", function(arg){
			var element = $(arg.target);
			var ph = element.attr("placeholder");
			if (element.hasClass(options.css_class) && ph && ph == element.val()) {
				element.val("").removeClass(options.css_class);
			}
		});
	
		$("form").on("focusout", "input", function(arg){
			var element = $(arg.target);
			var ph = element.attr("placeholder");
			if (ph && !element.val()) {
				element.val(ph).addClass(options.css_class);
			}else{
				element.removeClass(options.css_class);
			}
		});
		
		$('form').submit(function(){
			$('input', this).each(function(){
				if($(this).hasClass(options.css_class) && $(this).val() == $(this).attr('placeholder')) $(this).val('');
			});
		});
		
	return this;
	};
}
})(jQuery);

/**
 * Enabling placeHolder
 */
$(document).ready(function(){
	if(!window.hasPlaceholderSupport) $('[placeholder]').placeholder();
});