package fr.eni.devinet.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.facebook.stetho.Stetho;

import fr.eni.devinet.R;
import fr.eni.devinet.model.Category;
import fr.eni.devinet.repository.CategoryDBRepository;
import fr.eni.devinet.repository.ICategoryRepository;

public class MainActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        /**
         * A SUPPR
         * pour création de la base en test
         */
        ICategoryRepository repo = new CategoryDBRepository(this);
        repo.insert(new Category(0, "Autre"));

        // TODO à faire dynamiquement suivant les pages et les options
//        getSupportActionBar().setTitle("LE JEUX DEVINET");
    }

    /**************************************/
    /********** ACTION ON CLICK ***********/
    /**************************************/


    /**
     * Bouton pour jouer
     * @param view
     */
    public void onClickStart(View view) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(50,50));
        Intent intent = new Intent(this,LevelChoiceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    /**
     * Bouton pour faire une proposition
     * @param view
     */
    public void onClickProposeWord(View view) {
        Toast.makeText(MainActivity.this, "Fonctionnalité non disponible pour le moment", Toast.LENGTH_SHORT).show();
    }

    /**
     * Bouton pour arreter l'app
     * @param view
     */
    public void onClickStop(View view) {
//        Toast.makeText(MainActivity.this, "Fonctionnalité non disponible pour le moment", Toast.LENGTH_SHORT).show();
//        finishAffinity();

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Application Devinet")
                .setMessage("Voulez vous vraiment quitter l'application?")
                .setPositiveButton("OUI", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("NON", null)
                .show();
    }
}