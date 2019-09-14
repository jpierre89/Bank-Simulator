package acctMgr.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import acctMgr.controller.*;
import acctMgr.model.*;

/**
 * abstract view class for starting Agents
 * implements common GUI functionality.  
 * @author Jon Pierre
 * @version 1.0
 */
public abstract class StartAgentView extends JFrameView {
	private static final long serialVersionUID = -1023944246090085248L;
	private JButton btnStart;
	private JButton btnDismiss;
	private JTextField txtAmount;
	private JTextField txtFrequency;
	private FrameHandler frameHandler = new FrameHandler();
	
	/**
	 * ActionCommand name for starting an agent
	 */
	public final static String STARTAGENT = "STARTAGENT";
	
	/**
	 * ActionCommand name for dismissing an agent
	 */	
	public final static String DISMISS = "DISMISS";
	
	/**
	 * calls this.initialize()
	 * @param model to set
	 * @param controller to set
	 */
	public StartAgentView(Model model, Controller controller) {
		super(model, controller);
		initialize();		
	}
	
	/**
	 * StartAgentView is not updated by model.
	 */
	public void modelChanged(ModelEvent me) {}
	
	/**
	 * creates and shows GUI
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		// add panel w/ amount
		JPanel pAmount = new JPanel(new BorderLayout());
		txtAmount = new JTextField("0.0", 5);
		txtAmount.setEditable(true); 
		pAmount.add(new JLabel ("Amount: "), BorderLayout.WEST);
		pAmount.add(txtAmount, BorderLayout.CENTER);
		getContentPane().add(pAmount);
		
		// add panel w/ frequency
		JPanel pFrequency = new JPanel(new BorderLayout());
		txtFrequency = new JTextField("1", 5);
		txtAmount.setEditable(true); 
		pFrequency.add(new JLabel ("Frequency: "), BorderLayout.WEST);
		pFrequency.add(txtFrequency, BorderLayout.CENTER);
		getContentPane().add(pFrequency);

		// add start agent button
		btnStart = new JButton("Start Agent");
		btnStart.setActionCommand(STARTAGENT);
		btnStart.addActionListener(frameHandler);
		getContentPane().add(btnStart);
		
		// add dismiss agent button
		btnDismiss = new JButton("Dismiss");
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
		public void actionPerformed(ActionEvent event) {
			
			try {
				BigDecimal amount = new BigDecimal(txtAmount.getText());
				BigDecimal frequency = new BigDecimal(txtFrequency.getText());
				((StartAgentController)getController()).operation(
						event.getActionCommand(), amount, frequency.doubleValue());	
			}
			catch(java.lang.NumberFormatException e) {
				displayMessage(e.getMessage());
				txtAmount.setText("0.0");
			}
			
		}	
	}
	
}
