package fr.eni.devinet.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import fr.eni.devinet.R;
import fr.eni.devinet.activity.adapter.LevelAdapter;
import fr.eni.devinet.model.LevelWithProgress;
import fr.eni.devinet.view_model.LevelViewModel;

public class LevelChoiceActivity extends MenuActivity {
    public static final String LEVEL_ID = "levelId";

    private ListView lvLevel;
    private LevelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_choice);

        LevelViewModel vm = ViewModelProviders.of(this).get(LevelViewModel.class);
        lvLevel = findViewById(R.id.lv_level);

        LiveData<List<LevelWithProgress>> levels = vm.get();

        levels.observe(this, new Observer<List<LevelWithProgress>>() {
            @Override
            public void onChanged(List<LevelWithProgress> levelWithProgresses) {
                adapter = new LevelAdapter(LevelChoiceActivity.this, R.layout.style_level_list, levelWithProgresses);
                lvLevel.setAdapter(adapter);
            }
        });

        lvLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO à remplacer
                Toast.makeText(LevelChoiceActivity.this, "Click sur niveau position : " + i, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LevelChoiceActivity.this, WordListChoiceActivity.class);
                intent.putExtra(LEVEL_ID, adapter.getItem(i).getLevel().getId());

                startActivity(intent);
            }
        });


    }
}