package acctMgr.view;

import acctMgr.controller.*;
import acctMgr.model.*;

/**
 * Specialized view for AgentDeposit
 * @author Jon Pierre
 * @version 1.0
 */
public class AgentDepositView extends AgentView {
	private static final long serialVersionUID = 4598048766717532610L;

	/**
	 * requests Agent ID and Agent Account ID, sets GUI title
	 * @param model to set
	 * @param controller to set
	 */
	public AgentDepositView(Model model, Controller controller) {
		super(model, controller);
		int accountID = ((Agent)getModel()).getAccount().getID();
		int agentID = ((Agent)getModel()).getAgentID();
		setTitle("Deposit Agent " + agentID + " for Account " + accountID);
	}
}
