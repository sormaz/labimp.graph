/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineStripArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.gtk2d.InvalidLineException;
import edu.ohiou.mfgresearch.labimp.gtk3d.Line3d;
import edu.ohiou.mfgresearch.labimp.gtk3d.LineSegment;

/**
 * @author engineering
 *
 */
public class UndirectedEdge3D extends Edge3D 
{
	
	public UndirectedEdge3D(UndirectedArc arc, GraphLayouter3D layouterWF3D) 
	{
		super(arc, layouterWF3D);
	}
	
	public LinkedList getShapeList (DrawWFPanel canvasWF) 
	{
		Node[] nodes = arc.getNodes();
		Node node1 = nodes[0];
		Node node2 = nodes[1];
		
		Vertex3D pVertex = layouterWF3D.getVertex(node1);
		Vertex3D cVertex = layouterWF3D.getVertex(node2);
		
		Point3d p3dParent = pVertex.getP3d();
		Point3d p3dChild = cVertex.getP3d();
		
//		canvasWF.getViewTransform().transform(p3dParent);
//		canvasWF.getViewTransform().transform(p3dChild);
		LinkedList list = new LinkedList();
//		list.add(new Line2D.Double(p3dParent.x, p3dParent.y,
//				p3dChild.x, p3dChild.y));
		
		list.addAll(new LineSegment(p3dParent, p3dChild).geetShapeList(canvasWF));
		
//		try 
//		{
//			list.add(new Line3d(p3dParent, p3dChild));
//		} 
//		catch (InvalidLineException e) 
//		{
//			e.printStackTrace();
//		}
		return list;
	}
	
	public void makeShapeSets (DrawWFPanel canvasWF)
	{
		canvasWF.addDrawShapes(Color.red, getShapeList(canvasWF));
	}
	
	public BranchGroup createSceneGraph()
	{
		Node[] nodes = arc.getNodes();
		Node node1 = nodes[0];
		Node node2 = nodes[1];
		
		Vertex3D pVertex = layouterWF3D.getVertex(node1);
		Vertex3D cVertex = layouterWF3D.getVertex(node2);
		
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
		lineApp.setColoringAttributes(new ColoringAttributes(new Color3f(Color.RED), 0));
		
		TransformGroup lsaGroup = new TransformGroup();
		lsaGroup.addChild(new Shape3D(lsa, lineApp));
		edges.addChild(lsaGroup);
		return edges;
	}
	
}
