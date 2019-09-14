package acctMgr.controller;

import java.math.BigDecimal;

import acctMgr.model.*;
import acctMgr.view.*;

/**
 * creates AgentWithdraw, starts AgentWithdraw Thread.
 * Controller for AgentWithdrawView and AgentWithdraw.
 * @author Jon Pierre
 * @version 1.0
 */
public class AgentWithdrawController extends AbstractController implements AgentController {
	public AgentWithdrawController(Account account, BigDecimal amount, double frequency) 
			throws AgentCapacityReachedException {
		
		setModel(AgentFactory.createAgentWithdraw(account, amount, frequency));
		setView(new AgentWithdrawView( ((AgentWithdraw)getModel()),  this) );
	}
	
	/**
	 * handles View events
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
