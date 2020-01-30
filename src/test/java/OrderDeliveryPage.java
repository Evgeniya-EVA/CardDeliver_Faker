import com.codeborne.selenide.Driver;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@NoArgsConstructor
public class OrderDeliveryPage {
    @Getter private SelenideElement city = $("[data-test-id=\"city\"]");
    @Getter private SelenideElement date = $("[data-test-id=\"date\"]");
    @Getter private SelenideElement name = $("[data-test-id=\"name\"]");
    @Getter private SelenideElement phone = $("[data-test-id=\"phone\"]");
    @Getter private SelenideElement agreement = $("[data-test-id=\"agreement\"]");
    @Getter private SelenideElement reservation = $(byText("Запланировать"));
    @Getter private SelenideElement replan = $(byText("Перепланировать"));
    @Getter private SelenideElement notification = $("[data-test-id=\"success-notification\"]");
    @Getter private SelenideElement replanNotification = $("[data-test-id=\"replan-notification\"]");


    public SelenideElement getCityInput(){
        return this.city.$(By.cssSelector("input"));
    }

    public SelenideElement getDateInput(){
        return this.date.$(By.cssSelector("input"));
    }

    public SelenideElement getNameInput(){
        return this.name.$(By.cssSelector("input"));
    }

    public SelenideElement getPhoneInput(){
        return this.phone.$(By.cssSelector("input"));
    }

    public SelenideElement getAgreementCheckBox(){
        return this.agreement.$(".checkbox__text");
    }

    public void changeDate(String date){
        this.getDateInput().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        this.getDateInput().sendKeys(date);
    }

    public void setElementsValues(String city, String date, String name, String phone, Boolean checkedAgreement){
        if (city != null) {
            this.getCityInput().setValue(city);
        }
        if (date != null) {
            this.getDateInput().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            this.getDateInput().sendKeys(date);
        }
        if (name != null) {
            this.getNameInput().setValue(name);
        }
        if (phone != null) {
            this.getPhoneInput().setValue(phone);
        }
        if (checkedAgreement) {
            this.getAgreement().click();
        }
    }

    public SelenideElement getCityInputSub(){
        return this.city.$(".input__sub");
    }

    public SelenideElement getDateInputSub(){
        return this.date.$(".input__sub");
    }

    public SelenideElement getNameInputSub(){
        return this.name.$(".input__sub");
    }

    public SelenideElement getPhoneInputSub(){
        return this.phone.$(".input__sub");
    }
}
