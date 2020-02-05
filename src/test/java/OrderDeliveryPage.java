import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@NoArgsConstructor
public class OrderDeliveryPage {
    private String emptyFieldWarning = "Поле обязательно для заполнения";
    private String warningColor = "rgba(255, 92, 92, 1)";

    @Getter private SelenideElement cityInput = $("[data-test-id='city'] input");
    @Getter private SelenideElement cityInput_sub = $("[data-test-id='city']").$(".input__sub");
    @Getter private SelenideElement dateInput = $("[data-test-id='date']").$(By.cssSelector("input"));
    @Getter private SelenideElement dateInput_sub = $("[data-test-id='date']").$(".input__sub");
    @Getter private SelenideElement nameInput = $("[data-test-id='name'] input");
    @Getter private SelenideElement nameInput_sub = $("[data-test-id='name']").$(".input__sub");
    @Getter private SelenideElement phoneInput = $("[data-test-id='phone'] input");
    @Getter private SelenideElement phoneInput_sub = $("[data-test-id='phone']").$(".input__sub");
    @Getter private SelenideElement agreement = $("[data-test-id=\"agreement\"]");
    @Getter private SelenideElement agreementCheckbox_text = $("[data-test-id=\"agreement\"]").$(".checkbox__text");
    @Getter private SelenideElement reservationBtn = $(byText("Запланировать"));
    @Getter private SelenideElement replanBtn = $(byText("Перепланировать"));
    @Getter private SelenideElement successNotification = $("[data-test-id=\"success-notification\"]");
    @Getter private SelenideElement replanNotification = $("[data-test-id=\"replan-notification\"]");

    public void setElementsValues(RandomUser user, boolean isChecked){
        this.setInputValue(this.cityInput, user.getCity());
        this.setDateInput(user.getDate());
        this.setInputValue(this.nameInput, user.getName());
        this.setInputValue(this.phoneInput, user.getPhone());
        if (isChecked) {
            this.setAgreementChecked();
        }
    }

    private void setInputValue(SelenideElement element, String value){
        element.setValue(value);
    }

    public void setDateInput(String date){
        formatDate(date);
    }

    private void formatDate (String date){
        this.dateInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        if (date != null) { // для того, чтобы при необходимости проверить отправку формы с пустым полем "дата"
            this.dateInput.sendKeys(date);
        }
    }

    private void  setAgreementChecked(){
        agreement.click();
    }

    public void clickReservationButton(){
        this.getReservationBtn().click();
    }

    public void clickReplanButton(){
        this.getReplanBtn().click();
    }


    // ожидания

    public void waitUntilPageLoaded(){
        this.getCityInput().waitUntil(Condition.visible,20000);
    }

    public void waitUntilSuccessNotificationAppears(){
        this.successNotification.waitUntil(Condition.appears, 20000);
    }

    public void waitUntilReplanNotificationAppears(){
        this.getReplanNotification().waitUntil(Condition.appears, 15000);
    }


    // ошибки заполнения полей

    public void cityInputSubColorShouldBeWarningColor(){
        this.getCityInput_sub().shouldBe(Condition.cssValue("color", warningColor));
    }

    public void cityInputSubShouldHaveWarningMessage(){
        this.getCityInput_sub().shouldHave(Condition.exactText(emptyFieldWarning));
    }

    public void cityInputSubInvalidValueWarningMessage(){
        this.getCityInput_sub().shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    public void dateInputSubColorShouldBeWarningColor(){
        this.getDateInput_sub().shouldBe(Condition.cssValue("color", warningColor));
    }

    public void dateInputSubShouldHaveInvalidWarningMessage(){
        this.getDateInput_sub().shouldHave(Condition.exactText("Неверно введена дата"));
    }

    public void dateInputSubShouldHaveWarningMessage(){
        this.getDateInput_sub().shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    public void nameInputSubColorShouldBeWarningColor(){
        this.getNameInput_sub().shouldBe(Condition.cssValue("color", warningColor));
    }

    public void nameInputSubShouldHaveWarningMessage(){
        this.getNameInput_sub().shouldHave(Condition.exactText(emptyFieldWarning));
    }

    public void nameInputSubInvalidValueWarningMessage(){
        this.getNameInput_sub().shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    public void phoneInputSubShouldHaveWarningMessage(){
        this.getPhoneInput_sub().shouldHave(Condition.exactText(emptyFieldWarning));
    }

    public void phoneInputSubColorShouldBeWarningColor(){
        this.getPhoneInput_sub().shouldBe(Condition.cssValue("color", warningColor));
    }

    public void phoneInputSubInvalidValueWarningMessage(){
        this.getPhoneInput_sub().shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, " +
                "например, +79012345678."));
    }

    public void agreementCheckboxTextColorShouldBeWarningColor(){
        this.getAgreementCheckbox_text().shouldBe(Condition.cssValue("color", warningColor));
    }
}
