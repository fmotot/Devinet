package fr.eni.devinet.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import fr.eni.devinet.R;
import fr.eni.devinet.model.Preferences;
import fr.eni.devinet.view_model.PreferencesViewModel;

public class PreferencesActivity extends MenuActivity {

    private Preferences preferences;
    private PreferencesViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        vm = ViewModelProviders.of(this).get(PreferencesViewModel.class);
        preferences = new Preferences(this);

        Switch swSounds = findViewById(R.id.sw_sounds);
        swSounds.setChecked(preferences.isSounds());
        swSounds.setSoundEffectsEnabled(preferences.isSounds());

        Switch swVibrations = findViewById(R.id.sw_vibration);
        swVibrations.setChecked(preferences.isVibrations());
        swVibrations.setSoundEffectsEnabled(preferences.isSounds());

        this.setTitle(this.getTitle() + " - Préférences");
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

    public void onClickReinit(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Réinitialiser Devinet")
                .setMessage("Etes-vous sûr(e) de vouloir réinitialiser l'application ?")
                .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vm.reinitWordsProposal();
                        Toast.makeText(PreferencesActivity.this, "Le jeu a été réinitialisé", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PreferencesActivity.this, "Oui, c'est mieux comme ça ;)", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }
}