package acctMgr.model;

import java.math.BigDecimal;

import acctMgr.model.ModelEvent.EventKind;

/**
 * Specialized Agent for use as thread to conduct deposit operations.
 * @author Jon Pierre
 * @version 1.0
 */
public class AgentDeposit extends Agent {
	protected long sleepDuration;
	
	public AgentDeposit(Account a, int id, BigDecimal amount, double frequency) {		
		super(a, id, amount, frequency);
		sleepDuration = (long) (1000 / frequency);
	}
	
	/**
	 * deposits money into its Account based on transferRate
	 */
	public void run() {	
		while (!killThread) {
			try {
				Thread.sleep(sleepDuration);
				
				while (paused) {
					synchronized (pauseLock) {
						pauseLock.wait();
					}
				}				
			} catch (InterruptedException e) {}

			agentAccount.autoDeposit(transferAmount);
			++transferCount;
			transferTotal = transferTotal.add(transferAmount);
			notifyChanged(new AgentEvent(
							EventKind.AmountTransferredUpdate, null, null,
							agentID, transferTotal, transferCount));
		}
	}	
}


