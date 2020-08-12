package fr.eni.devinet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import fr.eni.devinet.R;
import fr.eni.devinet.model.Category;
import fr.eni.devinet.repository.CategoryDBRepository;
import fr.eni.devinet.repository.ICategoryRepository;
import fr.eni.devinet.view_model.ResultAllViewModel;

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
        Intent intent = new Intent(this,LevelChoiceActivity.class);
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
        finishAffinity();
    }
}