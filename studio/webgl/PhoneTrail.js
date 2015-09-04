function PhoneTrail(object){
    this.object=object;
    this.trailingStartTime=0;
    this.trailingStopTime=0;
    this.trailingRegressionFunc=null;
    this.defaultRGB = this.object.material.color.clone();
    this.updatePassedByTrailEventId=null;
};

var myRGBColorFunc=function(frac,RGB){
    if (frac>1) {this.object.material.color=this.defaultRGB;}
    else{
        RGB.r=frac;
        RGB.b=frac;
        return RGB;
    }
};
PhoneTrail.prototype = {
    getObject:function(){
        return this.object;
    },
    updateColor:function(R,G,B){
        this.object.material.color={r:R,g:G,b:B};
    },
    setPassedByTrail:function(seconds,regressionFunc){
        d = new Date();
        this.trailingStartTime = d.getTime();
        this.trailingStopTime =this.trailingStartTime + (1000*seconds);
        this.trailingRegressionFunc=regressionFunc;
    },
    updatePassedByTrail:function(){
        d = new Date();
        now = d.getTime();
        if (this.trailingStopTime > now) {
            t1 = this.trailingStopTime - this.trailingStartTime;
            t2 = now - this.trailingStartTime;
            frac =  (t2 / t1);
            this.object.material.color = this.trailingRegressionFunc(frac,this.object.material.color);
            return true;
        }
        return false;

    }
};
