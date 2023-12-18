package android.valkyrie.com.istay.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.valkyrie.com.istay.R;
import android.valkyrie.com.istay.fragments.FragmentBoardingHouse;
import android.valkyrie.com.istay.fragments.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public final static String DEFUALT_FRAGMENT = "android.valkyrie.com.istay.admin.MainActivity.defaultFragment";

    Toolbar toolbar;
    private DrawerLayout drawer;

    android.view.ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        setDefaultFragment();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            confirmExit();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.add_new_manager:
                startActivity(new Intent(MainActivity.this, AddManager.class));
                break;

            case R.id.nav_manager:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentManager()).commit();
                toolbar.setTitle("Managers");
                break;

            case R.id.nav_boarding_house:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentBoardingHouse()).commit();
                toolbar.setTitle("Boarding houses");
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void confirmExit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar_main);
        toolbar.inflateMenu(R.menu.fragment_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Toast.makeText(getApplicationContext(), menuItem.getTitle().toString() + " selected!", Toast.LENGTH_LONG).show();

                switch (menuItem.getItemId()){

                    case R.id.menu_search:

                        actionMode = MainActivity.this.startActionMode(new ContextualCallback());

                        break;
                }

                return true;
            }
        });
    }

    private void setDefaultFragment() {

        Intent i = getIntent();
        String default_fragment = i.getStringExtra(MainActivity.DEFUALT_FRAGMENT);

        Toast.makeText(getApplicationContext(), "Defualt Fragment : " + default_fragment, Toast.LENGTH_LONG).show();


        if (default_fragment == "FragmentManager.class") {
            Toast.makeText(getApplicationContext(), "default fragment is AddManager", Toast.LENGTH_LONG).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentManager()).commit();
        } else if (default_fragment == "FragmentBoardingHouse") {
            Toast.makeText(getApplicationContext(), "default fragment is AddManager", Toast.LENGTH_LONG).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentManager()).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentManager()).commit();
            Toast.makeText(getApplicationContext(), "default fragment is null", Toast.LENGTH_LONG).show();
            toolbar.setTitle("Managers");
        }
    }


    private class ContextualCallback implements android.view.ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu) {

            actionMode.getMenuInflater().inflate(R.menu.search_menu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu) {

            actionMode.setTitle("!");

            return false;
        }

        @Override
        public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {

            Toast.makeText(getApplicationContext(), "You clicked " + item.getTitle(), Toast.LENGTH_LONG).show();

            return false;
        }

        @Override
        public void onDestroyActionMode(android.view.ActionMode mode) {

        }
    }
}
