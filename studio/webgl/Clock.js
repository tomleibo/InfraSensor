/**
 * Created by nexus on 7/23/2015.
 */
function clock(stopTimeMilliseconds,onLoad){
    this._prevTime = new Date().getTime();
    this.stopTimeMilliseconds=stopTimeMilliseconds;
    this._elapsedTime=0;
    this._pause=true;
    if ((!(undefined==onLoad)) && (!(null == onLoad))){
        onLoad();
    }
}
clock.prototype={
  update:function(){
      if (this.stopTimeMilliseconds<this._elapsedTime){
          return false;
      }
      if (this._pause==true){
          this._prevTime = new Date().getTime();
      }
      else{
          this._elapsedTime+=(new Date().getTime()) - this._prevTime;
          this._prevTime = new Date().getTime();
      }
      return true;
  },
    pause:function(){
        this._pause=true;
    },
    play:function(){
        this._pause=false;
    },
    restart:function(){
        this._elapsedTime=0;
    },
    getElapsedTime:function(){
        return this._elapsedTime;
    }
};