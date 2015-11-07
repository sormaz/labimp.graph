/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineStripArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Sphere;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.gtk2d.InvalidLineException;
import edu.ohiou.mfgresearch.labimp.gtk3d.Line3d;
import edu.ohiou.mfgresearch.labimp.gtk3d.LineSegment;


/**
 * @author engineering
 *
 */
public class DirectedEdge3D extends Edge3D 
{
	public DirectedEdge3D(DirectedArc arc, GraphLayouter3D layouterWF3D) 
	{
		super(arc, layouterWF3D);
	}
	
	public LinkedList getShapeList (DrawWFPanel canvasWF) 
	{
		Node parent = arc.getParentNode();
		Node child = arc.getChildNode();
		
		Vertex3D pVertex = layouterWF3D.getVertex(parent);
		Vertex3D cVertex = layouterWF3D.getVertex(child);
		
		Point3d p3dParent = pVertex.getP3d();
		Point3d p3dChild = cVertex.getP3d();
		
//		canvasWF.getViewTransform().transform(p3dParent);
//		canvasWF.getViewTransform().transform(p3dChild);
		LinkedList list = new LinkedList();
//		list.add(new Line2D.Double(p3dParent.x, p3dParent.y,
//				p3dChild.x, p3dChild.y));
		list.addAll(new LineSegment(p3dParent, p3dChild).getShapeList(canvasWF));
		
		
//		try 
//		{	
//			Line3d l3d = new Line3d(p3dParent, p3dChild);
//			list.addAll(l3d.getShapeList(canvasWF));
//		} 
//		catch (InvalidLineException e) 
//		{
//			e.printStackTrace();
//		}
		return list;
	}
	
	public BranchGroup createSceneGraph()
	{
		Node parent = arc.getParentNode();
		Node child = arc.getChildNode();
		
		Vertex3D pVertex = layouterWF3D.getVertex(parent);
		Vertex3D cVertex = layouterWF3D.getVertex(child);
		
		Point3d p3dParent = pVertex.getP3d();
		Point3d p3dChild = cVertex.getP3d();
		
		BranchGroup edges = new BranchGroup();
//		edges.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
//		edges.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		
		
		int[] length = new int[1]; //There are 1 line
		length[0] = 2; //2 mean there are 2 spheres

		LineStripArray lsa = new LineStripArray (2, LineArray.COORDINATES, length);

		Point3d[] p3dArray = {p3dParent, p3dChild};
		
		lsa.setCoordinates(0, p3dArray);
		Appearance lineApp = new Appearance();
		
		Color edgeColor = null;
		
		if(parent.getUserObject().getClass().getName().toString().equalsIgnoreCase("edu.ohiou.implanner.parts.MfgPartModel"))
		{
			String stringEdgeColor = properties.getProperty(parent.getUserObject().getClass().getName()+".edgeColor."+String.class.getName(), properties.getProperty("defaultEdgeColor."+String.class.getName(), ""));
			edgeColor = new Color(Integer.parseInt(stringEdgeColor, 16));
		}
		else
		{
			if(parent.getUserObject().getClass().getName().toString().substring(0, "edu.ohiou.implanner.features".length()).equalsIgnoreCase("edu.ohiou.implanner.features"))
			{
				String stringEdgeColor = properties.getProperty("edu.ohiou.implanner.features.edgeColor."+String.class.getName(), properties.getProperty("defaultEdgeColor."+String.class.getName(), ""));
				edgeColor = new Color(Integer.parseInt(stringEdgeColor, 16));
			}
			else
			{
				if(parent.getUserObject().getClass().getName().toString().equalsIgnoreCase("edu.ohiou.implanner.activity.PartActivity"))
				{
					String stringEdgeColor = properties.getProperty(parent.getUserObject().getClass().getName()+".edgeColor."+String.class.getName(), properties.getProperty("defaultEdgeColor."+String.class.getName(), ""));
					edgeColor = new Color(Integer.parseInt(stringEdgeColor, 16));
				}
				else
				{
					if(parent.getUserObject().getClass().getName().toString().equalsIgnoreCase("edu.ohiou.implanner.activity.MachineActivity"))
					{
						String stringEdgeColor = properties.getProperty(parent.getUserObject().getClass().getName()+".edgeColor."+String.class.getName(), properties.getProperty("defaultEdgeColor."+String.class.getName(), ""));
						edgeColor = new Color(Integer.parseInt(stringEdgeColor, 16));
					}
					else
					{
						edgeColor = Color.BLUE;
					}
				}
			}
		}
		
		lineApp.setColoringAttributes(new ColoringAttributes(new Color3f(edgeColor), 0));
		
		TransformGroup lsaGroup = new TransformGroup();
		lsaGroup.addChild(new Shape3D(lsa, lineApp));
//		Transform3D lsaTranslation = new Transform3D();
//		lsaTranslation.set(new Vector3d(p3d.x, p3d.y, p3d.z));
//		lsaGroup.setTransform(lsaTranslation);
		edges.addChild(lsaGroup);
		return edges;
	}
	
}
