package android.valkyrie.com.istay.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.valkyrie.com.istay.ManagerInfo;
import android.valkyrie.com.istay.R;
import android.valkyrie.com.istay.database.Constants;
import android.valkyrie.com.istay.models.Manager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentManager extends Fragment {

    private DatabaseReference managerRef;
    private RecyclerView managerList;

    private ProgressBar progressBar;

    private View contentView;

    public FragmentManager() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_managers, container, false);

        managerRef = FirebaseDatabase.getInstance().getReference().child(Constants.MANAGER_TREE);

        progressBar = contentView.findViewById(R.id.progress_bar_fragment_manager);

        managerList = (RecyclerView) contentView.findViewById(R.id.recyclerview_fragment_manager);
        managerList.setLayoutManager(new LinearLayoutManager(getContext()));

        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        progressBar.setVisibility(View.VISIBLE);

        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Manager>()
                .setQuery(managerRef, Manager.class)
                .build();

        FirebaseRecyclerAdapter<Manager, ManagerViewHolder> adapter =
                new FirebaseRecyclerAdapter<Manager, ManagerViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ManagerViewHolder holder, final int position, @NonNull Manager model) {

                        String user_ids = getRef(position).getKey();

                        managerRef.child(user_ids).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String full_name = dataSnapshot.child("first_name").getValue().toString() + " " + dataSnapshot.child("last_name").getValue().toString();
                                String address = dataSnapshot.child("address").getValue().toString();

                                holder.manager_fullname.setText(full_name);
                                holder.manager_address.setText(address);

                                progressBar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "DATABASE ERROR : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String manager_id = getRef(position).getKey();

                                Intent i = new Intent(getActivity(), ManagerInfo.class);
                                i.putExtra(Constants.MANAGER_ID, manager_id);
                                startActivity(i);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ManagerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_managers_layout, viewGroup, false);
                        ManagerViewHolder holder = new ManagerViewHolder(view);
                        return holder;
                    }
                };

        progressBar.setVisibility(View.GONE);

        managerList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class ManagerViewHolder extends RecyclerView.ViewHolder{

        TextView manager_fullname, manager_address;
        CircleImageView manager_profile;

        public ManagerViewHolder(@NonNull View itemView) {
            super(itemView);

            manager_fullname = itemView.findViewById(R.id.manager_name);
            manager_address = itemView.findViewById(R.id.manager_address);
            manager_profile = itemView.findViewById(R.id.manager_profile_image);

        }
    }

}
