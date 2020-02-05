import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
public class RandomUser {
    @Getter @Setter private String city;
    @Getter @Setter private String name;
    @Getter @Setter private String phone;
    @EqualsAndHashCode.Exclude @Getter private String date;

    public RandomUser getAllFieldsFilledUser(){
        this.city = RandomUserData.getCity();
        this.date = RandomUserData.getDate(4);
        this.name = RandomUserData.getName();
        this.phone = RandomUserData.getPhone();
        return this;
    }

    public RandomUser getEmptyCityUser(){
        this.city = "";
        this.date = RandomUserData.getDate(4);
        this.name = RandomUserData.getName();
        this.phone = RandomUserData.getPhone();
        return this;
    }

    public RandomUser getEmptyDateUser(){
        this.city = RandomUserData.getCity();
        this.name = RandomUserData.getName();
        this.phone = RandomUserData.getPhone();
        return this;
    }

    public RandomUser getEmptyNameUser(){
        this.city = RandomUserData.getCity();
        this.name = "";
        this.date = RandomUserData.getDate(4);
        this.phone = RandomUserData.getPhone();
        return this;
    }

    public RandomUser getEmptyPhoneUser(){
        this.city = RandomUserData.getCity();
        this.date = RandomUserData.getDate(4);
        this.name = RandomUserData.getName();
        this.phone = "";
        return this;
    }

    public void changeDate(int days){
        this.date = RandomUserData.getDate(days);
    }

}
