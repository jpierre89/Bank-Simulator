package acctMgr.controller;

import java.math.BigDecimal;

import acctMgr.model.AccountList;
import acctMgr.model.IllegalAccountNameException;
import acctMgr.view.AddAccountView;

/**
 * controller for adding an account 
 * @author Jon Pierre
 * @version 1.0
 */
public class AddAccountController extends AbstractController {
	AddAccountController(AccountList model) {
		setModel(model);
		setView(new AddAccountView(model, this));
	}
	
	public void operation(String option, String accountName) {
		if (option == AddAccountView.ADD) {
			try {
				((AccountList)getModel()).addAccount(accountName, BigDecimal.valueOf(0.0));
			}
			catch (IllegalAccountNameException e) {
				((AddAccountView) getView()).displayMessage(e.toString());
			}
			
			((AddAccountView) getView()).dispose();
		}
		else if (option == AddAccountView.DISMISS) {
			((AddAccountView) getView()).dispose();
		}
	}
}
