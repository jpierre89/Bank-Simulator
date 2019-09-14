package acctMgr.controller;
import acctMgr.view.View;
import acctMgr.model.Model;

/**
 * interface for a Controller
 * @version 1.0
 */
public interface Controller {
	void setModel(Model model);
	Model getModel();
	View getView();
	void setView(View view);
}
