package locators;

import org.openqa.selenium.By;

public interface DataCapturePageLocators {
    By SAME_AS_SERVICE_PROVIDER_CHECKBOX = By.xpath("//label[text()='Same as Service Provider']");
    By CASS_ADDRESS_POPUP = By.xpath("//span[text()='CASS Address Found:']");
}
