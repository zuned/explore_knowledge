(function($) {
	$.fn.jQpopup = function(options) {
	// build main options before element iteration
	var opts = $.extend({}, $.fn.jQpopup.defaults);
	
	if(typeof options == "object"){
		$.extend(opts, $.fn.jQpopup.defaults, options);
	} else if(typeof options == "string"){
		switch(options){
			case "show" :
				$.extend(opts, {show : true});
				break;
			case "close" :
				$.extend(opts, {show : false});
				break;
			default : break;
		}
	}

	return this.each(function() {
		$this = $(this)
		ww = $(window).width();
		wh = $(window).height();
		pw = $this.outerWidth(true);
		ph = $this.outerHeight(true);
		pos = (wh > ph) ? 'fixed' : 'absolute';
		$this.css({
			left: (ww-pw)/2,
			top: (wh > ph) ? (wh-ph)/2 : '20px', 
			position: pos
		});
		if(pos == 'absolute' && opts.show == true){
			$('html, body').animate({scrollTop : 0},'slow');
		}
		if(opts.show == false){
			$this.hide();
			$(opts.overlay).hide();
		}else{
			$this.slideDown();
			$(opts.overlay).show();
		} 
		
		$(opts.closeBtn).click(function(){
			$this.slideUp('slow');
			$(opts.overlay).hide();
		});
	});
	
	};
$.fn.jQpopup.defaults = {
	overlay: '#overlay',
	closeBtn: '.closePopUp',
	show: false
	};
})(jQuery);