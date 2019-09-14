package acctMgr.view;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import acctMgr.controller.*;
import acctMgr.model.*;

/**
 * account selection GUI. Includes options to select an account
 * to edit and to create agents. 
 * @author Jon Pierre
 * @version 1.0
 */
public class AccountSelectionView extends JFrameView {
	private static final long serialVersionUID = -6150845046528294767L;
	private JComboBox<String> comboBox = new JComboBox<String>();
	private Handler frameHandler = new Handler();
	
	public final static String EDIT_ACCOUNT_DOLLAR = "EDIT_DOLLARS";
	public final static String EDIT_ACCOUNT_EURO = "EDIT_EURO";
	public final static String EDIT_ACCOUNT_YEN = "EDIT_YEN";
	public final static String MAKE_DEPOSIT_AGENT = "MAKE_DEPOSIT_AGENT";
	public final static String MAKE_WITHDRAW_AGENT = "MAKE_WITHDRAW_AGENT";
	public final static String ADD = "ADD";
	public final static String REMOVE = "REMOVE";
	public final static String SAVE = "SAVE";
	public final static String EXIT = "ExIT";

	/**
	 * calls this.initialize()
	 * @param model to set
	 * @param controller to set
	 */
	public AccountSelectionView(AccountList model, AccountSelectionController controller) {
		super(model, controller); 
		
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (Exception e) {}
		
		initialize();
	}
	
	/**
	 * updates GUI with Accounts for ComboBox when list of Account's changes.
	 * @param me ModelEvent for updating
	 */
	 public void modelChanged(ModelEvent me) {
		 SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				populateComboBox();
			} 
		 });
	 } 


	/**
	 * displays a message in a pop up window
	 * @param msg string message to be displayed as pop-up window
	 */
	public void displayMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	 
	/**
	 * displays a message in a pop up window and then exits program.
	 * @param msg String message to display
	 */
	 public void displayMessageFatal(String msg) {
		 JOptionPane.showMessageDialog(null, msg);
		 System.exit(1);
	 }
	
	/**
	 * creates and shows GUI
	 */
	private void initialize() {			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.LEADING));
		
		setPreferredSize(new Dimension(550,130));
		setTitle("Account Selection");
		getContentPane().add(comboBox);

		JButton btnEditDollar = new JButton("Edit Account in $");
		btnEditDollar.setActionCommand(EDIT_ACCOUNT_DOLLAR);
		btnEditDollar.addActionListener(frameHandler);
		getContentPane().add(btnEditDollar);
		
		JButton btnEditEuros = new JButton("Edit Account in Euros");
		btnEditEuros.setActionCommand(EDIT_ACCOUNT_EURO);
		btnEditEuros.addActionListener(frameHandler);
		getContentPane().add(btnEditEuros);
		
		JButton btnEditYen = new JButton("Edit Account in Yen");
		btnEditYen.setActionCommand(EDIT_ACCOUNT_YEN);
		btnEditYen.addActionListener(frameHandler);
		getContentPane().add(btnEditYen);
		
		JButton btnDepositAgent = new JButton("Create Deposit Agent");
		btnDepositAgent.setActionCommand(MAKE_DEPOSIT_AGENT);
		btnDepositAgent.addActionListener(frameHandler);
		getContentPane().add(btnDepositAgent);
		
		JButton btnWithdrawAgent = new JButton("Create Withdraw Agent");
		btnWithdrawAgent.setActionCommand(MAKE_WITHDRAW_AGENT);
		btnWithdrawAgent.addActionListener(frameHandler);
		getContentPane().add(btnWithdrawAgent);	
		
		JButton btnAddAccount = new JButton("Add Account");
		btnAddAccount.setActionCommand(ADD);
		btnAddAccount.addActionListener(frameHandler);
		getContentPane().add(btnAddAccount);	
		
		JButton btnRemoveAccount = new JButton("Remove Account");
		btnRemoveAccount.setActionCommand(REMOVE);
		btnRemoveAccount.addActionListener(frameHandler);
		getContentPane().add(btnRemoveAccount);	

		JButton btnSave = new JButton("Save");
		btnSave.setActionCommand(SAVE);
		btnSave.addActionListener(frameHandler);
		getContentPane().add(btnSave);	
		
		JButton btnExit = new JButton("Exit");
		btnExit.setActionCommand(EXIT);
		btnExit.addActionListener(frameHandler);
		getContentPane().add(btnExit);

		pack();	
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * populates the JComboBox with account ids.
	 */
	private void populateComboBox() {
		comboBox.removeAllItems();
		List<Integer> idList = ((AccountList)getModel()).getAccountIDs();
		Collections.sort(idList);
		for (Integer i : idList) {
			comboBox.addItem(i.toString() + " " + ((AccountList)getModel()).getNameOfAccount(i));
		}	
	}
	
	 /**
	  * handles all ActionEvent's
	  */
	class Handler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String selectedItem = ((String) comboBox.getSelectedItem());
			int selectedID = Integer.parseInt(selectedItem.split(" ")[0]);
			( (AccountSelectionController) getController()).operation(
					selectedID, e.getActionCommand());
		}		
	}
}

