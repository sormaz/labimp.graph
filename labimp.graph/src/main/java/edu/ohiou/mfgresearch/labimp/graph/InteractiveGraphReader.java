package edu.ohiou.mfgresearch.labimp.graph;

import java.io.InputStream;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class InteractiveGraphReader implements GraphReader {
	
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
	static final int COMMENT = 11;
	
	static final int OK = 0;
	

	
	InputStream stream;
	boolean makeNode = true;
	static String menu = new String ("Enter one of the following commands:" 
			+ "\n\tnode dir-arc undir-arc delete printout graph nodes arcs exit quit" +
"\nEnter your command with all needed parameters ->");

	public InteractiveGraphReader(InputStream s) {
		stream = s;
	}
	
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
		commands.put("a", DIR_ARC);
		commands.put("arc", DIR_ARC);
		commands.put("c",  COMMENT);
		commands.put("p",  COMMENT);
		commands.put("#",  COMMENT);
	}

	public void read (Graph g) {
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
						g.addObject(nodeName);
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
								Node p = g.findNode(parent, makeNode);
								Node c = g.findNode(child, makeNode);
								g.addDirectedArc(parent, child, val);
//								p.addDirectedArc (g.findNode(child));
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
							Node n = g.findNode(user1, makeNode);
							Node n2 = g.findNode(user2, makeNode);
							g.addUndirectedArc(user1, user2);
//							n.addUndirectedArc (g.findNode (user2));
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
							g.deleteObjectArc(arg1, arg2);
						}
						catch (GraphException e) {
							System.err.println(e.getMessage());
						}
					}
					else {
						// delete node
						
						if (!g.deleteObject(arg1)) {
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
					g.printout ();
					break;
				}
				case NODES: {			
					g.printNodes();
					break;
				}
				case ARCS: {
					System.out.println(g.printArcs());
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


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void setInputStream(InputStream is) {
		stream = is;
		
	}

}
