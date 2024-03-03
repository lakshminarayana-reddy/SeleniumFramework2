package stepdef;

import cucumber.api.java.en.Then;
import pages.CommonPageActions;
import pages.HomePage;
import pages.MedicalBillReviewSolutionsPage;
import utils.CommonActions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class UploadImageStepDef {

    @Then("^Upload the image in claim file view$")
    public void uploadImageInClaimFileView() throws SQLException {
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for(int i=0; i<excelData.size(); i++) {
            String username = (String) excelData.get(i).get("UserName");
            String password = (String) excelData.get(i).get("Password");
            String client = (String) excelData.get(i).get("Location");
            CommonPageActions commonPageActions = new CommonPageActions();
            commonPageActions.userNavigatesToApp((String) excelData.get(i).get("Env_URL"));
            commonPageActions.login(username, password, client);
            HomePage homePage = new HomePage();
            homePage.verifyHomePage();
            homePage.navigateToRequiredLinkInMedicalBillSolution("Claim File View");
            MedicalBillReviewSolutionsPage medicalBillReviewSolutionsPage = new MedicalBillReviewSolutionsPage();
            medicalBillReviewSolutionsPage.searchWithMemberNumber("TESTCLAIM-FLDed100");
            medicalBillReviewSolutionsPage.navigateToRequiredTab("Doc List");
            medicalBillReviewSolutionsPage.uploadImage();
            medicalBillReviewSolutionsPage.verifyImageUploadedSuccessfully();
            medicalBillReviewSolutionsPage.switchToFirstWindow();
            String dbInstance = (String) excelData.get(i).get("DB_Instance");
            Statement statement = CommonActions.createDBConnection((String) excelData.get(i).get("Env_Name"), dbInstance);
            ResultSet resultSet = statement.executeQuery("select top 10 * from "+dbInstance+"_WORKFLOW..ADUpload where OriginalFileName='Image.tif' order by Touch_Dt desc");
            resultSet.next();
            String finalPath = resultSet.getString("FinalPath");

        }
    }
}
