package gurjas.buttar.n01562033.js;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class GurjasButSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(GurjasButSplash.this, ButtarActivity9.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}