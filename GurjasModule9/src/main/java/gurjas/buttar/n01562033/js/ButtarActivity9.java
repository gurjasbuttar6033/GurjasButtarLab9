package gurjas.buttar.n01562033.js;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class ButtarActivity9 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.gurtoolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.gurmain);
        NavigationView navigationView = findViewById(R.id.gurnav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (getSupportFragmentManager().findFragmentById(R.id.gurfragment_container) == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.gurfragment_container, new Gurjas1Fragment())
                    .commit();
            navigationView.setCheckedItem(R.id.gur_nav_fragone);

            SharedPreferences prefs = getSharedPreferences(getString(R.string.settings), MODE_PRIVATE);
            boolean isDarkMode = prefs.getBoolean(getString(R.string.dark_mode), false);
            AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.gur_toggle) {
            toggleDarkMode();
            return true;
        } else if (id == R.id.gur_search) {
            showSearchDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleDarkMode() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.settings), MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean(getString(R.string.dark_mode), false);
        AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.dark_mode), !isDarkMode);
        editor.apply();
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.search);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.search), (dialog, which) -> {
            String query = input.getText().toString().trim();
            if (!query.isEmpty()) {
                launchGoogleSearch(query);
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void launchGoogleSearch(String query) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + Uri.encode(query)));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}