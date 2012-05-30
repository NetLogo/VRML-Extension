/** An extension (with only very basic functionality)
 * for creating VRML files from NetLogo.  
 * The main application I have in mind is creating files
 * that can then be printed on a 3D printer.
 * 
 * The X3D/VRML Java library that I'm interfacing to has a lot
 * more capability than what I'm using.  So... this extension
 * could be fleshed out to do a lot more.
 *  
 */


/* 
;; Here's a netlogo procedure to export a network as VRML.
to export-vrml
  vrml:clear-scene
  ask turtles [
    let rgbcol color
    if (not is-list? rgbcol)
      [ set rgbcol extract-rgb color ]
    vrml:set-color (item 0 rgbcol) (item 1 rgbcol) (item 2 rgbcol)
    vrml:add-sphere xcor ycor zcor (size / 2)
  ]
  ask links [
    let rgbcol color
    if (not is-list? rgbcol)
      [ set rgbcol extract-rgb color ]
    vrml:set-color (item 0 rgbcol) (item 1 rgbcol) (item 2 rgbcol)
    vrml:add-cylinder [xcor] of end1 [ycor] of end1 [zcor] of end1
                      [xcor] of end2 [ycor] of end2 [zcor] of end2
                      0.6
  ]
  vrml:save-scene "network.wrl"
end
 */
package org.nlogo.extensions.vrml;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.PrimitiveManager;
import org.nlogo.api.Syntax;

import org.cybergarage.x3d.SceneGraph;
import org.cybergarage.x3d.node.AppearanceNode;
import org.cybergarage.x3d.node.CylinderNode;
import org.cybergarage.x3d.node.MaterialNode;
import org.cybergarage.x3d.node.ShapeNode;
import org.cybergarage.x3d.node.SphereNode;
import org.cybergarage.x3d.node.TransformNode;
import org.cybergarage.x3d.node.BoxNode;

public class VRMLExtension extends DefaultClassManager {
    @Override
	public void load( PrimitiveManager primitiveManager )
	{
		primitiveManager.addPrimitive( "clear-scene" , new ClearScene() ) ;
		primitiveManager.addPrimitive( "set-color" , new SetColor() ) ;
		primitiveManager.addPrimitive( "add-sphere" , new AddSphere() ) ;
		primitiveManager.addPrimitive( "add-box" , new AddBox() ) ;
		primitiveManager.addPrimitive( "add-cylinder" , new AddCylinder() ) ;
		primitiveManager.addPrimitive( "save-scene" , new SaveScene() ) ;
		primitiveManager.addPrimitive( "save-scene-x3d" , new SaveSceneX3D() ) ;
	}
	private static SceneGraph sceneGraph = new SceneGraph(); 
	
	// the default color of any created objects is light gray
	// the user may change the color used by using the "set-color" command. 
	private static float[] currentColor = { 0.7f, 0.7f, 0.7f };
    
	private static ShapeNode makeDefaultShapeNode()
	{
		ShapeNode shape = new ShapeNode() ;
		AppearanceNode app = new AppearanceNode();  
		MaterialNode mat = new MaterialNode();  
		mat.setDiffuseColor(currentColor);    
		app.addChildNode(mat);  
		shape.addChildNode(app); 
		return shape;
	}
	
	
    public static class ClearScene extends DefaultCommand
	{
		@Override
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax
				( new int[] { } ) ;
		}
		@Override
		public String getAgentClassString()
		{
			return "OTPL" ;
		}
		public void perform( Argument args[] , Context context )
			//throws ExtensionException , LogoException
		{
			sceneGraph.clear();
		}					      
    }
    public static class SetColor extends DefaultCommand
	{
		@Override
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax
				( new int[] {Syntax.NumberType(), Syntax.NumberType(), Syntax.NumberType() } ) ;
		}
		@Override
		public String getAgentClassString()
		{
			return "OTPL" ;
		}
		public void perform( Argument args[] , Context context )
				throws ExtensionException , LogoException
		{
			double r = args[ 0 ].getDoubleValue() ;
			double g = args[ 1 ].getDoubleValue() ;
			double b = args[ 2 ].getDoubleValue() ;
			currentColor[0] = (float) (r / 255.0);
			currentColor[1] = (float) (g / 255.0);
			currentColor[2] = (float) (b / 255.0);
		}
	}

    
    public static class AddSphere extends DefaultCommand
	{
		@Override
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax
				( new int[] {Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType()} ) ;
		}
		@Override
		public String getAgentClassString()
		{
			return "OTPL" ;
		}
		public void perform( Argument args[] , Context context )
				throws ExtensionException , LogoException
		{
			double x = args[ 0 ].getDoubleValue() ;
			double y = args[ 1 ].getDoubleValue() ;
			double z = args[ 2 ].getDoubleValue() ;
			double radius = args[ 3 ].getDoubleValue() ;

			TransformNode trans = new TransformNode() ;
			trans.setTranslation( (float) x , (float) y , (float) z ) ;
			ShapeNode shape = makeDefaultShapeNode();
			SphereNode sphere = new SphereNode() ;
			sphere.setRadius( (float) radius ) ;

			sceneGraph.addNode( trans ) ;
			trans.addChildNode( shape ) ;
			shape.addChildNode( sphere ) ;
		}
	}

    public static class AddBox extends DefaultCommand
	{
		@Override
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax
				( new int[] {Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType()} ) ;
		}
		@Override
		public String getAgentClassString()
		{
			return "OTPL" ;
		}
		public void perform( Argument args[] , Context context )
				throws ExtensionException , LogoException
		{
			double x = args[ 0 ].getDoubleValue() ;
			double y = args[ 1 ].getDoubleValue() ;
			double z = args[ 2 ].getDoubleValue() ;
			double xWidth = args[ 3 ].getDoubleValue() ;
			double yWidth = args[ 4 ].getDoubleValue() ;
			double zWidth = args[ 5 ].getDoubleValue() ;

			TransformNode trans = new TransformNode() ;
			trans.setTranslation( (float) x , (float) y , (float) z ) ;
			ShapeNode shape = makeDefaultShapeNode();
			BoxNode box = new BoxNode() ;
			box.setSize( (float) xWidth , (float) yWidth , (float) zWidth);

			sceneGraph.addNode( trans ) ;
			trans.addChildNode( shape ) ;
			shape.addChildNode( box ) ;
		}
	}

    private static double[] crossProduct(double a1, double a2, double a3, double b1, double b2, double b3)
    {
    	double[] result = new double[3];
    	result[0] = a2 * b3 - a3 * b2;
    	result[1] = a3 * b1 - a1 * b3;
    	result[2] = a1 * b2 - a2 * b1;
    	return result;
    }
    private static double dotProduct(double a1, double a2, double a3, double b1, double b2, double b3)
    {
    	return a1 * b1 + a2 * b2 + a3 * b3;
    }
    public static class AddCylinder extends DefaultCommand
	{
		@Override
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax
				( new int[] {Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType() , Syntax.NumberType() } ) ;
		}
		@Override
		public String getAgentClassString()
		{
			return "OTPL" ;
		}
		public void perform( Argument args[] , Context context )
				throws ExtensionException , LogoException
		{
			double x1 = args[ 0 ].getDoubleValue() ;
			double y1 = args[ 1 ].getDoubleValue() ;
			double z1 = args[ 2 ].getDoubleValue() ;
			double x2 = args[ 3 ].getDoubleValue() ;
			double y2= args[ 4 ].getDoubleValue() ;
			double z2 = args[ 5 ].getDoubleValue() ;
			double radius = args[ 6 ].getDoubleValue() ;

			
			double x = (x1 + x2) / 2;
			double y = (y1 + y2) / 2;
			double z = (z1 + z2) / 2;
			double dx = x2 - x1;
			double dy = y2 - y1;			
			double dz = z2 - z1;
			
			double rotAxis[] = crossProduct(dx, dy, dz, 0, 1, 0);
			double cHeight = StrictMath.sqrt( dx * dx + dy * dy + dz * dz );
			double rotAngle = - StrictMath.acos(dotProduct(dx,dy,dz,0,1,0) / cHeight);
			
			TransformNode trans = new TransformNode() ;
			trans.setTranslation( (float) x , (float) y , (float) z ) ;
			trans.setRotation( (float) rotAxis[0] , (float) rotAxis[1], (float) rotAxis[2] , (float) rotAngle );
			ShapeNode shape = makeDefaultShapeNode();
			CylinderNode cyl = new CylinderNode();
			cyl.setHeight( (float) cHeight );
			cyl.setRadius( (float) radius );

			sceneGraph.addNode( trans ) ;
			trans.addChildNode( shape ) ;
			shape.addChildNode( cyl ) ;
		}
	}    
    /**
     * Highly recommended that you save your VMRL files with a 
     * .WRL filename extension so that VRML viewers, etc, recognize it.
     */
    public static class SaveScene extends DefaultCommand
	{
		@Override
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax
				( new int[] { Syntax.StringType() } ) ;
		}
		@Override
		public String getAgentClassString()
		{
			return "OTPL" ;
		}
		public void perform( Argument args[] , Context context )
			throws ExtensionException , LogoException
		{
			String filename = args[0].getString();
			sceneGraph.saveVRML( filename );			
		}					      
    }

    /**
     * Highly recommendeded that you save your X3D files
     * with a .x3d or .xml file extension.
     */
    public static class SaveSceneX3D extends DefaultCommand
	{
		@Override
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax
				( new int[] { Syntax.StringType() } ) ;
		}
		@Override
		public String getAgentClassString()
		{
			return "OTPL" ;
		}
		public void perform( Argument args[] , Context context )
			throws ExtensionException , LogoException
		{
			String filename = args[0].getString();
			sceneGraph.saveXML( filename );			
		}					      
    }

    @Override
    public java.util.List<String> additionalJars() {
        return new java.util.ArrayList<String>() {{
            add("cx3djava100a.jar");
        }};
    }
}
