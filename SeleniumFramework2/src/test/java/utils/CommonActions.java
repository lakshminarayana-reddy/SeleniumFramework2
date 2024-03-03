package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CommonActions extends SafeActions{

    public String getXMLData(String fileName, String xmlField) throws IOException {
        String elementFields = null;
        File file = new File(System.getProperty("user.dir")+"/"+fileName+".xml");
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((elementFields = br.readLine()) != null){
            if(elementFields.contains(xmlField)){
                elementFields=elementFields.split(xmlField)[1].split("\"")[1];
                break;
            }
        }
        return elementFields;
    }

    /**
     * Purpose - To get the column data in an array list
     * @param rs
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getColumnData(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        ArrayList<String> resultList= new ArrayList<>(columnCount);
        while (rs.next()) {
            for(int i=1;i <= columnCount; i++) {
                String data = rs.getString(i);
                if(data==null){
                    data="";
                }
                resultList.add(data);
            }
        }
        return resultList;
    }

    public int rowCount = 0;

    /**
     * Purpose - Convert date from YYYY-MM-DD to MM/DD/YYYY
     * @param date
     * @return
     */
    public static String convertDate_MMDDYYYY(Object date){
        String convertedDate = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        if (date != null && !date.getClass().getSimpleName().equals("String")) {
            convertedDate = simpleDateFormat.format(date);
        } else if (date != null) {
            try {
                Date dateObj = simpleDateFormat.parse(String.valueOf(date));
                convertedDate = simpleDateFormat.format(dateObj);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return convertedDate;
    }

    /**
     * Purpose - To get the entire table data in multiple array list
     * @param rs
     * @return
     * @throws SQLException
     */
    public ArrayList<ArrayList<String>> getTableData(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        ArrayList<ArrayList<String>> tableData = new ArrayList<>();
        rowCount = 0;
        while(rs.next()){
            rowCount++;
            ArrayList<String> resultL = new ArrayList<>();
            for (int j=1; j<=columnCount;j++){
                String data = rs.getString(j);
                if(data==null){
                    data="";
                }
                resultL.add(data);
            }
            tableData.add(resultL);
        }
        return tableData;
    }

    /**
     * Purpose - To get the row count in the table
     *              - Need to call this method after getTableData
     * @return
     */
    public int getRowCount(){
        return rowCount;
    }

    public static Statement createDBConnection(String environment, String dbInstance) throws SQLException {
        ConfigManager configManager = new ConfigManager();
        String connectionType = "jdbc:sqlserver";
        String connectionUrl = null;
        String dbUser = null;
        String dbPassword = null;
        if(environment.equalsIgnoreCase("prod")){
            connectionUrl = configManager.getProperty("prodUrl");
            dbUser = configManager.getProperty("prodDBUserName");
            dbPassword = configManager.getProperty("prodDBPassword");
        } else if (environment.equalsIgnoreCase("QA")){
            connectionUrl = configManager.getProperty("qaUrl");
            dbUser = configManager.getProperty("qaDBUserName");
            dbPassword = configManager.getProperty("qaDBPassword");
        }
        String selectDBName = dbInstance+"_Workflow";
        String selectConnectionUrl = "" + connectionType + "://" + connectionUrl + ":1433;databaseName=" + selectDBName + ";user=" + dbUser + ";password=" + dbPassword + "";
        Connection selectConnection = DriverManager.getConnection(selectConnectionUrl);
        Statement statement = selectConnection.createStatement();
        return statement;
    }
}
