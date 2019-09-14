package acctMgr.view;

import acctMgr.controller.*;
import acctMgr.model.*;

/**
 * Specialized view for AgentWithdraw
 * @author Jon Pierre
 * @version 1.0
 */
public class AgentWithdrawView extends AgentView {
	private static final long serialVersionUID = 7725478182767179587L;

	/**
	 * requests Agent ID and Agent Account ID, sets GUI title
	 * @param model to set
	 * @param controller to set
	 */
	public AgentWithdrawView(Model model, Controller controller) {
		super(model, controller);
		int accountID = ((Agent)getModel()).getAccount().getID();
		int agentID = ((Agent)getModel()).getAgentID();
		setTitle("Withdraw Agent " + agentID + " for Account " + accountID);		
	}

}
