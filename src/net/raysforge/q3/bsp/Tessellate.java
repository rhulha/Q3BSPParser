package net.raysforge.q3.bsp;

import java.util.ArrayList;
import java.util.List;

import net.raysforge.q3.map.Point;

public class Tessellate {
	
	public static Point getCurvePoint3( Point c0, Point c1, Point c2, double dist) {
		double b = 1.0 - dist;
	    return c0.scale(b*b).add(c1.scale(2*b*dist)).add(c2.scale(dist*dist));
	}
	
	public static Point getCurvePoint2( Point c0, Point c1, Point c2, double dist) {
	    //double b = 1.0 - dist;
	    
	    Point c30 = new Point(c0.x, c0.y, 0);
	    Point c31 = new Point(c1.x, c1.y, 0);
	    Point c32 = new Point(c2.x, c2.y, 0);
	    
	    return getCurvePoint3(c30, c31, c32, dist); //c30.scale( b*b).add( c31.scale(2*b*dist) ).add(  c32.scale(dist*dist) );
	}
	
	public static void tessellate( Face face, Vertex[] verts, int[] meshVerts, int level) {
	    int off = face.vertex;
	    //int count = face.n_vertexes;
	    
	    int L1 = level + 1;
	    
	    face.vertex = verts.length;
	    face.meshvert = meshVerts.length;
	    
	    face.n_vertexes = 0;
	    face.n_meshverts = 0;
	    
	    List<Vertex> newVerts = new ArrayList<Vertex>();
	    List<Integer> newMeshVerts = new ArrayList<Integer>();
	    
	    
	    for(int py = 0; py < face.patch_size[1]-2; py += 2) {
	        for(int px = 0; px < face.patch_size[0]-2; px += 2) {
	            
	            int rowOff = py*face.patch_size[0];
	            
	            // Store control points
	            Vertex c0 = verts[off+rowOff+px], c1 = verts[off+rowOff+px+1], c2 = verts[off+rowOff+px+2];
	            rowOff += face.patch_size[0];
	            Vertex c3 = verts[off+rowOff+px], c4 = verts[off+rowOff+px+1], c5 = verts[off+rowOff+px+2];
	            rowOff += face.patch_size[0];
	            Vertex c6 = verts[off+rowOff+px], c7 = verts[off+rowOff+px+1], c8 = verts[off+rowOff+px+2];
	            
	            int indexOff = face.n_vertexes;
	            face.n_vertexes += L1 * L1;
	            
	            // Tesselate!
	            for(int i = 0; i < L1; ++i) {
	                double a = i*1.0 / level;
	                
	                Point pos = getCurvePoint3(c0.position, c3.position, c6.position, a);
	                Point lmCoord = getCurvePoint2(c0.lmCoord, c3.lmCoord, c6.lmCoord, a);
	                Point texCoord = getCurvePoint2(c0.texCoord, c3.texCoord, c6.texCoord, a);
	                Point color = getCurvePoint3(c0.color, c3.color, c6.color, a);
	                
	                Vertex vert = new Vertex(pos, texCoord, lmCoord, new Point(0, 0, 1), color );
	                
	                newVerts.add(vert);
	            }
	            
	            for(int i = 1; i < L1; i++) {
	                double a = i*1.0 / level;
	                
	                Point pc0 = getCurvePoint3(c0.position, c1.position, c2.position, a);
	                Point pc1 = getCurvePoint3(c3.position, c4.position, c5.position, a);
	                Point pc2 = getCurvePoint3(c6.position, c7.position, c8.position, a);
	                
	                Point tc0 = getCurvePoint3(c0.texCoord, c1.texCoord, c2.texCoord, a);
	                Point tc1 = getCurvePoint3(c3.texCoord, c4.texCoord, c5.texCoord, a);
	                Point tc2 = getCurvePoint3(c6.texCoord, c7.texCoord, c8.texCoord, a);
	                
	                Point lc0 = getCurvePoint3(c0.lmCoord, c1.lmCoord, c2.lmCoord, a);
	                Point lc1 = getCurvePoint3(c3.lmCoord, c4.lmCoord, c5.lmCoord, a);
	                Point lc2 = getCurvePoint3(c6.lmCoord, c7.lmCoord, c8.lmCoord, a);
	                
	                Point cc0 = getCurvePoint3(c0.color, c1.color, c2.color, a);
	                Point cc1 = getCurvePoint3(c3.color, c4.color, c5.color, a);
	                Point cc2 = getCurvePoint3(c6.color, c7.color, c8.color, a);
	                
	                for(int j = 0; j < L1; j++)
	                {
	                    double b = j*1.0 / level;
	                    
	                    Point pos = getCurvePoint3(pc0, pc1, pc2, b);
	                    Point texCoord = getCurvePoint2(tc0, tc1, tc2, b);
	                    Point lmCoord = getCurvePoint2(lc0, lc1, lc2, b);
	                    Point color = getCurvePoint3(cc0, cc1, cc2, a);
	                    
	                    Vertex vert = new Vertex( pos, texCoord, lmCoord, new Point(0, 0, 1), color);
	                
	                    newVerts.add(vert);
	                }
	            }
	            
	            face.n_meshverts += level * level * 6;
	            
	            for(int row = 0; row < level; ++row) {
	                for(int col = 0; col < level; ++col) {
	                    newMeshVerts.add(indexOff + (row + 1) * L1 + col);
	                    newMeshVerts.add(indexOff + row * L1 + col);
	                    newMeshVerts.add(indexOff + row * L1 + (col+1));
	                    
	                    newMeshVerts.add(indexOff + (row + 1) * L1 + col);
	                    newMeshVerts.add(indexOff + row * L1 + (col+1));
	                    newMeshVerts.add(indexOff + (row + 1) * L1 + (col+1));
	                }
	            }
	    
	        }
	    }
	}


}
