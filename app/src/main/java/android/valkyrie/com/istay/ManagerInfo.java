package android.valkyrie.com.istay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.valkyrie.com.istay.admin.AddBoardingHouse;
import android.valkyrie.com.istay.admin.UpdateManager;
import android.valkyrie.com.istay.database.Constants;
import android.valkyrie.com.istay.models.BoardingHouse;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerInfo extends AppCompatActivity {

    private DatabaseReference boardingHouseRef;
    private DatabaseReference managerRef;

    private TextView toolbar_manager_name, toolbar_manager_address, toolbar_manager_email;
    private FloatingActionButton fabInsertBoardingHouse;
    private Toolbar toolbar;

    private ProgressBar progressBar;

    private RecyclerView boardingHouseList;

    private String MANAGER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_info);

        Intent i = getIntent();
        MANAGER_ID = i.getStringExtra(Constants.MANAGER_ID);

        managerRef = FirebaseDatabase.getInstance().getReference().child(Constants.MANAGER_TREE).child(MANAGER_ID);
        boardingHouseRef = FirebaseDatabase.getInstance().getReference().child(Constants.BOARDING_HOUSE_TREE).child(MANAGER_ID);

        fetchInfo();
        setupToolbar();

        boardingHouseList = findViewById(R.id.recyclerview_manager_info);
        boardingHouseList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressBar = findViewById(R.id.progress_bar_manager_info);
        fabInsertBoardingHouse = findViewById(R.id.fabInsertBH);
        fabInsertBoardingHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToAddBoardingHouse();
            }
        });

    }

    private void fetchInfo() {

        toolbar_manager_name = findViewById(R.id.text_toolbar_manager_name);
        toolbar_manager_address = findViewById(R.id.text_toolbar_manager_address);
        toolbar_manager_email = findViewById(R.id.text_toolbar_manager_email);

        managerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String first_name = dataSnapshot.child("first_name").getValue().toString();
                    String last_name = dataSnapshot.child("last_name").getValue().toString();
                    String address = dataSnapshot.child("address").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();

                    toolbar_manager_name.setText(first_name + " " + last_name);
                    toolbar_manager_address.setText(address);
                    toolbar_manager_email.setText("@" + email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManagerInfo.this, "DATABASE ERROR : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupToolbar() {

        toolbar = findViewById(R.id.toolbar_manager_info);
        toolbar.inflateMenu(R.menu.menu_manager_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Toast.makeText(getApplicationContext(), menuItem.getTitle().toString() + " selected!", Toast.LENGTH_LONG).show();

                switch (menuItem.getItemId()) {

                    case R.id.menu_update_manager:
                        sendUserToUpdateManager();
                        break;

                    case R.id.menu_refresh:

                        break;
                }


                return true;
            }
        });

    }

    private void fetchBoardingHouses(){

        progressBar.setVisibility(View.VISIBLE);

        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<BoardingHouse>()
                        .setQuery(boardingHouseRef, BoardingHouse.class)
                        .build();

        FirebaseRecyclerAdapter<BoardingHouse, BoardingHouseViewHolder> adapter =
                new FirebaseRecyclerAdapter<BoardingHouse, BoardingHouseViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final BoardingHouseViewHolder holder, final int position, @NonNull BoardingHouse model) {

                        String boarding_house_ids = getRef(position).getKey();

                        boardingHouseRef.child(boarding_house_ids).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String house_name = dataSnapshot.child("boarding_house_name").getValue().toString();
                                String location = dataSnapshot.child("location").getValue().toString();

                                holder.boarding_house_name.setText(house_name);
                                holder.boarding_house_address.setText(location);

                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "DATABASE ERROR : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String boarding_house_id = getRef(position).getKey();

                                Intent i = new Intent(getApplicationContext(), BoardingHouseInfo.class);
                                i.putExtra(Constants.BOARDING_HOUSE_ID, boarding_house_id);
                                i.putExtra(Constants.MANAGER_ID, MANAGER_ID);
                                startActivity(i);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public BoardingHouseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_boarding_house_layout, viewGroup, false);
                        BoardingHouseViewHolder holder = new BoardingHouseViewHolder(view);
                        return holder;
                    }
                };

        progressBar.setVisibility(View.GONE);

        boardingHouseList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class BoardingHouseViewHolder extends RecyclerView.ViewHolder{

        TextView boarding_house_name, boarding_house_address;
        Button viewDetails;

        public BoardingHouseViewHolder(@NonNull View itemView) {
            super(itemView);

            boarding_house_name = itemView.findViewById(R.id.text_cardview_bh_name);
            viewDetails = itemView.findViewById(R.id.btnViewBHDetails);
            boarding_house_address = itemView.findViewById(R.id.text_cardview_bh_location);
        }
    }

    private void sendUserToUpdateManager() {
        Intent i = new Intent(getApplicationContext(), UpdateManager.class);
        i.putExtra(Constants.MANAGER_ID, MANAGER_ID);
        startActivity(i);
    }

    private void sendUserToAddBoardingHouse() {
        Intent i = new Intent(getApplicationContext(), AddBoardingHouse.class);
        i.putExtra(Constants.MANAGER_ID, MANAGER_ID);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchBoardingHouses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchBoardingHouses();
    }

}
