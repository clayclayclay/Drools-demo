package com.max.Drools_rule_demo_2.data.jpa.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.max.Drools_rule_demo_2.data.jpa.domain.EntitlementInfo;
import com.max.Drools_rule_demo_2.data.jpa.domain.Rule;
import com.max.Drools_rule_demo_2.data.jpa.service.BasicRuleService;
import com.max.Drools_rule_demo_2.data.jpa.service.MainService;
import com.max.Drools_rule_demo_2.data.jpa.util.RuleStrUtil;

@Controller
public class DroolsDemoTest1Controller {
	
	
	@Autowired
	private MainService mainSevice;
	@Autowired
	private BasicRuleService bRuleService;
	
	@RequestMapping("/processRule")
	@ResponseBody
	public EntitlementInfo droosTest1() {
		return mainSevice.SimpleRuleProcess();
	}
	
	@RequestMapping("/saveRule")
	@ResponseBody
	public String saveRule() {
		Rule rule = bRuleService.saveRule();
		if (rule != null) {
			return "success"; 
		}
		else {
			return "failed";
		}
	}
	
	@RequestMapping("/getRule")
	@ResponseBody
	public List<String> getRule() {
		List<String> rule = RuleStrUtil.getRuleStrList(bRuleService.getRuleList());
		if (rule != null) {
			return rule; 
		}
		else {
			return null;
		}
	}
	
	

}
