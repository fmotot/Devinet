package fr.eni.devinet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import fr.eni.devinet.R;
import fr.eni.devinet.dal.AppDatabase;
import fr.eni.devinet.model.Category;
import fr.eni.devinet.repository.CategoryDBRepository;
import fr.eni.devinet.repository.ICategoryRepository;

public class MainActivity extends AppCompatActivity {

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

    }

    public void onClickStart(View view) {
        Toast.makeText(MainActivity.this, "Fonctionnalité non disponible pour le moment", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,LevelChoiceActivity.class);
        startActivity(intent);
    }

    public void onClickResults(View view) {
        Toast.makeText(MainActivity.this, "Fonctionnalité non disponible pour le moment", Toast.LENGTH_SHORT).show();
    }

    public void onClickProposal(View view) {
        Toast.makeText(MainActivity.this, "Fonctionnalité non disponible pour le moment", Toast.LENGTH_SHORT).show();
    }

    public void onClickStop(View view) {
        Toast.makeText(MainActivity.this, "Fonctionnalité non disponible pour le moment", Toast.LENGTH_SHORT).show();
    }
}