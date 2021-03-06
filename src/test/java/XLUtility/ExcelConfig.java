package XLUtility;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ExcelConfig {


    Collection data = null;

    public ExcelConfig(InputStream excelInputStream) throws IOException {
        this.data = dataFromExcel(excelInputStream);
    }

    public Collection getData() {
        return data;
    }

    private Collection dataFromExcel(final InputStream excelFile)
            throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(excelFile);

        data = new ArrayList();
        HSSFSheet sheet = workbook.getSheetAt(0);

        int numberOfColumns = countNonEmptyColumns(sheet);
        List rows = new ArrayList();
        List rowData = new ArrayList();

        for (Row row : sheet) {
            if (isEmpty(row)) {
                break;
            } else {
                rowData.clear();
                for (int column = 0; column < numberOfColumns; column++) {
                    Cell cell = row.getCell(column);
                    rowData.add(objectFrom(workbook, cell));
                }
                rows.add(rowData.toArray());
            }
        }
        return rows;
    }

    private boolean isEmpty(final Row row) {
        Cell firstCell = row.getCell(0);
        boolean rowIsEmpty = (firstCell == null)
                || (firstCell.getCellTypeEnum() == CellType.BLANK);
        return rowIsEmpty;
    }


    private int countNonEmptyColumns(final HSSFSheet sheet) {
        Row firstRow = sheet.getRow(0);
        return firstEmptyCellPosition(firstRow);
    }

    private int firstEmptyCellPosition(final Row cells) {
        int columnCount = 0;
        for (Cell cell : cells) {
            if (cell.getCellTypeEnum() == CellType.BLANK) {
                break;
            }
            columnCount++;
        }
        return columnCount;
    }

    private Object objectFrom(final HSSFWorkbook workbook, final Cell cell) {
        Object cellValue = null;

        if (cell.getCellTypeEnum() == CellType.STRING) {
            cellValue = cell.getRichStringCellValue().getString();
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            cellValue = getNumericCellValue(cell);
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            cellValue = cell.getBooleanCellValue();
        } else if (cell.getCellTypeEnum()  ==CellType.FORMULA) {
            cellValue = evaluateCellFormula(workbook, cell);
        }

        return cellValue;

    }

    private Object getNumericCellValue(final Cell cell) {
        Object cellValue;
        if (DateUtil.isCellDateFormatted(cell)) {
            cellValue = new Date(cell.getDateCellValue().getTime());
        } else {
            cellValue = cell.getNumericCellValue();
        }
        return cellValue;
    }

    private Object evaluateCellFormula(final HSSFWorkbook workbook, final Cell cell) {
        FormulaEvaluator evaluator = workbook.getCreationHelper()
                .createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(cell);
        Object result = null;

        if (cellValue.getCellTypeEnum() == CellType.BOOLEAN) {
            result = cellValue.getBooleanValue();
        } else if (cellValue.getCellTypeEnum() == CellType.NUMERIC) {
            result = cellValue.getNumberValue();
        } else if (cellValue.getCellTypeEnum() == CellType.STRING) {
            result = cellValue.getStringValue();
        }

        return result;
    }

}

