package acctMgr.controller;

import java.math.BigDecimal;

import acctMgr.model.*;
import acctMgr.view.*;

/**
 * Controller for StartAgentWithdrawView and Account.
 * Creates Agent Controller's.
 * @author Jon Pierre
 * @version 1.0
 */
public class StartAgentWithdrawController extends AbstractController implements StartAgentController {
	public StartAgentWithdrawController(Account a) {
		setModel(a);
		setView(new StartAgentWithdrawView(a , this));
	}
	
	/**
	 * handles View events
	 */
	public void operation(String option, BigDecimal amount, double frequency) {
		if (option == StartAgentView.STARTAGENT) {
			try {
				new AgentWithdrawController(((Account)getModel()), amount, frequency);
				((StartAgentWithdrawView)getView()).dispose();
			} catch (AgentCapacityReachedException e) {
				((StartAgentView)getView()).displayMessage(e.toString());
			}
		}
		else if (option == StartAgentView.DISMISS) {
			((StartAgentWithdrawView)getView()).dispose();
		}
	}
}
