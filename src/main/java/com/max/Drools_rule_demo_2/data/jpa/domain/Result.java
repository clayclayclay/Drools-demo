package com.max.Drools_rule_demo_2.data.jpa.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "result")
public class Result {
	
	@Id
	@GeneratedValue
	private int id;
	@Column(nullable = false)
	private String name;
	@OneToMany(mappedBy = "result", cascade = CascadeType.PERSIST)
	private List<ResultUnit> resultUnitList;
	
	
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
	public List<ResultUnit> getResultUnitList() {
		return resultUnitList;
	}
	public void setResultUnitList(List<ResultUnit> resultUnitList) {
		this.resultUnitList = resultUnitList;
	}
}
