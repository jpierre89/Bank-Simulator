package acctMgr.view;

import acctMgr.controller.*;
import acctMgr.model.*;

/**
 * Specialized view GUI for starting an AgentDeposit
 * @author Jon Pierre
 * @version 1.0
 */
@SuppressWarnings("serial")
public class StartAgentDepositView extends StartAgentView {
	/**
	 * sets title
	 * @param model to set
	 * @param controller to set
	 */
	public StartAgentDepositView(Model model, Controller controller) {
		super(model, controller);
		setTitle("Start Deposit Agent");
	}

}
