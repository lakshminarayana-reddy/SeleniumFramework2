package stepdef;

import cucumber.api.java.en.Then;
import org.junit.Assert;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseStepDef {

    @Then("^Verify the database connections$")
    public void verifyTheDatabaseConnections() {
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for(int i=0; i<excelData.size(); i++){
            Object databaseUrl = excelData.get(i).get("DB_URL");
            Object userName = excelData.get(i).get("DB_UserName");
            Object password = excelData.get(i).get("DB_Password");
            String connectionUrl = "jdbc:sqlserver://" + databaseUrl + ":1433;databaseName=DEN1_Workflow;user=" + userName + ";password=" + password + "";
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(connectionUrl);
                connection.createStatement();
            } catch (SQLException e) {
                Assert.fail("Database connection is failed for "+excelData.get(i).get("DB_Name")+" with connection url "+connectionUrl+" and exception is "+e);
            }
            System.out.println("Database connection is successful for "+connectionUrl);
        }
    }
}
