package acctMgr.controller;

import java.math.BigDecimal;

import acctMgr.model.*;
import acctMgr.view.*;

/**
 * Controller for StartAgentDepositView and Account.
 * Creates Agent Controller's.
 * @author Jon Pierre
 * @version 1.0
 */
public class StartAgentDepositController extends AbstractController implements StartAgentController {
	public StartAgentDepositController(Account a) {
		setModel(a);
		setView(new StartAgentDepositView(a , this));
	}
	
	/**
	 * handles View events
	 */
	public void operation(String option, BigDecimal amount, double frequency) {
		if (option == StartAgentView.STARTAGENT) {  
			try {
				new AgentDepositController(((Account)getModel()), amount, frequency);
				((StartAgentDepositView)getView()).dispose();
			} catch (AgentCapacityReachedException e) {
				((StartAgentDepositView)getView()).displayMessage(e.toString());
			}
		}
		else if (option == StartAgentView.DISMISS) {
			((StartAgentDepositView)getView()).dispose();
		}
	}
}
