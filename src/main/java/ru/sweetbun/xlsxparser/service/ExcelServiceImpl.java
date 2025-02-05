package ru.sweetbun.xlsxparser.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.sweetbun.xlsxparser.api.ExcelService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;

@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService{

    @Override
    public int getNthMax(String filePath, int n) {
        if (n <= 0) throw new IllegalArgumentException("N must be greater than 0");

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell.getCellType() == CellType.NUMERIC) {
                    int value = (int) cell.getNumericCellValue();
                    if (minHeap.size() < n) {
                        minHeap.add(value);
                    } else if (value > minHeap.peek()) {
                        minHeap.poll();
                        minHeap.add(value);
                    }
                } else {
                    log.warn("Invalid value at row {}, column {}. Expected a numeric value, but found: {}",
                            row.getRowNum() + 1, 1, cell);
                }
            }
            if (minHeap.size() < n)
                throw new IllegalStateException("The file contains fewer than " + n + " rows with numeric values");

            Integer nthMaxValue = minHeap.peek();
            if (nthMaxValue == null)
                throw new IllegalStateException("Unable to retrieve the " + n + "-th maximum value. Queue is empty");
            log.info("Successfully retrieved the {}-th maximum value.", n);
            return nthMaxValue;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("The specified file was not found. Please check the file path and try again.", e);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the file. Please try again later.", e);
        }
    }
}
