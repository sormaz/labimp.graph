package edu.ohiou.mfgresearch.labimp.graph;

public interface GraphListener {
	
	public void nodeAdded(Node n);
	public void nodeDeleted (Node n);
	public void arcAdded (Arc a);
	public void arcDeleted (Arc a);
	
//	public void graphCreated ();

}
