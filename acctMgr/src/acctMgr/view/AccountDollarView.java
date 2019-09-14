package acctMgr.view;

import acctMgr.controller.*;
import acctMgr.model.*;
import acctMgr.util.CurrencyConverter;
import acctMgr.util.CurrencyType;

/**
 * specialized AccountView with CurrencyType US
 * @author Jon Pierre
 * @version 1.0
 */
public class AccountDollarView extends AccountView {
	private static final long serialVersionUID = -1107206849241169103L;

	/**
	 * Requests Account info and sets GUI title.
	 * @param model to set
	 * @param controller to set
	 */
	public AccountDollarView(Model model, Controller controller) {
		super(model, controller, CurrencyType.US);
		currencySymbol = "$";
		setTitle(((Account)getModel()).getName() + " " + ((Account)getModel()).getID() + "; "
				+ "Operations in " + CurrencyConverter.getCurrencySymbol(CurrencyType.US));
	}
}
