package android.valkyrie.com.istay.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.valkyrie.com.istay.BoardingHouseInfo;
import android.valkyrie.com.istay.R;
import android.valkyrie.com.istay.database.Constants;
import android.valkyrie.com.istay.models.BoardingHouse;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentBoardingHouse extends Fragment {

    private DatabaseReference boardingHouseRef;

    private RecyclerView boardingHouseList;

    private ProgressBar progressBar;

    private View contentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_boarding_houses, container, false);

        boardingHouseRef = FirebaseDatabase.getInstance().getReference().child(Constants.BOARDING_HOUSE_TREE);

        boardingHouseList = contentView.findViewById(R.id.rv_boarding_house_list);
        boardingHouseList.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = contentView.findViewById(R.id.progress_bar_fragment_b_houses);

        fetchBoardingHouses();

        return contentView;
    }

    private void fetchBoardingHouses() {

        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<BoardingHouse>()
                        .setQuery(boardingHouseRef, BoardingHouse.class)
                        .build();

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

}
