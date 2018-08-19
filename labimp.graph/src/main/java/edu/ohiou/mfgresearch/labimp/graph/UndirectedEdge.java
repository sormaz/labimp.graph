/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

/**
 * @author sormaz
 *
 */
public class UndirectedEdge extends Edge {

	/**
	 * @param arc
	 * @param layouter
	 */
	public UndirectedEdge(UndirectedArc arc, GraphLayouter layouter) {
		super(arc, layouter);
		// TODO Auto-generated constructor stub
	}
	
	public void reposition() {
		Point2D.Double pos= new Point2D.Double();
		Point2D parentPosition = layouter.getVertex(arc.getNodes()[0]).geettPosition();
		Point2D childPosition = layouter.getVertex(arc.getNodes()[1]).geettPosition();
		pos.x = (parentPosition.getX() + childPosition.getX())/2.;
		pos.y = (parentPosition.getY() + childPosition.getY())/2.;
		settPosition(pos);
	}

	/* (non-Javadoc)
	 * @see edu.ohiou.labimp.basis.Drawable2D#generateImageList()
	 */
	
	public void generateImageList() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.ohiou.labimp.basis.Drawable2D#getDrawList()
	 */
	
	public LinkedList<Shape> geetDrawList() {
		LinkedList<Shape> shapes = new LinkedList<Shape>();
		Point2D parentPosition = layouter.getVertex(arc.getNodes()[0]).geettPosition();
		Point2D childPosition = layouter.getVertex(arc.getNodes()[1]).geettPosition();

		shapes.add(new Line2D.Double(parentPosition.getX(), parentPosition.getY(),
				childPosition.getX(), childPosition.getY()));
		return shapes;

	}

	/* (non-Javadoc)
	 * @see edu.ohiou.labimp.basis.Drawable2D#getFillList()
	 */
	
	public LinkedList<Shape> geetFillList() {
		// TODO Auto-generated method stub
		return null;
	}

}
