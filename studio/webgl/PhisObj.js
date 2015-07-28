/**
 * Created by nexus on 7/28/2015.
 */
function PhisObj(){
    this.v={};
    this.v.x=0;
    this.v.y=0;
    this.v.z=0;
    this.a={};
    this.a.x=0;
    this.a.y=0;
    this.a.z=0;
    this.delta={};
    this.delta.x=0;
    this.delta.y=0;
    this.delta.z=0;
    this.time=0;
}
PhisObj.prototype={
    applyAcceleration:function(a_xyz,t){
        this.a.x=a_xyz.x;
        this.a.y=a_xyz.y;
        this.a.z=a_xyz.z;
        var _time = t - this.time;
        this.delta.x += this.v.x*_time + 0.5*this.a.x*this.a*x*_time;
        this.delta.y += this.v.y*_time + 0.5*this.a.y*this.a*y*_time;
        this.delta.z += this.v.z*_time + 0.5*this.a.z*this.a*z*_time;
        this.v.x += this.a.x*_time;
        this.v.y += this.a.y*_time;
        this.v.z += this.a.z*_time;
        this.time=t;
    },
    getDelta:function(){
        x = this.delta.x;
        y = this.delta.y;
        z = this.delta.z;
        return {"x":x,"y":y,"z":z};
    }
};