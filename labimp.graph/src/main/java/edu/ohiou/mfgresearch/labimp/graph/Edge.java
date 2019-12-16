package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
//import java.awt.geom.Point2D.Double;
import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.basis.DrawString;
import edu.ohiou.mfgresearch.labimp.basis.ViewObject;

public abstract class Edge extends DrawObject {
	
	Arc arc;
	GraphLayouter layouter;
	static private double EDGE_TEXT_SIZE;
	static private boolean SHOW_EDGE_LABEL;
	{
		String edgeString = ViewObject.getProperty( Edge.class, "EDGE_TEXT_SIZE");
		String showEdgeLabelText = ViewObject.getProperty( Edge.class, "SHOW_EDGE_LABEL");
//		if (edgeString == null || edfgestrgin.equals("");
		try {
			EDGE_TEXT_SIZE = Double.parseDouble(edgeString);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			EDGE_TEXT_SIZE = Double.NaN;
		}
		SHOW_EDGE_LABEL = Boolean.parseBoolean(showEdgeLabelText);
	}

	public Edge(Arc arc, GraphLayouter layouter) {
		super(new Point2D.Double());
		Object o = arc.getUserObject();
		color = Color.GREEN;
		if (o instanceof DrawObject) {
			setColor (((DrawObject) o ).getColor());
		}
		this.layouter = layouter;
		this.arc = arc;
		canvas = layouter.canvas;
		reposition();

	}



	
	public LinkedList<DrawString> geetStringList() {
		reposition();
		LinkedList<DrawString> strings = new LinkedList<DrawString>();
		if (SHOW_EDGE_LABEL) {
			strings.add(new DrawString(arc.toString(), (float) position.x, (float) position.y, EDGE_TEXT_SIZE));
		}
		return strings;
	}
	
	abstract public void reposition();


//	public static Edge newEdge(Arc a, GraphLayouter g) {
//		if (a instanceof DirectedArc)
//			return new DirectedEdge ((DirectedArc) a, g);
//		if (a instanceof UndirectedArc)
//			return new UndirectedEdge ((UndirectedArc) a, g);
//		throw new IllegalArgumentException("Edge can not be created for arc of class " + a.getClass().getName());
//	}

}
