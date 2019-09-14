package acctMgr.model;

/**
 * Exception for Account not having a valid account name
 * @author Jon Pierre
 *
 */
public class IllegalAccountNameException extends IllegalArgumentException {
	private static final long serialVersionUID = 8925805179614703427L;
	String msg = "Account name does not have valid format.";
	
	/**
	 * @return string user message
	 */
	public String toString() {
		return msg;
	}
}