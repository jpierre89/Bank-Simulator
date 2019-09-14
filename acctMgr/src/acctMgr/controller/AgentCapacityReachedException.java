package acctMgr.controller;

/**
 * Exception for too many Agent's existing
 * @author JonPierre
 * @version 1.0
 */
public class AgentCapacityReachedException extends Exception {
	private static final long serialVersionUID = 9080154709508002997L;

	public String toString() {
		return "Max agent limit reached";		
	}
}
