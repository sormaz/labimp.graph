package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Shape;
import java.awt.geom.*;
import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.basis.DrawString;

public class Vertex extends DrawObject {
	
	public static final double RADIUS = 0.1;
	Node node;
	
	public Vertex (Node n, Point2D.Double p) {
		super (p);
		node = n;

	}
	
	public Vertex (Node n) {
		this (n, new Point2D.Double());
	}
	
	public LinkedList<Shape> getDrawList () {
		LinkedList<Shape>  shapes = new LinkedList<Shape>();
		double diameter = RADIUS * 2;
		shapes.add(new Ellipse2D.Double(position.x - RADIUS, position.y - RADIUS,
										diameter ,diameter));
		return shapes;
	}

	
	public void generateImageList() {
		// TODO Auto-generated method stub
		
	}

	
	public LinkedList<Shape> getFillList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public LinkedList<DrawString> getStringList() {
		LinkedList<DrawString> strings = new LinkedList<DrawString>();
		strings.add(new DrawString(node.toString(), (float) gettPosition().getX(), (float) gettPosition().getY()));
		return strings;
	}

}
