package android.valkyrie.com.istay.models;

public class BoardingHouse {

    private String boarding_house_id;
    private String boarding_house_name;
    private String location;
    private String description;
    private String allowed_gender;
    private String price_per_person;
    private String price_per_room;
    private String owner_id;

    public BoardingHouse() {
    }

    public BoardingHouse(String boarding_house_id, String boarding_house_name, String location, String description, String allowed_gender, String price_per_person, String price_per_room, String owner_id) {
        this.boarding_house_id = boarding_house_id;
        this.boarding_house_name = boarding_house_name;
        this.location = location;
        this.description = description;
        this.allowed_gender = allowed_gender;
        this.price_per_person = price_per_person;
        this.price_per_room = price_per_room;
        this.owner_id = owner_id;
    }

    public String getBoarding_house_id() {
        return boarding_house_id;
    }

    public void setBoarding_house_id(String boarding_house_id) {
        this.boarding_house_id = boarding_house_id;
    }

    public String getBoarding_house_name() {
        return boarding_house_name;
    }

    public void setBoarding_house_name(String boarding_house_name) {
        this.boarding_house_name = boarding_house_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAllowed_gender() {
        return allowed_gender;
    }

    public void setAllowed_gender(String allowed_gender) {
        this.allowed_gender = allowed_gender;
    }

    public String getPrice_per_person() {
        return price_per_person;
    }

    public void setPrice_per_person(String price_per_person) {
        this.price_per_person = price_per_person;
    }

    public String getPrice_per_room() {
        return price_per_room;
    }

    public void setPrice_per_room(String price_per_room) {
        this.price_per_room = price_per_room;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }
}