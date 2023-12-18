package android.valkyrie.com.istay.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.valkyrie.com.istay.Login;
import android.valkyrie.com.istay.R;
import android.valkyrie.com.istay.database.Constants;
import android.valkyrie.com.istay.models.User;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentSettingsLoggedIn extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    private TextView txtLogout, txtUser_name;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_logged_in, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        txtUser_name = view.findViewById(R.id.txt_user_name);
        progressBar = view.findViewById(R.id.progress_bar_fragment_settings_logged_in);

        fetchCurrentUser();

        txtLogout = view.findViewById(R.id.txt_logout);
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        return view;
    }

    private void fetchCurrentUser() {

        String user_id = mAuth.getCurrentUser().getUid();

        DatabaseReference userRef = firebaseDatabase.getReference(Constants.USER_TREE).child(user_id);

        progressBar.setVisibility(View.VISIBLE);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.GONE);

                User currentUser = dataSnapshot.getValue(User.class);

                txtUser_name.setText(currentUser.getFull_name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), "Database Error!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
