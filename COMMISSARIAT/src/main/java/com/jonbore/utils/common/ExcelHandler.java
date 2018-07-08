package com.jonbore.utils.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

/**
 * @author xiuyou.xu
 *
 *
 *
 * 添加
 */
public class ExcelHandler {

    /**
     * Excel 2007以前默认格式
     */
    public static final String EXT_XLS = ".xls";
    /**
     * Excel 2007以后压缩格式
     */
    public static final String EXT_XLSX = ".xlsx";
    /**
     * 读取xls文件
     *
     * @param is
     * @return
     */
    public static List<Map<String, Object>> readXls(InputStream is) {
        List<Map<String, Object>> ret = Lists.newArrayList();
        try (HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is)) {
            // 只读取第一个sheet，其他的丢弃
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            if (sheet != null) {
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    readRow(ret, sheet.getRow(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 读取xlsx文件
     *
     * @param is
     * @return
     */
    public static List<Map<String, Object>> readXlsx(InputStream is) {
        List<Map<String, Object>> ret = Lists.newArrayList();
        try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is)) {
            // 只读取第一个sheet，其他的丢弃
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            if (sheet != null) {
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    readRow(ret, sheet.getRow(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 根据excel扩展名读取
     *
     * @param fileExt
     * @param sheetIndex sheet索引，从0开始
     * @param is
     * @return
     */
    public static List<Map<String, Object>> read(String fileExt, int sheetIndex, InputStream is) throws Exception {
        try (Workbook xssfWorkbook = "xlsx".equalsIgnoreCase(fileExt) ? new XSSFWorkbook(is) : new HSSFWorkbook(is)) {
            List<Map<String, Object>> ret = Lists.newArrayList();
            Sheet sheet = xssfWorkbook.getSheetAt(sheetIndex);
            if (sheet != null) {
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    readRow(ret, sheet.getRow(i));
                }
            }

            return ret;
        } catch (Exception e) {
            throw e;
        }
    }

    private static void readRow(List<Map<String, Object>> ret, Row row) {
        if (row != null) {
            int columnCount = row.getPhysicalNumberOfCells();
            Map<String, Object> record = Maps.newHashMap();
            for (int j = 0; j < columnCount; j++) {
                record.put("column" + j, row.getCell(j) != null ? row.getCell(j).toString() : "");
            }
            ret.add(record);
        }
    }

    public static String readCell(Cell cell){

        String value = null;
        if (cell == null) {
            return value;
        }
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                DecimalFormat df = new DecimalFormat("0");
                value = df.format(cell.getNumericCellValue());
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue() + "";
                break;
            case FORMULA:
                value = cell.getCellFormula() + "";
                break;
            case BLANK:
                value = "";
                break;
            case ERROR:
                value = "error";
                break;
            default:
                value = "unknown type";
                break;
        }
        return value;
    }

    /**
     * 将excel读取为map，key为sheet名，value为sheet对象
     *
     * @param fileExt
     * @param is
     * @return
     */
    public static Map<String, Sheet> getSheetMap(String fileExt, InputStream is) {
        Map<String, Sheet> ret = Maps.newHashMap();
        try (Workbook xssfWorkbook = "xlsx".equalsIgnoreCase(fileExt) ? new XSSFWorkbook(is) : new HSSFWorkbook(is)) {
            int count = xssfWorkbook.getNumberOfSheets();
            for (int i = 0; i < count; i++) {
                Sheet sheet = xssfWorkbook.getSheetAt(i);
                ret.put(sheet.getSheetName(), sheet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 将Excel sheet转化为List
     *
     * @param sheet
     * @return
     */
    public static List<Map<String, Object>> sheet2List(Sheet sheet) {
        List<Map<String, Object>> result = Lists.newArrayList();
        if (sheet != null) {
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                readRow(result, sheet.getRow(i));
            }
        }
        return result;
    }

    /**
     * 将excel读取为list，每个元素为sheet
     *
     * @param fileExt
     * @param is
     * @return
     */
    public static List<Sheet> getSheetList(String fileExt, InputStream is) {
        List<Sheet> ret = Lists.newArrayList();
        try (Workbook xssfWorkbook = "xlsx".equalsIgnoreCase(fileExt) ? new XSSFWorkbook(is) : new HSSFWorkbook(is)) {
            int count = xssfWorkbook.getNumberOfSheets();
            for (int i = 0; i < count; i++) {
                Sheet sheet = xssfWorkbook.getSheetAt(i);
                ret.add(sheet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 读取excel文件内容并转换为xml格式字符串
     *
     * @param fileExt
     * @param is
     * @param rootNodeName
     * @param rowNodeNames 每个sheet中的行的节点名称，每个sheet一个，如果为null，则默认为row
     * @return
     */
    public static String toXml(String fileExt, InputStream is, String rootNodeName, String... rowNodeNames) {
        StringBuilder sb = new StringBuilder();
        if (rootNodeName != null && !rootNodeName.isEmpty()) {
            sb.append("<").append(rootNodeName).append(">");
        }
        try (Workbook xssfWorkbook = "xlsx".equalsIgnoreCase(fileExt) ? new XSSFWorkbook(is) : new HSSFWorkbook(is)) {
            int count = xssfWorkbook.getNumberOfSheets();
            for (int i = 0; i < count; i++) {
                Sheet sheet = xssfWorkbook.getSheetAt(i);
                if (sheet != null) {
                    sb.append("<").append(sheet.getSheetName()).append(">");
                    readSheet(sb, sheet, rowNodeNames.length > i ? rowNodeNames[i] : null);
                    sb.append("</").append(sheet.getSheetName()).append(">");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rootNodeName != null && !rootNodeName.isEmpty()) {
            sb.append("</").append(rootNodeName).append(">");
        }
        return sb.toString();
    }

    /**
     * 读取单个sheet
     *
     * @param sb
     * @param sheet
     * @param rowNodeName
     * @return sheet中的行数，不含表头
     */
    public static int readSheet(StringBuilder sb, Sheet sheet, String rowNodeName) {
        rowNodeName = rowNodeName == null ? sheet.getSheetName() : rowNodeName;

        int rows = 0;
        if (sheet != null) {
            // 第一行为表头，值为各列名称
            Row header = sheet.getRow(0);
            List<String> columns = Lists.newArrayList();
            if (header != null) {
                int count = header.getPhysicalNumberOfCells();
                for (int j = 0; j < count; j++) {
                    Cell cell = header.getCell(j);
                    columns.add(cell != null ? cell.toString() : "");
                }
            }
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    rows++;
                    sb.append("<").append(rowNodeName).append(">");
                    //如果有表头，应该按照表头的列数来遍历获取Excel的数据
                    //不能使用读到的最后一行的cellNum作为遍历的index，这样会造成读取不到最后一个内容为空的cell
                    for (int c = row.getFirstCellNum(); c < columns.size(); c++) {
                        //同时为了保证不出现index溢出及读到数据的完整性，在遍历时与能读到的最后一个非空cell做对比
                        //给后面未读取到的cell设置空值
                        if (c < row.getLastCellNum()) {
                            Cell cell = row.getCell(c);
                            sb.append("<").append(columns.get(c)).append(">").append(cell != null ? cell.toString() : "").append("</").append(columns.get(c)).append(">");
                        }
                        if (c >= row.getLastCellNum()) {
                            sb.append("<").append(columns.get(c)).append(">").append("").append("</").append(columns.get(c)).append(">");
                        }
                    }
                    sb.append("</").append(rowNodeName).append(">");
                }
            }
        }
        return rows;
    }

    public static StreamingOutput write(List<Map<String, Object>> rows, String sheetName) throws IOException {
        File f = new File("./tmp/" + System.currentTimeMillis() + ".xlsx");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);
            if (sheet != null && rows != null && !rows.isEmpty()) {
                for (int i = 0; i < rows.size(); i++) {
                    writeRow(rows.get(i), sheet, i);
                }
            }
            f.getParentFile().mkdirs();
            FileOutputStream os = new FileOutputStream(f);
            workbook.write(os);
            return new StreamingOutput() {
                @Override
                public void write(OutputStream output) throws IOException {
                    FileInputStream is = new FileInputStream(f);
                    IOUtils.copy(is, output);
                    IOUtils.closeQuietly(is);
                    f.delete();
                }
            };
        } catch (Exception e) {
            throw e;
        }finally {
            f.delete();
        }
    }

    private static void writeRow(Map<String, Object> row, Sheet sheet, int index) {
        Row r = sheet.createRow(index);
        for (int i = 0; i < row.size(); i++) {
            //判断是否为null，否则没法做toString
            if (row.get("column" + i) != null) {
                r.createCell(i).setCellValue(row.get("column" + i).toString());
            } else {
                r.createCell(i).setCellValue("");
            }
        }
    }
}
