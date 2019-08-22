package com.ibm.contract.parser;

import java.util.ArrayList;

public class FilterEngine {
	
	private static String FILE_PATH = "C:/Users/mgabersh/Documents/filters.xlsx";
	private static ArrayList<String[]> filterData = new ArrayList<String[]>();
	private static ArrayList<FilterRule> rulesList = new ArrayList<FilterRule>();



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ExcelParser excelParser = new ExcelParser();
		excelParser.parse(FILE_PATH);
		filterData = excelParser.getFilters();
		ObjectConverter converter = new ObjectConverter();
		converter.setFilterData(filterData);
		rulesList = converter.parseToObjects();
		System.out.println(rulesList.get(0).values[0]);

	}

}
