package com.max.Drools_rule_demo_2.data.jpa.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rule")
public class Rule implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@OneToOne
	@JoinColumn(name="result_id")
	private Result result;
	
	@OneToMany(mappedBy = "rule", cascade = CascadeType.PERSIST)
	private List<RuleUnit> ruleUnitList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public List<RuleUnit> getRuleUnitList() {
		return ruleUnitList;
	}
	public void setRuleUnitList(List<RuleUnit> ruleUnitList) {
		this.ruleUnitList = ruleUnitList;
	}
}
