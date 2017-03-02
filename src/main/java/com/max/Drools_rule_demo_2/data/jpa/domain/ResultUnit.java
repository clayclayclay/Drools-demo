package com.max.Drools_rule_demo_2.data.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.max.Drools_rule_demo_2.data.jpa.enumeration.FieldType;
import com.max.Drools_rule_demo_2.data.jpa.enumeration.OperatorType;

@Entity
@Table(name = "result_unit")
public class ResultUnit {
	
	@Id
	@GeneratedValue
	private int id;
	@Column(nullable = false)
	private String fieldName;
	@Column(nullable = false)
	private String fieldValue;
	@Column(nullable = false)
	private FieldType fieldType;
	@ManyToOne
	@JoinColumn(name = "result_id")
	private Result result;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public FieldType getFieldType() {
		return fieldType;
	}
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
