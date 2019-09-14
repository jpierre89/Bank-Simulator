package acctMgr.controller;

import java.math.BigDecimal;

import acctMgr.model.*;
import acctMgr.view.*;

/**
 * creates AgentDeposit, starts AgentDeposit Thread.
 * Controller for AgentDepositView and AgentDeposit.
 * @author Jon Pierre
 * @version 1.0
 */
public class AgentDepositController extends AbstractController implements AgentController {
	public AgentDepositController(Account account, BigDecimal amount, double frequency) 
			throws AgentCapacityReachedException {
		
		setModel(AgentFactory.createAgentDeposit(account, amount, frequency));
		setView(new AgentDepositView( ((AgentDeposit)getModel()), this));
	}
	
	/**
	 * handles View events
	 * 
	 */
	public void operation(String option) {
		if (option == AgentView.STOPAGENT) {
			((Agent)getModel()).killThread();
			((AgentView)getView()).enableDismiss();
		}
		else if (option == AgentView.DISMISSAGENT) {
			((JFrameView)getView()).dispose();
		}
		else if (option == AgentView.PAUSEAGENT) {
			((Agent)getModel()).pauseThread();
		}
		else if (option == AgentView.RESUMEAGENT) {
			((Agent)getModel()).resumeThread();
		}
	}
}
