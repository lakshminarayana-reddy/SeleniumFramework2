package locators;

import org.openqa.selenium.By;

public interface InboxPageLocators {
    By DOC_HEADER = By.xpath("//td[@title='Launch CFV Doc List']");
    By EDITEOR_LINK = By.xpath("//td[@id='Edit EOR']/a[contains(@onclick,'DocID=%s')]");
    By EOR_HEADER = By.id("lblEOR");
    By DOC_EOR = By.xpath("//td[@id='DocId' and contains(.,'%s')]/preceding-sibling::td/img[@title='EOR']");
    By NEXT_PAGE_LINK = By.xpath("//span/following-sibling::a[contains(@href,'doPostBack')]");
}
