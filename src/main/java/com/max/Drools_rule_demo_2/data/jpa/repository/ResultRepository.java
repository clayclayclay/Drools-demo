package com.max.Drools_rule_demo_2.data.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.max.Drools_rule_demo_2.data.jpa.domain.Result;

@Repository
public interface ResultRepository extends CrudRepository<Result, Integer>{
	
}
