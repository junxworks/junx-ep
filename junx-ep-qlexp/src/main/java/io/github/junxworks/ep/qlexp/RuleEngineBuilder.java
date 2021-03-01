/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RuleEngineBuilder.java   
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

import java.util.Map;

import com.google.common.collect.Maps;
import com.ql.util.express.ExpressRunner;

import io.github.junxworks.ep.qlexp.functions.DateFunc;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RuleEngineBuilder
 * @author: Michael
 * @date:   2021-3-1 21:18:48
 * @since:  v1.0
 */
public class RuleEngineBuilder {

	/** configuration. */
	private RuleEngineConfiguration configuration = new RuleEngineConfiguration();

	/** external operators. */
	private Map<String, String> externalOperators;

	/** external keywords. */
	private Map<String, String> externalKeywords;

	/** inner operators. */
	private static Map<String, String> innerOperators = Maps.newHashMap();

	/** inner keywords. */
	private static Map<String, String> innerKeywords = Maps.newHashMap();

	static {
		/*默认中文关键字*/
		innerKeywords.put("如果", "if");
		innerKeywords.put("则", "then");
		innerKeywords.put("否则", "else");
		innerKeywords.put("返回", "return");
	}

	/**
	 * Unprecise.
	 *
	 * @return the rule engine builder
	 */
	public RuleEngineBuilder unprecise() {
		configuration.setPrecise(false);
		return this;
	}

	/**
	 * Trace.
	 *
	 * @return the rule engine builder
	 */
	public RuleEngineBuilder trace() {
		configuration.setTrace(true);
		return this;
	}

	/**
	 * Unuse short circuit.
	 *
	 * @return the rule engine builder
	 */
	public RuleEngineBuilder unuseShortCircuit() {
		configuration.setShortCircuit(false);
		return this;
	}

	/**
	 * Unuse chinese operators.
	 *
	 * @return the rule engine builder
	 */
	public RuleEngineBuilder unuseChineseOperators() {
		configuration.setUseChineseOperators(false);
		return this;
	}

	/**
	 * Unuse chinese keywords.
	 *
	 * @return the rule engine builder
	 */
	public RuleEngineBuilder unuseChineseKeywords() {
		configuration.setShortCircuit(false);
		return this;
	}

	/**
	 * Sets the external operator map.
	 *
	 * @param externalOperators the external operators
	 * @return the rule engine builder
	 */
	public RuleEngineBuilder setExternalOperatorMap(Map<String, String> externalOperators) {
		this.externalOperators = externalOperators;
		return this;
	}

	/**
	 * Sets the external keywords map.
	 *
	 * @param externalKeywords the external keywords
	 * @return the rule engine builder
	 */
	public RuleEngineBuilder setExternalKeywordsMap(Map<String, String> externalKeywords) {
		this.externalKeywords = externalKeywords;
		return this;
	}

	/**
	 * Builds the.
	 *
	 * @return the rule engine
	 * @throws Exception the exception
	 */
	public RuleEngine build() throws Exception {
		RuleEngine engine = new RuleEngine();
		ExpressRunner runner = new ExpressRunner(configuration.isPrecise(), configuration.isTrace());
		runner.setShortCircuit(configuration.isShortCircuit());
		if (configuration.isUseChineseOperators()) {
			//中文操作符
			innerOperators.entrySet().forEach(en -> {
				try {
					runner.addOperatorWithAlias(en.getKey(), en.getValue(), null);
				} catch (Exception e) {
					throw new BaseRuntimeException(e);
				}
			});
			if (externalOperators != null) {
				externalOperators.entrySet().forEach(en -> {
					try {
						runner.addOperatorWithAlias(en.getKey(), en.getValue(), null);
					} catch (Exception e) {
						throw new BaseRuntimeException(e);
					}
				});
			}
		}
		if (configuration.isUseChineseKeywords()) {
			//中文关键字
			innerKeywords.entrySet().forEach(en -> {
				try {
					runner.addOperatorWithAlias(en.getKey(), en.getValue(), null);
				} catch (Exception e) {
					throw new BaseRuntimeException(e);
				}
			});
			if (externalKeywords != null) {
				externalKeywords.entrySet().forEach(en -> {
					try {
						runner.addOperatorWithAlias(en.getKey(), en.getValue(), null);
					} catch (Exception e) {
						throw new BaseRuntimeException(e);
					}
				});
			}
		}
		engine.setOriginalEngine(runner);
		/*内置函数初始化*/
		initInnerFunction(engine);
		return engine;
	}

	/**
	 * Inits the inner function.
	 *
	 * @param engine the engine
	 * @return the rule engine
	 * @throws Exception the exception
	 */
	public RuleEngine initInnerFunction(RuleEngine engine) throws Exception {
		ExpressRunner runner = engine.getOriginalEngine();
		runner.addFunctionOfClassMethod("dateFormat", DateFunc.class.getCanonicalName(), "dateFormat", new String[] { "String", "String", "String" }, null);
		runner.addFunctionOfClassMethod("parseDate", DateFunc.class.getCanonicalName(), "parseDate", new String[] { "String", "String" }, null);
//		runner.addFunctionOfClassMethod("daysBefore", DateFunc.class.getCanonicalName(), "daysBefore", new String[] { Date.class.getName(), Date.class.getName() }, null);
//		runner.addFunctionOfClassMethod("月间隔", DateFunc.class.getCanonicalName(), "monthsBefore", new String[] { Date.class.getName(), Date.class.getName() }, null);
//		runner.addFunctionOfClassMethod("年间隔", DateFunc.class.getCanonicalName(), "yearsBefore", new String[] { Date.class.getName(), Date.class.getName() }, null);
		return engine;
	}
}
