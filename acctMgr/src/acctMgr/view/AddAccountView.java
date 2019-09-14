package acctMgr.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import acctMgr.controller.*;
import acctMgr.model.AccountList;
import acctMgr.model.ModelEvent;

/**
 * view for adding account
 * @author Jon Pierre
 * @version 1.0
 */
public class AddAccountView extends JFrameView{
	private static final long serialVersionUID = 4194231423665563832L;
	protected JTextField txtEnterAccount;
	protected JButton btnAdd;
	protected JButton btndismiss;
	protected FrameHandler frameHandler = new FrameHandler();
	
	public static final String ADD = "ADD";
	public static final String DISMISS = "DISMISS";

	public AddAccountView(AccountList model, AddAccountController controller) {
		super(model, controller);
		initialize();
	}

	public void modelChanged(ModelEvent me) {}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		txtEnterAccount = new JTextField("Enter Account Name", 15);
		txtEnterAccount.setEditable(true);
		getContentPane().add(txtEnterAccount);
		
		JButton btnAdd = new JButton("Add Account");
		btnAdd.setActionCommand(ADD);
		btnAdd.addActionListener(frameHandler);
		getContentPane().add(btnAdd);
		
		JButton btnDismiss = new JButton("Dismiss");
		btnDismiss.setActionCommand(DISMISS);
		btnDismiss.addActionListener(frameHandler);
		getContentPane().add(btnDismiss);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * displays a message in a pop up window
	 * @param msg string message to be displayed as pop-up window
	 */
	public void displayMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	class FrameHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((AddAccountController)getController()).operation(
					e.getActionCommand(), txtEnterAccount.getText());
		}
		
	}
}
