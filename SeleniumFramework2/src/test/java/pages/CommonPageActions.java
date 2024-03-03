package pages;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import locators.CommonLocators;
import org.junit.Assert;
import utils.SafeActions;
import utils.Waits;

import java.sql.SQLException;
import java.sql.Statement;

public class CommonPageActions extends SafeActions implements CommonLocators {

    /**
     * Purpose - Navigates to application
     */
    public void userNavigatesToApp(String appUrl){
        driver.manage().deleteAllCookies();
        navigateToURL(appUrl);
    }

    /**
     * Purpose - User login to application
     * @param userName
     * @param password
     */
    public void login(String userName, String password, String location){
        clearAndType(USER_NAME,userName,"User name in login page", MEDIUMWAIT);
        clearAndType(PASSWORD, password, " password in login page", MEDIUMWAIT);
        click(LOGIN_BTN,Waits.MEDIUMWAIT, "Login button");
        selectByVisibleText(LB_LOCATION_DROPDOWN, location,"Location dropdown", Waits.MEDIUMWAIT);
        click(LOGIN_BTN,Waits.MEDIUMWAIT, "Login button");
    }

    public static void runMTREAPIForTheDoc(String docId, String dbInstanceName, String envName) {
        String dbUrl = "DMQDB1.reprice.nhr.com";
        if(envName.equalsIgnoreCase("Prod")){
            dbUrl = "prsqlprdcnm.reprice.nhr.com";
        }
        RestAssured.baseURI = "https://rbr.prod.aws.casualty.cccis.com/rulesproxy-api/api/rulesproxy/routeToRulesEngineSync";
        RequestSpecification request = RestAssured.given();
        request.auth().preemptive().basic("RpUser1", "RpPassword1");
        String payload = " {\n" +
                "    \"DocumentId\": \"" + docId + "\",\n" +
                "    \"ServerName\": \""+dbUrl+"\",\n" +
                "    \"DatabaseName\": \"" + dbInstanceName + "_RP_PROD\",\n" +
                "    \"DatabaseUserName\": \"rptuser\",\n" +
                "    \"DatabasePassword\": \"nopassword\",\n" +
                "    \"LoggedInUser\": \"WebFlow\"\n" +
                "}";
        Response response = request.contentType(ContentType.JSON).
                body(payload)
                .post(RestAssured.baseURI);
        Assert.assertEquals("API Response code is not 200", 200, response.getStatusCode());
        System.out.println("MRTE API successfully executed with status code " + response.getStatusCode());
    }

    public void skipPPO(Statement statement, String dbInstance, String docId) throws SQLException {
        statement.execute("use "+dbInstance+"_RP_PROD\n" +
                "go\n" +
                "\n" +
                "DECLARE @docid varchar(9) \n" +
                "DECLARE @MSG varchar(8000) \n" +
                "DECLARE @PPOName varchar(50)\n" +
                "\n" +
                "select @docid ='"+docId+"'\n" +
                "\n" +
                "--phs non par\n" +
                "select @PPOName ='PHS'\n" +
                "SELECT @MSG = 'PHS Not Participating Provider.Billing Provider TIN#' + BILLING_PROVIDER_TAX_CODE + '.Service Provider TIN# '+ SERVICE_PROVIDER_TAX_CODE \n" +
                "from BILL_HEADER where DOC_ID =@docid\n" +
                "insert into bill_messages_current \n" +
                "values (@docid, getdate(), left(@msg, 1500), 'PPOs', 100, 3, @PPOName) \n" +
                "\n" +
                "--IHP Non Par \n" +
                "select @PPOName ='IHP'\n" +
                "SELECT @MSG = 'IHP Non participating provider' \n" +
                "insert into bill_messages_current \n" +
                "values (@docid, getdate(), left(@msg, 1500), 'PPOs', 100, 3, @PPOName) \n" +
                "\n" +
                "--CHN Bridge\n" +
                "select @PPOName ='CHN_Bridge'\n" +
                "SELECT @MSG = 'CHN Not Participating Provider.Billing Provider TIN# ' + BILLING_PROVIDER_TAX_CODE + '.Service Provider TIN# '+ SERVICE_PROVIDER_TAX_CODE \n" +
                "from BILL_HEADER where DOC_ID =@docid\n" +
                "insert into bill_messages_current \n" +
                "values (@docid, getdate(), left(@msg, 1500), 'PPOs', 100, 3, @PPOName) \n" +
                "\n" +
                "--Magnacare NonPAR\n" +
                "select @PPOName ='MagnaCare'\n" +
                "SELECT @MSG = 'MagnaCare Not Participating Provider.Billing Provider TIN# ' + BILLING_PROVIDER_TAX_CODE + '.Service Provider TIN# '+ SERVICE_PROVIDER_TAX_CODE \n" +
                "from BILL_HEADER where DOC_ID =@docid\n" +
                "insert into bill_messages_current \n" +
                "values (@docid, getdate(), left(@msg, 1500), 'PPOs', 100, 3, @PPOName) \n" +
                "\n" +
                "--Multiplan Non Par \n" +
                "select @PPOName ='Multiplan'\n" +
                "SELECT @MSG = 'Multiplan Non participating provider' \n" +
                "insert into bill_messages_current \n" +
                "values (@docid, getdate(), left(@msg, 1500), 'PPOs', 100, 3, @PPOName) \n" +
                "\n" +
                "--MediNCrease\n" +
                "select @PPOName ='MediNCrease'\n" +
                "SELECT @MSG = 'MediNCrease Not Participating Provider.Billing Provider TIN# ' + BILLING_PROVIDER_TAX_CODE + '.Service Provider TIN# '+ SERVICE_PROVIDER_TAX_CODE \n" +
                "from BILL_HEADER where DOC_ID =@docid\n" +
                "insert into bill_messages_current \n" +
                "values (@docid, getdate(), left(@msg, 1500), 'PPOs', 100, 3, @PPOName) \n" +
                "\n" +
                "--Cofinity \n" +
                "select @PPOName ='Cofinity_Bridge'\n" +
                "SELECT @MSG = 'Cofinity Not Participating Provider.Billing Provider TIN# ' + BILLING_PROVIDER_TAX_CODE + '.Service Provider TIN# '+ SERVICE_PROVIDER_TAX_CODE \n" +
                "from BILL_HEADER where DOC_ID =@docid\n" +
                "insert into bill_messages_current \n" +
                "values (@docid, getdate(), left(@msg, 1500), 'PPOs', 100, 3, @PPOName) \n" +
                "\n" +
                "--CHN Bridge\n" +
                "select @PPOName ='CHN_Bridge'\n" +
                "SELECT @MSG = 'CHN Not Participating Provider.Billing Provider TIN# ' + BILLING_PROVIDER_TAX_CODE + '.Service Provider TIN# '+ SERVICE_PROVIDER_TAX_CODE \n" +
                "from BILL_HEADER where DOC_ID =@docid\n" +
                "insert into bill_messages_current \n" +
                "values (@docid, getdate(), left(@msg, 1500), 'PPOs', 100, 3, @PPOName) \n" +
                "\n" +
                "--TRPN Non Par \n" +
                "select @PPOName ='TRPN'\n" +
                "SELECT @MSG = 'TRPN Non participating provider' \n" +
                "insert into bill_messages_current \n" +
                "values (@docid, getdate(), left(@msg, 1500), 'PPOs', 100, 3, @PPOName) \n" +
                "\n" +
                "--HCS \n" +
                "select @PPOName ='HCS'\n" +
                "SELECT @MSG = 'HCS Not Participating Provider.Billing Provider TIN# ' + BILLING_PROVIDER_TAX_CODE + '.Service Provider TIN# '+ SERVICE_PROVIDER_TAX_CODE \n" +
                "from BILL_HEADER where DOC_ID =@docid\n" +
                "insert into bill_messages_current \n" +
                "values (@docid, getdate(), left(@msg, 1500), 'PPOs', 100, 3, @PPOName) ");
    }
}
