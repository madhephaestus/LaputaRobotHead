
import eu.mihosoft.vrl.v3d.svg.*;
import eu.mihosoft.vrl.v3d.Extrude;
import  eu.mihosoft.vrl.v3d.ext.quickhull3d.*

File f = ScriptingEngine
	.fileFromGit(
		"https://github.com/madhephaestus/LaputaRobotHead.git",//git repo URL
		"master",//branch
		"face.svg"// File from within the Git repo
	)
println "Extruding SVG "+f.getAbsolutePath()
SVGLoad s = new SVGLoad(f.toURI())
ArrayList<CSG>foil = s.extrude(1,0.01)

CSG slice = foil.remove(0)
			.rotx(-90)
double centering = -slice.getCenterX()
slice=slice.movex(centering)
			
		
foil = s.extrude(-centering*1.1,0.01)
foil.remove(0)
CSG face =foil.remove(0)
CSG holes = CSG.unionAll(foil)

face = face.difference(holes)
		.movex(centering)
		.rotx(-90)
def head = HullUtil.hull(Extrude.revolve(slice,0,30))
 head =head.union(face)
.toZMin()



return [head]