package com.ibm.contract.parser;

import java.util.ArrayList;

import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.param.Contracts;

public class FilterRule {
String field_name;
String comparison_type;
boolean include;
ArrayList<String> values;

public FilterRule(String field_name, String comparison_type, boolean include, ArrayList<String> values) {
this.field_name=field_name;
this.comparison_type=comparison_type;
this.include=include;
this.values=values;
}
}