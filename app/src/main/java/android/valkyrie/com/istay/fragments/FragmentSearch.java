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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentSearch extends Fragment {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        progressBar = view.findViewById(R.id.progress_bar_fragment_search);

        fetchBoardingHouses(view);

        return view;
    }


    private void fetchBoardingHouses(View view) {
    }

}
