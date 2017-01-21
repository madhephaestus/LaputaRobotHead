
import eu.mihosoft.vrl.v3d.svg.*;
import eu.mihosoft.vrl.v3d.Extrude;

File f = ScriptingEngine
	.fileFromGit(
		"https://github.com/madhephaestus/LaputaRobotHead.git",//git repo URL
		"master",//branch
		"face.svg"// File from within the Git repo
	)
SVGLoad s = new SVGLoad(f.toURI())
ArrayList<CSG>foil = s.extrude(10,0.01)

CSG slice = foil.get(0)
			.rotx(90)
double offset = (slice.getMaxX()-slice.getMinX())/-2
slice = slice.movex(offset)			
		

CSG face =foil.get(1)
		.rotx(90)
		.movex(offset)	 
		//.movey(offset)	
		.scaley(20)
		.movey(offset*1.1)
def head = Extrude.revolve(slice,0,20)
head.add(face)

CSG allParts = head.get(0).union(head)
	.toZMin()

return allParts