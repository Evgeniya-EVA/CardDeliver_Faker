import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RandomUserData {
    private static String[] cities = {"Майкоп",
            "Горно-Алтайск",
            "Уфа",
            "Улан-Удэ",
            "Махачкала",
            "Магас",
            "Нальчик",
            "Элиста",
            "Черкесск",
            "Петрозаводск"};
    private static List<String> cityList = Arrays.asList(cities);
    private static Faker faker = new Faker(new Locale("ru"));

    public static String getCity(){
        Random random = new Random();
        return cityList.get(random.nextInt(cityList.size()));
    }

    public static String getDate(int days){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now().plusDays(days);
        return date.format(formatter);
    }

    public static String getName(){
        StringBuilder name = new StringBuilder();
        name.append(faker.name().lastName());
        name.append(" ");
        name.append(faker.name().firstName());
        return name.toString().replaceAll("ё", "е");
    }

    public static String getPhone(){
        return faker.phoneNumber().phoneNumber().substring(1).replaceAll("\\D && [^+]","");
    }



}
