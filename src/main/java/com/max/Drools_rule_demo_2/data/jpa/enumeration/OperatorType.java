package com.max.Drools_rule_demo_2.data.jpa.enumeration;

public enum OperatorType {
	
	// basic operate ( = != > < >= <=)
	EQUAL, NO_EQUAL, BIGGER, SMALLER, EQUAL_BIGGER, EQUAL_SMALLER,

	// complex operate
	EXISTS_IN, NO_EXISTS_IN, BETWEEN,NO_BETWEEN, LIKE, NO_LIKE, CONTAINS, NO_CONTAINS

}
