package fr.eni.devinet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import fr.eni.devinet.R;
import fr.eni.devinet.activity.adapter.LevelAdapter;
import fr.eni.devinet.model.LevelWithProgress;
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
        //float test = rvm.get();
        float test = rvm.get();

        // TODO recuperation de la valeur
        Log.i("TEST", "Valeur : " + test);

    }
}