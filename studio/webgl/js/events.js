var myEvent = function (eventOvserverName, eventObserverClass,data) {
	this.eventOvserverName = eventOvserverName;
	this.eventObserverClass = eventObserverClass;
	this.data = data;
	this.toString= function () {return "{\"eventOvserverName\":\""+this.eventOvserverName.toString()+"\",\"eventObserverClass\":"+JSON.stringify(this.eventObserverClass)+",\"data\":"+JSON.stringify(this.data)+"}";};
};

var eventCapturer = function(evaluatorFunction,classNames,ObserverName){
		window.ObserverName = ObserverName;
		window.classNames = classNames;
		
		window.evaluatorFunction = function(event){
			event = JSON.parse(event.data);

			if (event.eventOvserverName==window.ObserverName || window.classNames.indexOf(event.eventObserverClass)>-1){evaluatorFunction(event); }
		}
		if (window.addEventListener){
		  addEventListener("message", window.evaluatorFunction, false);
		} else {
		  attachEvent("onmessage", window.evaluatorFunction);
		}
}

var defaultEventCapturer = function(){
	if (window.addEventListener){
		   addEventListener("message", function (event){
				$("iframe").each(function(){
		
					this.contentWindow.postMessage(event.data.toString(),'*');
				})
				}, false);
		} else {
		   attachEvent("message", function (event){
				$("iframe").each(function(){
					
					this.contentWindow.postMessage(event.data.toString(),'*');
				})
				});
		}
}
dEC = new defaultEventCapturer();
var eventSender = function (observableName,observableClasses,data){
		evt = new myEvent(observableName,observableClasses,data);
		try{
		window.top.contentWindow.postMessage(evt.toString(),'*');
		}catch (e){
			window.top.postMessage(evt.toString(),'*');
		}
		
	}
if (window.addEventListener){
		   addEventListener("message", function (event){
				}, false);
		} else {
		   attachEvent("message", function (event){
				});
		}	

