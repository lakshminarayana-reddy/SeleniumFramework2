package stepdef;

import cucumber.api.java.en.Then;
import org.junit.Assert;
import utils.CommonActions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ValidationsStepDef {
    @Then("^Verify ppo validations and discount calculations for doc$")
    public void verifyPPOValidationAndDiscountCalculations() throws SQLException, InterruptedException {
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for(int i=0; i<excelData.size(); i++){
            String location = (String) excelData.get(i).get("Location");
            String dbInstance = (String) excelData.get(i).get("DB_Instance");
            String docId = (String) excelData.get(i).get("DocId");
            String processorName = (String) excelData.get(i).get("ProcessorName");
            String binDesc = (String) excelData.get(i).get("BinDescription");
            String ppoName = (String) excelData.get(i).get("PPOName");
            String comment = (String) excelData.get(i).get("Comment");
            Statement statement = CommonActions.createDBConnection((String) excelData.get(i).get("Env_Name"), dbInstance);
            ResultSet resultSet = statement.executeQuery("select * from " + dbInstance + "_RP_PROD..auditall where docid = '" + docId + "' \n" +
                    "and ProcessorName='" + processorName + "' and BinDesc='" + binDesc + "'");
            if (resultSet.next()) {
                System.out.println("The processor name " + processorName + " and bin desc " + binDesc + " for doc " + docId + "  is present in audit all table");
            } else {
                Assert.fail("The processor name " + processorName + " and bin desc " + binDesc + " for doc " + docId + " is not present in audit all table");
            }
            //Discount Calculations
            DecimalFormat df = new DecimalFormat("0.00");
            resultSet = statement.executeQuery("select old_reimb,reimbursement,ppo_discount,old_rc,reason_code,DISCOUNT_TYPE,CHARGE,*\n" +
                    " from "+dbInstance+"_RP_PROD..bill_lines where doc_id='" + docId + "'");
            CommonActions commonActions = new CommonActions();
            ArrayList<ArrayList<String>> tableData = commonActions.getTableData(resultSet);
            String reasonCode = null;
            int count = commonActions.getRowCount();
            if (location.equals("NW")) {
                reasonCode = "PPO1";
            } else if (location.equals("USAA")) {
                reasonCode = "PPO";
            }
            if (count == 0) {
                Assert.fail("The bill lines table is empty");
            }
            for (int j = 0; j < count; j++) {
                float newReimbursement = Float.parseFloat(tableData.get(j).get(1));
                float charge = Float.parseFloat(tableData.get(j).get(6));
                float oldReimbursement = Float.parseFloat(tableData.get(j).get(0));
                if (tableData.get(j).get(5).startsWith("UC") && tableData.get(j).get(4).equals(reasonCode)) {
                    if (newReimbursement >= charge) {
                        Assert.assertEquals("PPO discount for doc " + docId + " is not 0 in bill lines table", tableData.get(j).get(2), "0");
                        System.out.println("PPO discount for doc " + docId + " is 0 in bill lines table");
                    } else if (newReimbursement < charge || newReimbursement >= oldReimbursement) {
                        float ppoDisc = charge - newReimbursement;
                        Assert.assertEquals("PPO discount for doc " + docId + " is not matched in bill lines table", df.format(Float.parseFloat(tableData.get(j).get(2))), df.format(ppoDisc));
                        System.out.println("PPO discount for doc " + docId + " is matched in bill lines table");
                    }
                } else if (!tableData.get(j).get(5).startsWith("UC") && tableData.get(j).get(4).equals(reasonCode)) {
                    float ppoDisc = oldReimbursement - newReimbursement;
                    Assert.assertEquals("PPO discount for doc " + docId + " is not matched in bill lines table", df.format(Float.parseFloat(tableData.get(j).get(2))), df.format(ppoDisc));
                    System.out.println("PPO discount for doc " + docId + " is matched in bill lines table");
                }
            }
            //if reimbursement is 0 then there is 0 PPO_discount and old_rc is equal to reason_code
            resultSet = statement.executeQuery("select old_reimb,reimbursement,ppo_discount,old_rc,reason_code,DISCOUNT_TYPE,CHARGE,*\n" +
                    " from "+dbInstance+"_RP_PROD..bill_lines where doc_id='" + docId + "'");
            tableData = commonActions.getTableData(resultSet);
            count = commonActions.getRowCount();
            if (count == 0) {
                Assert.fail("The bill lines table is empty");
            }
            for (int j = 0; j < count; j++) {
                float newReimbursement = Float.parseFloat(tableData.get(j).get(1));
                if (df.format(newReimbursement).equals("0")) {
                    Assert.assertEquals("The PPO Discount is not equal to 0 for line " + j + " in doc " + docId + " bill lines table", "0", tableData.get(j).get(2));
                    System.out.println("The PPO Discount is equal to 0 for line " + j + " in doc " + docId + " bill lines table");
                    Assert.assertEquals("The old reason code is not matched with reason code for doc " + docId + " bill lines table", tableData.get(j).get(3), tableData.get(j).get(4));
                    System.out.println("The old reason code is matched with reason code for line " + j + " in doc " + docId + " bill lines table");
                }
            }
            //PPO name validation in bill header
            Thread.sleep(5000);
            resultSet = statement.executeQuery("Select PPO_NAME, * from " + dbInstance + "_RP_PROD..BILL_HEADER where doc_id='" + docId + "'");
            resultSet.next();
            Assert.assertEquals("PPO Name is not as expected for doc " + docId + " in bill header table", ppoName, resultSet.getString("PPO_NAME"));
            System.out.println("PPO Name for " + docId + " is "+ppoName+" in bill header table");
            //Comment validation
            resultSet = statement.executeQuery("Select * from " + dbInstance + "_RP_PROD..auditall where docid='" + docId + "' and Comment='"+comment+"'");
            if (resultSet.next()) {
                System.out.println("Comment for " + docId + " is present in audit current table");
            } else {
                Assert.fail("Comment for " + docId + " is not present in audit current table");
            }
            System.out.println("Successfully validated the doc " + docId);
        }
    }
}
