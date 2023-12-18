package android.valkyrie.com.istay.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.valkyrie.com.istay.R;
import android.valkyrie.com.istay.database.Constants;
import android.view.View;
import android.widget.Toast;

public class UpdateManager extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputLayout txtFirst_name, txtLast_name, txtAddress, txtEmail, txtContact_no;
    private MaterialButton btnSaveChanges, btnCancelChanges;
    private String first_name, last_name, address, contact_no, email;

    private int MANAGER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_manager);

        Intent i = getIntent();
        MANAGER_ID = i.getIntExtra(Constants.MANAGER_ID, 0);

        setToolbar();

        txtFirst_name = findViewById(R.id.text_layout_update_manager_first_name);
        txtLast_name = findViewById(R.id.text_layout_update_manager_last_name);
        txtAddress = findViewById(R.id.text_layout_update_manager_address);
        txtContact_no = findViewById(R.id.text_layout_update_manager_contact_no);
        txtEmail = findViewById(R.id.text_layout_update_manager_email);
        btnSaveChanges = findViewById(R.id.btnSaveManagerUpdate);
        btnCancelChanges = findViewById(R.id.btnCancelManagerUpdate);

        displayPreviousRecord();

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateEmptyFields()){
                }
            }
        });
        btnCancelChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You just cancel, this activity will finish()", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void displayPreviousRecord() {


    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_update_manager);
        toolbar.setTitle("update Manager info");
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
