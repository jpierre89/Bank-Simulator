package acctMgr.view;
import acctMgr.model.*;
import acctMgr.controller.*;
import javax.swing.*;

/**
 * provides basic View implementations
 */
@SuppressWarnings("serial")
public abstract class JFrameView extends JFrame implements View, ModelListener {
	private Model model;
	private Controller controller;
	/**
	 * Constructor for JFrameView
	 * @param model - model to be set to view.
	 * @param controller controller to be set to view.
	 */
	public JFrameView (Model model, Controller controller){
		setModel(model);
		setController(controller);
	}
	/**
	 * registers the view with the model.
	 */
	public void registerWithModel(){
		((AbstractModel)model).addModelListener(this);
	}
	
	/**
	 * un-registers the view with the model.
	 */
	public void unregisterWithModel(){
		((AbstractModel)model).removeModelListener(this);
	}

	public Model getModel() {
		return this.model;
	}

	public void setModel(Model model) {
		this.model = model;
		registerWithModel();
	}

	public Controller getController() {
		return this.controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
