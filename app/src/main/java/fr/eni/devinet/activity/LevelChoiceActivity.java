package fr.eni.devinet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import fr.eni.devinet.R;
import fr.eni.devinet.activity.adapter.LevelAdapter;
import fr.eni.devinet.model.LevelWithProgress;
import fr.eni.devinet.view_model.LevelViewModel;

public class LevelChoiceActivity extends AppCompatActivity {

    private ListView lvLevel;

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
                LevelAdapter adapter = new LevelAdapter(LevelChoiceActivity.this, R.layout.style_level_list, levelWithProgresses);
                lvLevel.setAdapter(adapter);
            }
        });

        lvLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO à remplacer
                Toast.makeText(LevelChoiceActivity.this, "Click sur niveau position : " + i, Toast.LENGTH_LONG).show();
            }
        });


    }
}