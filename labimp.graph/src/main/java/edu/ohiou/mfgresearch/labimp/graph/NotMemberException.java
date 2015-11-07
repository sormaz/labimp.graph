/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

/**
 * @author sormaz
 *
 */
public class NotMemberException extends GraphException {

	/**
	 * 
	 */
	public NotMemberException() {

	}

	/**
	 * @param message
	 */
	public NotMemberException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public NotMemberException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotMemberException(String message, Throwable cause) {
		super(message, cause);

	}

}
