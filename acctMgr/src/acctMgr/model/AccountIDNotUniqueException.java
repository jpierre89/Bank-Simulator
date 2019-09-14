package acctMgr.model;

/**
 * Exception for Account not having a unique ID
 * @author Jon Pierre
 *
 */
public class AccountIDNotUniqueException extends IllegalArgumentException {
	private static final long serialVersionUID = 2927413068479096161L;
	String msg = "Account ID is not unique.";
	
	/**
	 * @return string user message
	 */
	public String toString() {
		return msg;
	}
}
