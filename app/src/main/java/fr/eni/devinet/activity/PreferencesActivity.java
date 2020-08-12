package fr.eni.devinet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import fr.eni.devinet.R;
import fr.eni.devinet.model.Preferences;

public class PreferencesActivity extends AppCompatActivity {

    private Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        preferences = new Preferences(this);

        Switch swSounds = findViewById(R.id.sw_sounds);
        swSounds.setChecked(preferences.isSounds());

        Switch swVibrations = findViewById(R.id.sw_vibration);
        swVibrations.setChecked(preferences.isVibrations());
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickToggleVibrations(View view) {
        preferences.switchVibrations();
    }

    public void onClickToggleSounds(View view) {
        preferences.switchSounds();
    }
}