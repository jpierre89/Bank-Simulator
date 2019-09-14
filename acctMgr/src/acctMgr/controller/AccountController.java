package acctMgr.controller;

import java.math.BigDecimal;

import acctMgr.model.*;
import acctMgr.view.*;
import acctMgr.util.*;

/**
 * controller for an Account and AccountView
 * @author Jon Pierre
 * @version 1.0
 */
public class AccountController extends AbstractController {

	public AccountController() {}

	public void operation(ViewEvent viewEvent) {
		if (viewEvent.getKind() == ViewEvent.EventKind.DepositEvent) {
			((Account) getModel()).deposit(viewEvent.getAmount());
		}
		else if (viewEvent.getKind() == ViewEvent.EventKind.WithdrawEvent) {
			BigDecimal amount = viewEvent.getAmount();
			try {
				((Account) getModel()).withdraw(amount);
			}
			catch (OverdrawException e) {
				CurrencyType type = ((AccountView)getView()).getCurrencyType();
				
				((AccountView)getView()).displayMessage(
				"Insufficient funds: amount to withdraw is " + type +
				" " + amount.toString() + ", it is greater than available funds: " + 
				type + " " + CurrencyConverter.convert(CurrencyType.US, type, ((Account)getModel()).getBalance()));
			}
		}
	}

}