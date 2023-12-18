package android.valkyrie.com.istay.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.valkyrie.com.istay.R;
import android.valkyrie.com.istay.database.Constants;
import android.valkyrie.com.istay.models.BoardingHouse;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddBoardingHouse extends AppCompatActivity {

    private DatabaseReference boardingHouseRef;

    private String MANAGER_ID;

    private TextInputLayout txt_boarding_house_name, txt_location;
    private EditText description;

    private AppCompatSeekBar seekBarPricePerRoom, seekBarPricePerPerson;
    private TextView txtPricePerRoom, txtPricePerPerson;

    private AppCompatSpinner genderSpinner;

    private MaterialButton btnSave, btnCancelAddBh;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_boarding_house);

        Intent i = getIntent();
        MANAGER_ID = i.getStringExtra(Constants.MANAGER_ID);

        boardingHouseRef = FirebaseDatabase.getInstance().getReference(Constants.BOARDING_HOUSE_TREE).child(MANAGER_ID);

        loadingBar = new ProgressDialog(this);

        Toolbar toolbar = findViewById(R.id.toolbar_add_bh);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Boarding House");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txt_boarding_house_name = findViewById(R.id.text_layout_bh_name);
        txt_location = findViewById(R.id.text_layout_bh_location);
        description = findViewById(R.id.description);
        seekBarPricePerRoom = findViewById(R.id.seekBarpricePerRoom);
        seekBarPricePerPerson = findViewById(R.id.seekBarpricePerPerson);
        txtPricePerPerson = findViewById(R.id.txtPricePerPerson);
        txtPricePerRoom = findViewById(R.id.txtPricePerRoom);

        genderSpinner = findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        seekBarPricePerRoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtPricePerRoom.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarPricePerPerson.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtPricePerPerson.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSave = findViewById(R.id.btnAddBH);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmptyFields()){
                    addNewBoardingHouse();
                }
            }
        });
        btnCancelAddBh = findViewById(R.id.btnCancelAddBH);
        btnCancelAddBh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addNewBoardingHouse() {

        loadingBar.setTitle("Add new boarding house");
        loadingBar.setMessage("Please wait, while we saving new data to the database...");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        String boarding_house_id = boardingHouseRef.push().getKey();
        String b_house_name = txt_boarding_house_name.getEditText().getText().toString().trim();
        String location = txt_location.getEditText().getText().toString().trim();;
        String desc = description.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        String pricePerPerson = txtPricePerPerson.getText().toString().trim();
        String pricePerRoom = txtPricePerRoom.getText().toString().trim();
        String owner_id = MANAGER_ID;

        HashMap houseMap = new HashMap();
        houseMap.put("boarding_house_id", boarding_house_id);
        houseMap.put("boarding_house_name", b_house_name);
        houseMap.put("location", location);
        houseMap.put("description", desc);
        houseMap.put("allowed_gender", gender);
        houseMap.put("price_per_person", pricePerPerson);
        houseMap.put("price_per_room", pricePerRoom);
        houseMap.put("owner_id", owner_id);

        boardingHouseRef.child(boarding_house_id).updateChildren(houseMap)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            loadingBar.dismiss();
                            finish();
                            Toast.makeText(AddBoardingHouse.this, "Boarding house successfully added...", Toast.LENGTH_SHORT).show();
                        }else {
                            finish();
                            loadingBar.dismiss();
                            Toast.makeText(AddBoardingHouse.this, "TASK ERROR : " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });




    }

    public boolean validateEmptyFields(){

        boolean isValid = true;

        if (txt_boarding_house_name.getEditText().getText().toString().trim().isEmpty()){
            txt_boarding_house_name.setErrorEnabled(true);
            txt_boarding_house_name.setError("Field cant be empty!");
            isValid = false;
        }else{
            txt_boarding_house_name.setErrorEnabled(false);
            txt_boarding_house_name.setError("");
        }

        if (txt_location.getEditText().getText().toString().trim().isEmpty()){
            txt_location.setErrorEnabled(true);
            txt_location.setError("Field cant be empty!");
            isValid = false;
        }else {
            txt_location.setErrorEnabled(false);
            txt_location.setError("");
        }

        return isValid;
    }
}
