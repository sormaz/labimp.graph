package edu.ohiou.mfgresearch.labimp.graph;

public class AlreadyMemberException extends GraphException {
	/**
	 * 
	 */
	public AlreadyMemberException() {

	}

	/**
	 * @param message
	 */
	public AlreadyMemberException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public AlreadyMemberException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public AlreadyMemberException(String message, Throwable cause) {
		super(message, cause);

	}
}
