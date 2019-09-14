package acctMgr.test;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import acctMgr.model.*;

public class AccountThreadSafetyTest {
	Account account;
	AgentDepositTestClass depositAgent1;
	AgentWithdrawTestClass withdrawAgent;
	Thread t1;
	Thread t2;
	
	@Before
	public void setup() {
		account = new Account("Jon", 123, BigDecimal.valueOf(50));
		depositAgent1 = new AgentDepositTestClass(account, 1, BigDecimal.valueOf(5), 1);
		withdrawAgent = new AgentWithdrawTestClass(account, 3, BigDecimal.valueOf(1), 1);
	}
	
	@After
	public void cleanUp() {
		account = null;
		depositAgent1 = null;
		withdrawAgent = null;
		t1 = null;
		t2 = null;
		System.gc();
	}
	
	@Test
	public void testThreads() throws InterruptedException {
		t1 = new Thread(depositAgent1);
		t2 = new Thread(withdrawAgent);
		t1.start(); // deposits $5 * 1000 = 5000
		t2.start(); // withdraw $1 * 1000 = 1000
		t1.join();
		t2.join();
		System.out.println(account.getBalance()); 		
		assertEquals(BigDecimal.valueOf(4050), account.getBalance()); //balance = 50 + 5000 - 1000 = 4050
	}
	
	@Test 
	public void AutoWithdrawBlockTest() throws InterruptedException {
		t2 = new Thread(withdrawAgent);
		t2.start(); // withdraw $1 * 1000 = 1000
		Thread.sleep(2000);
		assertEquals("Blocked" , withdrawAgent.getAgentStatusString()); //balance = 50 - 1000 = -950
	}

}
