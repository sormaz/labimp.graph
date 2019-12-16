package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.*;
import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.basis.DrawString;
import edu.ohiou.mfgresearch.labimp.basis.ViewObject;

public class Vertex extends DrawObject {
	
	public static final double RADIUS = 0.1;
	Node node;
	
	static private double VERTEX_TEXT_SIZE;
	static private boolean SHOW_EDGE_LABEL;
	{
		String edgeString = ViewObject.getProperty( Vertex.class, "VERTEX_TEXT_SIZE");

//		if (edgeString == null || edfgestrgin.equals("");
		try {
			VERTEX_TEXT_SIZE = Double.parseDouble(edgeString);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			VERTEX_TEXT_SIZE = Double.NaN;
		}

	}
	
	public Vertex (Node n, Point2D.Double p) {
		super (p);
		node = n;
		color = Color.BLUE;
		Object o = n.getUserObject();
		if (o instanceof DrawObject) {
			setColor (((DrawObject) o ).getColor());
		}

	}
	
	public Vertex (Node n) {
		this (n, new Point2D.Double());
	}
	
	public LinkedList<Shape> geetDrawList () {
		LinkedList<Shape>  shapes = new LinkedList<Shape>();
		double diameter = RADIUS * 2;
		shapes.add(new Ellipse2D.Double(position.x - RADIUS, position.y - RADIUS,
										diameter ,diameter));
		return shapes;
	}

	
	public void generateImageList() {
		// TODO Auto-generated method stub
		
	}

	
	public LinkedList<Shape> geetFillList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public LinkedList<DrawString> geetStringList() {
		LinkedList<DrawString> strings = new LinkedList<DrawString>();
		strings.add(new DrawString(node.toString(), (float) geettPosition().getX(), (float) geettPosition().getY(), VERTEX_TEXT_SIZE));
		return strings;
	}
	
	public String toToolTipString () {
		Object u = node.getUserObject();
		if (u instanceof DrawObject) {
			return ((DrawObject) u).toToolTipString();
		}
		return u.toString();
	}

}
