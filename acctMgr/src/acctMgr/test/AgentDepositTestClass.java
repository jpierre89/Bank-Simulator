package acctMgr.test;

import java.math.BigDecimal;

import acctMgr.model.Account;
import acctMgr.model.Agent;
import acctMgr.model.AgentEvent;
import acctMgr.model.ModelEvent.EventKind;

public class AgentDepositTestClass extends Agent {
	// ADDED BY TEST CLASS
	private int count = 0;
	
	public AgentDepositTestClass(Account a, int id, BigDecimal amount, double frequency) {
		super(a, id, amount, frequency);
	}
	
	public void run() {
		// ARGUMENT CHANGED BY TEST CLASS
		while (count < 1000) {
			try {
				// DURATION CHANGED BY TEST CLASS
				Thread.sleep((long) transferFrequency);
			} catch (InterruptedException e) {}
			
			while (paused) {
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {}
				}
			}
			
			agentAccount.autoDeposit(transferAmount);
			++transferCount;
			transferTotal = transferTotal.add(transferAmount);
			notifyChanged(new AgentEvent(
							EventKind.AmountTransferredUpdate, null, agentStatus,
							agentID, transferTotal, transferCount));
			
			// ADDED BY TEST CLASS
			++count;
		}
	}	
	
}
