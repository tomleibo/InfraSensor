var phone = function (position,rotation){
    this.position=position;
    this.rotation=rotation;
    var manager = new THREE.LoadingManager();
    var texture = new THREE.Texture();
    this.Obj_phone=2;

    var tex_phone_loader = new THREE.ImageLoader( manager );

    tex_phone_loader.load( 'textures/phone/phone.png', function ( image ) {

        texture.image = image;
        texture.needsUpdate = true;

    } );
    var Obj_phone_loader = new THREE.OBJLoader( manager );
    this.loadObj = function ( object ) {
        this.Obj_phone;
        if ( this.Obj_phone==undefined && object!=null) {
            this.Obj_phone = object;
        }
        this.retObj=function(){return this.Obj_phone;}
        if (object!=null) {
            object.traverse(function (child) {

                if (child instanceof THREE.Mesh) {
                    child.material.map = texture;
                    console.log(this.Obj_phone_loader);
                    this.Obj_phone = child;
                }
            });
        }
        return this.Obj_phone;
    };
    Obj_phone_loader.load( 'obj/phone/phone.obj',  this.loadObj);
    console.log( this.loadObj(null));
    this.setRotation=function(rotationVector){
        this.phoneObj.rotation(rotationVector.x,rotationVector.y,rotationVector.z);
    }

    this.getObj=function(){
        return this.Obj_phone_loader;
    }

    this.addToScene=function(scene){
        this.Obj_phone_loader.addToScene(scene);
    }

}