package android.valkyrie.com.istay.admin;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.valkyrie.com.istay.R;
import android.valkyrie.com.istay.database.Constants;
import android.valkyrie.com.istay.models.BoardingHouse;
import android.view.View;
import android.widget.Toast;

public class UpdateBoardingHouse extends AppCompatActivity {

    private Toolbar toolbar;
    private int BOARDING_HOUSE_ID;

    private TextInputLayout txt_boarding_house_name, txt_location, txt_pricePePerson, txt_pricePerRoom;
    private String boarding_house_name, location, pricePePerson, pricePerRoom;
    private MaterialButton btnSaveBHUpdate, btnCancelBHUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_boarding_house);

        toolbar = findViewById(R.id.toolbar_update_bh);
        toolbar.setTitle("Update Boarding house");

        Intent i = getIntent();
        BOARDING_HOUSE_ID = i.getIntExtra(Constants.BOARDING_HOUSE_ID, 0);

        txt_boarding_house_name = findViewById(R.id.text_layout_update_bh_name);
        txt_location = findViewById(R.id.text_layout_update_bh_location);
        txt_pricePePerson = findViewById(R.id.text_layout_update_bh_price_per_person);
        txt_pricePerRoom = findViewById(R.id.text_layout_update_bh_price_per_room);
        btnSaveBHUpdate = findViewById(R.id.btnSaveBHUpdate);
        btnSaveBHUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateEmptyFields()){
                }

            }
        });
        btnCancelBHUpdate = findViewById(R.id.btnCancelBHUpdate);
        btnCancelBHUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        displayPreviousRecord();

    }

    private void displayPreviousRecord() {

        txt_boarding_house_name.getEditText().setText(boarding_house_name);
        txt_location.getEditText().setText(location);
        txt_pricePePerson.getEditText().setText(pricePePerson);
        txt_pricePerRoom.getEditText().setText(pricePerRoom);

    }

    public boolean validateEmptyFields(){

        boolean isValid = true;

        if (txt_boarding_house_name.getEditText().getText().toString().trim().isEmpty()){
            txt_boarding_house_name.setErrorEnabled(true);
            txt_boarding_house_name.setError("Field cant be empty!");
            isValid = false;
        }else {
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
        if (txt_pricePePerson.getEditText().getText().toString().trim().isEmpty()){
            txt_pricePePerson.setErrorEnabled(true);
            txt_pricePePerson.setError("Field cant be empty!");
            isValid = false;
        }else {
            txt_pricePePerson.setErrorEnabled(false);
            txt_pricePePerson.setError("");
        }
        if (txt_pricePerRoom.getEditText().getText().toString().trim().isEmpty()){
            txt_pricePerRoom.setErrorEnabled(true);
            txt_pricePerRoom.setError("Field cant be empty!");
            isValid = false;
        }else {
            txt_pricePerRoom.setErrorEnabled(false);
            txt_pricePerRoom.setError("");
        }

        return isValid;
    }

}