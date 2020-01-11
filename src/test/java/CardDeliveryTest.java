import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    private Map<String, SelenideElement> elementsList = new HashMap<>();
    private String emptyFieldWarning = "Поле обязательно для заполнения";
    private String warningColor = "rgba(255, 92, 92, 1)";

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        elementsList.put("city", $("[data-test-id=\"city\"]"));
        elementsList.put("name", $("[data-test-id=\"name\"]"));
        elementsList.put("date", $("[data-test-id=\"date\"]"));
        elementsList.put("phone", $("[data-test-id=\"phone\"]"));
        elementsList.put("agreement", $("[data-test-id=\"agreement\"]"));
        elementsList.put("reservation", $(byText("Забронировать")));
      //  elementsList.put("notification", $("[data-test-id=\"notification\"]"));
    }

    @AfterEach
    void tearDown(){
        elementsList.clear();
    }

    private String formatDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter).toString();
    }

    @Test
    void validDataTest(){
        elementsList.get("city").$(By.cssSelector("input")).setValue("Москва");
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(formatDate(LocalDate.now().plusDays(3)));
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        $("[data-test-id=\"notification\"]").waitUntil(Condition.appears, 15000);
    }

    @Test
    void invalidNowMinusOneDateTest(){
        elementsList.get("city").$(By.cssSelector("input")).setValue("Москва");
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(formatDate(LocalDate.now().plusDays(-1)));
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("date").$(".input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidNowDateTest(){
        elementsList.get("city").$(By.cssSelector("input")).setValue("Москва");
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(formatDate(LocalDate.now()));
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("date").$(".input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidNowPlusOneDateTest(){
        elementsList.get("city").$(By.cssSelector("input")).setValue("Москва");
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(formatDate(LocalDate.now().plusDays(1)));
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("date").$(".input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidNowPlusTwoDateTest(){
        elementsList.get("city").$(By.cssSelector("input")).setValue("Москва");
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(formatDate(LocalDate.now().plusDays(2)));
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("date").$(".input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void emptyCityTest(){
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("city").$(".input__sub").shouldHave(Condition.exactText(emptyFieldWarning));
    }

    @Test
    void emptyCityWarningColorTest(){
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("city").$(".input__sub").shouldBe(Condition.cssValue("color", warningColor));
    }

    @Test
    void emptyNameTest(){
        elementsList.get("city").$(By.cssSelector("input")).setValue("Москва");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("name").$(".input__sub").shouldHave(Condition.exactText(emptyFieldWarning));
    }

    @Test
    void emptyNameWarningColorTest(){
        elementsList.get("city").$(By.cssSelector("input")).setValue("Москва");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("name").$(".input__sub").shouldBe(Condition.cssValue("color", warningColor));
    }

    @Test
    void emptyPhoneTest(){
        elementsList.get("city").$(By.cssSelector("input")).setValue("Москва");
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("phone").$(".input__sub").shouldHave(Condition.exactText(emptyFieldWarning));
    }

    @Test
    void emptyPhoneWarningColorTest(){
        elementsList.get("city").$(By.cssSelector("input")).setValue("Москва");
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("phone").$(".input__sub").shouldBe(Condition.cssValue("color", warningColor));
    }

    @Test
    void noCheckAgreementTest(){
        elementsList.get("city").$(By.cssSelector("input")).setValue("Москва");
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("reservation").click();
        elementsList.get("agreement").$(".checkbox__text").shouldBe(Condition.cssValue("color", warningColor));
    }

    @Test
    void allEmptyTest(){
        elementsList.get("date").$(By.cssSelector("input")).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        elementsList.get("reservation").click();
        elementsList.get("city").$(".input__sub").shouldHave(Condition.exactText(emptyFieldWarning));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/validCityData.csv")
    void validCityTest(String city){
        elementsList.get("city").$(By.cssSelector("input")).setValue(city);
        elementsList.get("name").$(By.cssSelector("input")).setValue("Иванов Иван");
        elementsList.get("phone").$(By.cssSelector("input")).setValue("+79250000000");
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        $("[data-test-id=\"notification\"]").waitUntil(Condition.appears, 15000);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidCityData.csv", numLinesToSkip = 1)
    void invalidCityTest(String city, String name, String phone){
        elementsList.get("city").$(By.cssSelector("input")).setValue(city);
        elementsList.get("name").$(By.cssSelector("input")).setValue(name);
        elementsList.get("phone").$(By.cssSelector("input")).setValue(phone);
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("city").$(".input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidNameData.csv", numLinesToSkip = 1)
    void invalidNameTest(String city, String name, String phone){
        elementsList.get("city").$(By.cssSelector("input")).setValue(city);
        elementsList.get("name").$(By.cssSelector("input")).setValue(name);
        elementsList.get("phone").$(By.cssSelector("input")).setValue(phone);
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("name").$(".input__sub")
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPhoneData.csv", numLinesToSkip = 1)
    void invalidPhoneTest(String city, String name, String phone){
        elementsList.get("city").$(By.cssSelector("input")).setValue(city);
        elementsList.get("name").$(By.cssSelector("input")).setValue(name);
        elementsList.get("phone").$(By.cssSelector("input")).setValue(phone);
        elementsList.get("agreement").click();
        elementsList.get("reservation").click();
        elementsList.get("phone").$(".input__sub")
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}
