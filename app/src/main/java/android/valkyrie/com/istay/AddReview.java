package android.valkyrie.com.istay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.valkyrie.com.istay.database.Constants;
import android.valkyrie.com.istay.models.Manager;
import android.valkyrie.com.istay.models.Review;
import android.valkyrie.com.istay.models.User;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class AddReview extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;

    private TextView txtUserFullName;
    private EditText txtUserReview;
    private RatingBar ratingBarValue;

    private MaterialButton btnSubmit, btnCancel;

    private ProgressBar progressBar;

    private String USER_ID, BOARDING_HOUSE_ID;

    private String userFullName = "Unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_review);

        firebaseDatabase = FirebaseDatabase.getInstance();

        progressBar = findViewById(R.id.progress_bar_add_review);

        Intent i = getIntent();
        USER_ID = i.getStringExtra(Constants.USER_ID);
        BOARDING_HOUSE_ID = i.getStringExtra(Constants.BOARDING_HOUSE_ID);

        txtUserFullName = findViewById(R.id.txt_rater);
        txtUserReview = findViewById(R.id.txt_new_review);
        ratingBarValue = findViewById(R.id.ratingbar_new_rating);

        btnSubmit = findViewById(R.id.btnAddReview);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview();

            }
        });

    }

    private void submitReview() {

        DatabaseReference reviewRef = firebaseDatabase.getReference(Constants.REVIEW_TREE).child(BOARDING_HOUSE_ID);

        progressBar.setVisibility(View.VISIBLE);

        String review_id = reviewRef.push().getKey();
        String rating = ratingBarValue.getRating() + "";
        String review = txtUserReview.getText().toString();
        String date_uploaded = DateFormat.getDateTimeInstance().format(new Date());

        String reviewer_full_name = getReviewerName();
        String reviewer_id = USER_ID;
        String boarding_house_id = BOARDING_HOUSE_ID;

        Review newReview = new Review(review_id, rating, review, date_uploaded, reviewer_full_name, reviewer_id, boarding_house_id);

        reviewRef.child(review_id).setValue(newReview, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Review added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private String getReviewerName(){

        DatabaseReference userRef = firebaseDatabase.getReference(Constants.USER_TREE).child(USER_ID);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                userFullName = user.getFull_name();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database Error!!", Toast.LENGTH_SHORT).show();
            }
        });

        return userFullName;
    }


}
