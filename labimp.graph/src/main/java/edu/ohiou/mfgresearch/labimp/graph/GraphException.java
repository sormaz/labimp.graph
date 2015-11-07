package edu.ohiou.mfgresearch.labimp.graph;

public class GraphException extends Exception {
	
	public GraphException() {

	}

	/**
	 * @param message
	 */
	public GraphException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public GraphException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public GraphException(String message, Throwable cause) {
		super(message, cause);

	}

}
