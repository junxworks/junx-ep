/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RuleEngine.java   
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

import com.ql.util.express.ExpressRunner;

import io.github.junxworks.junx.core.util.ExceptionUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RuleEngine
 * @author: Michael
 * @date:   2021-3-1 21:18:48
 * @since:  v1.0
 */
public class RuleEngine {

	/** original engine. */
	private ExpressRunner originalEngine;

	public ExpressRunner getOriginalEngine() {
		return originalEngine;
	}

	public void setOriginalEngine(ExpressRunner originalEngine) {
		this.originalEngine = originalEngine;
	}

	/**
	 * Check expression.
	 *
	 * @param exp the exp
	 * @return the check result
	 */
	public CheckResult checkExpression(String exp) {
		CheckResult result = new CheckResult();
		try {
			originalEngine.parseInstructionSet(exp);
		} catch (Exception e) {
			result.setPass(false);
			result.setMessage("表达式编译出错，请检查语法");
			result.setException(e);
			result.setExceptionCause(ExceptionUtils.getCauseMessage(e));
		}
		return result;
	}

	/**
	 * Execute.
	 *
	 * @param exp the exp
	 * @param context the context
	 * @return the exec result
	 */
	public ExecResult execute(String exp, SpringQLContext context) {
		ExecResult res = new ExecResult();
		try {
			Object obj = originalEngine.execute(exp, context, null, true, false);
			res.setResult(obj);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setError(e);
			res.setErrorMsg(ExceptionUtils.getCauseMessage(e));
		}
		return res;
	}

	/**
	 * 返回 out variable 属性.
	 *
	 * @param exp the exp
	 * @return out variable 属性
	 * @throws Exception the exception
	 */
	public String[] getOutVariable(String exp) throws Exception {
		return originalEngine.getOutVarNames(exp);
	}

	/**
	 * 返回 out function names 属性.
	 *
	 * @param exp the exp
	 * @return out function names 属性
	 * @throws Exception the exception
	 */
	public String[] getOutFunctionNames(String exp) throws Exception {
		return originalEngine.getOutFunctionNames(exp);
	}
}
