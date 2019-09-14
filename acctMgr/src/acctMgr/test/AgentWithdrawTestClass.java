package acctMgr.test;

import java.math.BigDecimal;

import acctMgr.model.Account;
import acctMgr.model.Agent;
import acctMgr.model.AgentEvent;
import acctMgr.model.AgentStatus;
import acctMgr.model.ModelEvent.EventKind;


public class AgentWithdrawTestClass extends Agent {
	// ADDED BY TEST CLASS
	private int count = 0; 
	
	boolean withdrawSuccess;
	
	public AgentWithdrawTestClass(Account a, int id, BigDecimal amount, double frequency) {
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
				synchronized(this) {
					try {
						this.wait();
					}
					catch(InterruptedException e) {}
				}
			}
			
			while (agentStatus == AgentStatus.Blocked) {
				try {
					// thread requires lock for wait(), otherwise
					// will throw IllegalMonitorStateException
					synchronized(this) {
						wait();
					}
				} catch (InterruptedException e) {}			
			}
			
			// sets AgentStatus.Blocked if overdraw
			withdrawSuccess = agentAccount.autoWithdraw(transferAmount); 
			if (withdrawSuccess) {
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

}
