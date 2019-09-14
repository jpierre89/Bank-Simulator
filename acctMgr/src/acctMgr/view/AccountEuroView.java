package acctMgr.view;

import acctMgr.controller.*;
import acctMgr.model.*;
import acctMgr.util.CurrencyConverter;
import acctMgr.util.CurrencyType;

/**
 * specialized AccountView with CurrencyType Euro
 * @author Jon Pierre
 * @version 1.0
 */
public class AccountEuroView extends AccountView {
	private static final long serialVersionUID = -2833901916732142290L;

	/**
	 * Requests Account info and sets GUI title.
	 * @param model to set
	 * @param controller to set
	 */
	public AccountEuroView(Model model, Controller controller) {
		super(model, controller, CurrencyType.Euro);
		setTitle(((Account)getModel()).getName() + " " + ((Account)getModel()).getID() + "; "
				+ "Operations in " + CurrencyConverter.getCurrencySymbol(CurrencyType.Euro));
	}
}
