package acctMgr.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import acctMgr.controller.*;
import acctMgr.model.*;

/**
 * abstract view class for specialized agent views. 
 * implements common GUI functionality. 
 * 
 * @author Jon Pierre
 * @version 1.0
 */
abstract public class AgentView extends JFrameView {
	private static final long serialVersionUID = 8385391268541450775L;
	public final static String STOPAGENT = "STOPAGENT";
	public final static String DISMISSAGENT = "DISMISSAGENT";
	public final static String PAUSEAGENT = "PAUSEAGENT";
	public final static String RESUMEAGENT = "RESUMEAGENT";
	protected FrameHandler frameHandler = new FrameHandler();
	protected JTextField txtAccountBalance;
	protected JTextField txtAgentState;
	protected JTextField txtTransferTotal;
	protected JTextField txtTransferRate;
	protected JTextField txtTransferCount;
	protected JButton btnPause;
	protected JButton btnResume;
	protected JButton btnStop;
	protected JButton btnDismiss;
	
	/**
	 * calls this.initialize()
	 * @param model to set
	 * @param controller to set
	 */
	public AgentView(Model model, Controller controller) {
		super(model, controller);
		initialize();
	}
	
	/**
	 * creates and shows GUI
	 */
	protected void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(550,130));
		setLayout(new FlowLayout());
		
		// add panel to display Account balance; requested from agent (immutable)
		JPanel panelAccountBalance = new JPanel(new BorderLayout());
		BigDecimal balance = ((Agent)getModel()).getAccount().getBalance();
		txtAccountBalance = new JTextField(balance.setScale(2, RoundingMode.HALF_EVEN).toString(), 9);
		txtAccountBalance.setEditable(false);
		panelAccountBalance.add(new JLabel ("Account Balance: $"), BorderLayout.WEST);
		panelAccountBalance.add(txtAccountBalance, BorderLayout.CENTER);
		getContentPane().add(panelAccountBalance);
		
		// add panel to display agent status (immutable)
		JPanel panelAgentStatus = new JPanel(new BorderLayout());
		txtAgentState = new JTextField( ((Agent)getModel()).getAgentStatusString(), 7 );
		txtAgentState.setEditable(false);
		panelAgentStatus.add(new JLabel ("Agent State: "), BorderLayout.WEST);
		panelAgentStatus.add(txtAgentState, BorderLayout.CENTER);
		getContentPane().add(panelAgentStatus);
		
		// add panel to display operations frequency; requested from agent (immutable)
		JPanel panelOpRate = new JPanel(new BorderLayout());
		txtTransferRate = new JTextField(
				String.valueOf(String.format("%.2f", ((Agent)getModel()).getTransferFrequency())) , 5);
		txtTransferRate.setEditable(false); 
		panelOpRate.add(new JLabel ("Frequency: "), BorderLayout.WEST);
		panelOpRate.add(txtTransferRate, BorderLayout.CENTER);
		getContentPane().add(panelOpRate);
		
		// add panel to display amount transferred; requested from agent (immutable)
		JPanel panelAmountTransfer = new JPanel(new BorderLayout());
		txtTransferTotal = new JTextField( ((Agent)getModel()).getTransferTotal().
				setScale(2, RoundingMode.HALF_EVEN).toString(), 7);
		txtTransferTotal.setEditable(false); 
		panelAmountTransfer.add(new JLabel ("Total Transferred: "), BorderLayout.WEST);
		panelAmountTransfer.add(txtTransferTotal, BorderLayout.CENTER);
		getContentPane().add(panelAmountTransfer);
		
		
		// add panel to display operations count; requested from agent (immutable)
		JPanel panelOpCount = new JPanel(new BorderLayout());
		txtTransferCount = new JTextField( String.valueOf(((Agent)getModel()).getTransferCount()), 5);
		txtTransferCount.setEditable(false); 
		panelOpCount.add(new JLabel ("Transfer Count: "), BorderLayout.WEST);
		panelOpCount.add(txtTransferCount, BorderLayout.CENTER);
		getContentPane().add(panelOpCount);
		
		// add pause agent button
		btnPause = new JButton("Pause Agent ");
		btnPause.setActionCommand(PAUSEAGENT);
		btnPause.addActionListener(frameHandler);
		getContentPane().add(btnPause);
		
		// add resume agent button
		btnResume = new JButton("Resume Agent");
		btnResume.setActionCommand(RESUMEAGENT);
		btnResume.addActionListener(frameHandler);
		getContentPane().add(btnResume);
		btnResume.setVisible(false);
		
		// add stop agent button
		btnStop = new JButton("Stop Agent");
		btnStop.setActionCommand(STOPAGENT);
		btnStop.addActionListener(frameHandler);
		getContentPane().add(btnStop);
		
		// add dismiss agent button
		btnDismiss = new JButton("Dismiss Agent");
		btnDismiss.setActionCommand(DISMISSAGENT);
		btnDismiss.addActionListener(frameHandler);
		btnDismiss.setEnabled(false);
		getContentPane().add(btnDismiss);
			
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * enables dismiss button
	 */
	public void enableDismiss() {
		btnDismiss.setEnabled(true);
	}
	
	/**
	 * updates View when Model is changed
	 */
	public void modelChanged(ModelEvent agEvent) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (agEvent.getKind() == ModelEvent.EventKind.BalanceUpdate) {
					txtAccountBalance.setText(
							((Agent)getModel()).getAccount().getBalance().setScale(2, RoundingMode.HALF_EVEN).toString() );
				}		
				else if (agEvent.getKind() == ModelEvent.EventKind.AmountTransferredUpdate) {
					txtTransferTotal.setText(
							((AgentEvent)agEvent).transferTotal.setScale(2, RoundingMode.HALF_EVEN).toString());
					txtTransferCount.setText(String.valueOf(((AgentEvent)agEvent).transferCount));
				}	
				else if (agEvent.getKind() == ModelEvent.EventKind.AgentStatusUpdate) {
					txtAgentState.setText(agEvent.getAgStatus().name());
				}
			}
		});
	}
	
	/**
	 * handles all ActionEvent's
	 */
	class FrameHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == PAUSEAGENT) {
				btnPause.setVisible(false);
				btnResume.setVisible(true);
				pack();
			}
			else if (e.getActionCommand() == RESUMEAGENT) {
				btnResume.setVisible(false);
				btnPause.setVisible(true);
				pack();
			}
			
			((AgentController)getController()).operation(e.getActionCommand());
		}
	}
	
}


