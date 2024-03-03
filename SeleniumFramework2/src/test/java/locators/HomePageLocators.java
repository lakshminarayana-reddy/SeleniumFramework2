package locators;

import org.openqa.selenium.By;

public interface HomePageLocators {
    By HEADER_TXT = By.xpath("//a[contains(text(),'MedFlow Adjuster')]");
    String FRAME2 = "srframe";
    String FRAME1 = "frame1";
    By INTERNAL_LINK = By.xpath("//span[text()='Internal']");
    By DYNAMIC_LINK = By.xpath("//span[contains(text(),'%s')]");
}
