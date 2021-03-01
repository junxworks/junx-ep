/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RuleEngineConfiguration.java   
 * @Package io.github.junxworks.ep.qlexp   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-3-1 21:18:48   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.qlexp;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RuleEngineConfiguration
 * @author: Michael
 * @date:   2021-3-1 21:18:48
 * @since:  v1.0
 */
public class RuleEngineConfiguration {
	
	/** is precise. */
	private boolean isPrecise = true;

	/** is short circuit. */
	private boolean isShortCircuit = true;

	/** is trace. */
	private boolean isTrace = false;

	/** use chinese operators. */
	private boolean useChineseOperators = true;

	/** use chinese keywords. */
	private boolean useChineseKeywords = true;

	public boolean isPrecise() {
		return isPrecise;
	}

	public void setPrecise(boolean isPrecise) {
		this.isPrecise = isPrecise;
	}

	public boolean isShortCircuit() {
		return isShortCircuit;
	}

	public void setShortCircuit(boolean isShortCircuit) {
		this.isShortCircuit = isShortCircuit;
	}

	public boolean isTrace() {
		return isTrace;
	}

	public void setTrace(boolean isTrace) {
		this.isTrace = isTrace;
	}

	public boolean isUseChineseOperators() {
		return useChineseOperators;
	}

	public void setUseChineseOperators(boolean useChineseOperators) {
		this.useChineseOperators = useChineseOperators;
	}

	public boolean isUseChineseKeywords() {
		return useChineseKeywords;
	}

	public void setUseChineseKeywords(boolean useChineseKeywords) {
		this.useChineseKeywords = useChineseKeywords;
	}

}
