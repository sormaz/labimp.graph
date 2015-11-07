/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

import cv97.j3d.loader.VRML97Loader;

import edu.ohiou.mfgresearch.labimp.basis.Viewable;
import edu.ohiou.mfgresearch.labimp.draw.AnimPanel;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.Drawable3D;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import edu.ohiou.mfgresearch.labimp.gtk3d.Ellipse;

/**
 * @author engineering
 *
 */
public class Vertex3D extends ImpObject
{
	public static final double RADIUS = 1.0;
	private Node node;
	private Point3d p3d;
	
	public Vertex3D (Node n, Point3d p3d, DrawWFPanel canvasWF) 
	{
		node = n;
		this.p3d = p3d;
		settCanvas(canvasWF);
	}
	
	public Vertex3D (Node n, Point3d p3d, AnimPanel canvasAnim) 
	{
		node = n;
		this.p3d = p3d;
		settCanvas(canvasAnim);
	}

	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}


	/**
	 * @param node the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}


	/**
	 * @return the p3d
	 */
	public Point3d getP3d() {
		return p3d;
	}


	/**
	 * @param p3d the p3d to set
	 */
	public void setP3d(Point3d p3d) {
		this.p3d.x = p3d.x;
		this.p3d.y = p3d.y;
		this.p3d.z = p3d.z;
	}
	
//	public void makeShapeSets (DrawWFPanel canvasWF)
//	{
//		canvasWF.addDrawShapes(Color.blue, getShapeList(canvasWF));
//	}
	
	public LinkedList getShapeList (DrawWFPanel canvasWF) {
//		canvasWF.getViewTransform().transform(p3d);
		LinkedList list = new LinkedList();
//		double diameter = RADIUS * 2;
//		list.add(new Ellipse2D.Double(p3d.x - RADIUS, p3d.y - RADIUS,
//										diameter ,diameter));
		list.addAll(new Ellipse(RADIUS, RADIUS, p3d, new Vector3d(0,1,0), 
				new Vector3d(0,0,1)).getShapeList(canvasWF));
		return list;
	}
	
	public BranchGroup createSceneGraph()
	{
////		System.out.println("V3D.getClass()= "+this.getClass());
//		System.out.println("V3D.getClassName()= "+this.getClassName());
//		System.out.println("V3D.getClass().getName()= "+this.getClass().getName());
////		System.out.println("Node.getClass()= "+node.getClass());
////		System.out.println(node.getClassName());
//		System.out.println("Node.getClass().getName()= "+node.getClass().getName());
//		System.out.println("----------");
		
		BranchGroup nodes = new BranchGroup();
		
		if(node.getUserObject() instanceof Drawable3D)
		{
			Drawable3D d3d = (Drawable3D) node.getUserObject();
			
			TransformGroup userObjectTransformGroup = new TransformGroup();
			userObjectTransformGroup.addChild(d3d.createSceneGraph());
			
			Transform3D userObjectTranslation = new Transform3D();
//			System.out.println(node.getUserObject().getClass().getName());
//			System.out.println(properties.getProperty(node.getUserObject().getClass().getName()+".Z_VALUE"));
			float z_value = Float.parseFloat(properties.getProperty(
					node.getUserObject().getClass().getName()+".Z_VALUE"));
			p3d.z = z_value;
			
//			System.out.println("this.getClass().getName() = "+node.getUserObject().getClass().getName());
//			System.out.println(node.getUserObject().getClass().getName().toString().substring(0, 29));
			
			if(node.getUserObject().getClass().getName().toString().substring(0, "edu.ohiou.implanner.processes".length()).equalsIgnoreCase("edu.ohiou.implanner.processes"))
			{
				p3d.x = 20* Math.random();
				p3d.y = 20* Math.random();
			}
			
			userObjectTranslation.set(new Vector3d(p3d.x, p3d.y, p3d.z));
			userObjectTransformGroup.setTransform(userObjectTranslation);
			nodes.addChild(userObjectTransformGroup);
		}
		else
		{
			String vrmlFileString = properties.getProperty(node.getClass().getName()+"."+node.getUserObject());
			if(vrmlFileString != null)
			{
				File vrmlFile = new File(vrmlFileString);
				VRML97Loader vrmlFileLoader = new VRML97Loader();
				if (vrmlFile != null) 
				{
					try
					{
						vrmlFileLoader.load(vrmlFile.getCanonicalPath());
						BranchGroup vrmlGroup;
						vrmlGroup = vrmlFileLoader.getBranchGroup();
						
						TransformGroup vrmlTransformGroup = new TransformGroup();
						vrmlTransformGroup.addChild(vrmlGroup);
						
						Transform3D vrmlTranslation = new Transform3D();
						vrmlTranslation.set(new Vector3d(p3d.x, p3d.y, p3d.z));
						vrmlTransformGroup.setTransform(vrmlTranslation);
						nodes.addChild(vrmlTransformGroup);
						
//						Shape3D various=(Shape3D)vrmlGroup.getChild(0);
//						for (Enumeration e = vrmlGroup.getAllChildren() ; e.hasMoreElements() ;) 
//						{
//							System.out.println(e.nextElement().getClass().getName());
////					        System.out.println(e.nextElement());
//					    }
//						System.out.println("---------------");
//						System.out.println(vrmlGroup.getChild(0).getClass().getName());
//						GeometryArray ga = (GeometryArray)various.getGeometry();
//						GeometryInfo geometryInfo = new GeometryInfo(ga);
//						Point3f[] coords = geometryInfo.getCoordinates();
					}
					catch (Exception ex) 
					{
						ex.printStackTrace();
					}
				}
			}
			else
			{
				System.err.println(node.getUserObject()+" node vrml file " +
						"does not exist");
				Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
				
				Shape3D s3d = new Shape3D();
				String shapeString = properties.getProperty(s3d.getClass()
						.getName()+"."+node.getUserObject());
				if(shapeString != null)
				{
					Class c;
					try 
					{
//						System.out.println("1");
						c = Class.forName(shapeString);
//						System.out.println("2");
						Primitive shape = (Primitive) c.newInstance();
						Appearance shapeApp = new Appearance();
						
						if (node.getUserObject().toString().toLowerCase().equals(node.getUserObject().toString())) 
						{
							String sphereColor2 = properties.getProperty(String.class.getName()+"."+node.getUserObject()+".sphereColor2", properties.getProperty(String.class.getName()+"."+"defaultSphereColor", ""));
							Color color2 = new Color(Integer.parseInt(sphereColor2, 16));
							shapeApp.setMaterial(new Material(new Color3f(color2), black,
									new Color3f(color2), black, 80.0f));
						}
						else
						{
							String sphereColor = properties.getProperty(String.class.getName()+"."+node.getUserObject()+".sphereColor", properties.getProperty(String.class.getName()+"."+"defaultSphereColor", ""));
							Color color = new Color(Integer.parseInt(sphereColor, 16));
							shapeApp.setMaterial(new Material(new Color3f(color), black,
									new Color3f(color), black, 80.0f));
						}
						shape.setAppearance(shapeApp);
						
						
						TransformGroup shapeTransformGroup = new TransformGroup();
						shapeTransformGroup.addChild(shape);
						
						Transform3D shapeTranslation = new Transform3D();
						shapeTranslation.set(new Vector3d(p3d.x, p3d.y, p3d.z));
						shapeTransformGroup.setTransform(shapeTranslation);
						nodes.addChild(shapeTransformGroup);
					}
					catch (Exception e) 
					{
						System.err.print("Class " + shapeString + " can not be loaded");
//						e.printStackTrace();
					}
				}
				else
				{
					System.err.println(node.getUserObject()+" node shape does not exist");
						
//					Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
						
					TransformGroup sphereGroup = new TransformGroup();
					Appearance sphereApp = new Appearance();
						
					if (node.getUserObject().toString().toLowerCase().equals(node.getUserObject().toString())) 
					{
						String sphereColor2 = properties.getProperty(String.class.getName()+"."+node.getUserObject()+".sphereColor2", properties.getProperty(String.class.getName()+"."+"defaultSphereColor", ""));
						Color color2 = new Color(Integer.parseInt(sphereColor2, 16));
						sphereApp.setMaterial(new Material(new Color3f(color2), black,
								new Color3f(color2), black, 80.0f));
					}
					else
					{
						String sphereColor = properties.getProperty(String.class.getName()+"."+node.getUserObject()+".sphereColor", properties.getProperty(String.class.getName()+"."+"defaultSphereColor", ""));
						Color color = new Color(Integer.parseInt(sphereColor, 16));
						sphereApp.setMaterial(new Material(new Color3f(color), black,
								new Color3f(color), black, 80.0f));
					}
					float sphereRadius = Float.parseFloat(properties.getProperty
					(String.class.getName()+"."+node.getUserObject()+
					".sphereRadius", properties.getProperty(
					String.class.getName()+"."+"defaultSphereRadius")));
						
					sphereGroup.addChild (new Sphere(sphereRadius, sphereApp));
						
					Transform3D sphereTranslation = new Transform3D();
					sphereTranslation.set(new Vector3d(p3d.x, p3d.y, p3d.z));
					sphereGroup.setTransform(sphereTranslation);
					nodes.addChild(sphereGroup);
				}
			}
		}
		
////		nodes.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
////		nodes.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
//		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
//		
//		TransformGroup sphereGroup = new TransformGroup();
//		Appearance sphereApp = new Appearance();
////		sphereApp.setColoringAttributes(new ColoringAttributes(new Color3f(Color.BLUE), 0));
////		sphereGroup.addChild (new Sphere((float) RADIUS, sphereApp));
//		
//		if (node.getUserObject().toString().toLowerCase().equals(node.getUserObject().toString())) 
//		{
//			String sphereColor2 = properties.getProperty(String.class.getName()+"."+node.getUserObject()+".sphereColor2", properties.getProperty(String.class.getName()+"."+"defaultSphereColor", ""));
//			Color color2 = new Color(Integer.parseInt(sphereColor2, 16));
//			sphereApp.setMaterial(new Material(new Color3f(color2), black,
//					new Color3f(color2), black, 80.0f));
//		}
//		else
//		{
//			String sphereColor = properties.getProperty(String.class.getName()+"."+node.getUserObject()+".sphereColor", properties.getProperty(String.class.getName()+"."+"defaultSphereColor", ""));
//			Color color = new Color(Integer.parseInt(sphereColor, 16));
//			sphereApp.setMaterial(new Material(new Color3f(color), black,
//					new Color3f(color), black, 80.0f));
//		}
//		float sphereRadius = Float.parseFloat(properties.getProperty(String.class.getName()+"."+node.getUserObject()+".sphereRadius", properties.getProperty(String.class.getName()+"."+"defaultSphereRadius")));
//		
////		sphereApp.setColoringAttributes(new ColoringAttributes(new Color3f(color), 0));
//		sphereGroup.addChild (new Sphere(sphereRadius, sphereApp));
//		
//		
//		Transform3D sphereTranslation = new Transform3D();
////		sphereTranslation.set(new Vector3d(1, 2, 3));
//		sphereTranslation.set(new Vector3d(p3d.x, p3d.y, p3d.z));
//		sphereGroup.setTransform(sphereTranslation);
//		nodes.addChild(sphereGroup);
//		
//		
////		BranchGroup bg = new BranchGroup();
////		Appearance app = new Appearance();
////		Color3f nodeColor = new Color3f(0.8f, 0.2f, 1.0f);
////		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
////		app.setMaterial(new Material(nodeColor, black, nodeColor, black, 80.0f));
////		Sphere sphere = new Sphere(2.0f, Primitive.GENERATE_NORMALS, app); 
////		bg.addChild(sphere);
////		bg.compile();
		
		return nodes;
	}
	
}
