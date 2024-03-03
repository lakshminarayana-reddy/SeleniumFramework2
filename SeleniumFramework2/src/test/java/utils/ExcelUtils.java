package utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ExcelUtils {
    public static ArrayList<Object> getRowData(String sheetName, int rowIndex) {
        ArrayList<Object> cellValues = new ArrayList<Object>();
        String filePath = ConfigManager.getExcelPath();
        File file = new File(filePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(fis);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        HSSFSheet sheet = workbook.getSheet(sheetName);
        HSSFRow row = sheet.getRow(rowIndex);
        int cellCount = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < cellCount; i++) {
            Cell c = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (c == null) {
                c = row.createCell(i);
                c.setBlank();
            }
        }

        Iterator<Cell> cells = row.cellIterator();
        while (cells.hasNext()) {
            String cellValue;
            HSSFCell cell = (HSSFCell) cells.next();
            CellType type = cell.getCellType();
            if (cell.getCellType() == CellType.BLANK) {
                cellValues.add(null);
            } else if (type == CellType.STRING && cell.getStringCellValue().isEmpty()) {
                cellValues.add(null);
            } else if (type == CellType.STRING) {
                cellValue = cell.getStringCellValue();
                cellValues.add(cellValue);
            } else if (DateUtil.isCellDateFormatted(cell)) {
                if (cell.getDateCellValue() != null) {
                    cellValues.add(CommonActions.convertDate_MMDDYYYY(cell.getDateCellValue()));
                } else {
                    cellValues.add(null);
                }
            } else if (type == CellType.NUMERIC) {
                Integer value = (int) cell.getNumericCellValue();
                cellValues.add(value);
            }
        }
        return cellValues;
    }

    public static HashMap<Object, Object> excelDataMap(String sheetName, int rowIndex) {

        ArrayList<Object> headers = getRowData(sheetName, 0);
        ArrayList<Object> values = getRowData(sheetName, rowIndex);
        HashMap<Object, Object> excelData = new HashMap<>();

        for (int i = 0; i < headers.size(); i++) {
            excelData.put(headers.get(i), values.get(i));
        }
        return excelData;
    }

    public static ArrayList<HashMap<Object, Object>> userGetDataFromExcel(String sheetName){
        int rowCount = getExelRowCount(sheetName);
        ArrayList<HashMap<Object, Object>> excelData = new ArrayList<>();
        for(int i=1; i<=rowCount; i++) {
            Object executionStatus = ExcelUtils.excelDataMap(sheetName, i).get("ExecutionStatus");
            if(executionStatus.equals("Yes")){
                excelData.add(ExcelUtils.excelDataMap(sheetName,i));
            }
        }
        return excelData;
    }

    public static int getExelRowCount(String sheetName) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(ConfigManager.getExcelPath());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(fis);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        HSSFSheet sheet = workbook.getSheet(sheetName);
        return sheet.getLastRowNum();
    }
}
