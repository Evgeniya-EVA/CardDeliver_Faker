import com.github.javafaker.Faker;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RandomUser {
    String city;
    String name;
    String phone;
    String date;
    Faker faker = new Faker(new Locale("ru"));

    public RandomUser() {
        this.name = faker.name().fullName();
        this.phone = faker.phoneNumber().phoneNumber();
        this.date = setDateFromLocalDate(LocalDate.now().plusDays(4));
    }

    private String setDateFromLocalDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    public void setDateFromString(String date){
        this.date = date;
    }

    private String setCity(){
        File cityFile = new java.io.File("validCityData.csv");
        return
    }

    private String getCityList(){

    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }
}
