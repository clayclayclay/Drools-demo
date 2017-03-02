package com.max.Drools_rule_demo_2.data.jpa.global;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.max.Drools_rule_demo_2.data.jpa.domain.DocumentInfo;
import com.max.Drools_rule_demo_2.data.jpa.domain.EntitlementInfo;
import com.max.Drools_rule_demo_2.data.jpa.domain.ResultUnit;
import com.max.Drools_rule_demo_2.data.jpa.domain.Rule;
import com.max.Drools_rule_demo_2.data.jpa.domain.RuleUnit;
import com.max.Drools_rule_demo_2.data.jpa.enumeration.FieldType;
import com.max.Drools_rule_demo_2.data.jpa.service.BasicRuleService;
import com.max.Drools_rule_demo_2.data.jpa.service.MainService;

@Service
public class GlobaleEntitlementHelper {

	@Autowired
	private BasicRuleService bRuleService;
	
	@Autowired
	private MainService mainService;

	private List<Rule> ruleList;

	private Rule targetResult;
	
	List<DocumentInfo> documentInfoList = new ArrayList<DocumentInfo>();

	private EntitlementInfo entitlementInfo = new EntitlementInfo();

	public void initRuleList() {
		ruleList = bRuleService.getRuleList();
	}

	public void setEntitlementProperty(String ruleName, String fieldName) {
		if (ruleList == null) {
			initRuleList();
		}

		if (targetResult == null) {
			targetResult = bRuleService.getRuleMap().get(ruleName);
		}

		List<ResultUnit> resultUnitList = targetResult.getResult().getResultUnitList();
		System.out.println("the fieldName is :" + fieldName);

		for (ResultUnit unit : resultUnitList) {
			System.out.println("the unit fieldName is :" + unit.getFieldName());
			if (unit.getFieldName().equals(fieldName)) {
				String method = "set" + unit.getFieldName().substring(0, 1).toUpperCase()
						+ unit.getFieldName().substring(1, unit.getFieldName().length());
				try {
					Class<?> type = getParameterType(unit);
					entitlementInfo.getClass().getMethod(method, type).invoke(entitlementInfo, getParameterValue(unit));
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
			// System.out.println("the name is:" + doc.getName());
			// System.out.println("the type is :" + doc.getType());
		}
	}

	private Class<?> getParameterType(ResultUnit unit) {
		switch (unit.getFieldType()) {
		case STRING:
			return String.class;
		case INT:
			return int.class;
		case DATE:
			return Date.class;
		case LIST:
			return List.class;
		case DOCUMENTINFO:
			return DocumentInfo.class;
		case DOCUMENTINFOLIST:
			return List.class;
		default:
			return null;
		}
	}

	private Object getParameterValue(ResultUnit unit) {
		
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
		case ENUM:
			return null;
		case DOCUMENTINFO:
			return mainService.getDocumentInfo1();
		case DOCUMENTINFOLIST:
			documentInfoList.add(mainService.getDocumentInfo1());
			System.out.println("the size is :" + documentInfoList.size());
			return documentInfoList;
		default:
			return null;
			
		}
	}

	public EntitlementInfo getEntitlementInfo() {
		return entitlementInfo;
	}
	
	
	
	
}
