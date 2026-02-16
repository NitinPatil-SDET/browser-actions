package com.nitin.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    private ExcelUtils() {
    }

    public static String readCell(String filePath, String sheetName,
                                  int rowNum, int colNum) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null)
                throw new RuntimeException("Sheet not found: " + sheetName);

            Row row = sheet.getRow(rowNum);
            if (row == null)
                return "";

            Cell cell = row.getCell(colNum);
            if (cell == null)
                return "";

            return getCellValueAsString(cell);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }
    }

    private static String getCellValueAsString(Cell cell) {

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell))
                    return cell.getDateCellValue().toString();
                else
                    return String.valueOf(cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            default:
                return "";
        }
    }


    public static void writeCell(String filePath, String sheetName,
                                 int rowNum, int colNum, String value) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null)
                sheet = workbook.createSheet(sheetName);

            Row row = sheet.getRow(rowNum);
            if (row == null)
                row = sheet.createRow(rowNum);

            Cell cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            cell.setCellValue(value);

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to write Excel file: " + filePath, e);
        }
    }

}
