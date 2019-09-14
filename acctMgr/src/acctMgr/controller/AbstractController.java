package acctMgr.controller;

import acctMgr.view.*;
import acctMgr.model.*;

/**
 * Abstract Controller implementations
 * @version 1.0
 */
public abstract class AbstractController implements Controller {
	private View view;
	private Model model;
	
	/**
	 * setter for model
	 */
	public void setModel(Model model){this.model = model;}
	/**
	 * getter for model
	 */
	public Model getModel(){return model;}
	/**
	 * getter for the view.
	 */
	public View getView(){return view;}
	/**
	 * setter for the view.
	 */
	public void setView(View view){this.view = view;}
}
