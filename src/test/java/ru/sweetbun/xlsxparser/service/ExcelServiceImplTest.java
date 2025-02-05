package ru.sweetbun.xlsxparser.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.sweetbun.xlsxparser.api.ExcelService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExcelServiceImplTest {

    private ExcelService excelService;

    @TempDir
    File tempDir;

    @BeforeEach
    void setUp() {
        excelService = new ExcelServiceImpl();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 9",
            "2, 7",
            "3, 5",
            "4, 3",
            "5, 1"
    })
    void getNthMax_ValidInput_ReturnsCorrectNthMax(int n, int expected) throws IOException {
        File testFile = createTestExcelFile(new int[]{5, 3, 9, 1, 7});
        int result = excelService.getNthMax(testFile.getAbsolutePath(), n);
        assertEquals(expected, result);
    }

    @Test
    void getNthMax_InputSizeLessThanN_ThrowsIllegalStateException() throws IOException {
        File testFile = createTestExcelFile(new int[]{5, 3});
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                excelService.getNthMax(testFile.getAbsolutePath(), 3)
        );
        assertTrue(exception.getMessage().contains("fewer than 3 rows"));
    }

    @Test
    void getNthMax_NonExistingFile_ThrowsRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                excelService.getNthMax("non_existing_file.xlsx", 2)
        );
        assertTrue(exception.getMessage().contains("not found"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void getNthMax_InvalidN_ThrowsIllegalArgumentException(int invalidN) throws IOException {
        File testFile = createTestExcelFile(new int[]{10, 20, 30});

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                excelService.getNthMax(testFile.getAbsolutePath(), invalidN)
        );
        assertTrue(exception.getMessage().contains("N must be greater than 0"));
    }

    private File createTestExcelFile(int[] values) throws IOException {
        File file = new File(tempDir, "test.xlsx");
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(file)) {
            var sheet = workbook.createSheet();
            for (int i = 0; i < values.length; i++) {
                var row = sheet.createRow(i);
                row.createCell(0).setCellValue(values[i]);
            }
            workbook.write(fos);
        }
        return file;
    }
}
