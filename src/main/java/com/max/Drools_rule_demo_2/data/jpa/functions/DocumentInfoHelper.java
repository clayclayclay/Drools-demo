package com.max.Drools_rule_demo_2.data.jpa.functions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.max.Drools_rule_demo_2.data.jpa.domain.DocumentInfo;
import com.max.Drools_rule_demo_2.data.jpa.domain.Rule;
import com.max.Drools_rule_demo_2.data.jpa.domain.RuleUnit;
import com.max.Drools_rule_demo_2.data.jpa.enumeration.FieldType;
import com.max.Drools_rule_demo_2.data.jpa.repository.RuleRepository;
import com.max.Drools_rule_demo_2.data.jpa.service.BasicRuleService;

@Component
public class DocumentInfoHelper {

	@Autowired
	private BasicRuleService bRuleService;

	private static DocumentInfoHelper docHelper;

	private static DocumentInfo doc = new DocumentInfo();

	@PostConstruct
	public void init() {
		System.out.println("the DocumentInfoHelper is inited");
		docHelper = this;
	}

	public static DocumentInfo getDocByRule(String ruleName, String fieldName) {
		Rule rule = docHelper.bRuleService.getRuleMap().get(ruleName);
		List<RuleUnit> ruleUnitList = rule.getRuleUnitList();
		for (RuleUnit unit : ruleUnitList) {
			if (unit.getFieldName().equals(fieldName)) {
				String method = "set" + unit.getFieldName().substring(0, 1).toUpperCase()
						+ unit.getFieldName().substring(1, unit.getFieldName().length());
				try {
					Class<?> type = getParameterType(unit.getFieldType());
					doc.getClass().getMethod(method, type).invoke(doc, getParameterValue(unit));
					break;
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return doc;
	}

	private static Class<?> getParameterType(FieldType fieldType) {
		switch (fieldType) {
		case STRING:
			return String.class;
		case INT:
			return int.class;
		case DATE:
			return Date.class;
		case LIST:
			return List.class;
		default:
			return null;
		}
	}

	private static Object getParameterValue(RuleUnit unit) {
		switch (unit.getFieldType()) {
		case STRING:
			return unit.getFieldValue();
		case INT:
			return Integer.valueOf(unit.getFieldValue());
		// case DATE:
		// return Date.class;
		case LIST:
			List<String> list = Arrays.asList(unit.getFieldValue().split(","));
			System.out.println("the list length is :" + list.size());
			return list;
		default:
			return null;
		}
	}

}
