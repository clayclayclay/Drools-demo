package com.max.Drools_rule_demo_2.data.jpa.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.max.Drools_rule_demo_2.data.jpa.domain.Result;
import com.max.Drools_rule_demo_2.data.jpa.domain.ResultUnit;
import com.max.Drools_rule_demo_2.data.jpa.domain.Rule;
import com.max.Drools_rule_demo_2.data.jpa.domain.RuleUnit;

public class RuleStrUtil {

	// 获得拼接而成的字符串rule
	public static List<String> getRuleStrList(List<Rule> ruleList) {
		Iterator<Rule> ruleIterator = ruleList.iterator();
		List<String> ruleStrList = new ArrayList<String>();
		while (ruleIterator.hasNext()) {
			ruleStrList.add(getRuleStr(ruleIterator.next()));
		}
		return ruleStrList;
	}

	// 通过解析组合RuleUnit对象来获得字符串rule
	private static String getRuleStr(Rule rule) {
		StringBuffer ruleStr = new StringBuffer();
		List<RuleUnit> ruleUnitList = rule.getRuleUnitList();
		String ruleName = "rule \"" + rule.getName() + "\"\n";
		String expressionWhenStr = null;
		StringBuffer expressionThen = new StringBuffer();
		String expressionThenStr = null;
		// String result = rule.getResult();
		String result = null;
		ruleStr.append(getPrefix());
		ruleStr.append(ruleName);

		ruleStr.append("when\n" + "$doc : DocumentInfo(");
		expressionWhenStr = getWhenStr(rule);
		ruleStr.append(expressionWhenStr);
		ruleStr.append(")\n");
		
		expressionThenStr = getThenStr(rule.getResult(), rule);
		ruleStr.append(expressionThenStr);

		ruleStr.append("end\n");

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> the rule is :");
		System.out.println(ruleStr.toString());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return ruleStr.toString();

	}

	/*
	 * 获得rule规则的一些前缀，包括： import global declare function等 下一步考虑将这些前缀进行持久化存储。
	 */
	private static String getPrefix() {
		StringBuffer prefixStr = new StringBuffer();

		// package prefix
		String packageStr = "package com.max.rule\n\n";

		// import class prefix
		List<String> importClassList = new ArrayList<String>();
		importClassList.add("import com.max.Drools_rule_demo_2.data.jpa.domain.*;\n");
		importClassList.add("import java.util.List;\n");
		importClassList.add("import java.util.ArrayList;\n\n");

		// import Function prefix
		List<String> importFuncList = new ArrayList<String>();
		importFuncList.add(
				"import function com.max.Drools_rule_demo_2.data.jpa.functions.DocumentInfoHelper.getDocByRule;\n\n");

		// global variable prefix
		List<String> globalList = new ArrayList<String>();
		globalList.add("global com.max.Drools_rule_demo_2.data.jpa.domain.EntitlementInfo entitlementInfo;\n"
				+ "global com.max.Drools_rule_demo_2.data.jpa.global.GlobaleEntitlementHelper helper;\n\n");
		// globalList.add("global java.lang.String[] cost;\n");
		// globalList.add("global java.util.List cost;\n\n");
		prefixStr.append(packageStr);

		for (String str : importClassList) {
			prefixStr.append(str);
		}

		for (String str : importFuncList) {
			prefixStr.append(str);
		}

		for (String str : globalList) {
			prefixStr.append(str);
		}

		return prefixStr.toString();
	}

	// 构造 规则中的 "When语句"
	private static String getWhenStr(Rule rule) {
		StringBuffer expressionBuilder = new StringBuffer();
		List<RuleUnit> ruleUnitList = rule.getRuleUnitList();
		int size = 0;
		for (RuleUnit unit : ruleUnitList) {
			String getMethodParameter = "(\"" + rule.getName() + "\"," + "\"" + unit.getFieldName() + "\").";
			String getMethod = "getDocByRule" + getMethodParameter;
			getMethod = getMethod + unit.getFieldName();
			String fieldName = unit.getFieldName();
			String expression = null;
			switch (unit.getOperator()) {
			case EQUAL:
				expression = fieldName + " == " + getMethod + ",";
				break;
			case NO_EQUAL:
				expression = fieldName + " != " + getMethod + ",";
				break;
			case BIGGER:
				expression = fieldName + " > " + getMethod + ",";
				break;
			case SMALLER:
				expression = fieldName + " < " + getMethod + ",";
				break;
			case EQUAL_BIGGER:
				expression = fieldName + " >= " + getMethod + ",";
				break;
			case EQUAL_SMALLER:
				expression = fieldName + " <= " + getMethod + ",";
				break;
			case CONTAINS:
				expression = fieldName + " contains " + getMethod + ",";
				break;
			case NO_CONTAINS:
				expression = fieldName + " not contains " + getMethod + ",";
				break;
			case EXISTS_IN:
				expression = fieldName + ".get(0)" + " memberOf " + getMethod + ",";
				break;
			case NO_EXISTS_IN:
				expression = fieldName + " not memberOf " + getMethod + ",";
				break;
			case BETWEEN:
				break;
			case NO_BETWEEN:
				break;
			case LIKE:
				break;
			case NO_LIKE:
				break;
			default:
				break;
			}
			size++;
			if (size == ruleUnitList.size()) {
				expression = expression.substring(0, expression.length() - 1);
			}
			expressionBuilder.append(expression);
		}
		return expressionBuilder.toString();
	}

	// 构造 规则中的 "Then语句"
	private static String getThenStr(Result result, Rule rule) {
		List<ResultUnit> resultUnitList = result.getResultUnitList();
		StringBuffer thenStr = new StringBuffer();
		thenStr.append("then\n");
		String setStr = null;
		for (ResultUnit unit : resultUnitList) {
			setStr = "helper.setEntitlementProperty(\"" + rule.getName() + "\",\"" + unit.getFieldName() + "\");\n";
			thenStr.append(setStr);
			setStr = null;
		}
		return thenStr.toString();
	}
}
