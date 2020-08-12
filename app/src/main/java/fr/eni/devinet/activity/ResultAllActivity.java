package fr.eni.devinet.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import fr.eni.devinet.R;
import fr.eni.devinet.activity.adapter.LevelAdapter;
import fr.eni.devinet.model.LevelWithProgress;
import fr.eni.devinet.utils.Utils;
import fr.eni.devinet.view_model.LevelViewModel;
import fr.eni.devinet.view_model.ResultAllViewModel;

public class ResultAllActivity extends MenuActivity {

    public static final String LEVEL_ID = "levelId";

    private ListView lvLevel;
    private LevelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_all);

        LevelViewModel vm = ViewModelProviders.of(this).get(LevelViewModel.class);
        lvLevel = findViewById(R.id.lv_level);

        LiveData<List<LevelWithProgress>> levels = vm.get();

        levels.observe(this, new Observer<List<LevelWithProgress>>() {
            @Override
            public void onChanged(List<LevelWithProgress> levelWithProgresses) {
                adapter = new LevelAdapter(ResultAllActivity.this, R.layout.style_result_level, levelWithProgresses);
                lvLevel.setAdapter(adapter);
            }
        });

        ResultAllViewModel rvm = ViewModelProviders.of(this).get(ResultAllViewModel.class);
        LiveData<Float> generalProgress = rvm.get();

        generalProgress.observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float allProgress) {
                int pourcentage = Math.round(allProgress);

                // Ajoutement de la couleur suivant la progression
                ProgressBar pbProgress = findViewById(R.id.pb_progress);
                pbProgress.setMax(100);
                pbProgress.setProgress(pourcentage);

                // Affichage de la valeur
                TextView tvName = findViewById(R.id.tv_progress);
                tvName.setText(pourcentage + "%");
            }
        });

        // Donner un titre à l'activité
        getSupportActionBar().setTitle("RESULTATS - GENERAL");

    }
}