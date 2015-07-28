/**
 * Created by nexus on 7/23/2015.
 */
function Phone (TimeSeries,scene,phoneGeometry,PhoneTexture, phoneTrailObj,onLoad){
    var manager = new THREE.LoadingManager();
    this.Obj_phone_loader = new THREE.OBJLoader( manager );
    this.object = null;
    var tex_phone_loader = new THREE.ImageLoader( manager );
    var daClass = this;
    var texture=new THREE.Texture();
    this.TimeSeries = TimeSeries;
    this.lastTimeIndex=0;
    this.lastTime=0;
    this.currTime=0;
    this.initiated=false;
    this.speed={};
    this.speed.x=0;
    this.speed.y=0;
    this.speed.z=0;
    this.__time=0;
    this.j=0;
    this.phoneTrailController = phoneTrailObj;

    tex_phone_loader.load( PhoneTexture, function ( image ) {
        texture.image = image;
        texture.needsUpdate = true;
    } );

    this.loadObj=function ( object ) {


        if (object != null) {
            object.traverse(function (child) {

                if (child instanceof THREE.Mesh) {
                    child.material.map = texture;
                    child.position.x=0;
                    child.position.z=0;
                    child.position.y=0;
                    daClass.object = child;
                    scene.add(daClass.object);
                }
            });
        }
        onLoad();
    };
            this.Obj_phone_loader.load(phoneGeometry, this.loadObj, function (aaa) {
            }, function (err) {
                console.log(err);
            });

    }
Phone.prototype={
    play:function(){
        if (this.initiated==false){
            this.lastTime=new Date().getTime();
            this.currTime=this.lastTime;
            this.initiated=true;
        }
        _clock.play();
        for (i=this.lastTimeIndex; i< this.TimeSeries.length;i++){
            if (this.TimeSeries[i].time>=this.__time){
                if (i==0) break;
                i--;
                break;
            }
        };

            if (i>=this.TimeSeries.length-1){
                this.object.position.x = this.phoneTrailController.objects[this.phoneTrailController.objects.length-2].getObject().position.x;
                this.object.position.y = this.phoneTrailController.objects[this.phoneTrailController.objects.length-2].getObject().position.y;
                this.object.position.z = this.phoneTrailController.objects[this.phoneTrailController.objects.length-2].getObject().position.z;
                return true;
            }
        this.currTime=new Date().getTime();
        portionOfDuration = this.currTime - this.lastTime;
        this.object.rotation.x = this.TimeSeries[i].gyroscope.x;
        this.object.rotation.y = this.TimeSeries[i].gyroscope.y;
        this.object.rotation.z = this.TimeSeries[i].gyroscope.z;
        this.object.position.x +=  this.speed.x*portionOfDuration + 0.5*this.TimeSeries[i].accelerometer.x*portionOfDuration*portionOfDuration;
        this.object.position.y +=  this.speed.y*portionOfDuration + 0.5*this.TimeSeries[i].accelerometer.y*portionOfDuration*portionOfDuration;
        this.object.position.z +=  this.speed.z*portionOfDuration + 0.5*this.TimeSeries[i].accelerometer.z*portionOfDuration*portionOfDuration;
        this.speed.x  += this.TimeSeries[i].accelerometer.x*portionOfDuration;
        this.speed.y  += this.TimeSeries[i].accelerometer.y*portionOfDuration;
        this.speed.z  += this.TimeSeries[i].accelerometer.z*portionOfDuration;
        this.lastTime=this.currTime;
        this.__time+=portionOfDuration;
        if (i!=this.j){
            this.j=i;
          //  this.phoneTrailController.objects[i-1].updateColor(0, 1, 0);
           // this.phoneTrailController.objects[i-1].setPassedByTrail(1,myRGBColorFunc);
           // q = this.phoneTrailController.objects[i-1];
           // eventMannager.registerEvent(function(){ a = q.updatePassedByTrail(); console.log(q); return a;});
            //console.log(eventMannager.events);
        }

        return true;

    }

}
