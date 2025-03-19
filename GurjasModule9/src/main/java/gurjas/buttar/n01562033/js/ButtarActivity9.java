//Gurjas Buttar N01562033
package gurjas.buttar.n01562033.js;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class ButtarActivity9 extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.gurtoolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.gurmain);
        NavigationView navigationView = findViewById(R.id.gurnav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.gurfragment_container, new Gurjas1Fragment()).commit();
            navigationView.setCheckedItem(R.id.gur_nav_fragone);

        }
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.gur_nav_fragone) {
            getSupportFragmentManager().beginTransaction().replace(R.id.gurfragment_container, new Gurjas1Fragment()).commit();
        } else if (itemId == R.id.but_nav_fragtwo) {
            getSupportFragmentManager().beginTransaction().replace(R.id.gurfragment_container, new Buttar2Fragment()).commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
            public void onBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
            }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.gurfragment_container, fragment);
        transaction.commit();
    }
}