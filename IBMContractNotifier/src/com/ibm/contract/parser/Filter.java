package com.ibm.contract.parser;

public class Filter {
			String field_name;
			String comparison_type;
			String[] values;
			boolean include;
				

	public Filter(String field_name, String comparison_type, String[] values ,boolean include) {
			this.field_name=field_name;
			this.comparison_type=comparison_type;
			this.values=values;
			this.include=include;
			
	}
}