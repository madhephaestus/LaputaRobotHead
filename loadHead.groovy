
import eu.mihosoft.vrl.v3d.svg.*;

import com.neuronrobotics.bowlerstudio.scripting.ScriptingEngine

import eu.mihosoft.vrl.v3d.CSG
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


// seperate holes and outsides using layers to differentiate
def headPart = s.extrudeLayerToCSG(0.1,"head")
CSG slice = headPart
			.rotx(-90)
double centering = -slice.getCenterX()
slice=slice.movex(centering)
def head = HullUtil.hull(Extrude.revolve(slice,0,30))
		
def distanceFaceSticksOut = 10;
// layers can be extruded at different depths
def face = s.extrudeLayerToCSG(-centering+distanceFaceSticksOut,"eyeShield")

def holeParts = s.extrudeLayerToCSG(-centering+distanceFaceSticksOut,"holes")

def faceWithHoles = face
					.difference(holeParts)
					.movex(centering)
					.rotx(-90)

 def headTotal =head.union(faceWithHoles)
.toZMin()
headTotal.setName("LaputaGuardHead")

return [headTotal]