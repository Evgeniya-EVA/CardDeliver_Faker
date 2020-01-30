import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@EqualsAndHashCode
public class RandomUser {
    @Getter(AccessLevel.PROTECTED) private String city;
    @Getter @Setter private String name;
    @Getter private String phone;
    @EqualsAndHashCode.Exclude @Getter @Setter private String date;
    @EqualsAndHashCode.Exclude private Faker faker = new Faker(new Locale("ru"));

    public RandomUser() throws IOException {
        this.name = faker.name().lastName() + " " + faker.name().firstName();
        this.phone = phoneBuilder();
        setCustomizedDate(4); // default value
        setRandomCity();
    }

    private String phoneBuilder(){
        StringBuilder resultPhone = new StringBuilder();
        resultPhone.append("+");
        resultPhone.append(faker.phoneNumber().phoneNumber().substring(1).replaceAll("\\D",""));
        return resultPhone.toString();
    }

    public void setCustomizedDate(int days){
        this.date = setDateFromLocalDate(LocalDate.now().plusDays(days));
    }

    private String setDateFromLocalDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    private void setRandomCity() throws IOException {
        List<String> cityList = getCityList();
        if (!cityList.isEmpty()) {
            Random random = new Random();
            this.city = cityList.get(random.nextInt(cityList.size())).replaceAll("\"", "");
        }
    }

    private List<String> getCityList() throws IOException {
        List<String> cityList = new ArrayList<>();
        Path cityFile = Paths.get("./src/test/resources/validCityData.csv");
        cityList.addAll(Files.readAllLines(cityFile));
        return cityList;
    }

}
