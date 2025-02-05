package ru.sweetbun.xlsxparser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sweetbun.xlsxparser.api.ExcelService;

@Tag(name = "Excel Operations", description = "Работа с файлами Excel")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ExcelController {

    private final ExcelService excelService;

    @Operation(summary = "Получить N-ое max-ое число из Excel")
    @GetMapping("/get-nth-max")
    public int getNthMax(
        @Parameter(description = "Путь к Excel файлу (xlsx)") @RequestParam String filePath,
        @Parameter(description = "Какое максимальное число нужно вернуть") @RequestParam int n) {
        return excelService.getNthMax(filePath, n);
    }
}
