package acctMgr.view;

import acctMgr.controller.*;
import acctMgr.model.*;
import acctMgr.util.*;

/**
 * specialized AccountView with CurrencyType Yen.
 * @author Jon Pierre
 * @version 1.0
 */
public class AccountYenView extends AccountView {
	private static final long serialVersionUID = -680278028013934178L;

	/**
	 * Requests Account info and sets GUI title.
	 * @param model to set
	 * @param controller to set
	 */
	public AccountYenView(Model model, Controller controller) {
		super(model, controller, CurrencyType.Yen);
		setTitle(((Account)getModel()).getName() + " " + ((Account)getModel()).getID() + "; "
				+ "Operations in " + CurrencyConverter.getCurrencySymbol(CurrencyType.Yen));
	}
}
