function loadPopup(html){
		$.jsPanel({
		selector: "#jspanel-func .panel-body",
		title:    "A Basic Example",
		position: "center",
		theme:    "success",
		size: {
			width: 700,
			height: 222
		},
		 content: html
		});
	}
		function makeImage(object){
	 tmp = document.createElement("img");
	 tmp.src= object.src;
	 return tmp;
	}
	/*
	var highlighted=[];
	function highlight(object){
	if (!isCntrl) {
		removeHighlighted();
		}
		object.addClass("highlighted");
		highlighted.push(object);
	}
	
	function removeHighlighted(){
		for (i=0;i<highlighted.length;i++){
			highlighted[i].removeClass("highlighted");
			console.log(highlighted[i]);
		}
		highlighted=[];
	}
	
	function makeImage(object){
	 tmp = document.createElement("img");
	 tmp.src= object.src;
	 return tmp;
	}
	
	function Resize(obj,direction){
	
	}
	
	$(document).bind('mousemove',function(e){ 
        Mx= e.pageX; 
		mY= e.pageY
	}); 
		var Mx=0;
		var mY=0;
		var isCntrl = false;
		$(document).keydown(function(e){
			if(e.keyCode==17){isCntrl= true;};
		});
		$(document).keyup(function(e){
			if(e.keyCode==17){isCntrl= false;};
		});
		*/