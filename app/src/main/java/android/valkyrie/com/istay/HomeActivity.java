package android.valkyrie.com.istay;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.valkyrie.com.istay.fragments.FragmentFavorite;
import android.valkyrie.com.istay.fragments.FragmentFavoriteLoggedOut;
import android.valkyrie.com.istay.fragments.FragmentSearch;
import android.valkyrie.com.istay.fragments.FragmentSettingsLoggedIn;
import android.valkyrie.com.istay.fragments.FragmentSettingsLoggedOut;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new FragmentSearch()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){

                        case R.id.item_home_search:
                            selectedFragment = new FragmentSearch();
                            break;

                        case R.id.item_favorites:

                            if(mAuth.getCurrentUser() != null){
                                selectedFragment = new FragmentFavorite();
                            }else {
                                selectedFragment = new FragmentFavoriteLoggedOut();
                            }break;

                        case R.id.item_settings:

                            if(mAuth.getCurrentUser() != null){
                                selectedFragment = new FragmentSettingsLoggedIn();
                            }else {
                                selectedFragment = new FragmentSettingsLoggedOut();
                            }break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        confirmExit();
    }

    public void confirmExit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
