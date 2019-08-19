package com.ibm.contract.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Contract;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Parser {

	private ArrayList<Contract> contractsList = new ArrayList<Contract>();
	private Contract contract;

	private FileInputStream fileInputStream;
	private Workbook workbook;
	private int numberOfSheets;
	private Sheet sheet;
	private Row row;
	private Cell cell;

	private Iterator rowIterator;
	private Iterator cellIterator;

	private Map<String, Integer> columnsHeaderMap = new HashMap<String, Integer>();
	private int colNum;
	
	private Map<String, Feedback> feedbackMap = new HashMap<String, Feedback>();

	public List getContractListFromExcel(String FILE_PATH , Map<String, Feedback> feedbackMap) {
		
		this.feedbackMap = feedbackMap;

		try {
			this.fileInputStream = new FileInputStream(FILE_PATH);

			// Using XSSF for xlsx format, for xls use HSSF
			this.workbook = new XSSFWorkbook(fileInputStream);

			this.numberOfSheets = this.workbook.getNumberOfSheets();

			// looping over each workbook sheet
			for (int i = 0; i < this.numberOfSheets; i++) {
				this.sheet = this.workbook.getSheetAt(i);
				this.rowIterator = this.sheet.iterator();

				// Init header map
				this.initColumnsHeaderMap();

				// iterating over each row and start from the second one
				this.rowIterator.next();
				while (this.rowIterator.hasNext()) {

					this.row = (Row) this.rowIterator.next();

					if(this.initContract()) {
						this.contractsList.add(this.contract);
					}
				}
			}

			this.fileInputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.contractsList;
	}

	private void initColumnsHeaderMap() {
		this.colNum = this.sheet.getRow(0).getLastCellNum();
		if (this.sheet.getRow(0).cellIterator().hasNext()) {
			for (int j = 0; j < this.colNum; j++) {
				this.columnsHeaderMap.put((this.sheet.getRow(0).getCell(j).toString()), j);
			}
		}
	}

	
	
	private boolean initContract() {
		this.contract = new Contract();

		if (this.row.getCell(this.columnsHeaderMap.get("AccountID")) == null||
				this.row.getCell(this.columnsHeaderMap.get("AccountID")).toString().equals("")||
				this.row.getCell(this.columnsHeaderMap.get("AccountID")).toString().equals("TBD")||
				this.row.getCell(this.columnsHeaderMap.get("AccountID")).toString().equals("NULL")) {
			return false;
		} else {
			this.contract.setAccoundID(this.row.getCell(this.columnsHeaderMap.get("AccountID")).toString());
		}

		if (this.row.getCell(this.columnsHeaderMap.get("TCV (k$)")) == null||
				this.row.getCell(this.columnsHeaderMap.get("TCV (k$)")).toString().equals("")) {
			return false;
		} else {
			String tcvValue=this.row.getCell(this.columnsHeaderMap.get("TCV (k$)")).toString();
			this.contract.setTcv(Double.parseDouble(tcvValue));
		}
		
		// TODO : Bind pv columns
		Double[] pv = new Double[4];
		if (this.row.getCell(this.columnsHeaderMap.get("PV Q1")) == null) {
			return false;
		} else {
			String pv1 = this.row.getCell(this.columnsHeaderMap.get("PV Q1")).toString();
			pv[0] = Double.parseDouble(pv1);
		}
		if (this.row.getCell(this.columnsHeaderMap.get("PV Q2")) == null) {
			return false;
		} else {
			String pv2 = this.row.getCell(this.columnsHeaderMap.get("PV Q2")).toString();
			pv[1] = Double.parseDouble(pv2);
		}
		if (this.row.getCell(this.columnsHeaderMap.get("PV Q3")) == null) {
			return false;
		} else {
			String pv3 = this.row.getCell(this.columnsHeaderMap.get("PV Q3")).toString();
			pv[2] = Double.parseDouble(pv3);
		}
		if (this.row.getCell(this.columnsHeaderMap.get("PV Q4")) == null) {
			return false;
		} else {
			String pv4 = this.row.getCell(this.columnsHeaderMap.get("PV Q4")).toString();
			pv[3] = Double.parseDouble(pv4);
		}
//		if (this.row.getCell(this.columnsHeaderMap.get("PV_05")) == null) {
//			return false;
//		} else {
//			pv[4] = this.row.getCell(this.columnsHeaderMap.get("PV_05")).getNumericCellValue();
//		}
//		if (this.row.getCell(this.columnsHeaderMap.get("PV_06")) == null) {
//			return false;
//		} else {
//			pv[5] = this.row.getCell(this.columnsHeaderMap.get("PV_06")).getNumericCellValue();
//		}
//		if (this.row.getCell(this.columnsHeaderMap.get("PV_07")) == null) {
//			return false;
//		} else {
//			pv[6] = this.row.getCell(this.columnsHeaderMap.get("PV_07")).getNumericCellValue();
//		}
//		if (this.row.getCell(this.columnsHeaderMap.get("PV_08")) == null) {
//			return false;
//		} else {
//			pv[7] = this.row.getCell(this.columnsHeaderMap.get("PV_08")).getNumericCellValue();
//		}
//		if (this.row.getCell(this.columnsHeaderMap.get("PV_09")) == null) {
//			return false;
//		} else {
//			pv[8] = this.row.getCell(this.columnsHeaderMap.get("PV_09")).getNumericCellValue();
//		}
//		if (this.row.getCell(this.columnsHeaderMap.get("PV_10")) == null) {
//			return false;
//		} else {
//			pv[9] = this.row.getCell(this.columnsHeaderMap.get("PV_10")).getNumericCellValue();
//		}
//		if (this.row.getCell(this.columnsHeaderMap.get("PV_11")) == null) {
//			return false;
//		} else {
//			pv[10] = this.row.getCell(this.columnsHeaderMap.get("PV_11")).getNumericCellValue();
//		}
//		if (this.row.getCell(this.columnsHeaderMap.get("PV_12")) == null) {
//			return false;
//		} else {
//			pv[11] = this.row.getCell(this.columnsHeaderMap.get("PV_12")).getNumericCellValue();
//		}
		
		//this.contract.setPv(pv);
		/**adding recently update**/
		if(this.row.getCell(this.columnsHeaderMap.get("Customer Name")) != null){
			if(this.row.getCell(this.columnsHeaderMap.get("Customer Name")).toString().contains("Sales Order")){
				return false;
			}
		}
		if(this.row.getCell(this.columnsHeaderMap.get("BL Status")) != null ){
			if(this.row.getCell(this.columnsHeaderMap.get("BL Status")).toString().equals("Eroded")||
					this.row.getCell(this.columnsHeaderMap.get("BL Status")).toString().equals("Closed")){
		
			return false;
		}
		}		
		/***/	
		
		if (this.row.getCell(this.columnsHeaderMap.get("Bcklg (k$)")) == null||
				this.row.getCell(this.columnsHeaderMap.get("Bcklg (k$)")).toString().equals("")) {
			return false;
		} else {
			String blVal = this.row.getCell(this.columnsHeaderMap.get("Bcklg (k$)")).toString();
			this.contract.setBackLog(Double.parseDouble(blVal));
		}
		
		String[] candidates = this.contract.getAccoundID().split(",");
		int count = 0;

		for (String candidate : candidates) {
			if (feedbackMap.containsKey(candidate)) {
				count = count + this.feedbackMap.get(candidate).getNo_of_feedbacks();
				this.contract.setNumberOfFeedbacks(this.feedbackMap.get(candidate).getNo_of_feedbacks());
			}
		}
		
		this.contract.setNumberOfFeedbacks(count);

		int month = Calendar.getInstance().get(Calendar.MONTH);
		int cPV = ((month-1)/4)-1;
		this.contract.setCurrentPv(pv[cPV]);
//		if(cPV == -1){
//			this.contract.setCurrentPv(pv[month-1]);
//		}else {
//			this.contract.setCurrentPv(pv[cPV]);
//		}

		
		
		/////////////////////////////////////////////////////////
		int year = Calendar.getInstance().get(Calendar.YEAR);

		if (this.row.getCell(this.columnsHeaderMap.get("Year")) != null && Integer.parseInt(this.row.getCell(this.columnsHeaderMap.get("Year")).toString()) != year||
				this.row.getCell(this.columnsHeaderMap.get("Year")).toString().equals("")){
			return false;

		} 
		
		if(this.row.getCell(this.columnsHeaderMap.get("IOT")) != null &&!(this.row.getCell(this.columnsHeaderMap.get("IOT")).toString().equals("MEA"))||
				this.row.getCell(this.columnsHeaderMap.get("IOT")).toString().equals("")){
			return false;

		}
		
		if(this.row.getCell(this.columnsHeaderMap.get("Div")) != null &&!(this.row.getCell(this.columnsHeaderMap.get("Div")).toString().contains("7H")||
				this.row.getCell(this.columnsHeaderMap.get("Div")).toString().contains("K4")||
				this.row.getCell(this.columnsHeaderMap.get("Div")).toString().contains("7G")||
				this.row.getCell(this.columnsHeaderMap.get("Div")).toString().contains("8E"))){
			return false;

		}
		
		/*not checked*/
		//System.out.println();
		if(this.row.getCell(this.columnsHeaderMap.get("Signing Prob %")) != null && (!(this.row.getCell(this.columnsHeaderMap.get("Signing Prob %")).toString().equals("1.0"))||
				!(this.row.getCell(this.columnsHeaderMap.get("Signing Prob %")).toString().equals("signed"))||
				this.row.getCell(this.columnsHeaderMap.get("Signing Prob %")).toString().equals(""))){
			return false;

		}

		
		if(this.row.getCell(this.columnsHeaderMap.get("OppName")) != null && (this.row.getCell(this.columnsHeaderMap.get("OppName")).toString().contains("Sales Order")||
				this.row.getCell(this.columnsHeaderMap.get("OppName")).toString().equals(""))){
			return false;

		}
		
		if (this.row.getCell(this.columnsHeaderMap.get("Country")) == null||
				this.row.getCell(this.columnsHeaderMap.get("Country")).toString().equals("")) {
			return false;
		} else {
			this.contract.setCountry(this.row.getCell(this.columnsHeaderMap.get("Country")).toString());
		}
		/*
		if(this.feedbackMap.get(this.contract.getAccoundID()) != null){
			this.contract.setProjectName(this.feedbackMap.get(this.contract.getAccoundID()).getProject_name());
		
			this.contract.setProjectDescription(this.feedbackMap.get(this.contract.getAccoundID()).getProject_description());
		}
		else{
			this.contract.setProjectName("");
			
			this.contract.setProjectDescription("");
		
		}
		*/
		String CustomerName="";
		String oppName="";
		if (this.row.getCell(this.columnsHeaderMap.get("Customer Name")) != null) {
			CustomerName=this.row.getCell(this.columnsHeaderMap.get("Customer Name")).toString().replaceAll("\u001a", " ");
		} 
		if (this.row.getCell(this.columnsHeaderMap.get("OppName")) != null) {
			oppName=this.row.getCell(this.columnsHeaderMap.get("OppName")).toString().replaceAll("\u001a", " ");
		} 
		this.contract.setProjectName(CustomerName+ " "+oppName+ " ("+this.contract.getAccoundID()+")");
		
		return true;
	}
}
