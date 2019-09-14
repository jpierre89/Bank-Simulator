package acctMgr.controller;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;

import acctMgr.model.*;
import acctMgr.view.*;

/**
 * controller for AccountList and AccountSelectionView
 * @author Jon Pierre
 * @version 1.0
 */
public class AccountSelectionController extends AbstractController {
	private final String fileName;

	/**
	 * instantiates AccountList model and AccountSelectionView controller.
	 * Sends message for model to load accounts and Handles any file problems with pop-up dialogue. 
	 * @param fileName the fileName for AccountList to load.
	 */
	public AccountSelectionController(String fileName) {
		this.fileName = fileName;
		setModel( (Model) new AccountList());
		setView( (View) new AccountSelectionView( (AccountList) getModel(), this) );
		
		try {
			((AccountList) getModel()).loadAccount(fileName);
		} catch (IllegalFileFormatException formatException) {
			((AccountSelectionView)getView()).displayMessageFatal(formatException.toString());
			System.exit(1);
		} catch (FileNotFoundException notFoundException) { 
			((AccountSelectionView)getView()).displayMessageFatal(notFoundException.toString());
		} catch (NoSuchElementException noElementException) {
			((AccountSelectionView)getView()).displayMessageFatal(noElementException.toString());
		}
	}

	/**
	 * handles GUI events. 
	 * @param accountID int id of Account
	 * @param option String selected GUI option
	 */
	public void operation(int accountID, String option) {
		if (option == AccountSelectionView.EDIT_ACCOUNT_DOLLAR) {
			Account a = ((AccountList)getModel() ).getAccount(accountID);
			AccountController aController = new AccountController();
			AccountDollarView aView = new AccountDollarView(a, aController);
			aController.setModel(a);
			aController.setView(aView);		
		}
		else if (option == AccountSelectionView.EDIT_ACCOUNT_EURO) {
			Account a = ((AccountList)getModel() ).getAccount(accountID);
			AccountController aController = new AccountController();
			AccountEuroView aView = new AccountEuroView(a, aController);
			aController.setModel(a);
			aController.setView(aView);	
		}
		else if (option == AccountSelectionView.EDIT_ACCOUNT_YEN) {
			Account a = ((AccountList) getModel() ).getAccount(accountID);
			AccountController aController = new AccountController();
			AccountYenView aView = new AccountYenView(a, aController);
			aController.setModel(a);
			aController.setView(aView);	
		}
		else if (option == AccountSelectionView.MAKE_DEPOSIT_AGENT) {
			Account a = ((AccountList) getModel()).getAccount(accountID);
			new StartAgentDepositController(a);
		}
		else if (option == AccountSelectionView.MAKE_WITHDRAW_AGENT) {
			Account a = ((AccountList) getModel() ).getAccount(accountID);
			new StartAgentWithdrawController(a);
		}
		else if (option == AccountSelectionView.ADD) {
			new AddAccountController(((AccountList) getModel()));
		}
		else if (option == AccountSelectionView.REMOVE) {
			 if (JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
			 	((AccountList) getModel()).removeAccount(accountID);
			}
		}
		else if (option == AccountSelectionView.SAVE) {
			try {
				((AccountList) getModel()).saveAccount(fileName);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error writing to file. File integrity may be lost.");
			}			
		}
		else if (option == AccountSelectionView.EXIT) {
			try {
				((AccountList) getModel()).saveAccount(fileName);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error writing to file. File integrity may be lost.");
			}
			finally {
				System.exit(0);
			}		
		}
		
			
	}
}