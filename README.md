# XLSX Service

## Запуск приложения

### 1. Запуск через IDE

1. **Откройте проект в вашей IDE** (IntelliJ IDEA, Eclipse, VS Code и т. д.).
2. **Убедитесь, что установлены зависимости** (Maven/Gradle автоматически подтянет их).
3. Запустите главный класс:
   ```plaintext
   ru.sweetbun.xlsxparser.XlsxParserApplication
   ```
4. Перейдите в **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

### 2. Запуск через сборку JAR

1. Соберите JAR-файл:
   ```sh
   mvn clean package
   ```
2. Запустите приложение:
   ```sh
   java -jar target/xlsxparser-0.0.1-SNAPSHOT.jar
   ```
   или попробуйте запустить файл двойным нажатием мыши.


3. Перейдите в **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## API Документация
После запуска откройте **Swagger UI** для тестирования эндпоинта:
```http
GET api/get-nth-max?filePath={filePath}&n={N}
```
- `filePath` – путь к XLSX-файлу (например, `./numbers.xlsx`).
- `n` – номер максимального числа.

Пример вызова:
```http
GET api/get-nth-max?filePath=src/main/resources/numbers.xlsx&n=3
```