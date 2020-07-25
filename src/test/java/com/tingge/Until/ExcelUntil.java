package com.tingge.Until;


import com.tingge.pojo.Case;
import com.tingge.pojo.Rest;
import com.tingge.pojo.WriteBackDate;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUntil {
    public static Map<String, Integer> caseIdRowNumMapping = new HashMap<String, Integer>();
    public static Map<String, Integer> cellNameCellNumMapping = new HashMap<String, Integer>();
    public static List<WriteBackDate> writeBackDates = new ArrayList<WriteBackDate>();

    static {
        loadRownnumAndCellnumMapping("src/test/resources/casesV1.xlsx", "login");
    }

    /**
     * @Description 获取caseid及对应的行索引 获取列名cellname 以及对应列索引
     * @Param
     * @Return
     */
    public static void loadRownnumAndCellnumMapping(String Excelpath, String sheetName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(Excelpath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            Row titleRow = sheet.getRow(0);
            //获取第一行 标题行
            if (titleRow != null && !isEmptyRow(titleRow)) {
                int lastCellNum = titleRow.getLastCellNum();
                for (int i = 0; i < (lastCellNum-4); i++) {
//                    Cell a = titleRow.getCell(0);
                    Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String title = cell.getStringCellValue();
                    title = title.substring(0, title.indexOf("("));
                    int column = cell.getAddress().getColumn();
                    cellNameCellNumMapping.put(title, column);
                }

            }
            //从第二行开始，获取所有的数据行
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row datarow = sheet.getRow(i);
                Cell firstCellRow = datarow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                firstCellRow.setCellType(CellType.STRING);
                String caseId = firstCellRow.getStringCellValue();
                //获取行索引
                int rowNum = datarow.getRowNum();
                caseIdRowNumMapping.put(caseId, rowNum);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }


    }

    /**
     * @Description
     * @Param [excelPath, startRow 传的行号而非索引, endRow, startCell 传的列号而非索引, endCell]
     * @Return java.lang.Object[][]
     */
    public static Object[][] datas(String excelPath, int startRow, int endRow, int startCell, int endCell) {

        //获取workbook对象
        Object[][] datas = null;
        try {
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            //获取sheet 对象
            Sheet sheet = workbook.getSheet("sheet1");
            // 获取行
            datas = new Object[endRow - startRow + 1][endCell - startCell + 1];
            for (int i = startRow; i <= endRow; i++) {
                // 传的行号需要减1，传索引不需要
                Row row = sheet.getRow(i - 1);
                for (int j = startCell; j <= endCell; j++) {
                    // 获取列  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK  解决xsl 空值为null.
                    // 传的列号需要减1，传索引不需要
                    Cell cell = row.getCell(j - 1);
                    //将列设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    datas[i - startRow][j - startCell] = value;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * @Description
     * @Param [excelPath, rows 行号，cells列号  ]
     * @Return java.lang.Object[][]
     */
    public static Object[][] datas(String excelPath, String ShteetName, int[] rows, int[] cells) {

        //获取workbook对象
        Object[][] datas = null;
        try {
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            //获取sheet 对象
            Sheet sheet = workbook.getSheet(ShteetName);
            // 定义保存的数组
            datas = new Object[rows.length][cells.length];
            //通过循环获取每一行
            for (int i = 0; i < rows.length; i++) {
                // 根据行索引取出第一行
                Row row = sheet.getRow(rows[i] - 1);
                for (int j = 0; j < cells.length; j++) {
                    // 获取列  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK  解决xsl 空值为null.
                    // 传的列号需要减1，传索引不需要
                    Cell cell = row.getCell(cells[j] - 1);
                    //将列设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    datas[i][j] = value;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return datas;
    }


    // sheetName 表单名
    public static void load(String excelPath, String sheeName) {
        //创建WorkBook对象
        Class<Case> clazz = Case.class;
        try {
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            Sheet sheet = workbook.getSheet(sheeName);
            //获取第一行
            Row titleRow = sheet.getRow(0);
            //获取最后一列的列号 返回的是int
            int lastCellNum = titleRow.getLastCellNum();
            String[] filed = new String[lastCellNum];
            for (int i = 0; i < lastCellNum; i++) {
                //根据索引获取对应的列
                Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //获取列的类型为字符串
                cell.setCellType(CellType.STRING);
                //获取列的值
                String title = cell.getStringCellValue();
                title = title.substring(0, title.indexOf("("));
                filed[i] = title;

            }
            int lastRowNumIndex = sheet.getLastRowNum();
            //循环处理每一个数据
            for (int i = 1; i <= lastRowNumIndex; i++) {
                Case cs = (Case) clazz.newInstance();
                //拿到第一行数据
                Row dataRow = sheet.getRow(i);
                if (dataRow == null || isEmptyRow(dataRow)) {
                    continue;
                }
                //拿到此行的每一列
                for (int j = 0; j < lastCellNum; j++) {

                    Cell cell = dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    //获取要反射的方法名
                    String methodName = "set" + filed[j];
                    //获取要反射的方法对象
                    Method method = clazz.getMethod(methodName, String.class);
                    method.invoke(cs, value);
                }
//                System.out.println(cs);
                //
                caseUntil.cases.add(cs);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取sheet对象
        //获取行
        //获取列
    }

    public static <T> List<T> load(String excelPath, String sheeName, Class<T> clazz) {
        //创建WorkBook对象
//        Class<Case>  clazz = Case.class;
        List<T> list = new ArrayList<T>();
        try {
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            Sheet sheet = workbook.getSheet(sheeName);
            //获取第一行
            Row titleRow = sheet.getRow(0);
            //获取最后一列的列号 返回的是int
            int lastCellNum = titleRow.getLastCellNum();
            String[] filed = new String[lastCellNum];
            for (int i = 0; i < lastCellNum; i++) {
                //根据索引获取对应的列
                Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //获取列的类型为字符串
                cell.setCellType(CellType.STRING);
                //获取列的值
                String title = cell.getStringCellValue();
                title = title.substring(0, title.indexOf("("));
                filed[i] = title;

            }
            int lastRowNumIndex = sheet.getLastRowNum();
            //循环处理每一个数据
            for (int i = 1; i <= lastRowNumIndex; i++) {
                T obj = clazz.newInstance();
                //拿到第一行数据
                Row dataRow = sheet.getRow(i);
                if (dataRow ==null||isEmptyRow(dataRow)) {
                    continue;
                }
                //拿到此行的每一列
                for (int j = 0; j < lastCellNum; j++) {

                    Cell cell = dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    //获取要反射的方法名
                    String methodName = "set" + filed[j];
                    //获取要反射的方法对象
                    Method method = clazz.getMethod(methodName, String.class);
                    method.invoke(obj, value);
                }
//                if (obj instanceof Case) { // instanceof 是判断对象类型的语法
//                    Case cs = (Case) obj;
//                    caseUntil.cases.add(cs);
//                } else if (obj instanceof Rest) {
//                    Rest res = (Rest) obj;
//                    RestUntil.rests.add(res);
//                }
                list.add(obj);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取sheet对象
        //获取行
        //获取列
        return list;
    }

    private static boolean isEmptyRow(Row dataRow) {
        int lastCellNum = dataRow.getLastCellNum();
        for (int j = 0; j < lastCellNum; j++) {
            Cell cell = dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            String value = cell.getStringCellValue();
            if (value != null && value.trim().length() > 0) {
                return false;
            }
        }
        return true;

    }

    /**
     * @Description 回写相应报文
     * @Param
     * @Return
     */
    public static void writerBackData(String excelpath, String sheetname, String caseId, String cellName, String result) {
//        Integer rownum = caseIdRowNumMapping.get(caseId);
//        Integer cellnum = caseIdRowNumMapping.get(cellName);
        //写入  sheet.getRow(rownum)  rou.getcell(cellnum)  cell.setStringValue(result)
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelpath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetname);
            int rownum = caseIdRowNumMapping.get(caseId);
            Row row = sheet.getRow(rownum);
            int cellnum = cellNameCellNumMapping.get(cellName);
            Cell cell = row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(result);
            outputStream = new FileOutputStream(new File(excelpath));
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }

        }
    }


    public static void batchWriteBackDatas(String excelPath) {
        InputStream inp = null;
        OutputStream outputStream = null;
        try {
            inp = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(inp);
            for (WriteBackDate writeBackDate : writeBackDates) {
                //获取sheetName
                String sheetName = writeBackDate.getSheetName();
                Sheet sheet = workbook.getSheet(sheetName);
                String caseId = writeBackDate.getCaseId();
                int rownum = caseIdRowNumMapping.get(caseId);
                Row row = sheet.getRow(rownum);
                String cellName = writeBackDate.getCellName();
                int cellnum = cellNameCellNumMapping.get(cellName);
                Cell cell =  row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String result = writeBackDate.getResult();
                cell.setCellValue(result);
            }
            outputStream = new FileOutputStream(new File(excelPath));
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream != null){
                    outputStream.close();
                }
                if (inp != null){
                    inp.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        loadRownnumAndCellnumMapping("src/main/resources/casesV1.xlsx", "login");
        System.out.println(caseIdRowNumMapping.toString());
        System.out.println(cellNameCellNumMapping.toString());
    }
}


