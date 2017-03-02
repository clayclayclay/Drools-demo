package com.max.Drools_rule_demo_2.data.jpa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.max.Drools_rule_demo_2.data.jpa.domain.Result;
import com.max.Drools_rule_demo_2.data.jpa.domain.ResultUnit;
import com.max.Drools_rule_demo_2.data.jpa.domain.Rule;
import com.max.Drools_rule_demo_2.data.jpa.domain.RuleUnit;
import com.max.Drools_rule_demo_2.data.jpa.enumeration.FieldType;
import com.max.Drools_rule_demo_2.data.jpa.enumeration.OperatorType;
import com.max.Drools_rule_demo_2.data.jpa.repository.ResultRepository;
import com.max.Drools_rule_demo_2.data.jpa.repository.RuleRepository;

@Service
public class BasicRuleService {
	
	
	@Autowired
	private RuleRepository ruleRepositiry;
	@Autowired
	private ResultRepository resultRepository;
	
	List<RuleUnit> ruleUnitList = new ArrayList<RuleUnit>();
	
	Map<String, Rule> ruleMap = new HashMap<String, Rule>();
	
	// 进行规则的创建、保存
	public Rule saveRule() {
		Rule rule = new Rule();

		/**
		 * 此处为 design_time 如果要 run_time 则需要获取"个数"参数
		 */
		RuleUnit ruleUnit1 = new RuleUnit();
		RuleUnit ruleUnit2 = new RuleUnit();
		RuleUnit ruleUnit3 = new RuleUnit();
		RuleUnit ruleUnit4 = new RuleUnit();
		RuleUnit ruleUnit5 = new RuleUnit();

		ruleUnit1.setFieldName("name");
		ruleUnit1.setOperator(OperatorType.EQUAL);
		ruleUnit1.setFieldValue("IBM");
		ruleUnit1.setFieldType(FieldType.STRING);
		ruleUnit1.setRule(rule);

		ruleUnit2.setFieldName("amount");
		ruleUnit2.setOperator(OperatorType.BIGGER);
		ruleUnit2.setFieldValue("2222");
		ruleUnit2.setFieldType(FieldType.INT);
		ruleUnit2.setRule(rule);

		ruleUnit3.setFieldName("date");
		ruleUnit3.setOperator(OperatorType.EQUAL);
		ruleUnit3.setFieldValue("2017-02-02");
		ruleUnit3.setFieldType(FieldType.STRING);
		ruleUnit3.setRule(rule);

		ruleUnit4.setFieldName("type");
		ruleUnit4.setOperator(OperatorType.EQUAL);
		ruleUnit4.setFieldValue("SAP");
		ruleUnit4.setFieldType(FieldType.STRING);
		ruleUnit4.setRule(rule);

		ruleUnit5.setFieldName("cost");
		ruleUnit5.setOperator(OperatorType.EXISTS_IN);
		ruleUnit5.setFieldValue("100,200,300,400,500");
		ruleUnit5.setFieldType(FieldType.LIST);
		ruleUnit5.setRule(rule);

		ruleUnitList.add(ruleUnit1);
		ruleUnitList.add(ruleUnit2);
		ruleUnitList.add(ruleUnit3);
		ruleUnitList.add(ruleUnit4);
		ruleUnitList.add(ruleUnit5);
		
		rule.setRuleUnitList(ruleUnitList);
		rule.setName("rule Demo2");

		ruleMap.put("rule Demo2", rule);
		
		Result result = new Result();
		result.setName("result 1");
		ResultUnit resultUnit1 = new ResultUnit();
		resultUnit1.setFieldName("entitlementType");
		resultUnit1.setFieldType(FieldType.STRING);
		resultUnit1.setFieldValue("TRAINING");
		resultUnit1.setResult(result);

		ResultUnit resultUnit2 = new ResultUnit();
		resultUnit2.setFieldName("documentInfo");
		resultUnit2.setFieldType(FieldType.DOCUMENTINFO);
		resultUnit2.setFieldValue("DocumentInfo");
		resultUnit2.setResult(result);

		ResultUnit resultUnit3 = new ResultUnit();
		resultUnit3.setFieldName("documentInfoList");
		resultUnit3.setFieldType(FieldType.DOCUMENTINFOLIST);
		resultUnit3.setFieldValue("documentInfoList");
		resultUnit3.setResult(result);

		List<ResultUnit> resultUnitList = new ArrayList<ResultUnit>();
		resultUnitList.add(resultUnit1);
		resultUnitList.add(resultUnit2);
		resultUnitList.add(resultUnit3);

		result.setResultUnitList(resultUnitList);
		rule.setResult(result);
		resultRepository.save(result);
		return ruleRepositiry.save(rule);
	}
	
	public List<Rule> getRuleList() {
		Iterable<Rule> ruleIt = ruleRepositiry.findAll();
		Iterator<Rule> ruleIte = ruleIt.iterator();
		List<Rule> ruleList = new ArrayList<Rule>();
		while (ruleIte.hasNext()) {
			Rule rule = ruleIte.next();
			ruleList.add(rule);
			ruleMap.put(rule.getName(), rule);
		}
		return ruleList;
	}

	public Map<String, Rule> getRuleMap() {
		return ruleMap;
	}
	
	

}
