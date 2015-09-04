function PhoneTrailControler(data,pathToObj,pathToTexture,scene,onLoad){
    this.pathToTexture=pathToTexture;
    this.pathToObj=pathToObj;

    this.data=data;
    this.objects=[];
    this.speed={};
    this.speed.x=0;
    this.speed.y=0;
    this.speed.z=0;
    this.position={};
    this.position.x=0;
    this.position.y=0;
    this.position.z=0;

    this.rotation={};
    this.rotation.x=0;
    this.rotation.y=0;
    this.rotation.z=0;

    var manager = new THREE.LoadingManager();
    this.Obj_phone_loader = new THREE.OBJLoader( manager );
    var tex_phone_loader = new THREE.ImageLoader( manager );

    var texture=new THREE.Texture();
    tex_phone_loader.load( pathToTexture, function ( image ) {
        texture.image = image;
        texture.needsUpdate = true;
    } );
    this.objects=[];
    var daClass= this;
    this.scene=scene;
    this.loadObj=function ( object ) {


        if (object!=null) {
            object.traverse(function (child) {

                if (child instanceof THREE.Mesh) {
                    tex = texture.clone();
                    for (i=0;i<data.length;i++) {
                        t = new THREE.Mesh(child.geometry, new THREE.MeshLambertMaterial({
                            color: 0xffffffff, map:texture
                        }));

                        t.material.transparent =true;
                        t.material.opacity=0.2;
                        t.material.needsUpdate=true;
                        if (i<daClass.data.length-1){
                            t.position.x = daClass.position.x + daClass.speed.x*(daClass.data[i+1].time - daClass.data[i].time) + 0.5*daClass.data[i].accelerometer.x*(daClass.data[i+1].time - daClass.data[i].time)*(daClass.data[i+1].time - daClass.data[i].time);
                            t.position.y = daClass.position.y + daClass.speed.y*(daClass.data[i+1].time - daClass.data[i].time) + 0.5*daClass.data[i].accelerometer.y*(daClass.data[i+1].time - daClass.data[i].time)*(daClass.data[i+1].time - daClass.data[i].time);
                            t.position.z = daClass.position.z + daClass.speed.z*(daClass.data[i+1].time - daClass.data[i].time) + 0.5*daClass.data[i].accelerometer.z*(daClass.data[i+1].time - daClass.data[i].time)*(daClass.data[i+1].time - daClass.data[i].time);

                            daClass.speed.x  = daClass.speed.x + daClass.data[i].accelerometer.x*(daClass.data[i+1].time - daClass.data[i].time);
                            daClass.speed.y  = daClass.speed.y + daClass.data[i].accelerometer.y*(daClass.data[i+1].time - daClass.data[i].time);
                            daClass.speed.z  = daClass.speed.z + daClass.data[i].accelerometer.z*(daClass.data[i+1].time - daClass.data[i].time);

                            daClass.rotation.x = daClass.data[i].gyroscope.x;
                            daClass.rotation.y = daClass.data[i].gyroscope.y;
                            daClass.rotation.z = daClass.data[i].gyroscope.z;
                            daClass.position.x = t.position.x;
                            daClass.position.y = t.position.y;
                            daClass.position.z = t.position.z;


                            t.rotation.x =  daClass.rotation.x;
                            t.rotation.y =  daClass.rotation.y;
                            t.rotation.z =  daClass.rotation.z;
                        }
                       /* if (i<daClass.data.length-1) {
                            daClass.speed.x += daClass.data[i].accelerometer.x*(daClass.data[i+1].time - daClass.data[i].time);
                            daClass.speed.y += daClass.data[i].accelerometer.y*(daClass.data[i+1].time - daClass.data[i].time);
                            daClass.speed.z += daClass.data[i].accelerometer.z*(daClass.data[i+1].time - daClass.data[i].time);
                        }
                        if (i==0){
                            t.position.x = daClass.speed.x;
                            t.position.y = daClass.speed.y;
                            t.position.z = daClass.speed.z;
                        }
                        else {
                            t.position.x += daClass.objects[i-1].getObject().position.x + daClass.speed.x;
                            t.position.y += daClass.objects[i-1].getObject().position.y + daClass.speed.y;
                            t.position.z += daClass.objects[i-1].getObject().position.z + daClass.speed.z;
                        }
                       /* t.rotation.x = daClass.data[i].rotation.x;
                        t.rotation.y = daClass.data[i].rotation.y;
                        t.rotation.z = daClass.data[i].rotation.z;*/

                        daClass.objects.push(new PhoneTrail(t));
                        daClass.scene.add(daClass.objects[i].getObject());
                        if ((!(undefined==onLoad)) && (!(null==onLoad))){
                            onLoad();
                        }
                    }
                    tmpTex=null;
                    daClass.initiated=true;
                    daClass.positions=null;
                    daClass.rotations=null;
                }
            });

        }

    };
    this.Obj_phone_loader.load( pathToObj,  this.loadObj,function(aaa){},function(err){console.log(err);});
};
