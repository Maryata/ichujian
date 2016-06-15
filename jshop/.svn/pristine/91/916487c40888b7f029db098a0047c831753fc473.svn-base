(function($, w, d){
	function b(){
		h = $(w).height();
		t = $(d).scrollTop();
		if(t > h){
			$('#gotop').show();
		}else{
			$('#gotop').hide();
		}
	}

	$('#gotop').click(function(){
		$(d).scrollTop(0);	
	})

	$(w).scroll(function(e){
		b();		
	})
	
	b();
}(jQuery, window, document));

