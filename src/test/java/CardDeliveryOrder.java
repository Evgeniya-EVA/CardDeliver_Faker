import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrder {

    private OrderDeliveryPage pageOrder;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        pageOrder = new OrderDeliveryPage();
    }

    @Test
    void validDataTest(){
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilSuccessNotificationAppears();
    }

    @Test
    void emptyCityWarningColorTest() {
        RandomUser user = new RandomUser().getEmptyCityUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.cityInputSubColorShouldBeWarningColor();
    }

    @Test
    void emptyCityWarningMessageTest(){
        RandomUser user = new RandomUser().getEmptyCityUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.cityInputSubShouldHaveWarningMessage();
    }

    @Test
    void emptyDateWarningMessageTest(){
        RandomUser user = new RandomUser().getEmptyDateUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.dateInputSubShouldHaveInvalidWarningMessage();
    }

    @Test
    void emptyDateWarningColorMessageTest(){
        RandomUser user = new RandomUser().getEmptyDateUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.dateInputSubColorShouldBeWarningColor();
    }

    @Test
    void invalidNowMinusOneDateTest(){
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        user.changeDate(-1);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.dateInputSubShouldHaveWarningMessage();
    }

    @Test
    void invalidNowDateTest(){
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        user.changeDate(0);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.dateInputSubShouldHaveWarningMessage();
    }

    @Test
    void invalidNowPlusOneDateTest(){
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        user.changeDate(1);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.dateInputSubShouldHaveWarningMessage();
    }

    @Test
    void invalidNowPlusTwoDateTest(){
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        user.changeDate(2);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.dateInputSubShouldHaveWarningMessage();
    }

    @Test
    void changeToLaterDateTest() {
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilSuccessNotificationAppears();
        user.changeDate(7);
        pageOrder.setDateInput(user.getDate());
        pageOrder.clickReservationButton();
        pageOrder.waitUntilReplanNotificationAppears();
        pageOrder.clickReplanButton();
        pageOrder.waitUntilSuccessNotificationAppears();
    }

    @Test
    void ChangeToEarlierDateTest() {
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        user.changeDate(7);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilSuccessNotificationAppears();
        user.changeDate(3);
        pageOrder.setDateInput(user.getDate());
        pageOrder.clickReservationButton();
        pageOrder.waitUntilReplanNotificationAppears();
        pageOrder.clickReplanButton();
        pageOrder.waitUntilSuccessNotificationAppears();
    }

    @Test
    void ChangeToInvalidEarlierDateTest() {
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        user.changeDate(7);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilSuccessNotificationAppears();
        user.changeDate(2);
        pageOrder.setDateInput(user.getDate());
        pageOrder.clickReservationButton();
        pageOrder.dateInputSubShouldHaveWarningMessage();
    }

    @Test
    void ChangeToTheSameDateTest(){
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        user.changeDate(7);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilSuccessNotificationAppears();
        pageOrder.setDateInput(user.getDate());
        pageOrder.clickReservationButton();
        pageOrder.waitUntilReplanNotificationAppears();
        pageOrder.clickReplanButton();
        pageOrder.waitUntilSuccessNotificationAppears();
    }

    @Test
    void changeToLaterDateAfterPageRefreshTest(){
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilSuccessNotificationAppears();
        refresh();
        user.changeDate(7);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilReplanNotificationAppears();
        pageOrder.clickReplanButton();
        pageOrder.waitUntilSuccessNotificationAppears();
    }

    @Test
    void changeToLaterDateAfterBrowserReopenTest() {
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilSuccessNotificationAppears();
        Selenide.close();
        open("http://localhost:9999");
        pageOrder = new OrderDeliveryPage();
        pageOrder.waitUntilPageLoaded();
        user.changeDate(7);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilReplanNotificationAppears();
        pageOrder.clickReplanButton();
        pageOrder.waitUntilSuccessNotificationAppears();
    }

    @Test
    void emptyNameWarningMessageTest(){
        RandomUser user = new RandomUser().getEmptyNameUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.nameInputSubShouldHaveWarningMessage();
    }

    @Test
    void emptyNameWarningColorTest(){
        RandomUser user = new RandomUser().getEmptyNameUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.nameInputSubColorShouldBeWarningColor();
    }

    @Test
    void emptyPhoneWarningMessageTest() {
        RandomUser user = new RandomUser().getEmptyPhoneUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.phoneInputSubShouldHaveWarningMessage();
    }

    @Test
    void emptyPhoneWarningColorTest(){
        RandomUser user = new RandomUser().getEmptyPhoneUser();
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.phoneInputSubColorShouldBeWarningColor();
    }

    @Test
    void noCheckAgreementTest(){
        RandomUser user = new RandomUser().getAllFieldsFilledUser();
        pageOrder.setElementsValues(user, false);
        pageOrder.clickReservationButton();
        pageOrder.agreementCheckboxTextColorShouldBeWarningColor();
    }

    @Test
    void allEmptyFieldsTest(){
        pageOrder.clickReservationButton();
        pageOrder.cityInputSubShouldHaveWarningMessage();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/validCityData.csv")
    void validCityTest(String city){
        RandomUser user = new RandomUser().getEmptyCityUser();
        user.setCity(city);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilSuccessNotificationAppears();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidCityData.csv")
    void invalidCityTest(String city){
        RandomUser user = new RandomUser().getEmptyCityUser();
        user.setCity(city);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.cityInputSubInvalidValueWarningMessage();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/validNameData.csv")
    void validNameTest(String name){
        RandomUser user = new RandomUser().getEmptyNameUser();
        user.setName(name);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.waitUntilSuccessNotificationAppears();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidNameData.csv")
    void invalidNameTest(String name){
        RandomUser user = new RandomUser().getEmptyNameUser();
        user.setName(name);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.nameInputSubInvalidValueWarningMessage();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPhoneData.csv")
    void invalidPhoneTest(String phone){
        RandomUser user = new RandomUser().getEmptyPhoneUser();
        user.setPhone(phone);
        pageOrder.setElementsValues(user, true);
        pageOrder.clickReservationButton();
        pageOrder.phoneInputSubInvalidValueWarningMessage();
    }

}
