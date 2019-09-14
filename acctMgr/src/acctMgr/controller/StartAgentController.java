package acctMgr.controller;

import java.math.BigDecimal;

/**
 * defines behavior of StartAgentController
 * @author Jon Pierre
 * @version 1.0
 */
public interface StartAgentController {
	public void operation(String option, BigDecimal amount, double frequency);
}
