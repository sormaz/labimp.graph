package edu.ohiou.mfgresearch.labimp.graph;

import java.io.InputStream;

public interface GraphReader {
	
	public void read (Graph g);
	
	public void setInputStream(InputStream is);

}
