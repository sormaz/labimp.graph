/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;

/**
 * @author engineering
 *
 */
public abstract class Edge3D extends ImpObject
{
	Arc arc;
	GraphLayouter3D layouterWF3D;

	public Edge3D(Arc arc, GraphLayouter3D layouterWF3D) 
	{
		this.arc = arc;
		this.layouterWF3D = layouterWF3D;
	}
	
	
//	public boolean isDirectedEdge()
//	{
//		if(arc instanceof DirectedArc) return true;
//		else return false;
//		
//	}
	
//	public LinkedList getShapeList (DrawWFPanel canvasWF)
//	{
//		LinkedList list = new LinkedList();
//		if (arc instanceof DirectedArc)
//		{
//			DirectedEdge3D de3d = (DirectedEdge3D) this;
//			list.addAll(de3d.getShapeList(canvasWF));
//		}
//		else
//		{
//			UndirectedEdge3D ude3d = (UndirectedEdge3D) this;
//			list.addAll(ude3d.getShapeList(canvasWF));
//		}
//		return list;
//		
//	}
	
//	public static Edge3D newEdge(Arc a, GraphLayouterWF3D g) {
//		if (a instanceof DirectedArc)
//			return new DirectedEdge3D ((DirectedArc) a, g);
//		if (a instanceof UndirectedArc)
//			return new UndirectedEdge3D ((UndirectedArc) a, g);
//		throw new IllegalArgumentException("Edge can not be created for arc of class " + a.getClass().getName());
//	}
}
