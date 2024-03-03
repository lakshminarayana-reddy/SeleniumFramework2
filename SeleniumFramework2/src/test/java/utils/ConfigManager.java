package utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;

import java.io.*;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class ConfigManager {

    private Properties properties = new Properties();
    private String configFilePath=System.getProperty("user.dir")+"/src/test/java/testdata/App.properties";
    private static Logger logger = Logger.getLogger(DriverManager.class);
    /**
     * Returns the value of given property from either sys.properties or
     * app.properties file
     *
     * @param key
     *            - ConfigParamvalue that requires to be returned from
     *            Config.properties file
     * @return - return ConfigValue
     */
    public String getProperty(String key) {
        String value = "";
        if (key != "") {
            loadProperties();
            try {
                if (!properties.getProperty(key).trim().isEmpty())
                    value = properties.getProperty(key).trim();
            } catch (NullPointerException e) {
                Assert.fail("Key - '" + key + "' does not exist or not given a value in properties file");
            }
        } else {
            logger.info("key cannot be null.. ");
            Assert.fail("key cannot be null.. ");
        }
        return value;
    }

    /**
     *
     * This method is sued to load properties file that has to be accessed
     *
     */
    private void loadProperties() {
        FileInputStream fis;
        try {
            fis = new FileInputStream(configFilePath);
            properties.load(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            logger.error("Cannot find configuration file at " + configFilePath);
            Assert.fail("Cannot find configuration file at " + configFilePath);
        } catch (IOException e) {
            logger.error("Cannot read configuration file at " +configFilePath);
            Assert.fail("Cannot read configuration file at " + configFilePath);
        }
    }

    /**
     * This method helps to write any new key value pairs to app.properties
     * file.
     *
     * @param sKey  key to write app.properties
     * @param sData  data to write app.properties
     */
    public void writeProperty(String sKey, String sData) {
        if (sKey.trim().length() > 0) {
            try {
                PropertiesConfiguration config = new PropertiesConfiguration(configFilePath);
                config.setProperty(sKey, sData);
                config.getLayout();
                config.save();
            } catch (ConfigurationException e) {
                Assert.fail("cannot write to properties file..." + e.getMessage() + e.getStackTrace());
            }
        } else {
            Assert.fail("cannot write to properties file...Please check if key value is empty");
        }
    }

    public void setCellValue(String sheetName, String existingDoc, String cellValue, String dbName) throws IOException {
        writeProperty("DocId",cellValue);
        String filePath = System.getProperty("user.dir")+"/src/test/java/testdata/Docs Data.xls";
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
        HSSFSheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        HSSFRow row = sheet.createRow(rowCount+1);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(cellValue);
        cell = row.createCell(1);
        cell.setCellValue(existingDoc);
        cell = row.createCell(2);
        cell.setCellValue(dbName);
        cell = row.createCell(3);
        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd-HH:mm").format(new Date()));
        fis.close();
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.close();
    }

    public ArrayList<String> getDocsFromExcel(String sheetName, String dbName) throws IOException {
        ArrayList<String> cellValues= new ArrayList<String>();
        String filePath = System.getProperty("user.dir")+"/src/test/java/testdata/Docs Data.xls";
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
        HSSFSheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        for(int i=1; i<rowCount+1; i++){
            if(sheet.getRow(i).getCell(2).getStringCellValue().equalsIgnoreCase(dbName)) {
                String cellValue = sheet.getRow(i).getCell(0).getStringCellValue();
                cellValues.add(cellValue);
            }
        }
        return cellValues;
    }

    public static String getExcelPath() {
        String path = null;
        if (System.getProperty("excelPath") != null) {
            path = System.getProperty("excelPath");
        } else {
            path = System.getProperty("user.dir") + "/src/test/java/testdata/MedFlowTestData.xls";
        }

        logger.info("Reading test data from " + path);

        return path;
    }
}
