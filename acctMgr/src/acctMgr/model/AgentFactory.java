package acctMgr.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import acctMgr.controller.*;

/**
 * a factory for Agent threads with capacity of 25 agents
 * @author Jon Pierre
 * @version 1.0
 */
public class AgentFactory {
	public final static int AGENT_LIST_CAP = 25;
	private static ArrayList<Agent> agentList = new ArrayList<Agent>(AGENT_LIST_CAP);
	private static int numAgents = 0;
	private static int nextAgentID = 1;

	/**
	 * instantiates Agent, starts Agent Thread
	 * @param account Account the Agent is assigned to
	 * @param amount BigDecimal Agent transfer amount
	 * @param frequency the operation frequency
	 * @return AgentDeposit reference to new Agent
	 * @throws AgentCapacityReachedException iff AgentFactory cannot create more Agent's
	 */
	public static synchronized AgentDeposit createAgentDeposit(Account account, BigDecimal amount, double frequency) 
			throws AgentCapacityReachedException {
		
		if (numAgents == AGENT_LIST_CAP)
			throw new AgentCapacityReachedException();
		
		AgentDeposit agent = new AgentDeposit(account, nextAgentID, amount, frequency);
		agentList.add(agent);
		++nextAgentID;
		++numAgents;	
		
		Thread t = new Thread(agent);
		t.start();
		return agent;
	}
	
	/**
	 * instantiates Agent, starts Agent Thread, returns reference to Agent
	 * @param account Account the Agent is assigned
	 * @param amount BigDecimal Agent transfer amount
	 * @param frequency the frequency to set Agent operations
	 * @return AgentWithdraw 
	 * @throws AgentCapacityReachedException iff AgentFactory is at capacity
	 */
	public static synchronized AgentWithdraw createAgentWithdraw(Account account, BigDecimal amount, double frequency) 
			throws AgentCapacityReachedException {
		
		if (numAgents == AGENT_LIST_CAP)
			throw new AgentCapacityReachedException();
		
		AgentWithdraw agent = new AgentWithdraw(account, nextAgentID, amount, frequency);
		agentList.add(agent);
		++nextAgentID;
		++numAgents;
		
		Thread t = new Thread(agent);
		t.start();
		return agent;
	}
	
	/**
	 * kills all agents
	 */
	public static synchronized void killAgents() {		
		for (Agent a : agentList) {
			a.killThread();
		}
	}

}
