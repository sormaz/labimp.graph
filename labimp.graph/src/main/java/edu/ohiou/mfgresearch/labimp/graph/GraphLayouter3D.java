/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.media.j3d.BranchGroup;
import javax.vecmath.Point3d;

import cv97.j3d.loader.VRML97Loader;

import edu.ohiou.mfgresearch.labimp.basis.DrawString;
import edu.ohiou.mfgresearch.labimp.draw.AnimPanel;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.Drawable3D;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;

/**
 * @author engineering
 *
 */
public class GraphLayouter3D extends ImpObject implements Layouter, GraphListener
{
	private DrawWFPanel canvasWF;
	private AnimPanel canvasAnim;
	private BranchGroup bg = new BranchGroup();
	private Graph graph;
	Map<Node, Vertex3D> vertices = new HashMap<Node, Vertex3D>();
	Collection<Edge3D> edges = new ArrayList<Edge3D>();
	
	public GraphLayouter3D (Graph graph) 
	{
		this.graph = graph;
		graph.addListener(this);
	}
	
	
	/**
	 * @return the canvasWF
	 */
	public DrawWFPanel getCanvasWF() {
		return canvasWF;
	}

	/**
	 * @param canvasWF the canvasWF to set
	 */
	public void setCanvasWF(DrawWFPanel canvasWF) {
		this.canvasWF = canvasWF;
	}

	/**
	 * @return the canvasAnim
	 */
	public AnimPanel getCanvasAnim() {
		return canvasAnim;
	}

	/**
	 * @param canvasAnim the canvasAnim to set
	 */
	public void setCanvasAnim(AnimPanel canvasAnim) {
		this.canvasAnim = canvasAnim;
	}
	
	
	public void makeLayout() 
	{
		for (Vertex3D v : vertices.values()) 
		{
			v.setP3d(new Point3d(10*Math.random(), 10*Math.random(), 10*Math.random()));
		}  
	}
	
	public Vertex3D getVertex (Node n) 
	{
		return vertices.get(n);
	}
	
	public void makeShapeSets (DrawWFPanel canvasWF)
	{
		canvasWF.addDrawShapes(Color.blue, geetShapeList(canvasWF));
	}
	
	public LinkedList geetShapeList(DrawWFPanel canvasWF) 
	{
		LinkedList list = new LinkedList();
		for (Vertex3D v : vertices.values()) {
			list.addAll(v.geetShapeList(canvasWF));
		}
		for (Edge3D e : edges) 
		{
//			if(e.isDirectedEdge())
//			{
//				DirectedEdge3D de3d = (DirectedEdge3D) e;
//				list.addAll(de3d.getShapeList(canvasWF));
//			}
//			else
//			{
//				UndirectedEdge3D ude3d = (UndirectedEdge3D) e;
//				list.addAll(ude3d.getShapeList(canvasWF));
//			}
			if(e instanceof DirectedEdge3D)
				list.addAll(e.geetShapeList(canvasWF));
			else
			{
				e.makeShapeSets(canvasWF);
				list.addAll(e.geetShapeList(canvasWF));
			}
		}
		return list;
		
//		Point3d p1 = new Point3d(10, 20, 0);
//		canvas.getViewTransform().transform(p1);
//		Point3d p2 = new Point3d(50, 50, 0);
//		canvas.getViewTransform().transform(p2);
//		LinkedList list = new LinkedList();
//		list.add(new Rectangle2D.Double(p1.x, p1.y, p2.x, p2.y));
//		return list;
//		return null;
	}
	
//	public LinkedList getDrawList()
//	{
//		LinkedList list = new LinkedList();
//		list.add(new Rectangle2D.Double(2, 3, 4, 5));
//		return list;
////		return null;
//	}
	
//	public LinkedList getStringList() 
//	{
////		LinkedList list = new LinkedList();
////		list.add(new DrawString("corner", 2, 3));
////		return list;
//		return null;
//	}
	
	public BranchGroup createSceneGraph()
	{
//		BranchGroup bg = new BranchGroup();
		
//		System.out.println("no. of vertices= "+vertices.size());
//		for (Vertex3D v : vertices.values()) 
//		{
//			bg.addChild(v.createSceneGraph());
//		}
//		for (Edge3D e : edges) 
//		{
//			bg.addChild(e.createSceneGraph());
//		}
		
//		String vrmlFileString = properties.getProperty(this.getClass().getName()+".vrmlFile");
//		try 
//		{
//			File vrmlFile = new File(vrmlFileString);
//			VRML97Loader vrmlFileLoader = new VRML97Loader();
//			if (vrmlFile != null) 
//			{
//				try
//				{
//					vrmlFileLoader.load(vrmlFile.getCanonicalPath());
//					BranchGroup vrmlGroup;
//					vrmlGroup = vrmlFileLoader.getBranchGroup();
//					bg.addChild(vrmlGroup);
////					bg.compile();
//				}
//				catch (Exception ex) 
//				{
//					ex.printStackTrace();
//				}
//			}
//		} 
//		catch (NullPointerException e) 
//		{
//			System.err.println("vrml file does not exist");
//		}
		
		return bg;
	}
	
	
	public void nodeAdded(Node n) {
		vertices.put(n, new Vertex3D(n, new Point3d(10* Math.random(), 10* Math.random(), 10* Math.random()), canvasWF));
//		if (canvasWF != null) canvasWF.repaint();
		bg.addChild(vertices.get(n).createSceneGraph());
//		System.out.println("UserObjNode.getClass().getName()= "+n.getClass().getName());
//		if(vertices.get(n) instanceof Drawable3D)
//		{
//			System.out.println("Yes "+n.getUserObject()+" is D3D instance");
//		}
//		else
//		{
//			System.out.println("No "+n.getUserObject()+" is not D3D instance");
//		}
		
//		String vrmlFileString = properties.getProperty(n.getClass().getName()+"."+n.getUserObject());
//		try 
//		{
//			File vrmlFile = new File(vrmlFileString);
//			VRML97Loader vrmlFileLoader = new VRML97Loader();
//			if (vrmlFile != null) 
//			{
//				try
//				{
//					vrmlFileLoader.load(vrmlFile.getCanonicalPath());
//					BranchGroup vrmlGroup;
//					vrmlGroup = vrmlFileLoader.getBranchGroup();
//					bg.addChild(vrmlGroup);
//				}
//				catch (Exception ex) 
//				{
//					ex.printStackTrace();
//				}
//			}
//		} 
//		catch (NullPointerException e) 
//		{
//			System.err.println(n.getUserObject()+" node vrml file does not exist");
//		}
		
	}

	
	public void nodeDeleted(Node n) {
		// TODO Auto-generated method stub
		
	}

	
	public void arcAdded(Arc a) {
		Edge3D e;
		if(a instanceof DirectedArc)
		{
			e = new DirectedEdge3D((DirectedArc) a, this);
//			System.out.println("Directed arc added in 3D");
			edges.add(e);
		}
		else
		{
			e = new UndirectedEdge3D((UndirectedArc) a, this); 
//			System.out.println("Undirected arc added in 3D");
			edges.add(e);
		}
		bg.addChild(e.createSceneGraph());
//		if (canvasWF != null) canvasWF.repaint();
	}

	
	public void arcDeleted(Arc a) {
		// TODO Auto-generated method stub
		
	}
	
}
