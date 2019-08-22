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
import java.util.Arrays;
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

    private ArrayList<FilterRule> filtersArr = new ArrayList<>(); 

    public List getContractListFromExcel(String FILE_PATH , Map<String, Feedback> feedbackMap) {

        this.feedbackMap = feedbackMap;
        initFiltersArr();

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
//TODO: add function to fill the filters array
    private void initFiltersArr(){
    	
//        this.filtersArr[0] = new FilterRule("Div Code", "equal", true,new ArrayList<String>(Arrays.asList("7H", "K4", "7G", "8E")));
//        this.filtersArr[1] = new FilterRule("OppName", "contain", false,new ArrayList<String>(Arrays.asList("sales order")));
//        this.filtersArr[2] = new FilterRule("SignProbability", "equal", true,new ArrayList<String>(Arrays.asList("1.0")));
//        this.filtersArr[3] = new FilterRule("IMT", "equal", true,new ArrayList<String>(Arrays.asList("MEA")));
//        this.filtersArr[4] = new FilterRule("AccountID", "equal", false,new ArrayList<String>(Arrays.asList("TBD")));
//        this.filtersArr[5] = new FilterRule("Backlog", "contain", false,new ArrayList<String>());
//        this.filtersArr[6] = new FilterRule("Country", "contain", false,new ArrayList<String>());
//        this.filtersArr[7] = new FilterRule("TCV", "contain", false,new ArrayList<String>());
    }
    
    private boolean contains(String[] filtervals, String v, String operation){
        if (operation == "equal"){
            for (int i = 0; i < filtervals.length; i++) {
                if (filtervals[i].equals(v))
                    return true;
            }
        } else {
            for (int i = 0; i < filtervals.length; i++) {
                if (filtervals[i].contains(v))
                    return true;
            }
        }
        return false;
    }

    private void addContractData (String fieldName){
        switch(fieldName) {
            case "AccountID":
                this.contract.setAccoundID(this.row.getCell(this.columnsHeaderMap.get("AccountID")).toString());
                break;
            case "TCV":
                this.contract.setTcv(this.row.getCell(this.columnsHeaderMap.get("TCV")).getNumericCellValue());
                break;
            case "Backlog":
                this.contract.setBackLog(this.row.getCell(this.columnsHeaderMap.get("Backlog")).getNumericCellValue());
                break;
            case "Country":
                this.contract.setCountry(this.row.getCell(this.columnsHeaderMap.get("Country")).toString());
                break;
            default:
                return;
        }
    }
    private boolean initContract() {
        this.contract = new Contract();
        for (int i = 0; i < filtersArr.size(); i++) {
        	Cell val = null;
        	try{
                val = this.row.getCell(this.columnsHeaderMap.get(filtersArr.get(i).field_name));
        	} catch (Exception e){
        		System.out.println("Column with field name  "  + filtersArr.get(i).field_name +" not found" );
        		System.exit(0);
        	}
            if (val == null || val.toString() == "" || val.toString() == "NULL") {
                return false;
            }
            String strVal = val.toString();
            if (filtersArr.get(i).values.length == 0){
                addContractData(filtersArr.get(i).field_name);
            } else if (filtersArr.get(i).include) {
                if (contains(filtersArr.get(i).values, strVal, filtersArr.get(i).comparison_type)){
                    addContractData(filtersArr.get(i).field_name);
                } else {
                    return false;
                }
            } else {
                if (contains(filtersArr.get(i).values, strVal, filtersArr.get(i).comparison_type)) {
                    return false;
                } else {
                    addContractData(filtersArr.get(i).field_name);
                }
            }
        }

        Double[] pv = new Double[12];
        String pvStr = "PV_";
        for (int i = 1; i < 13; i++) {
            if (i <= 9) {
                if (this.row.getCell(this.columnsHeaderMap.get(pvStr + "0" + i)) == null) {
                    return false;
                } else {
                    pv[i - 1] = this.row.getCell(this.columnsHeaderMap.get(pvStr + "0" + i)).getNumericCellValue();
                }
            } else {
                if (this.row.getCell(this.columnsHeaderMap.get(pvStr + i)) == null) {
                    return false;
                } else {
                    pv[i - 1] = this.row.getCell(this.columnsHeaderMap.get(pvStr + i)).getNumericCellValue();
                }
            }
        }

        int month = Calendar.getInstance().get(Calendar.MONTH);
        this.contract.setCurrentPv(pv[month-1]);

        String[] candidates = this.contract.getAccoundID().split(",");
        int count = 0;
        for (String candidate : candidates) {
            if (feedbackMap.containsKey(candidate)) {
                count = count + this.feedbackMap.get(candidate).getNo_of_feedbacks();
                this.contract.setNumberOfFeedbacks(this.feedbackMap.get(candidate).getNo_of_feedbacks());
            }
        }
        this.contract.setNumberOfFeedbacks(count);

        double year = (double)Calendar.getInstance().get(Calendar.YEAR);
        if (this.row.getCell(this.columnsHeaderMap.get("Yr")) != null &&this.row.getCell(this.columnsHeaderMap.get("Yr")).getNumericCellValue() != year||
                this.row.getCell(this.columnsHeaderMap.get("Yr")).toString().equals("")){
            return false;
        }

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