/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author sormaz
 *
 */
public class GraphApplication {
	
	static Graph graph;
	


	/**
	 * 
	 */
	public GraphApplication() {
		// TODO Auto-generated constructor stub
	}
	
	static public void read (InputStream str) {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputStream input = null;
		if (args.length == 0) {
			 input = System.in;
		}
		else {
			try {
				input = new FileInputStream (new File(args[0]));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		read(input);
		Graph graph = new Graph();
		graph.read(input);
	}

}
