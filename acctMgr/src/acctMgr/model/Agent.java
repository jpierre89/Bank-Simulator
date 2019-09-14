package acctMgr.model;

import java.math.BigDecimal;

import acctMgr.model.ModelEvent.EventKind;

/**
 * abstract agent for conducting operations on Account.
 * The Agent is a ModelListener for the Account in which it
 * conducts operations. 
 * @author Jon Pierre
 * @version 1.0
 */
public abstract class Agent extends AbstractModel implements ModelListener, Runnable {
	protected final Account agentAccount;
	protected final int agentID;
	protected final BigDecimal transferAmount;
	protected final double transferFrequency;
	
	/**
	 * lock for pausing
	 */
	protected Object pauseLock = new Object();
	
	/**
	 * boolean set true when thread is paused
	 */
	protected volatile boolean paused = false;
	
	/**
	 * the total number of operations performed by Agent
	 */
	protected volatile int transferCount;
	
	/**
	 * the total amount transferred by Agent
	 */
	protected volatile BigDecimal transferTotal;
	
	/**
	 * the Agent status
	 */
	protected volatile AgentStatus agentStatus;
	
	/**
	 * set to true to kill Agent thread
	 */
	protected volatile boolean killThread = false;
	
	public Agent(Account account, int id, BigDecimal transferAmount, double frequency) {
		this.agentAccount = account;
		this.agentID = id;
		this.transferAmount = transferAmount;
		this.transferFrequency = frequency;
		agentStatus = AgentStatus.Running;
		transferCount = 0;
		transferTotal = BigDecimal.valueOf(0);
		account.addModelListener(this);
	}
	
	/**
	 * @return Account of Agent
	 */
	public Account getAccount() {return agentAccount;}
	
	/**
	 * @return int int Agent ID
	 */
	public int getAgentID() {return agentID;};
	
	/**
	 * @return BigDecimal Agent transfer amount
	 */
	public BigDecimal getAmount() {return transferAmount;}
	
	/**
	 * @return Double Agent transfer rate
	 */
	public double getTransferFrequency() {return transferFrequency;}
	
	/**
	 * @return int total count of operations performed
	 */
	public int getTransferCount() {return transferCount;}
	
	/**
	 * @return BigDecimal total amount of all operations
	 */
	public BigDecimal getTransferTotal() {return transferTotal;}
	
	/**
	 * @return String Agent status
	 */
	public String getAgentStatusString() {
		if (agentStatus == AgentStatus.Running) {
			return "Running";
		}
		else if (agentStatus == AgentStatus.Paused) {
			return "Paused";
		}
		else if (agentStatus == AgentStatus.Blocked) {
			return "Blocked";
		}
		else {
			return "NA";
		}
	}
	
	/**
	 * Kills current Agent thread. If its paused, thread is
	 * resumed and previous transfer operation will be 
	 * attempted before thread is killed.
	 */
	public synchronized void killThread() {
		killThread = true;
		agentStatus = AgentStatus.NA;
		resumeThread(); // if paused
		notifyChanged(
				new ModelEvent(EventKind.AgentStatusUpdate, null, AgentStatus.NA));
	}
	
	/**
	 * notifies thread to pause
	 */
	public void pauseThread() {
		synchronized (pauseLock) {
			paused = true;
			pauseLock.notify();
		}	
		notifyChanged(new ModelEvent(EventKind.AgentStatusUpdate, null, AgentStatus.Paused));
	}
	
	/**
	 * notifies thread to resume
	 */
	public void resumeThread() {
		synchronized (pauseLock) {
			paused = false;
			pauseLock.notify();
		}
		notifyChanged(new ModelEvent(EventKind.AgentStatusUpdate, null, AgentStatus.Running));
	}
	
	/**
	 * sets Agent status and notifies agent thread
	 * @param status the AgentStatus to set
	 */
	public void setStatus(AgentStatus status) {	
		synchronized (this) {
			agentStatus = status;
			notify(); 
		}
		
		notifyChanged(new ModelEvent(ModelEvent.EventKind.AgentStatusUpdate, null, agentStatus));
	}
	
	public abstract void run();	
	
	/**
	 * updates Agent on Account changes and notifies Agent thread
	 */
	public void modelChanged(ModelEvent me) {
		
		synchronized (this) {
			this.notifyAll();	
		}
		
		// pass ModelEvent generated from Account through to View
		notifyChanged(me);	
	}
}
