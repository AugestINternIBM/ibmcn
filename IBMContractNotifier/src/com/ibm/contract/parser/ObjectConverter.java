package com.ibm.contract.parser;

import java.util.ArrayList;

public class ObjectConverter {

	private static ArrayList<String[]> filterData = new ArrayList<String[]>();

	private ArrayList<Filter> rulesList = new ArrayList<Filter>();

	public void setFilterData(ArrayList<String[]> filterData) {
		this.filterData = filterData;
	}

	public ArrayList<Filter> parseToObjects() {
		for (int i = 0; i < filterData.size(); i++) {
			String[] data = filterData.get(i);
			String[] vars = data[2].split(",");
			Filter rule = null;
			if (data[3].equalsIgnoreCase("Accepted")) {
				rule = new Filter(data[0], data[1], vars, true);
			} else {
				if (data[3].equalsIgnoreCase("Declined")) {
					rule = new Filter(data[0], data[1], data[2].split(","), false);
				} else
					rule = new Filter(data[0], data[1], vars, false);
			}
			rulesList.add(rule);
		}

		return rulesList;
	}

}
