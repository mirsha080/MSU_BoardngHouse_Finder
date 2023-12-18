package android.valkyrie.com.istay.models;

public class Favorite{

    private String favorite_id;
    private String user_id;
    private String manager_id;
    private String boarding_house_id;
    private String date_added;

    public Favorite() {
    }

    public Favorite(String favorite_id, String user_id, String manager_id, String boarding_house_id, String date_added) {
        this.favorite_id = favorite_id;
        this.user_id = user_id;
        this.manager_id = manager_id;
        this.boarding_house_id = boarding_house_id;
        this.date_added = date_added;
    }

    public String getFavorite_id() {
        return favorite_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getManager_id() {
        return manager_id;
    }

    public String getBoarding_house_id() {
        return boarding_house_id;
    }

    public String getDate_added() {
        return date_added;
    }
}
