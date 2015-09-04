function extender(container,imgObj,direction,style,_alsoResize){
	resizeDiv = document.createElement("div");
	resizeDiv.className +=" resizeBar";
	resizeDiv.style.cssText = style;
	resizeDiv.style.position="absolute";
	resizeDiv = $(resizeDiv);
	imgObj = $(imgObj);
	
	function flipImg(img,deg){
			img.css({  
                                '-webkit-transform': 'rotate(' + deg + 'deg)',  //Safari 3.1+, Chrome  
                                '-moz-transform': 'rotate(' + deg + 'deg)',     //Firefox 3.5-15  
                                '-ms-transform': 'rotate(' + deg + 'deg)',      //IE9+  
                                '-o-transform': 'rotate(' + deg + 'deg)',       //Opera 10.5-12.00  
                                'transform': 'rotate(' + deg + 'deg)',          //Firefox 16+, Opera 12.50+   
        });
	}
	

	if (direction=="up"){
	
		container.resizable({
		handles: "n",
		alsoResize: _alsoResize,
		stop : function(event,ui) {
			imgObj.attr("topX",container.css("top"));
	   		imgObj.attr("folded","false");
			flipImg(imgObj,0);
			}
		});
	
		container.css("bottom","0px");
		resizeDiv.css("width",container.css("width"));
		resizeDiv.css("top","0px");
		imgObj.css("height","inherit");
		imgObj.css("zIndex","100");
		imgObj.css( 'cursor', 'pointer' );
		imgObj.css("position","absolute");
		imgObj.bind('load', function() { imgObj.css("left",(parseInt(container.css("width"))/2)- (parseInt(imgObj.css("width"))/2)+"px"); });
		imgObj.attr("folded","false");
		imgObj.bind('click',function(){
			if (imgObj.attr("folded")=="false"){
				imgObj.attr("folded","true");
				imgObj.attr("topX",container.css("top"));
				newSize = (parseInt($(window).height())-parseInt(resizeDiv.css("height")))+"px";
				container.animate({top:newSize});
				flipImg(imgObj,180);
			}
			else {
				imgObj.attr("folded","false");
				container.animate({top:imgObj.attr("topX")});
				flipImg(imgObj,0);
			}
		});
	}
	if (direction=="right"){
	
		container.resizable({
		handles: "e",
		alsoResize: _alsoResize,
		stop : function(event,ui) {
			imgObj.attr("rightX",container.css("right"));
	   		imgObj.attr("folded","false");
			flipImg(imgObj,0);
			}
		});
	
		container.css("bottom","0px");
		resizeDiv.css("height",container.css("height"));
		resizeDiv.css("left","0px");
		imgObj.css("width","inherit");
		imgObj.css("zIndex","100");
		imgObj.css( 'cursor', 'pointer' );
		imgObj.css("position","absolute");
		imgObj.bind('load', function() { imgObj.css("left",(parseInt(container.css("width"))/2)- (parseInt(imgObj.css("width"))/2)+"px"); });
		imgObj.attr("folded","false");
		imgObj.bind('click',function(){
			if (imgObj.attr("folded")=="false"){
				imgObj.attr("folded","true");
				imgObj.attr("topX",container.css("top"));
				newSize = (parseInt($(window).height())-parseInt(resizeDiv.css("height")))+"px";
				container.animate({top:newSize});
				flipImg(imgObj,180);
			}
			else {
				imgObj.attr("folded","false");
				container.animate({top:imgObj.attr("topX")});
				flipImg(imgObj,0);
			}
		});
	}	

	resizeDiv.append(imgObj);
	container.append(resizeDiv);
	container.click();

}
var qqq;