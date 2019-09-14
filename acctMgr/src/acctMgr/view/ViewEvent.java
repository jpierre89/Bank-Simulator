package acctMgr.view;

import java.math.BigDecimal;

/**
 * passed from AccountView to AccountViewController
 * @author Jon Pierre
 */
public class ViewEvent {
	private EventKind kind;
	private BigDecimal amount;
	
	/**
	 * enumerates event type
	 */
	public enum EventKind {
		DepositEvent, WithdrawEvent, Dismiss
	}	
	
	public ViewEvent(EventKind kind, BigDecimal amount){
		this.amount = amount;
		this.kind = kind;
	}
	
	public EventKind getKind(){return kind;}	
	public BigDecimal getAmount(){return amount;}
}
