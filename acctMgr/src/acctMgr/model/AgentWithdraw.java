package acctMgr.model;

import java.math.BigDecimal;

import acctMgr.model.ModelEvent.EventKind;

/**
 * Specialized Agent for use as thread to conduct withdraw operations.
 * @author Jon Pierre
 * @version 1.0
 */
public class AgentWithdraw extends Agent {
	protected long sleepDuration;
	
	public AgentWithdraw(Account a, int id, BigDecimal amount, double frequency) {
		super(a, id, amount, frequency);
		sleepDuration = (long) (1000 / frequency);
	}

	/**
	 * continously withdraws money from  Account
	 */
	public void run() {
		while (!killThread) {
			try {
				Thread.sleep(sleepDuration);
			} catch (InterruptedException e) {}
			
			while (paused) {
				synchronized(pauseLock) {
					try {
						pauseLock.wait();
					}
					catch(InterruptedException e) {}
				}
			}
			
			while (agentStatus == AgentStatus.Blocked) { 
				try {
					synchronized(this) { 
						this.wait();
					}
					
					if ( transferAmount.compareTo(agentAccount.getBalance()) != 1) {
						setStatus(AgentStatus.Running);
					}
					
				} catch (InterruptedException e) {}			
			}
			
			if (agentAccount.autoWithdraw(transferAmount)) {
				++transferCount;
				transferTotal = transferTotal.add(transferAmount);
				notifyChanged(new AgentEvent(
					EventKind.AmountTransferredUpdate, null, null,
					agentID, transferTotal, transferCount));
			}	
			else {
				setStatus(AgentStatus.Blocked);
			}
		}
	}

}
