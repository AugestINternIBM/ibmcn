package com.ibm.contract.parser;

import java.util.ArrayList;

public class ExcelFilterBuilder {
	
	private String FILE_PATH;
	private ArrayList<String[]> filterData = new ArrayList<String[]>();
	ArrayList<Filter> rulesList = new ArrayList<Filter>();



	public ExcelFilterBuilder(String FILE_PATH) {
		this.FILE_PATH=FILE_PATH;

	}
	
	public ArrayList<Filter> buildFilters() {
		ExcelParser excelParser = new ExcelParser();
		excelParser.parse(FILE_PATH);
		filterData = excelParser.getFilters();
		ObjectConverter converter = new ObjectConverter();
		converter.setFilterData(filterData);
		rulesList = converter.parseToObjects();
		return rulesList;
	}

}
