package acctMgr.test;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import acctMgr.model.Account;
import acctMgr.model.OverdrawException;
import java.math.BigDecimal;

public class AccountTest {
	Account account;

	@Before
	public void setup() {
	}
	
	@After
	public void cleanUp() {
		account = null;
		System.gc();
	}
	
	@Test
	public void testWithdraw() {
		account = new Account("test", 1, BigDecimal.valueOf(100.00));
		account.withdraw(BigDecimal.valueOf(49.99));
		assertEquals(BigDecimal.valueOf(50.01), account.getBalance());
	}
	
	@Test (expected = OverdrawException.class)
	public void testWithdrawOverdraft() {
		account = new Account("test", 1, BigDecimal.valueOf(100.00));
		account.withdraw(BigDecimal.valueOf(125.00));
	}
	
	@Test
	public void testDeposit() {
		account = new Account("test", 1, BigDecimal.valueOf(100.00));
		account.deposit(BigDecimal.valueOf(49.99));
		assertEquals(BigDecimal.valueOf(149.99), account.getBalance());
	}

}
