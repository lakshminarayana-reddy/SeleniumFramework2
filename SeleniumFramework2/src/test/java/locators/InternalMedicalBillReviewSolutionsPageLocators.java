package locators;

import org.openqa.selenium.By;

public interface InternalMedicalBillReviewSolutionsPageLocators {
    By ADVANCEREVIEWMODULE_TEXT = By.xpath("//a[text()='Doc ID']");
    String SINGLE_FRAME = "SingleFrame";
    By DOCID_LINK = By.xpath("//a[text()='%s']");
    By REWORK_LINK = By.xpath("//input[@name='btnRework']");
    By ADVANCEREVIEWMODULE_LINK = By.xpath("//input[@name='lbtARM']");
    By APPLYDEFAULTFLAGACTIONS_LINK = By.xpath("//input[@name='btnApplyDefault']");
    By FLAGACTION_DROPDOWN = By.xpath("//select[@class='fl-dropdown']");
    By APPLY_BTN = By.xpath("//input[@name='btnApply']");
    By SAVEANDRECALC_BTN = By.xpath("//input[@name='btnRecalc']");
    By NEWYORKBILL_DROPDOWN = By.xpath("//select[contains(@id,'New_York_Bill')]");
    By TABS_LINKS = By.xpath("//span[text()='%s']");
    By FLORIDABILL_DROPDOWN = By.xpath("//select[contains(@id,'Florida_Bill')]");
    By SAVE_BTN = By.xpath("//input[@title='Save Changes']");
    By SUCCESS_MSG = By.xpath("//span[text()='DocuNet Information Saved Successfully!']");
    By MOVETO_DROPDOWN = By.id("CtrlCmtAnnt_ddlPrefab_Input");
    By COMPLETE = By.xpath("//li[text()='Complete']");
    By SUBMIT_BTN = By.xpath("//input[contains(@value,'Submit')]");
    By PAGENUMBER_DROPDOWN = By.id("cmdPageNumber");
    By SAVE_BTN_DIALOG = By.id("Save");
    By SERVICE_PROVIDER_INFO_LINK = By.id("BillHeader_lnkSrvcProv");
    By TIN_INPUT = By.id("radSTin_Input");
    By SELECT_TIN = By.xpath("//td[contains(text(),'%s')]/preceding-sibling::td[contains(text(),'%s')]/parent::tr");
    By SAVE_BUTTON = By.id("btnSave");
    By ADDRESS_FOR_FLAGS_CHECKBOX = By.xpath("//input[contains(@id,'chkAllAddressForFlags')]");
}
