package acctMgr.model;

/**
 * Exception for no Account existing with given Account ID
 * @author Jon Pierre
 * @version 1.0
 */
public class NoAccountFoundByIDException extends IllegalArgumentException {
	private static final long serialVersionUID = -5965474063745780116L;
	String msg = "No Account found by that account ID in AccountList.";
		
	/**
	 * @return String user message
	 */
	public String toString() {
		return msg;
	}
}
