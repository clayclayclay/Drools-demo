package com.max.Drools_rule_demo_2.data.jpa.domain;

import java.util.List;

import com.max.Drools_rule_demo_2.data.jpa.enumeration.EntitlementType;

public class EntitlementInfo {
	
	private int id;
	private String name;
//	private EntitlementType entitlementType;
	private String entitlementType;
	private DocumentInfo documentInfo;
	private List<DocumentInfo> documentInfoList;
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
	
	public String getEntitlementType() {
		return entitlementType;
	}
	public void setEntitlementType(String entitlementType) {
		this.entitlementType = entitlementType;
	}
	public DocumentInfo getDocumentInfo() {
		return documentInfo;
	}
	public void setDocumentInfo(DocumentInfo documentInfo) {
		this.documentInfo = documentInfo;
	}
	public List<DocumentInfo> getDocumentInfoList() {
		return documentInfoList;
	}
	public void setDocumentInfoList(List<DocumentInfo> documentInfoList) {
		this.documentInfoList = documentInfoList;
	}
	
	

}
