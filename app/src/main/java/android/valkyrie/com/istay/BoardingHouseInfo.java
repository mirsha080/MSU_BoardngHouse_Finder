package android.valkyrie.com.istay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.valkyrie.com.istay.database.Constants;
import android.valkyrie.com.istay.models.BoardingHouse;
import android.valkyrie.com.istay.models.Favorite;
import android.valkyrie.com.istay.models.Review;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BoardingHouseInfo extends AppCompatActivity {

    private DatabaseReference boardingHouseRef, managerRef;

    private String BOARDING_HOUSE_ID, OWNER_ID;

    private Toolbar toolbar;

    private ProgressBar progressBar;

    private TextView header, bh_location, bh_gender, bh_description, bh_person_price, bh_room_price,
                man_fullname, man_address, man_email, man_contact_no, link_reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding_house_info);

        Intent i = getIntent();
        BOARDING_HOUSE_ID = i.getStringExtra(Constants.BOARDING_HOUSE_ID);
        OWNER_ID = i.getStringExtra(Constants.MANAGER_ID);

        boardingHouseRef = FirebaseDatabase.getInstance().getReference().child(Constants.BOARDING_HOUSE_TREE).child(OWNER_ID).child(BOARDING_HOUSE_ID);
        managerRef = FirebaseDatabase.getInstance().getReference().child(Constants.MANAGER_TREE).child(OWNER_ID);

        toolbar = findViewById(R.id.toolbar_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Boarding House info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.load_info);

        header = findViewById(R.id.header);
        bh_location = findViewById(R.id.bh_location);
        bh_gender = findViewById(R.id.bh_gender);
        bh_description = findViewById(R.id.bh_description);
        bh_person_price = findViewById(R.id.bh_person_price);
        bh_room_price = findViewById(R.id.bh_room_price);
        man_fullname = findViewById(R.id.man_fullname);
        man_address = findViewById(R.id.man_address);
        man_email =findViewById(R.id.man_email);
        man_contact_no = findViewById(R.id.man_contact_no);
        link_reviews = findViewById(R.id.link_reviews);

    }

    private void displayDetails() {

        progressBar.setVisibility(View.VISIBLE);

        boardingHouseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String house_name = dataSnapshot.child("boarding_house_name").getValue().toString();
                    String loc = dataSnapshot.child("location").getValue().toString();
                    String desc = dataSnapshot.child("description").getValue().toString();
                    String gen = dataSnapshot.child("allowed_gender").getValue().toString();
                    String person_price = dataSnapshot.child("price_per_person").getValue().toString();
                    String room_price = dataSnapshot.child("price_per_room").getValue().toString();

                    header.setText(house_name);
                    bh_location.setText(loc);
                    bh_description.setText(desc);
                    bh_gender.setText(gen);
                    bh_person_price.setText(person_price);
                    bh_room_price.setText(room_price);

                    managerRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                String first_name = dataSnapshot.child("first_name").getValue().toString();
                                String last_name = dataSnapshot.child("last_name").getValue().toString();
                                String address = dataSnapshot.child("address").getValue().toString();
                                String email = dataSnapshot.child("email").getValue().toString();
                                String contact = dataSnapshot.child("contact").getValue().toString();

                                man_fullname.setText(first_name + " " + last_name);
                                man_address.setText(address);
                                man_email.setText(email);
                                man_contact_no.setText(contact);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(BoardingHouseInfo.this, "Database ERROR : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BoardingHouseInfo.this, "DATABASE ERROR : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        progressBar.setVisibility(View.GONE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayDetails();
    }
}
