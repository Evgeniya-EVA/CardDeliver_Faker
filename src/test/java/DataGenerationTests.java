import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Condition;

import java.io.IOException;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.*;

public class DataGenerationTests {
    private String emptyFieldWarning = "Поле обязательно для заполнения";
    private String WARNINGCOLOR = "rgba(255, 92, 92, 1)";
    private OrderDeliveryPage pageOrder;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        pageOrder = new OrderDeliveryPage();
    }

    @Test
    void validDataTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNotification().waitUntil(Condition.appears, 20000);
    }

    @Test
    void emptyCityWarningColorTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                null,
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getCityInputSub().shouldBe(Condition.cssValue("color", WARNINGCOLOR));
    }

    @Test
    void invalidNowMinusOneDateTest() throws IOException {
        RandomUser user = new RandomUser();
        user.setCustomizedDate(-1);
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getCityInputSub().shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidNowDateTest()throws IOException {
        RandomUser user = new RandomUser();
        user.setCustomizedDate(0);
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getCityInputSub().shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidNowPlusOneDateTest() throws IOException {
        RandomUser user = new RandomUser();
        user.setCustomizedDate(1);
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getCityInputSub().shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidNowPlusTwoDateTest() throws IOException {
        RandomUser user = new RandomUser();
        user.setCustomizedDate(2);
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getCityInputSub().shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void changeToLaterDateTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
        user.setCustomizedDate(7);
        pageOrder.changeDate(user.getDate());
        pageOrder.getReservation().click();
        pageOrder.getReplanNotification().waitUntil(Condition.visible, 15000);
        pageOrder.getReplan().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
    }

    @Test
    void ChangeToEarlierDateTest() throws IOException {
        RandomUser user = new RandomUser();
        user.setCustomizedDate(7);
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
        user.setCustomizedDate(3);
        pageOrder.changeDate(user.getDate());
        pageOrder.getReservation().click();
        pageOrder.getReplanNotification().waitUntil(Condition.visible, 15000);
        pageOrder.getReplan().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
    }

    @Test
    void ChangeToInvalidEarlierDateTest() throws IOException {
        RandomUser user = new RandomUser();
        user.setCustomizedDate(7);
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
        user.setCustomizedDate(2);
        pageOrder.changeDate(user.getDate());
        pageOrder.getReservation().click();
        pageOrder.getDateInputSub().shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void ChangeToTheSameDateTest() throws IOException {
        RandomUser user = new RandomUser();
        user.setCustomizedDate(7);
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
        pageOrder.changeDate(user.getDate());
        pageOrder.getReservation().click();
        pageOrder.getReplanNotification().waitUntil(Condition.visible, 15000);
        pageOrder.getReplan().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
    }

    @Test
    void changeToLaterDateAfterPageRefreshTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
        refresh();
        user.setCustomizedDate(7);
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getReplanNotification().waitUntil(Condition.visible, 15000);
        pageOrder.getReplan().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
    }

    @Test
    void changeToLaterDateAfterBrowserReopenTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
        Selenide.close();
        open("http://localhost:9999");
        pageOrder = new OrderDeliveryPage();
        pageOrder.getCityInput().waitUntil(Condition.visible, 15000);
        user.setCustomizedDate(7);
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getReplanNotification().waitUntil(Condition.visible, 15000);
        pageOrder.getReplan().click();
        pageOrder.getNotification().waitUntil(Condition.visible, 15000);
        String string = user.getDate();
    }

    @Test
    void emptyCityTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                null,
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getCityInputSub().shouldHave(Condition.exactText(emptyFieldWarning));
    }

    @Test
    void emptyNameTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                null,
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNameInputSub().shouldHave(Condition.exactText(emptyFieldWarning));
    }

    @Test
    void emptyNameWarningColorTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                null,
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNameInputSub().shouldBe(Condition.cssValue("color", WARNINGCOLOR));
    }

    @Test
    void emptyPhoneTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                null,
                true);
        pageOrder.getReservation().click();
        pageOrder.getPhoneInputSub().shouldHave(Condition.exactText(emptyFieldWarning));
    }

    @Test
    void emptyPhoneWarningColorTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                null,
                true);
        pageOrder.getReservation().click();
        pageOrder.getPhoneInputSub().shouldBe(Condition.cssValue("color", WARNINGCOLOR));
    }

    @Test
    void noCheckAgreementTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                user.getPhone(),
                false);
        pageOrder.getReservation().click();
        pageOrder.getAgreementCheckBox().shouldBe(Condition.cssValue("color", WARNINGCOLOR));
    }

    @Test
    void allEmptyTest() throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                null,
                null,
                null,
                null,
                false);
        pageOrder.getReservation().click();
        pageOrder.getCityInputSub().shouldHave(Condition.exactText(emptyFieldWarning));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/validCityData.csv")
    void validCityTest(String city) throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                city,
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNotification().waitUntil(Condition.appears, 15000);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidCityData.csv")
    void invalidCityTest(String city) throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                city,
                user.getDate(),
                user.getName(),
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getCityInputSub().shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/validNameData.csv")
    void validNameTest(String name) throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                name,
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNotification().waitUntil(Condition.appears, 15000);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidNameData.csv")
    void invalidNameTest(String name) throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                name,
                user.getPhone(),
                true);
        pageOrder.getReservation().click();
        pageOrder.getNameInputSub()
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. " +
                        "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPhoneData.csv")
    void invalidPhoneTest(String phone) throws IOException {
        RandomUser user = new RandomUser();
        pageOrder.setElementsValues(
                user.getCity(),
                user.getDate(),
                user.getName(),
                phone,
                true);
        pageOrder.getReservation().click();
        pageOrder.getPhoneInputSub()
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

}
