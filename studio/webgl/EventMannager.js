/**
 * Created by nexus on 7/22/2015.
 */
function EventManagerClass () {
    this.events=[];
}

EventManagerClass.prototype = {
    registerEvent: function(event){
        this.events.push(event);
        return (this.events.length-2);
    },
    runEvents:function(){
        for (var i = this.events.length-1;i>=0;i--) {

            res = this.events[i]();
            if (res == false) {
               this.events.splice(i,1);
            }

    }
}
};