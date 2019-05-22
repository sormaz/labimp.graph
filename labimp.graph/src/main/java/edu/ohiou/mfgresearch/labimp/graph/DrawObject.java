package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JPanel;

import edu.ohiou.mfgresearch.labimp.basis.Draw2DPanel;
import edu.ohiou.mfgresearch.labimp.basis.DrawString;
import edu.ohiou.mfgresearch.labimp.basis.Drawable2D;
import edu.ohiou.mfgresearch.labimp.basis.GraphicsConfiguration;

public abstract class DrawObject implements Drawable2D {
	
	Point2D.Double position;
	protected Draw2DPanel canvas;
	
	
	public DrawObject (Point2D.Double point) {
		position = point;
	}
	public void addButtonOptions(int arg0) {
		// TODO Auto-generated method stub

	}
	
	public JPanel geettCanvas() {
		return canvas;
	}

	public GraphicsConfiguration geetGraphicsConfig() {
		// TODO Auto-generated method stub
		return new GraphicsConfiguration();
	}

	
	public Point2D geettPosition() {
		// TODO Auto-generated method stub
		return position;
	}
	
	public Collection giveSelectables() {
		
		ArrayList collectables = new ArrayList();
		collectables.add(this);

		return collectables;
	}

	
	public void makeDrawSets() {
		canvas.addDrawShapes(Color.red, geetDrawList());

	}

	
	public void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub

	}

	
	public void paintComponent(Graphics2D arg0) {
		// TODO Auto-generated method stub

	}

	
	public void removeButtonOptions(int arg0) {
		// TODO Auto-generated method stub

	}

	
	public void settCanvas(Draw2DPanel canvas) {
		this.canvas = canvas;

	}

	
	public void setNeedUpdate(boolean arg0) {
		// TODO Auto-generated method stub

	}

	
	public void settPosition(Point2D pos) {
		position.x = pos.getX();
		position.y = pos.getY();
	}

	
	public String toToolTipString() {
		// TODO Auto-generated method stub
		return "I need tool tip string";
	}

}
