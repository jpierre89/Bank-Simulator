package acctMgr.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import acctMgr.controller.AccountController;
import acctMgr.controller.Controller;
import acctMgr.model.Account;
import acctMgr.model.Model;
import acctMgr.model.ModelEvent;
import acctMgr.util.CurrencyConverter;
import acctMgr.util.CurrencyType;

/**
 * Abstract class for specialized account views in various currencies.
 * Sub-classes must set currencyType and currencyTypeSymbol.
 * @author Jon Pierre
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public abstract class AccountView extends JFrameView {
	
	private FrameHandler frameHandler = new FrameHandler();
	private JButton btnDeposit;
	private JButton btnWithdraw;
	private JButton btnDismiss;
	private JTextField txtAvailableFunds;
	private JTextField txtEnterAmount;
	
	protected CurrencyType currencyNative;
	protected String currencySymbol;

	public AccountView(Model model, Controller controller, CurrencyType type) {
		super(model, controller);
		currencyNative = type;
		currencySymbol = CurrencyConverter.getCurrencySymbol(currencyNative);
		initialize();
	}
	
	public CurrencyType getCurrencyType( ) {return currencyNative;}; 
	
	/**
	 * displays pop-up dialogue
	 * @param msg String message to display
	 */
	public void displayMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	/**
	 * updates GUI when model is changed
	 */
	public void modelChanged(ModelEvent me) {
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				BigDecimal balUS = me.getBalance();
				BigDecimal balNative = CurrencyConverter.convert(
						CurrencyType.US, currencyNative, balUS);
				txtAvailableFunds.setText(balNative.setScale(2, RoundingMode.HALF_EVEN).toString());
			}
		});
	}

	/**
	 * creates and shows GUI
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		JPanel availablefunds = new JPanel(new BorderLayout());
		BigDecimal balNative = CurrencyConverter.convert(
				CurrencyType.US, currencyNative, ((Account)getModel()).getBalance()); 
		txtAvailableFunds = new JTextField(balNative.setScale(2, RoundingMode.HALF_EVEN).toString(), 9);
		txtAvailableFunds.setEditable(false);
		JLabel fundsLabel = new JLabel("Available Funds (" + currencySymbol + "): ");
		availablefunds.add(fundsLabel, BorderLayout.WEST);
		availablefunds.add(txtAvailableFunds, BorderLayout.CENTER);
		getContentPane().add(availablefunds);
		
		JPanel enterAmount = new JPanel(new BorderLayout());
		txtEnterAmount = new JTextField(String.valueOf(0.0), 5);
		txtEnterAmount.setEditable(true);
		JLabel enterLabel = new JLabel("Enter amount (" + currencySymbol + "): ");
		enterAmount.add(enterLabel, BorderLayout.WEST);
		enterAmount.add(txtEnterAmount, BorderLayout.CENTER);
		getContentPane().add(enterAmount);
				
		btnDeposit = new JButton("Deposit");
		btnDeposit.setActionCommand("Deposit");
		btnDeposit.addActionListener(frameHandler);
		getContentPane().add(btnDeposit);	
		
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setActionCommand("Withdraw");
		btnWithdraw.addActionListener(frameHandler);
		getContentPane().add(btnWithdraw);
		
		btnDismiss = new JButton("Dismiss");
		btnDismiss.setActionCommand("Dismiss");
		btnDismiss.addActionListener(frameHandler);
		getContentPane().add(btnDismiss);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * handles all ActionEvent
	 */
	class FrameHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {		
			if (event.getSource() == btnDeposit) {
				String fieldText = txtEnterAmount.getText();
				txtEnterAmount.setText("0.0");			
				
				try {
					BigDecimal amountNative = new BigDecimal(fieldText);
					BigDecimal amountUS = CurrencyConverter.convert(currencyNative, CurrencyType.US, amountNative);
					((AccountController) getController()).
						operation(new ViewEvent(ViewEvent.EventKind.DepositEvent, amountUS));
				}
				catch(java.lang.NumberFormatException n) {
					displayMessage(n.getMessage());
				}

			}
			else if (event.getSource() == btnWithdraw) {
				String fieldText = txtEnterAmount.getText();
				txtEnterAmount.setText("0.0");
				
				try {
					BigDecimal amountNative = new BigDecimal(fieldText);
					BigDecimal amountUS = CurrencyConverter.convert(currencyNative, CurrencyType.US, amountNative);
					((AccountController) getController()).
						operation(new ViewEvent(ViewEvent.EventKind.WithdrawEvent, amountUS));
				}
				catch(java.lang.NumberFormatException n) {
					displayMessage(n.getMessage());
				}
				
			}
			else if (event.getSource() == btnDismiss) {
				dispose();
			}
		}	
	}

}