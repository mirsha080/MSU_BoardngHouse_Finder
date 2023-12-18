package android.valkyrie.com.istay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.valkyrie.com.istay.admin.MainActivity;
import android.valkyrie.com.istay.models.User;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private FirebaseAuth userAuth;
    private DatabaseReference databaseUser;

    private TextInputLayout txtUsername, txtPassword, txtPassword2;
    private TextInputLayout txtFullName, txtEmail, txtAddress;

    private TextView btnLoginNew;
    private MaterialButton btnRegister;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setupToolbar();
        assignId();

        loadingBar = new ProgressDialog(this);

        userAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference("user");

        btnRegister = findViewById(R.id.btnRegisterNew);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateFields()){
                    signUp();
                }

            }
        });

        btnLoginNew = findViewById(R.id.btnLoginAgain);
        btnLoginNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLogin();
            }
        });

    }

    private void signUp() {

        String username = txtUsername.getEditText().getText().toString().trim() + "@iStay.com";
        String password = txtPassword.getEditText().getText().toString().trim();

        loadingBar.setTitle("Signup");
        loadingBar.setMessage("Please wait, while we are processing your account...");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        userAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    String user_id = userAuth.getCurrentUser().getUid();
                    String user_type = "user";
                    String full_name = txtFullName.getEditText().getText().toString().trim();
                    String email = txtEmail.getEditText().getText().toString().trim();
                    String address = txtAddress.getEditText().getText().toString().trim();

                    User newUser = new User(user_id, user_type, full_name, email, address);

                    databaseUser.child(user_id).setValue(newUser, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Registered Successfully!!", Toast.LENGTH_SHORT).show();
                            sendUserToLogin();
                        }
                    });
                }else{
                    loadingBar.dismiss();
                    Toast.makeText(getApplicationContext(), "Registration failed, please try again!!!", Toast.LENGTH_SHORT).show();
                }

                userAuth.signOut();
            }
        });

    }

    private void sendUserToLogin() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void assignId() {

        txtUsername = findViewById(R.id.text_layout_username);
        txtPassword = findViewById(R.id.text_layout_password);
        txtPassword2 = findViewById(R.id.text_layout_Password2);

        txtFullName = findViewById(R.id.text_layout_fullname);
        txtEmail = findViewById(R.id.text_layout_email);
        txtAddress = findViewById(R.id.text_layout_address);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_signup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Signup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private boolean validateFields(){

        boolean isValid = true;

        if (txtFullName.getEditText().getText().toString().trim().isEmpty()){
            txtFullName.setErrorEnabled(true);
            txtFullName.setError("Field cant be empty!");
            isValid = false;
        }else {
            txtFullName.setErrorEnabled(false);
            txtFullName.setError("");
        }

        if (txtUsername.getEditText().getText().toString().trim().isEmpty()){
            txtUsername.setErrorEnabled(true);
            txtUsername.setError("Field cant be empty!");
            isValid = false;
        }else {
            txtUsername.setErrorEnabled(false);
            txtUsername.setError("");
        }

        if (txtEmail.getEditText().getText().toString().trim().isEmpty()){
            txtEmail.setErrorEnabled(true);
            txtEmail.setError("Field cant be empty!");
            isValid = false;
        }else {
            txtEmail.setErrorEnabled(false);
            txtEmail.setError("");
        }

        if (txtPassword.getEditText().getText().toString().trim().isEmpty()){
            txtPassword.setErrorEnabled(true);
            txtPassword.setError("Field cant be empty!");
            isValid = false;
        }else {
            if (txtPassword.getEditText().getText().toString().trim().length() < 6){
                txtPassword.setErrorEnabled(true);
                txtPassword.setError("password minimum length is 6!");
                isValid = false;
            }else {
                txtPassword.setErrorEnabled(false);
                txtPassword.setError("");
            }
        }

        if (txtPassword2.getEditText().getText().toString().trim().isEmpty()){
            txtPassword2.setErrorEnabled(true);
            txtPassword2.setError("Field cant be empty!");
            isValid = false;
        }else {

            if (!txtPassword.getEditText().getText().toString().trim().equals(txtPassword2.getEditText().getText().toString().trim())){
                isValid = false;

                txtPassword2.setErrorEnabled(true);
                txtPassword2.setError("Password Mismatch!");
            }else{
                txtPassword2.setErrorEnabled(false);
                txtPassword2.setError("");
            }

            if (txtPassword2.getEditText().getText().toString().trim().length() < 6){
                txtPassword2.setErrorEnabled(true);
                txtPassword2.setError("password minimum length is 6!");
                isValid = false;
            }else {
                txtPassword2.setErrorEnabled(false);
                txtPassword2.setError("");
            }

        }

        return isValid;
    }

}
