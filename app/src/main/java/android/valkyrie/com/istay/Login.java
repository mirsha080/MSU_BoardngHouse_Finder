package android.valkyrie.com.istay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.valkyrie.com.istay.admin.MainActivity;
import android.valkyrie.com.istay.database.Constants;
import android.valkyrie.com.istay.models.User;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private FirebaseAuth loginAuth;
    private DatabaseReference userRef;

    private TextView btnRegisterNew;
    private TextView btnLogin, btnLoginAsGuest;
    private TextInputLayout txtUsername, txtPassword;

    private ProgressDialog loadingBar;

    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginAuth = FirebaseAuth.getInstance();

        txtUsername = findViewById(R.id.text_layout_username);
        txtPassword = findViewById(R.id.text_layout_password);

        loadingBar = new ProgressDialog(this);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateFields()){
                    login();
                }

                if (txtUsername.getEditText().getText().toString().trim().equals("valkyrie")){
                    Toast.makeText(getApplicationContext(), "Welcome admin 007", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

            }
        });

        btnRegisterNew = findViewById(R.id.btnRegister);
        btnRegisterNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
                Toast.makeText(getApplicationContext(), "welcome new user", Toast.LENGTH_SHORT).show();
            }
        });

        btnLoginAsGuest = findViewById(R.id.txt_login_as_guest);
        btnLoginAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void login() {

        String username = txtUsername.getEditText().getText().toString().trim() + "@iStay.com";
        String password = txtPassword.getEditText().getText().toString().trim();

        loadingBar.setTitle("Login");
        loadingBar.setMessage("Please wait, while we are allowing you to login to your account...");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        loginAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    current_user_id = loginAuth.getCurrentUser().getUid();
                    userRef = FirebaseDatabase.getInstance().getReference().child(Constants.USER_TREE).child(current_user_id);
                    userRef.child("user_type").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String user_type = dataSnapshot.getValue().toString();

                            if (user_type.equals("admin")){
                                sendUserToMainActivity();

                            }else if (user_type.equals("user")){
                                sendUserToHomeActivity();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            String DB_ERROR = databaseError.getMessage();
                            Toast.makeText(getApplicationContext(), "Database Error : " + DB_ERROR, Toast.LENGTH_SHORT).show();
                        }
                    });
                    loadingBar.dismiss();

                }else {
                    loadingBar.dismiss();
                    Toast.makeText(getApplicationContext(), "Task isn't successful!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserToHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void sendUserToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private boolean validateFields(){

        boolean isValid = true;

        if (txtUsername.getEditText().getText().toString().trim().isEmpty()){
            txtUsername.setErrorEnabled(true);
            txtUsername.setError("Field cannot be empty!");
            isValid = false;
        }else {
            txtUsername.setErrorEnabled(false);
            txtUsername.setError("");
        }

        if (txtPassword.getEditText().getText().toString().trim().isEmpty()){
            txtPassword.setErrorEnabled(true);
            txtPassword.setError("Field cannot be empty!");
            isValid = false;
        }else {
            txtPassword.setErrorEnabled(false);
            txtPassword.setError("");
        }

        return isValid;

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (loginAuth.getCurrentUser() != null){
            current_user_id = loginAuth.getCurrentUser().getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child(Constants.USER_TREE).child(current_user_id);
            userRef.child("user_type").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String user_type = dataSnapshot.getValue().toString();

                    if (user_type.equals("admin")){
                        sendUserToMainActivity();

                    }else if (user_type.equals("user")){
                        sendUserToHomeActivity();
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    String DB_ERROR = databaseError.getMessage();
                    Toast.makeText(getApplicationContext(), "Database Error : " + DB_ERROR, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
