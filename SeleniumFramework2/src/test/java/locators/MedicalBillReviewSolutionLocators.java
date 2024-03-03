package locators;

import org.openqa.selenium.By;

public interface MedicalBillReviewSolutionLocators {
    By MEMBER_NUMBER_INPUT = By.id("ctrlSearch_txtCLMN");
    By DOC_ID_INPUT = By.id("ctrlSearch_txtDOC_ID");
    By SEARCH_BTN = By.id("ctrlSearch_btnSearch");
    By DYNAMIC_LINK = By.xpath("//span[text()='%s']");
    By EOR_DOCUMENT = By.cssSelector("tr>#EOR>img");
    By IMAGE = By.cssSelector("tr>#Image>img");
    By NEXT_LINK = By.xpath("//a[contains(@href,'doPostBack') and text()='%s']");
    By FRAME2 = By.id("RAD_SPLITTER_PANE_EXT_CONTENT_rdpPages");
    By MORE_LINK = By.xpath("//a[text()='DraftEOB']/../../td/a[text()='More']");
    By REPORT_TYPE = By.xpath("//a[text()='Report Type']");
    By PDF = By.cssSelector("table.rgMasterTable input[type='image']");
    By PRINT = By.xpath("//input[@title='Printer Friendly Version']");
    By DRAFT_EOB_CREATED_DATE = By.xpath("//td[contains(text(),'%s')]");
    By DRAFT_EOB_3M = By.xpath("//td[text()='3M']/following-sibling::td[contains(text(),'%s')]");
    By LAST_PAGE = By.xpath("//div[contains(@class,'rgNumPart')]/a[last()]");
    By UPLOAD_IMAGE = By.id("ctrlGrid_imgUpload");
    By FILE_INPUT = By.xpath("//input[@class='ruFileInput']");
    By UPLOAD_BUTTON = By.id("btnUpload");
    By SELECT_LABEL = By.xpath("//select[@title='Select Label']");
    By CLIENT_RECEIVED_DATE = By.xpath("//input[@title='Client Received Date']");
    By SUBMIT_BUTTON = By.id("btnSubmit");
    By IMAGE_UPLOADED = By.xpath("//span[contains(text(),'uploaded successfully')]");
}
