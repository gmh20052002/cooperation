/*
 *	jquery.msgbox 4.2 - 2009-11-23
 *
 *	All the stuff written by pwwang (pwwang.com)	
 *	Feel free to do whatever you want with this file
 *  Please keep the distribution
 *
 */
(function($) {
	$.msgbox = function(o) {
		
		if(typeof(o) == 'string'){ o = { content:o } }
		var options = o || {};
		
		options.width 			= o.width 		|| 360;  
		options.height 			= o.height 		|| 200, 
		options.closeAfter 		= o.closeAfter 	|| 0;
		options.title 			= o.title 		|| '提示', 			// title of msgbox
		options.wrapperClass	= o.wrapperClass|| 'msgbox_wrapper';
		options.titleClass 		= o.titleClass 	|| 'msgbox_title';
		options.titleWrapperClass = o.titleWrapperClass || 'msgbox_title_wrapper';
		options.mainClass 		= o.mainClass 	|| 'msgbox_main';
		options.bgClass 		= o.bgClass 	|| 'msgbox_bg';
		options.buttonClass		= o.buttonClass || 'msgbox_button';
		options.type 			= o.type 		|| 'text';
		// support:  text, url(=get), iframe, confirm, alert; confirm, alert is added in version 4.0
		options.content 		= o.content 	|| 'Hello, world!';
		options.closeEvent 		= o.closeEvent 	|| function(){};
		options.closeImg		= o.closeImg	|| 'images/close.gif';
		options.bgOpacity		= o.bgOpacity	|| 0.6;   			// from 0 to 1
		options.afterAjaxEvent	= o.afterAjaxEvent|| function(){};
		options.oneBoxMode		= typeof(o.oneBoxMode) == 'undefined' ? true : o.oneBoxMode;
		options.hideScrollBar	= typeof(o.hideScrollBar) == 'undefined' ? false : o.hideScrollBar;
		// only works in firefox
		
		var dragFlag = false;
		var startX = 0;
		var startY = 0;
		var returnValue = false;
		
		var $background = $("<div>")
			.css({
				 'position'	: 'absolute',
				 'top'		: '0',
				 'left'		: '0',
				 'z-index'	: '9999'
			})
			.addClass(options.bgClass)
			.appendTo('body')
			.animate({opacity:options.bgOpacity})
			.dblclick(closeMe)
			.click(function(){
				flashTitle(0.5, 4, 80);
			})
			.mouseup(dragEnd);
			
		// help IE users if possible
		//try {
		//	$background.bgiframe();
		//} catch(e) { }	
		
		var $wrapper = $("<div>")
			.css({
				'width' 	: options.width + 'px',
				'height' 	: options.height + 'px',
				'position' 	: 'absolute',
				'z-index'	: '10000'
			})
			.addClass(options.wrapperClass)
			.appendTo('body')
			.mouseup(dragEnd);
		
		var $titleWrapper = $('<table width=100% cellspacing=0 cellpadding=0><tr><td></td><td><a href="javascript:;">关闭</a></td></tr></table>')
			.css({
			 'cursor' : 'move',
			 'vertical-align' : 'middle'
			})
			.addClass(options.titleWrapperClass)
			.appendTo($wrapper)
			.mousedown(function(e){
				dragStart(e);					
			})
			.mousemove(function(e){
				if(dragFlag)
					dragTo(startX, startY, e);				
			})
			.mouseout(function(e){
				if(dragFlag)
					dragTo(startX, startY, e);			   
			})
			.mouseup(dragEnd);
			
		var $titleTd = $("td",$titleWrapper).eq(0)
			.html(options.title)
			.addClass(options.titleClass)
			.mouseup(dragEnd);
			
		var $closeA = $titleTd.next()
			.attr('align','right')
			.find("a")
			.mousedown(closeMe)
			.html("<img src="+ options.closeImg +" border=0 />");
						
		var $main = $(document.createElement("div"))
			.addClass(options.mainClass)
			.appendTo($wrapper);
			
		$main.height( 
			options.height 
				- $titleWrapper.outerHeight(true)
				- $main.outerHeight(true)
				+ $main.height()
			)
			.mouseup(dragEnd);

		function closeMe(){
			$('select').css('visibility','visible'); //4.2
			if(options.hideScrollBar)
				$('body').css('overflow','auto'); // show scroll bar if any
			$wrapper.fadeOut();
			$background.fadeOut();
			options.closeEvent(returnValue);
		}
		
		function isVisible(){
			return	$background.is(":visible") &&
					$wrapper.is(":visible");
		}

		function autoCloseMe(closeAfter){			
			if( closeAfter > 0 && isVisible() ){ // prevent manually closing
				autoCloseStr = closeAfter + " 秒后关闭 ...";
				$titleTd.html(options.title + " &nbsp; " + autoCloseStr);		
				closeAfter --;
				if( closeAfter == 0 ) 
					closeMe();	
				setTimeout(function(){ autoCloseMe(closeAfter) }, 1000);	
			}		
		}
		
		function dragStart(e){			
			if(e.preventDefault)
				e.preventDefault(); 
			if(e.stopPropagation())
				e.stopPropagation();			
			dragFlag = true;
			var offset = $wrapper.offset();
			startX = e.pageX - offset.left;
			startY = e.pageY - offset.top;				
		}
		
		function dragTo(x, y, e){		
			if(e.preventDefault)
				e.preventDefault(); 
			if(e.stopPropagation())
				e.stopPropagation();			
			ensureMoveInScreen(e.pageX-x, e.pageY-y);
		}
		
		function ensureMoveInScreen(x,y){		
			var offset = $wrapper.offset();
			if( x < $(window).scrollLeft() ) boxMoveTo($(window).scrollLeft(), y);
			if( y < $(window).scrollTop() ) boxMoveTo(x, $(window).scrollTop());
			if( offset.left > $(window).scrollLeft() + $(window).width() - options.width ) 
				boxMoveTo($(window).scrollLeft() + $(window).width() - options.width, y);
			if( offset.top > $(window).scrollTop() + $(window).height() - options.height )
				boxMoveTo(x, $(window).scrollTop() + $(window).height() - options.height);
			if( x>=$(window).scrollLeft() && x<=$(window).scrollLeft() + $(window).width()-options.width &&
				y>=$(window).scrollTop() && y<=$(window).scrollTop() + $(window).height()-options.height)
				boxMoveTo(x,y);		
		}
		
		function boxMoveTo(x,y){
			$wrapper.css({
				'top'  : y + 'px',
				'left' : x + 'px'
			});		
		}
		
		function dragEnd(){					
			var offset = $wrapper.offset();
			ensureMoveInScreen(offset.left, offset.top);	
			dragFlag = false;
		}
		
		function resetPosition() {
			$background.css({
				 'width'	: document.documentElement.scrollWidth + 'px',
				 'height'	: Math.max( document.documentElement.scrollHeight,
										$('body').outerHeight() ) + 'px'
			});
			$wrapper.css({
				'top'		: $(window).scrollTop() + ($(window).height() - options.height)/2 + 'px',
				'left'	 	: $(window).scrollLeft() + ($(window).width() - options.width)/2 + 'px'			 
			});	
		}
		
		function flashTitle(opacity, times, interval, flag){
			if( times > 0 ) {
				flag = !flag;
				op = flag ? opacity : 1;
				$titleWrapper.css('opacity',op);	
				setTimeout(function(){ flashTitle(opacity, times-1, interval, flag) }, interval);
			}
		}
		
		function bindIframeMouseup(index){ // for dragEnd, if mouse released in the iframe
			try {
				window.frames[index].document.onmouseup = dragEnd;
			} catch(e) {}
			setTimeout(function(){bindIframeMouseup(index)}, 200);
		}
		/*
		function oneBox(){
			if(self!=top && options.oneBoxMode) { 
				$background.remove();
				$wrapper.remove();
				$('body').html($main);
			}		
		}*/
		
		function msgbox(){
			$('select').css('visibility','hidden'); //4.2
			
			if(options.hideScrollBar)
				$('body').css('overflow','hidden');  //hide scroll bar if any
				
			switch(options.type){
				case 'text':
					$main.html(options.content);
					//oneBox();
					break;
				case 'alert':
					$main.html(options.content);
					//oneBox();
					var $buttonWrapper = $("<div>")
						.css({
							'text-align':'center',
							'padding':'15px 0'
						})
						.appendTo($main);
					var $OKButton = $("<input type=button value=' OK '>")
						.appendTo($buttonWrapper)
						.addClass(options.buttonClass)
						.click(closeMe);
					break;
				case 'confirm':
					$main.html(options.content);
					//oneBox();
					var $buttonWrapper = $("<div>")
						.css({
							'text-align':'center',
							'padding':'15px 0'
						})
						.appendTo($main);
					var $yesButton = $("<input type=button value=' Yes '>")
						.appendTo($buttonWrapper)
						.addClass(options.buttonClass)
						.after(" &nbsp; &nbsp; ")
						.click(function(){
							returnValue = true;
							closeMe();
						});
					var $noButton = $("<input type=button value=' No '>")
						.appendTo($buttonWrapper)
						.addClass(options.buttonClass)
						.click(function(){
							returnValue = false;
							closeMe();
						});
					break;					
				case 'get':
				case 'ajax':
				case 'url':
					$main.text("Loading ...").load(
						options.content,
						'',
						function(data){
							(options.afterAjaxEvent)(data);	
						}
					);
					//oneBox();
					break;			
				case 'iframe':
					if(self!=top && options.oneBoxMode) { 
						$background.remove();
						$wrapper.remove();
						//window.open(options.content, '_self', '');
						setTimeout(function(){window.location.href = options.content;},1);
						// bug for ie 8
					} else { // mark sure this page is not in an iframe
						var $iframe = $("<iframe frameborder=0 marginheight=0 marginwidth=0></iframe>")
							.appendTo($main)
							.attr({
								'width'			: '100%',
								'height'		: '100%',
								'scrolling' 	: 'auto',
								'src'			: options.content
							});
							//.appendTo($main);   a bug for IE, iframe not show	
						$iframe.find(document)
							.ready(function(){
								var index = $("iframe").index($iframe);
								bindIframeMouseup(index);
							});				
					}
					break;
			}	
		}
		
		resetPosition();
		$(window)
			.load(resetPosition)		// just in case user is changing size of page while loading
			.resize(resetPosition)
			.mouseup( dragEnd );	
		if( options.closeAfter > 0 ) 
			autoCloseMe(options.closeAfter);
		msgbox();

		return this;
	}			
})(jQuery);
