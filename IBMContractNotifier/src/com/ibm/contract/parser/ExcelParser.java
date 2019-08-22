package com.ibm.contract.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelParser {

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
	
	ArrayList<String[]> myList = new ArrayList<String[]>();

		
	
	public void parse (String FILE_PATH){
		try {
			fileInputStream = new FileInputStream(FILE_PATH);

			// Using XSSF for xlsx format, for xls use HSSF
			workbook = new XSSFWorkbook(fileInputStream);

			numberOfSheets = workbook.getNumberOfSheets();

			sheet = workbook.getSheetAt(0);
			rowIterator = sheet.iterator();

			// Init header map
			this.initColumnsHeaderMap();

			// iterating over each row and start from the second one
			this.rowIterator.next();
			int s=0;
			while (this.rowIterator.hasNext()) {
					s++;
					this.colNum = this.sheet.getRow(0).getLastCellNum();
					this.row = (Row) this.rowIterator.next();
					String[] rowContent = new String[4];
					for(int k=0 ; k<colNum ; k++) {
						if (row.getCell(k)==null){
							rowContent[k] = "null";
						} else {
							rowContent[k] = row.getCell(k).toString();
						}
					}
					myList.add(rowContent);
			}
			

			this.fileInputStream.close();

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"Excel file not found! \n"
					+ "Please reinstall the program.", "Error 404!" ,JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initColumnsHeaderMap() {
		this.colNum = this.sheet.getRow(0).getLastCellNum();
		if (this.sheet.getRow(0).cellIterator().hasNext()) {
			for (int j = 0; j < this.colNum; j++) {
				this.columnsHeaderMap.put((this.sheet.getRow(0).getCell(j).toString()), j);
			}
		}
	}
	
	public ArrayList<String[]> getFilters() {
		return myList;
	}

}
