package utils;

import com.jonbore.utils.common.ExcelHandler;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author bo.zhou
 * @date 2018/7/8
 */
public class ExcelHandlerTest {


    @Test
    public void readCell() throws FileNotFoundException {
        File file = new File("/Users/bo.zhou/Downloads/aaaa1a.xlsx");
        List<Sheet> sheetList = ExcelHandler.getSheetList("xlsx", new FileInputStream(file));
        for (Sheet rows : sheetList) {
            ExcelHandler.sheet2List(rows);
        }
    }
}
