package edu.ohiou.mfgresearch.labimp.graph;




import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators.ParentIterator;

public class Graph {
	
//	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Arc> arcs =new ArrayList<Arc>();
	private HashMap<Object, Node> nodeMap = new HashMap <Object, Node>();
	private ArrayList<GraphListener> listeners = new ArrayList<GraphListener>();
	
	static HashMap<String, Integer> commands;
	static final int NODE = 1;
	static final int DIR_ARC = 2;
	static final int UNDIR_ARC = 3;
	static final int DELETE = 4;
	static final int PRINTOUT = 5;
	static final int GRAPH = 6;
	static final int NODES = 7;
	static final int ARCS = 8;
	static final int EXIT = 9;
	static final int QUIT = 10;


	static final int OK = 0;
	static String menu = new String ("Enter one of the following commands:" 
			+ "\n\tnode dir-arc undir-arc delete printout graph nodes arcs exit quit" +
"\nEnter your command with all needed parameters ->");

	
	
	static {
		commands = new HashMap<String, Integer>(); 
		commands.put("node", NODE);
		commands.put("dir-arc", DIR_ARC);
		commands.put("undir-arc", UNDIR_ARC);
		commands.put("delete", DELETE);
		commands.put("printout", PRINTOUT);
		commands.put("graph", GRAPH);
		commands.put("nodes", NODES);
		commands.put("arcs", ARCS);
		commands.put("exit", EXIT);
		commands.put("quit", QUIT);
	}
	
	public Graph () {
		
	}
	
	private Collection<Node> nodes () {
		return nodeMap.values();
	}
	
	public void  addObject  (Object user) throws AlreadyMemberException {
		if (hasObject(user)) {
			throw new AlreadyMemberException ("Object " + user + " is already in the graph");
		}
		else {
			addNode(new Node (user));
		}
	}
	
	public void addDirectedArc (Object p, Object c, double val) 
					throws AlreadyMemberException, NotMemberException {
		Node pNode = findNode(p);
		Node cNode = findNode(c);
		DirectedArc arc= pNode.addDirectedArc(cNode, val);
//		arc.setValue();
		arcAdded(arc);
	}
	
	public void addDirectedArc(Object parent, Object child) 
			throws AlreadyMemberException, NotMemberException {
		addDirectedArc( parent,  child, 0.0);
		
	}
	
	public void addUndirectedArc (Object p, Object c) 
	throws AlreadyMemberException, NotMemberException {
		Node pNode = findNode(p);
		Node cNode = findNode(c);
		arcAdded(pNode.addUndirectedArc(cNode));
	}
	
	public void addNode (Node n) {
		nodeMap.put(n.getUserObject(), n);
		nodeAdded(n);
	}
	
	public boolean hasObject (Object user) {
		return nodeMap.containsKey(user);
	}
	
	public Iterator<Node> getNodes () {
		return nodes().iterator();
	}
		
	public Stream<Node> getNodeStream(){
		return nodes().stream();
	}
	
	public Node findNode (Object user) throws NotMemberException {
		if (nodeMap.containsKey(user)) {
			return nodeMap.get(user);
		}
		else {
			throw new NotMemberException ("Node for " + user + " does not exist in the graph");
		}
	}
	
	public void printNodes () {
		
		System.out.println(nodeMap.values());
		
	}
	
	public void printout () {
		System.out.println("Graph has " + nodes().size() + " nodes and " + arcs.size() + " arcs.");
		for (Node n : nodes()) {
			System.out.println(n);
		}
	}
	
	public Set<Arc> findArcs () {
		Set<Arc> arcs = new HashSet<Arc>();
		for (Node n : nodes()) {
			arcs.addAll(n.getArcs());
			arcs.addAll(n.getParents());
			arcs.addAll(n.getChildren());
		}
		return arcs;
	}

	public String printArcs() {
		Set<Arc> s = findArcs();
		return s.toString();
			
		}

	public boolean deleteObject(Object user) {
		try {
			deleteNode(findNode(user));
		} catch (NotMemberException e) {
			return false;
		}
	
		return true;
	}
			
		
	
//	public void addArc (Arc a) {
//		
//	}
	
	class NodeCounter {
		public int countNodes () {
			return nodes().size();
		}
	}

	public void deleteNode(Node node) {
		arcsDeleted(node.getArcs());
		node.remove();
		nodeMap.remove(node.getUserObject());	
		nodeDeleted(node);
	}
	
	public void addListener (GraphListener gl) {
		listeners.add(gl);
	}
	
	public void removeListener (GraphListener gl) {
		listeners.remove(gl);
	}
	
	private void nodeAdded(Node n) {
		for (Iterator<GraphListener> itr = listeners.iterator(); itr.hasNext();) {
			itr.next().nodeAdded(n);
		}
	}
	
	private void nodeDeleted(Node n) {
		for (Iterator<GraphListener> itr = listeners.iterator(); itr.hasNext();) {
			itr.next().nodeDeleted(n);
		}
	}
	
	private void arcAdded (Arc a) {
		for (Iterator<GraphListener> itr = listeners.iterator(); itr.hasNext();) {
			itr.next().arcAdded(a);
		}		
	}
	
	private void arcDeleted (Arc a) {
		for (Iterator<GraphListener> itr = listeners.iterator(); itr.hasNext();) {
			itr.next().arcDeleted(a);
		}		
	}
	
	private void arcsDeleted (Collection<Arc> arcs) {
		for (Iterator<Arc> itr = arcs.iterator(); itr.hasNext();) {
			arcDeleted(itr.next());
		}		
	}
	
	public void read (String file) throws FileNotFoundException {
		read (new FileInputStream(file));
	}

	public void read (InputStream stream) {
		Scanner sc = new Scanner (stream);
		StringTokenizer tokenizer; 
//		String input;
		try 
		{
			while (true) 
			{
				System.err.flush();
//				System.out.print(menu);
				if (stream == System.in) {
					System.out.println(menu);
				}
				String input = sc.nextLine();
				System.out.println("input is: " + input);
				
				tokenizer = new StringTokenizer(input);
				String commandString;
				try {
					commandString = tokenizer.nextToken();
				} catch (Exception e1) {
					System.err.println ("No input specified");
					continue;
				}
				Integer commandObj = commands.get(commandString.toLowerCase());
				if (commandObj == null) {
					System.err.println("Your command '" 
								+ commandString + "' is not supported");
					continue;
				}
				int command = commandObj;
				switch (command) {
				case NODE: {
					// make node
					try {
						String nodeName = tokenizer.nextToken();
						this.addObject(nodeName);
					} catch (AlreadyMemberException ex) {
						System.err.println (ex.getMessage());
					} catch (NoSuchElementException e) {
						System.err.println ("No node parameter specified");
					}
					break;
				}
				case DIR_ARC: {
					// make arc
					try {
						String parent = tokenizer.nextToken();
						String child = tokenizer.nextToken();
						String valueString = tokenizer.nextToken();
						double val = Double.parseDouble(valueString);

							try {
								Node p = this.findNode(parent);
								Node c = this.findNode(child);
								this.addDirectedArc(parent, child, val);
//								p.addDirectedArc (this.findNode(child));
							} catch (GraphException e) {
								System.err.println
									(e.getMessage());
								continue;
							}
						
					} catch (NoSuchElementException e) {
						System.err.println 
						("Two node parameter objects should be specified: " + input);
					}
					break;
				}
				case UNDIR_ARC: {
					// make arc
					try {
						String user1 = tokenizer.nextToken();
						String user2 = tokenizer.nextToken();
						try {
							Node n = this.findNode(user1);
							Node n2 = this.findNode(user2);
							this.addUndirectedArc(user1, user2);
//							n.addUndirectedArc (this.findNode (user2));
						}
						catch (GraphException e) {
							System.err.println
								(e.getMessage());
							continue;
						}
					}
					catch (NoSuchElementException e) {
						System.err.println 
						("Two node parameter objects should be specified");
					}
					break;
				}
				case DELETE: {
					String arg1 = tokenizer.nextToken();
					if (tokenizer.hasMoreTokens()) {
						String arg2 = tokenizer.nextToken();
						try {
							deleteObjectArc(arg1, arg2);
						}
						catch (GraphException e) {
							System.err.println(e.getMessage());
						}
					}
					else {
						// delete node
						
						if (!this.deleteObject(arg1)) {
							System.err.println ("Your parameter is not in the graph");
						}
					}				
					break;
				}
				case PRINTOUT: {
					// printout chosen object
					break;
				}
				case GRAPH: {
					// printout the graph
					this.printout ();
					break;
				}
				case NODES: {			
					this.printNodes();
					break;
				}
				case ARCS: {
					System.out.println(this.printArcs());
					break;
				}
				case EXIT:
				case QUIT: {
					// exit the program
					System.exit(OK);
				}
					
			}
			} 
			// need to check for duplicate nodes
		}
	
		catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
//			System.out.println("I am here");
//			e.printStackTrace();
		}
	}
	


	public boolean deleteObjectArc(Object arg1, Object arg2) throws NotMemberException {
		Node n1 = findNode(arg1);
		Node n2 = findNode(arg2);
		Arc a = n1.removeArc(n2);
		if (a != null) {
			arcDeleted(a);
			return true;
		}
		else {
			throw new NotMemberException("An undirected arc between " + n1 + " and " + n2 + " does not exist in the graph");
		}

		
	}

	public void display()
	{
		GraphViewer v = new GraphViewer(this);
		v.display();
	}
	
	
	HashSet<Node> setS = new HashSet<Node>();
	SortedSet<Node> setSdash = new TreeSet<Node>(){
		public boolean contains(Object o) {
			ArrayList<Node> list = new ArrayList<Node> (this);
			return list.contains(o);
		}
			
	};
	
	public void dijkstra (Node source, Node sink, int direction) {
		if(direction ==0){
			
		}
	}
	
	
	public void dijkstra (Node source) {

		source.shortestPath=0;
		PriorityQueue<Node> nodeQueue = new PriorityQueue<Node>();
	    nodeQueue.add(source);
	    while (!nodeQueue.isEmpty()) {
	    	 System.out.println("node queue: " + nodeQueue);
		    Node parentNode = nodeQueue.poll();

		    for (Node childNode : parentNode.getNeighbors())
            {
		    	DirectedArc arc = parentNode.getDirectedArc(childNode);            
                childNode = arc.child;
                double weight = arc.value;
                double distanceViaChild = parentNode.shortestPath + weight;
                
                if (distanceViaChild < childNode.shortestPath) {
        //		    nodeQueue.remove(childNode);
        		    childNode.shortestPath = distanceViaChild ;
        		    childNode.previous = parentNode;
        		    nodeQueue.add(childNode);
        		    
        		}
            }
		    
	       }
	      }
                
         public static List<Node> getPathTo(Node targetNode)
                {
                    List<Node> pathTillNow = new ArrayList<Node>();
                    for (Node n1 = targetNode; n1 != null; n1 = n1.previous){
                        pathTillNow.add(n1);
                        }
                    
                    Collections.reverse(pathTillNow);
                    return pathTillNow;
                }
        public  void printoutShortestPath(){
        	
 //        dijkstra(nodes.get(0));
          for (Node nano : nodes())
		{
		    System.out.println("Distance traversed to reach " + nano + ": " + nano.shortestPath);
		    List<Node> pathTaken = getPathTo(nano);
		    System.out.println("Directed path traversed: " + pathTaken);
		}
        }
        
        public void dijkstraReverse (Node sink) {
    		sink.shortestPath=0;
    		PriorityQueue<Node> nodeQueue = new PriorityQueue<Node>();
    	    nodeQueue.add(sink);
    	    while (!nodeQueue.isEmpty()) {
    	    	 System.out.println("node queue: " + nodeQueue);
    		    Node childNode =  nodeQueue.poll();

    		    for (Node parentNode : childNode.getNeighbors1())
                {
    		    	DirectedArc arc = childNode.getDirectedArc1(parentNode);            
                    parentNode = arc.parent;
                    double weight = arc.value;
                    double distanceViaParent = childNode.shortestPath + weight;
                    
                    if (distanceViaParent < parentNode.shortestPath) {
            //		    nodeQueue.remove(childNode);
            		    parentNode.shortestPath = distanceViaParent ;
            		    parentNode.previous = childNode;
            		    nodeQueue.add(parentNode);
            		    
            		}
                }
    		    
    	       }
    	      }
        
//        private boolean haveCommonMember (PriorityQueue a, PriorityQueue b) {
//        	Set aSet = new HashSet (a);
//        	aSet.retainAll(b);
//        	return !aSet.isEmpty();
//        }
        
        private Set<Node> haveCommonMember (PriorityQueue a, PriorityQueue b) {
        	Set<Node> aSet = new HashSet (a);
        	aSet.retainAll(b);
        	if(!aSet.isEmpty()){
        		return aSet;
        	}
        	return null;
        }
        

		public void biDijkstra(Node source, Node sink) {

			source.shortestPath=0;
			sink.backwardShortestPath=0;
			PriorityQueue<Node> nodeQueue = new PriorityQueue<Node>();
		    nodeQueue.add(source);
		     PriorityQueue<Node> nodeQueue1 = new PriorityQueue<Node>();
    	    nodeQueue1.add(sink);
    	    
    //	    while (!haveCommonMember(nodeQueue, nodeQueue1)) {
    	    while (!isSearchDone(nodeQueue, nodeQueue1)) {
    	    	
       		    System.out.println("node queue: " + nodeQueue);
    		    System.out.println("node queue1: " + nodeQueue1);

    		    Node parentNode = nodeQueue.poll();
    		    System.out.println("p node is: " + parentNode);
    		    System.out.println("p neigbors: " + parentNode.getNeighbors());
    		    for (Node childNode : parentNode.getNeighbors())
                {
    		    	DirectedArc arc = parentNode.getDirectedArc(childNode);            
                    childNode = arc.child;
                    double weight = arc.value;
                    double distanceViaChild = parentNode.shortestPath + weight;
                    
                    if (distanceViaChild < childNode.shortestPath) {
            //		    nodeQueue.remove(childNode);
            		    childNode.shortestPath = distanceViaChild ;
            		    childNode.previous = parentNode;
            		    nodeQueue.add(childNode);
            		    
            		}
               
                }
    		    
       		    System.out.println("middle node queue: " + nodeQueue);
    		    System.out.println("middle node queue1: " + nodeQueue1);

    		    if (isSearchDone(nodeQueue, nodeQueue1)) {
    		    		break;
    		    }
    		    
    	        Node childNode =  nodeQueue1.poll();
    		    System.out.println("c node is: " + childNode);
    		    System.out.println("c neighbors: " + childNode.getNeighbors1());
    		    for (Node parentNode1 : childNode.getNeighbors1())
                {
    		    	DirectedArc arc = childNode.getDirectedArc1(parentNode1);            
                    parentNode1 = arc.parent;
                   
                    double weight = arc.value;
                    double distanceViaParent = childNode.backwardShortestPath + weight;
                    
                    if (distanceViaParent < parentNode1.backwardShortestPath) {
            //		    nodeQueue.remove(childNode);
            		    parentNode1.backwardShortestPath = distanceViaParent ;
            		    parentNode1.previous = childNode;
            		    nodeQueue1.add(parentNode1);
            		    
            		}
                }
    		    
    	    }
		}

		private boolean isSearchDone(PriorityQueue q1, PriorityQueue q2) {
			Set<Node> commonMembers = haveCommonMember(q1,q2);
			if (commonMembers == null) {
			return false;
			}
			else {
				
				for (Node n : commonMembers) {
					if (n.isPermanent())
						return true;
				}
				return false;
				
			}
		}
}
			
		
          
	        
                
//		setSdash.addAll(nodeMap.values());
//		
//		for (Node n : nodeMap.values()) {
//			System.out.println( n + "contains: " + setSdash.contains(n));
//			System.out.println( n + "equals: " + n.equals(n));
//		}
//
//		while ((currentNode = setSdash.first()) != null)
//        {
//			System.out.println("setS: " + setS);
//			System.out.println("setSdash: " + setSdash);
//			System.out.println("contains: " + setSdash.contains(currentNode));
//			if(currentNode==nodes.get(0)){
//				currentNode.permanentLabel =0;
//			}
//			
//		   if(currentNode==nodes.subList(1, nodes.size()))
//		   {
//			   currentNode.temporaryLabel=Double.POSITIVE_INFINITY;
//			   
//		   }
////            if ( currentNode.isPermanent()) {
////            	break;
////            }
//            
//            // if last node (destination)reached then stop
//            if (currentNode == setSdash.last())
//            {
//            	break;
//            }
//            else {
//            	 setSdash.remove(currentNode);
////    	         shortestPath.put(node, distance);//Update the shortest distance.
////    	         graph.setSdash.add(node);// adding the updated distance according to the new shortest distance found    
//
//            setS.add(currentNode);
//            currentNode.updateChildrenNodes();
//            System.out.println("permanent " + setSdash.first().permanentLabel);
//            
//			}
//        }
//		return setS;
		 
	
	
	
 

