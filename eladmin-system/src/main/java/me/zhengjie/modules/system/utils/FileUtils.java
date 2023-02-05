package me.zhengjie.modules.system.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileUtils {

    public static final String SYS_TEM_DIR = System.getProperty("java.io.tmpdir") + File.separator;

    /**
     * 将上传的excelfile 解析成map文件
     * @param file
     * @return
     */
    public static Map<Integer, List<String>> readExcel(MultipartFile file) {
        //存储excel所有sheet信息
        Map<Integer, List<String>> totalList = new HashMap<>();
        //创建工作簿
        Workbook workbook = null;
        try {
//            is = new FileInputStream(file);
//            workbook = new XSSFWorkbook(is);
//            workbook = new XSSFWorkbook(file.getInputStream());//这是07版本的，不兼容03版本HSSFWorkbook
            workbook = WorkbookFactory.create(file.getInputStream());//可兼容03、07版本
            //获取sheet数量
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                //获取当前工作表
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                if (!("人机服务冲测规划".equals(sheetName) || "sheet1".equals(sheetName))) {
                    continue;
                }
                int numOfRows = sheet.getPhysicalNumberOfRows();
                for (int rowNum = 0; rowNum < numOfRows; rowNum++) {
                    List<String> infoList = new ArrayList<>();
                    Row rowData = sheet.getRow(rowNum);
                    if (rowData != null) {
                        //读取列
                        int cellCount = rowData.getLastCellNum();
                        for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                            Cell cell = rowData.getCell(cellNum);
                            //匹配数据类型
                            String cellValue = "";
                            if (cell != null) {
                                if (cell.getCellType() == CellType.STRING) {
                                    cellValue = cell.getStringCellValue();
                                }
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    //数值型
                                    //poi读取整数会自动转化成小数，这里对整数进行还原，小数不做处理
                                    long longValue = Math.round(cell.getNumericCellValue());
                                    if (Double.parseDouble(longValue + ".0") == cell.getNumericCellValue()) {
                                        cellValue = String.valueOf(longValue);
                                    } else {
                                        cellValue = String.valueOf(cell.getNumericCellValue());
                                    }
                                } else if (cell.getCellType() == CellType.FORMULA) {
                                    //公式型
                                    //公式计算的值不会转化成小数，这里数值获取失败后会获取字符
                                    try {
                                        cellValue = String.valueOf(cell.getNumericCellValue());
                                    } catch (Exception e) {
                                        cellValue = cell.getStringCellValue();
                                    }
                                }
                                infoList.add(cellValue);
                            } else {
                                infoList.add(null);
                            }
                        }
                    }
                    totalList.put(rowNum, infoList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
//                if (is != null) {
//                    is.close();
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return totalList;
    }

    /**
     * 导出excel
     */
    public static void downloadExcel(List<Map<String, Object>> list, HttpServletResponse response) throws IOException {
        String tempPath = SYS_TEM_DIR + IdUtil.fastSimpleUUID() + ".xlsx";
        File file = new File(tempPath);
        BigExcelWriter writer = ExcelUtil.getBigWriter(file);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        SXSSFSheet sheet = (SXSSFSheet)writer.getSheet();
        //上面需要强转SXSSFSheet  不然没有trackAllColumnsForAutoSizing方法
        sheet.trackAllColumnsForAutoSizing();
        //列宽自适应
        writer.autoSizeColumnAll();
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
        ServletOutputStream out = response.getOutputStream();
        // 终止后删除临时文件
        file.deleteOnExit();
        writer.flush(out, true);
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


}
