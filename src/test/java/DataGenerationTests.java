import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.open;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataGenerationTests {
    private String emptyFieldWarning = "Поле обязательно для заполнения";
    private String warningColor = "rgba(255, 92, 92, 1)";
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
        pageOrder.getCityInputSub().shouldBe(Condition.cssValue("color", warningColor));
    }

}
