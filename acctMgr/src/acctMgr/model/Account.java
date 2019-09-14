package acctMgr.model;

import java.math.BigDecimal;

/**
 * A thread safe bank account. Includes methods to deposit and
 * withdraw currency. When balance has changed, aBalanceChanged is set true.
 * @author Jon Pierre
 * @version 1.0
 */
public class Account extends AbstractModel {
	// invariant: balance and aBalanceChanged updated atomically
	
	private final int id;
	private final String name;
	private BigDecimal balance;
	
	/**
	 * set to true if an account balance has changed.
	 */
	public static volatile boolean aBalanceChanged = false;
	
	/**
	 * @param id int id of account
	 * @param name String name of account
	 * @param initBalance BigDecimal initial account balance
	 */
	public Account(String name, int id, BigDecimal initBalance) {
		this.id = id;
		this.name = name;
		this.balance = initBalance;
	}
	
	public String getName() {return name;}
	public int getID() {return id;}
	public BigDecimal getBalance() {return balance;}

	/** 
	 * deposits depositAmount into account, and wakes up all threads on this lock
	 * @param depositAmount BigDecimal amount in US currency to deposit
	 */
	public synchronized void deposit(BigDecimal depositAmount) {
		balance = balance.add(depositAmount);
		ModelEvent me = new ModelEvent(
				ModelEvent.EventKind.BalanceUpdate, balance, null);
		aBalanceChanged = true;
		notifyChanged(me);
	}
	
	/**
	 * withdraws withdrawAmount from account
	 * @param withdrawAmount BigDecimal amount in US currency to withdraw
	 * @throws OverdrawException if withdrawAmount greater than account balance
	 */
	public synchronized void withdraw(BigDecimal withdrawAmount) throws OverdrawException {
		if (withdrawAmount.compareTo(balance) == 1) {
			throw new OverdrawException();
		}
		
		balance = balance.subtract(withdrawAmount);
		aBalanceChanged = true;		
		ModelEvent me = new ModelEvent(
				ModelEvent.EventKind.BalanceUpdate, balance, null);	
		notifyChanged(me);
	}

	/** 
	 * for Agent's to deposit depositAmount into account, and wakes up all threads on this lock
	 * @param depositAmount BigDecimal amount in US currency to deposit
	 */
	public synchronized void autoDeposit(BigDecimal depositAmount) {
		balance = balance.add(depositAmount);
		aBalanceChanged = true;
		notifyChanged(new ModelEvent(
				ModelEvent.EventKind.BalanceUpdate, balance, null));
	}
	
	/**
	 * for Agent's to withdraw withdrawAmount from account
	 * sets AgentStatus.Blocked if withdraw would result in negative Account balance.
	 * @param withdrawAmount BigDecimal amount in US currency to withdraw
	 * @return true iff withdrawAmount successfully withdrawn from account
	 */
	public synchronized boolean autoWithdraw(BigDecimal withdrawAmount) {
		if (withdrawAmount.compareTo(balance) == 1) {		
			return false;
		}
		else {
			balance = balance.subtract(withdrawAmount);
			aBalanceChanged = true;		
			ModelEvent me = new ModelEvent(
					ModelEvent.EventKind.BalanceUpdate, balance, null);	
			notifyChanged(me);
			return true;
		}
		
	}
	
}

