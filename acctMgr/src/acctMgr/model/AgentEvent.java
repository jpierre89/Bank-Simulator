package acctMgr.model;

import java.math.BigDecimal;

/**
 * specialized ModelEvent
 * @author Jon Pierre
 * @version 1.0
 */
public class AgentEvent extends ModelEvent {
	/**
	 * the total operation count of agent
	 */
	public final int transferCount;
	/**
	 * the transfer total of agent
	 */
	public final BigDecimal transferTotal;

	public AgentEvent(
			EventKind kind, BigDecimal balance, AgentStatus agSt, int transferCount, BigDecimal transferTotal, int transferCount1) {
		super(kind, balance, agSt);
		this.transferCount = transferCount1;
		this.transferTotal = transferTotal;
	}
}
