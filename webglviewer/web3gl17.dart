import 'dart:html' as HTML;
import 'dart:async';
import 'package:ChronosGL/chronos_gl.dart';
import 'dart:typed_data';

Future<Object> loadFile(String url)
{
  Completer c = new Completer();
  HTML.HttpRequest hr = new HTML.HttpRequest();
  hr.responseType = "arraybuffer";
  hr.open("GET", url);
  hr.onLoadEnd.listen( (e) {
    c.complete(hr.response);
  });
  hr.send();
  return c.future;
}


void main() {
  ChronosGL chronosGL = new ChronosGL('#webgl-canvas', false);
  Camera camera = chronosGL.getCamera();
  camera.setPos( 0.0, 0.0, 56.0 );
  FlyingCamera fc = new FlyingCamera(camera);
  chronosGL.addAnimatable('fc', fc);
  
  Utils utils = chronosGL.getUtils();
  TextureCache textureCache = chronosGL.getTextureCache();
  textureCache.add("textures/skybox_nx.png");
  textureCache.add("textures/skybox_px.png");
  textureCache.add("textures/skybox_ny.png");
  textureCache.add("textures/skybox_py.png");
  textureCache.add("textures/skybox_nz.png");
  textureCache.add("textures/skybox_pz.png");
  textureCache.loadAllThenExecute((){
    
    utils.addSkybox( "textures/skybox_", ".png", "nx", "px", "nz", "pz", "ny", "py");
    
    ShaderProgram sp = chronosGL.getShaderLib().createFixedVertexColorShaderProgram();
    
    var verts = loadFile( 'q3dm17.verts');
    var indices = loadFile( 'q3dm17.indices');
    
    Future.wait([verts,indices]).then( (List list) {
      Float32List vs = new Float32List.view( list.first);
      Uint16List xs = new Uint16List.view( list.last);
      
      for( var a =0; a<vs.length ;a++) {
        vs[a] = vs[a] / 100;
      }
      
      sp.add( new MeshData(vertices: vs, vertexIndices: xs).createMesh());
      chronosGL.run();
    });
  }); 
}
