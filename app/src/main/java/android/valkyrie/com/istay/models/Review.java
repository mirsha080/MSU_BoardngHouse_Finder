package android.valkyrie.com.istay.models;

public class Review {

    private String review_id;
    private String rating;
    private String review;
    private String date_uploaded;

    private String reviewer_full_name;
    private String reviewer_id;
    private String b_house_id;

    public Review() {
    }

    public Review(String review_id, String rating, String review, String date_uploaded, String reviewer_full_name, String reviewer_id, String b_house_id) {
        this.review_id = review_id;
        this.rating = rating;
        this.review = review;
        this.date_uploaded = date_uploaded;
        this.reviewer_full_name = reviewer_full_name;
        this.reviewer_id = reviewer_id;
        this.b_house_id = b_house_id;
    }

    public String getReview_id() {
        return review_id;
    }

    public String getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getDate_uploaded() {
        return date_uploaded;
    }

    public String getReviewer_full_name() {
        return reviewer_full_name;
    }

    public String getReviewer_id() {
        return reviewer_id;
    }

    public String getB_house_id() {
        return b_house_id;
    }
}
