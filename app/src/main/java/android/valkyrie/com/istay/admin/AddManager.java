package android.valkyrie.com.istay.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.valkyrie.com.istay.R;
import android.valkyrie.com.istay.database.Constants;
import android.valkyrie.com.istay.models.Manager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddManager extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private TextInputLayout txtFirst_name, txtLast_name, txtAddress, txtEmail, txtContact_no;
    private MaterialButton btnSave, btnCancel;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);

        setupToolbar();
        assignId();

        loadingBar = new ProgressDialog(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmptyFields()){
                    addNewManager();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addNewManager() {

        loadingBar.setTitle("Add new manager");
        loadingBar.setMessage("Please wait, Data is being saved...");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        DatabaseReference managerRef = firebaseDatabase.getReference(Constants.MANAGER_TREE);

        String profile_url = "none";
        String id = managerRef.push().getKey();
        String first_name = txtFirst_name.getEditText().getText().toString().trim();
        String last_name = txtLast_name.getEditText().getText().toString().trim();
        String address = txtAddress.getEditText().getText().toString().trim();
        String email = txtEmail.getEditText().getText().toString().trim();
        String contact_no = txtContact_no.getEditText().getText().toString().trim();

        Manager newManager = new Manager(profile_url, id, first_name, last_name, address, email, contact_no);

        managerRef.child(id).setValue(newManager, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                loadingBar.dismiss();
                Toast.makeText(getApplicationContext(), "new Manager successfully added...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        loadingBar.dismiss();
    }

    private void setupToolbar() {

        Toolbar toolbar = findViewById(R.id.activity_add_manager_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Manager");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void assignId() {

        txtFirst_name = findViewById(R.id.text_layout_manager_first_name);
        txtLast_name = findViewById(R.id.text_layout_manager_last_name);
        txtAddress = findViewById(R.id.text_layout_manager_address);
        txtEmail = findViewById(R.id.text_layout_manager_email);
        txtContact_no = findViewById(R.id.text_layout_manager_contact_no);

        btnSave = findViewById(R.id.btnAddManager);
        btnCancel = findViewById(R.id.btnCancelAddManager);
    }

    public boolean validateEmptyFields(){

        boolean isValid = true;

        if (txtFirst_name.getEditText().getText().toString().trim().isEmpty()){
            txtFirst_name.setErrorEnabled(true);
            txtFirst_name.setError("Field cant be empty!");
            isValid = false;
        }else {
            txtFirst_name.setErrorEnabled(false);
            txtFirst_name.setError("");
        }
        if (txtLast_name.getEditText().getText().toString().trim().isEmpty()){
            txtLast_name.setErrorEnabled(true);
            txtLast_name.setError("Field cant be empty!");
            isValid = false;
        }else {
            txtLast_name.setErrorEnabled(false);
            txtLast_name.setError("");
        }
        if (txtEmail.getEditText().getText().toString().trim().isEmpty()){
            txtEmail.setErrorEnabled(true);
            txtEmail.setError("Field cant be empty!");
            isValid = false;
        }else {
            txtEmail.setErrorEnabled(false);
            txtEmail.setError("");
        }
        if (txtContact_no.getEditText().getText().toString().trim().isEmpty()){
            txtContact_no.setErrorEnabled(true);
            txtContact_no.setError("Field cant be empty!");
            isValid = false;
        }else {
            txtContact_no.setErrorEnabled(false);
            txtContact_no.setError("");
        }

        return isValid;
    }
}