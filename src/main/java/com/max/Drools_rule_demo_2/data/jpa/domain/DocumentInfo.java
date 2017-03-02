package com.max.Drools_rule_demo_2.data.jpa.domain;

import java.util.List;

public class DocumentInfo {
	
	private int id;
	
	private String name;
	
	private int amount;

	private String date;
	
	private String type;
	
	private List<String> cost;
	
	private String result;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<String> getCost() {
		return cost;
	}

	public void setCost(List<String> cost) {
		this.cost = cost;
	}
}
