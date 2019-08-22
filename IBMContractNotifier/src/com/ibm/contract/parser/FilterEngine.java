package com.ibm.contract.parser;

import java.util.ArrayList;

public class FilterEngine {
	
	private static String FILE_PATH = "FilterRules.xlsx";
	private static ArrayList<String[]> filterData = new ArrayList<String[]>();
	static ArrayList<FilterRule> rulesList = new ArrayList<FilterRule>();



	public FilterEngine() {
		// TODO Auto-generated method stub

		ExcelParser excelParser = new ExcelParser();
		excelParser.parse(FILE_PATH);
		filterData = excelParser.getFilters();
		ObjectConverter converter = new ObjectConverter();
		converter.setFilterData(filterData);
		rulesList = converter.parseToObjects();

	}

}
