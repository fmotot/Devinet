package fr.eni.devinet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
}