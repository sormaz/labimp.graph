package edu.ohiou.mfgresearch.labimp.graph;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.vecmath.Vector3d;

import edu.ohiou.mfgresearch.labimp.basis.Draw2DPanel;
import edu.ohiou.mfgresearch.labimp.draw.AnimPanel;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;

public class GraphViewer extends JFrame implements GraphListener 
{
	
	public static int VIEW_2D = 1;
	public static int VIEW_WF = 2;
	public static int VIEW_3D = 4;
	
	private int viewOptions = 0;
	
	// dns comment
	 Graph graph;
	 JPanel panel;
	 DefaultListModel<Node> nodeListModel;
	 DefaultListModel<Arc> arcListModel = new DefaultListModel<Arc>();
	 GraphLayouter layouter; 
	 GraphLayouter3D layouterWF3D;
	boolean showBorders= false;
	
	public GraphViewer (Graph g, int options) 
	{
		this (g, new GraphLayouter(g, new Point2D.Double()), options);
	}
	
	public GraphViewer (Graph g, GraphLayouter lay, int options) 
	{
		graph = g;	
		graph.addListener(this);
		nodeListModel = new DefaultListModel<Node>();
		setViewOptions(options);
		layouter = lay;
		layouter.makeLayout();
		if (hasOption(VIEW_3D | VIEW_WF)) {
			layouterWF3D = new GraphLayouter3D(graph);
			layouterWF3D.makeLayout();
		}
//		addNodes();
//		layouter.makeLayout();
//		layouterWF3D.makeLayout();
		
		init();
//		remove adding nodes, read them from file instead
//		addNodes();
	}
	
	public GraphViewer(Graph g) {
		this (g, VIEW_2D | VIEW_WF | VIEW_3D);
	}
	
//	private void addNodes () {
//		graph.addNode(new Node ("dns"));
//		graph.addNode(new Node ("BDS"));		
//		graph.addNode(new Node ("ODS"));	
//		graph.addNode(new Node ("SMS"));
//		try {
//			graph.addDirectedArc("dns", "ODS");
//			graph.addDirectedArc("SMS", "ODS");
//			graph.addDirectedArc("SMS", "BDS");
//			graph.addDirectedArc("dns", "BDS");
//		} catch (AlreadyMemberException e) {
//			e.printStackTrace();
//		} catch (NotMemberException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public String toString() {
	 return "DrawViewer contains " + graph;
	}
	

	public void init () {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		for (Iterator<Arc> itr = graph.findArcs().iterator(); itr.hasNext();) {
			arcListModel.addElement(itr.next());
		}
		panel = new DrawViewPanel();
		this.getContentPane().add(panel);
	}
	
	public void setViewOptions (int options) {
		viewOptions = options;
	}
	
	public boolean hasOption (int option) {
		return (viewOptions & option) == option;
	}
	
	public void display() {
		setTitle ("Graph display");
		setSize(600,400);
//		this.pack();
//		this.
		setVisible(true);
	}
	
	public static void main (String [] args) {
//		System.out.println(System.getProperty("user.home")); // C:\Users
//	//	\engineering.ENT-292-CART
//		System.out.println(System.getProperty("user.dir")); // C:
//	//	\ISE589_EclipseWorkspace\graph-project
		GraphViewer v = new GraphViewer(new Graph(), VIEW_2D );
		v.display();
	}
	
	class DrawViewPanel extends JPanel {
		JScrollPane fromListScrollPane;
		JSplitPane splitPane;
		Draw2DPanel canvas;
		DrawWFPanel canvasWF = new DrawWFPanel();
		AnimPanel canvasAnim; // = new AnimPanel();
		JList arcList;
		
		JList<Node> fromList = new JList<Node>(nodeListModel);
		JList<Node> toList = new JList<Node>(nodeListModel);
		
		public DrawViewPanel () {
			
			setLayout(new BorderLayout());
			splitPane= new JSplitPane();
			fromList.setBorder(BorderFactory.createTitledBorder("Select FROM node to Connect"));
			toList.setBorder(BorderFactory.createTitledBorder("Select TO node to Connect"));
			fromList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			toList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JPanel nodePanel = new JPanel();
			JPanel toNodePanel = new JPanel();
			JList arcList= new JList(arcListModel);

			nodePanel.setLayout(new BorderLayout());
			toNodePanel.setLayout(new BorderLayout());
			fromListScrollPane = new JScrollPane(fromList);
			
			nodePanel.add(fromListScrollPane, BorderLayout.CENTER );
			toNodePanel.add(new JScrollPane(toList), BorderLayout.CENTER );
			nodePanel.add(toNodePanel, BorderLayout.SOUTH );
			
			JPanel arcButtonPanel = new JPanel();
			arcButtonPanel.setLayout(new GridLayout(1,2));
			JPanel northArcButtonPanel = new JPanel();
			JPanel southArcButtonPanel = new JPanel();

			northArcButtonPanel.setLayout(new BorderLayout());
			southArcButtonPanel.setLayout(new BorderLayout());
			
			JButton addDirArcButton = new JButton("==>>");
			addDirArcButton.setToolTipText("Add Directed Arc from left node to right node");
			addDirArcButton.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					addDirectedArc();				
				}
			});
			JButton addUndirArcButton = new JButton("<==>");
			addUndirArcButton.setToolTipText("Add Undrected Arc between selected nodes");
			addUndirArcButton.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					addUndirectedArc();
				}
			});
			
			/** Adding Dijkstra button to the viewer**/
			JButton runDijkstraButton = new JButton("Dijkstra");
			runDijkstraButton.setToolTipText("Runs Dijsktra's algorithm on the given graph");
			runDijkstraButton.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					try {
						runDijkstra();
					} catch (NotMemberException e1) {
						JOptionPane.showMessageDialog(GraphViewer.this, e1.getMessage());
					}
					
				}

				private void runDijkstra() throws NotMemberException {

					graph.dijkstra(fromList.getSelectedValue(), toList.getSelectedValue(), Graph.DIRECT);
					graph.printoutShortestPath();
					
				}
			});			
			
			/** Adding DijkstraReverse button to the viewer**/
			JButton runDijkstraReverseButton = new JButton("DijkstraRev");
			runDijkstraReverseButton.setToolTipText("Runs Dijsktra's algorithm (in reverse order) on the given graph");
			runDijkstraReverseButton.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					try {
						runDijkstraReverse();
					} catch (NotMemberException e1) {
						JOptionPane.showMessageDialog(GraphViewer.this, e1.getMessage());
					}
					
				}

				private void runDijkstraReverse() throws NotMemberException {
					Node lastNode = null;
					graph.dijkstraReverse(graph.findNode("12a"));
					graph.printoutShortestPath();
					
				}
			});	
			
			/** Adding BiDijkstra button to the viewer**/
			JButton runBiDijkstraButton = new JButton("BiDijkstra");
			runBiDijkstraButton.setToolTipText("Runs Bidirectional Dijsktra's algorithm on the given graph");
			runBiDijkstraButton.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					try {
						runBiDijkstra();
					} catch (NotMemberException e1) {
						JOptionPane.showMessageDialog(GraphViewer.this, e1.getMessage());
					}
					
				}

				private void runBiDijkstra() throws NotMemberException {
					Node firstNode = null;
					Node lastNode = null;
					graph.biDijkstra(fromList.getSelectedValue(), toList.getSelectedValue());
					graph.printoutShortestPath();
					
				}
			});	
			northArcButtonPanel.add(addDirArcButton, BorderLayout.EAST);
			southArcButtonPanel.add(addUndirArcButton, BorderLayout.WEST);
			arcButtonPanel.add(northArcButtonPanel);
			arcButtonPanel.add(southArcButtonPanel);
			toNodePanel.add(arcButtonPanel, BorderLayout.NORTH );
			
			
			splitPane.setLeftComponent(nodePanel);
			
			// right component
//			rightPanel = new JPanel();
			
			
			graph.addListener(layouter);
			canvas = new Draw2DPanel(layouter, null);
			layouter.settCanvas(canvas);
			
			JTabbedPane tabbedPane = new JTabbedPane();
			splitPane.setRightComponent(tabbedPane);
			
			if (hasOption(VIEW_2D)) {
			tabbedPane.addTab("Graph2D", canvas);
			}

			if (hasOption(VIEW_WF)) {
				canvasWF = new DrawWFPanel(layouterWF3D, null);
				layouterWF3D.setCanvasWF(canvasWF);
			tabbedPane.addTab("WireFrame3D", canvasWF);
			}
			if (hasOption(VIEW_3D)) {
				canvasAnim = new AnimPanel(layouterWF3D, null, new Vector3d(10.0,
						10.0, 50.0), false);
				layouterWF3D.setCanvasAnim(canvasAnim);
			tabbedPane.addTab("Graph3D", canvasAnim);
			}
//			rightPanel.setLayout(new BorderLayout());
			arcList.setBorder(BorderFactory.createTitledBorder("Arcs in the graph"));
//			rightPanel.add(new JScrollPane (arcList), BorderLayout.CENTER);
			JToolBar  toolBar = new JToolBar();
			
			// toolbar buttons
			JButton openFileButton = new JButton ("File");
			JButton addNodeButton = new JButton("AN");
			JButton addArcButton = new JButton("Add Arc");
			JButton deleteNodeButton = new JButton("Delete Node");
			JButton deleteArcButton = new JButton("Delete Arc");
			JButton showBordersButton = new JButton("SB");
			
			openFileButton.setToolTipText("Open File");
			
			openFileButton.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					openFile();
					
				}
			});
			
			addNodeButton.setToolTipText("Add Node");
			addNodeButton.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					addNode();
					
				}
			});
			
			deleteNodeButton.setToolTipText("Delete Node");
			deleteNodeButton.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					deleteNode();
					
				}
			});
			
			deleteArcButton.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					deleteArc();
					
				}
			});
			showBordersButton.setToolTipText("ShowBorders");
			showBordersButton.addActionListener(new ActionListener() {


				public void actionPerformed(ActionEvent e) {
					showBorders = !showBorders;
					changeBorderDisplay();
					
				}
			
			});
			toolBar.add(openFileButton);
			toolBar.add(addNodeButton);
			toolBar.add(addArcButton);
			toolBar.add(deleteNodeButton);
			toolBar.add(deleteArcButton);
			toolBar.add(showBordersButton);
			toolBar.add(runDijkstraButton);
			toolBar.add(runDijkstraReverseButton);
			toolBar.add(runBiDijkstraButton);
					
			add (splitPane, BorderLayout.CENTER);
			add (toolBar, BorderLayout.SOUTH);
		}

		JFileChooser fc = new JFileChooser(".");
		protected void openFile() {
			    
			        int returnVal = fc.showOpenDialog(this);

			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			           try {
//			        	   graph.clear();
			        	   GraphReader r = new InteractiveGraphReader  (new FileInputStream(file));
						r.read(graph);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        } 
			        layouter.makeLayout();
			        if (hasOption(VIEW_3D) || hasOption (VIEW_WF)) {
			        	layouterWF3D.makeLayout();
			        	canvasWF.repaint();
			        }
			        canvas.repaint();
			        
//			        canvasAnim.addContentBranchGroupToNewUniverse(layouterWF3D.createSceneGraph());
			}
			

		protected void deleteArc() {

		}

		protected void deleteNode() {
			Node node = (Node) fromList.getSelectedValue();
			graph.deleteNode(node);
		
		}

		protected void addDirectedArc() {
			Node fromNode = (Node) fromList.getSelectedValue();
			Node toNode = (Node) toList.getSelectedValue();
			if (!fromNode.isChild(toNode)) {
			DirectedArc dArc = new DirectedArc(fromNode, toNode);
			arcListModel.addElement(dArc);
			layouter.arcAdded(dArc);
			}
			else {
				JOptionPane.showMessageDialog(null,
						"Arc from " + fromNode + " to " 
							+ toNode + " already exists!", "Warning", 
						JOptionPane.WARNING_MESSAGE);
			}
		}
		
		protected void addUndirectedArc() {
			Node firstNode = (Node) fromList.getSelectedValue();
			Node secNode = (Node) toList.getSelectedValue();
			if (!firstNode.isConnected(secNode)) {
			UndirectedArc udArc = new UndirectedArc(firstNode, secNode);
			arcListModel.addElement(udArc);
			layouter.arcAdded(udArc);
			}
			else {
				JOptionPane.showMessageDialog(null,
						"Arc from " + firstNode + " to " 
							+ secNode + " already exists!", "Warning", 
						JOptionPane.WARNING_MESSAGE);
			}
		}

		protected void addNode() {
			
			String s = (String)JOptionPane.showInputDialog(
			                    null,
			                    "Enter the node object:\n",
			                    "Add Node", JOptionPane.PLAIN_MESSAGE);

			//If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
			    try {
					graph.addObject(s);
					
				} catch (AlreadyMemberException e) {
					
					//custom title, custom icon
					JOptionPane.showMessageDialog(null,
					"Node for " + s + " already exists!", "Warning", JOptionPane.WARNING_MESSAGE);

				} 
			}
		}

		protected void changeBorderDisplay() {
			if (showBorders) {
				setBorder(BorderFactory.createTitledBorder("DrawViewPanel"));
				fromListScrollPane.setBorder(BorderFactory.createTitledBorder("ScrollPane from list"));
				splitPane.setBorder(BorderFactory.createTitledBorder("Split Pane"));

			}
			else {
				setBorder(null);
				fromListScrollPane.setBorder(null);
				splitPane.setBorder(null);

			}
//			repaint();
			
		}
		
	}

	
	public void nodeAdded(Node n) {
		nodeListModel.addElement(n);
		
	}


	
	public void nodeDeleted(Node n) {
		nodeListModel.removeElement(n);
		
	}


	
	public void arcAdded(Arc a) {
		arcListModel.addElement(a);
		
	}


	
	public void arcDeleted(Arc a) {
		arcListModel.removeElement(a);
		
	}
}


