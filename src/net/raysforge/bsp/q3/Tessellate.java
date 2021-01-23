package net.raysforge.bsp.q3;

import java.util.List;

import net.raysforge.bsp.q3.model.Surface;
import net.raysforge.bsp.q3.model.Vertex;
import net.raysforge.map.Point;

public class Tessellate {
	
	/*
	 * This class turns Quake 3 curved surfaces into normal brushes.
	 */
	
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
	
	public static void tessellate( Surface face, List<Vertex> vertexes, List<Integer> indexes, int level) {
	    int off = face.firstVert;
	    //int count = face.n_vertexes;
	    
	    int L1 = level + 1;
	    
	    face.firstVert = vertexes.size();
	    face.firstIndex = indexes.size();
	    
	    face.numVerts = 0;
	    face.numIndexes = 0;
	    
	    for(int py = 0; py < face.patch_size[1]-2; py += 2) {
	        for(int px = 0; px < face.patch_size[0]-2; px += 2) {
	            
	            int rowOff = py*face.patch_size[0];
	            
	            // Store control points
	            Vertex c0 = vertexes.get(off+rowOff+px), c1 = vertexes.get(off+rowOff+px+1), c2 = vertexes.get(off+rowOff+px+2);
	            rowOff += face.patch_size[0];
	            Vertex c3 = vertexes.get(off+rowOff+px), c4 = vertexes.get(off+rowOff+px+1), c5 = vertexes.get(off+rowOff+px+2);
	            rowOff += face.patch_size[0];
	            Vertex c6 = vertexes.get(off+rowOff+px), c7 = vertexes.get(off+rowOff+px+1), c8 = vertexes.get(off+rowOff+px+2);
	            
	            int indexOff = face.numVerts;
	            face.numVerts += L1 * L1;
	            
	            // Tesselate!
	            for(int i = 0; i < L1; ++i) {
	                double a = i*1.0 / level;
	                
	                Point pos = getCurvePoint3(c0.xyz, c3.xyz, c6.xyz, a);
	                Point lmCoord = getCurvePoint2(c0.lightmap, c3.lightmap, c6.lightmap, a);
	                Point texCoord = getCurvePoint2(c0.st, c3.st, c6.st, a);
	                Point color = getCurvePoint3(c0.color, c3.color, c6.color, a);
	                
	                Vertex vert = new Vertex(pos, texCoord, lmCoord, new Point(0, 0, 1), color );
	                
	                vertexes.add(vert);
	            }
	            
	            for(int i = 1; i < L1; i++) {
	                double a = i*1.0 / level;
	                
	                Point pc0 = getCurvePoint3(c0.xyz, c1.xyz, c2.xyz, a);
	                Point pc1 = getCurvePoint3(c3.xyz, c4.xyz, c5.xyz, a);
	                Point pc2 = getCurvePoint3(c6.xyz, c7.xyz, c8.xyz, a);
	                
	                Point tc0 = getCurvePoint3(c0.st, c1.st, c2.st, a);
	                Point tc1 = getCurvePoint3(c3.st, c4.st, c5.st, a);
	                Point tc2 = getCurvePoint3(c6.st, c7.st, c8.st, a);
	                
	                Point lc0 = getCurvePoint3(c0.lightmap, c1.lightmap, c2.lightmap, a);
	                Point lc1 = getCurvePoint3(c3.lightmap, c4.lightmap, c5.lightmap, a);
	                Point lc2 = getCurvePoint3(c6.lightmap, c7.lightmap, c8.lightmap, a);
	                
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
	                
	                    vertexes.add(vert);
	                }
	            }
	            
	            face.numIndexes += level * level * 6;
	            
	            for(int row = 0; row < level; ++row) {
	                for(int col = 0; col < level; ++col) {
	                    indexes.add(indexOff + (row + 1) * L1 + col);
	                    indexes.add(indexOff + row * L1 + col);
	                    indexes.add(indexOff + row * L1 + (col+1));
	                    
	                    indexes.add(indexOff + (row + 1) * L1 + col);
	                    indexes.add(indexOff + row * L1 + (col+1));
	                    indexes.add(indexOff + (row + 1) * L1 + (col+1));
	                }
	            }
	    
	        }
	    }
	}


}
