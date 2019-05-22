package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.*;

import edu.ohiou.mfgresearch.labimp.basis.DrawString;

public class GraphLayouter extends DrawObject implements Layouter, GraphListener {
	protected Map<Node, Vertex> vertices = new HashMap<Node, Vertex>();
	
//	Collection<Edge> edges = new ArrayList<Edge>();
	Map<Arc, Edge> edgeMap = new HashMap();
	
	public GraphLayouter (Graph graph, Point2D.Double point) {
		super (point);
		graph.addListener(this);
		for (Iterator<Node> itr = graph.getNodes(); itr.hasNext();) {
			Node n = itr.next();
			Vertex v = new Vertex(n);
			vertices.put(n, v);
			System.out.println("Vertex Node = "+v.node.toString());
			
		}
	makeLayout();
		for (Arc a : graph.findArcs()) {
			edgeMap.put(a,new DirectedEdge((DirectedArc) a, this));
		}

//		for (Arc a : graph.findArcs()) {
//			edges.add(new Edge (a));
//		}
//		
	}
	
	public void makeLayout() {
		for (Vertex v : vertices.values()) {
			v.settPosition(new Point2D.Double (50 *Math.random(), 50* Math.random()));
		}  
	}
	
	public void repositionEdges() {
		for (Edge e : edgeMap.values()) {
			e.reposition();
		}
	}
	
	public Vertex getVertex (Node n) {
		return vertices.get(n);
	}
	
	// GraphListener interface
	
	public void nodeAdded(Node n) {
		vertices.put(n, new Vertex (n));
		if (canvas != null) canvas.repaint();
	}

	
	public void nodeDeleted(Node n) {
		vertices.remove(n);
		if (canvas != null) canvas.repaint();
	}

	
	public void arcAdded(Arc a) {
		if(a instanceof DirectedArc)
		{
//			System.out.println("Directed arc added");
			edgeMap.put(a, new DirectedEdge((DirectedArc) a, this));
		}
		else
		{
//			System.out.println("Undirected arc added");
			edgeMap.put(a, new UndirectedEdge((UndirectedArc) a, this));
		}
		if (canvas != null) canvas.repaint();
	}

	
	public void arcDeleted(Arc a) {
		edgeMap.remove(a);
		
	}
	
	
	// ExtendDrawObject - Drawable2D interface
	public LinkedList<Shape> geetDrawList() {
		LinkedList<Shape> shapes = new LinkedList<Shape>();
		for (Vertex v : vertices.values()) {
			shapes.addAll(v.geetDrawList());
		}
		for (Edge e : edgeMap.values()) {
			shapes.addAll(e.geetDrawList());
		}
		return shapes;
	}


	public LinkedList<Shape> geetFillList() {
	
		return new LinkedList<Shape>();
	}
	
	
	public LinkedList<DrawString> geetStringList() {
	
		LinkedList<DrawString> strings = new LinkedList<DrawString>();
		for (Vertex v : vertices.values()) {
			strings.addAll(v.geetStringList());
		}
		for (Edge e : edgeMap.values()) {
			strings.addAll(e.geetStringList());
		}
		return strings;
	}
	
	public Collection giveSelectables() {
		
		ArrayList collectables = new ArrayList();
		collectables.addAll(vertices.values());

		return collectables;
	}

	
	public void generateImageList() {
		// TODO Auto-generated method stub		
	}
	

	
}
