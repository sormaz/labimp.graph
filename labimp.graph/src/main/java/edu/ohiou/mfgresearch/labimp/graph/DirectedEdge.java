package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class DirectedEdge extends Edge {

	public DirectedEdge(DirectedArc arc, GraphLayouter layouter) {
		super(arc, layouter);
	}
	
	public void reposition() {
		Point2D.Double pos= new Point2D.Double();
		Point2D parentPosition = layouter.getVertex(arc.getParentNode()).geettPosition();
		Point2D childPosition = layouter.getVertex(arc.getChildNode()).geettPosition();
		pos.x = (parentPosition.getX() + childPosition.getX())/2.;
		pos.y = (parentPosition.getY() + childPosition.getY())/2.;
		settPosition(pos);

	}
	
	
	public void generateImageList() {
		// TODO Auto-generated method stub
		
	}

	
	public LinkedList<Shape> geetDrawList() {
		LinkedList<Shape> shapes = new LinkedList<Shape>();
		Point2D parentPosition = layouter.getVertex(arc.getParentNode()).geettPosition();
		Point2D childPosition = layouter.getVertex(arc.getChildNode()).geettPosition();

		shapes.add(new Line2D.Double(parentPosition.getX(), parentPosition.getY(),
				childPosition.getX(), childPosition.getY()));
		return shapes;
	}

	
	public LinkedList<Shape> geetFillList() {
		// TODO Auto-generated method stub
		return new LinkedList<Shape>();
	}

}
