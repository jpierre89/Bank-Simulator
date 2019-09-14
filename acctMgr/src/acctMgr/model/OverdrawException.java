package acctMgr.model;

/**
 * Exception for attempting to overdraw an Account
 * @author Jon Pierre
 * version 1.0
 */
public class OverdrawException extends IllegalArgumentException {
	private static final long serialVersionUID = -2350941896624913203L;
	String msg = "Cannot withdraw more than current account balance.";
	
	OverdrawException () {
		super();
	}
	
	/**
	 * @return String user message
	 */
	public String toString() {
		return msg;
	}

}