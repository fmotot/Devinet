package fr.eni.devinet.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import fr.eni.devinet.R;
import fr.eni.devinet.activity.adapter.LevelAdapter;
import fr.eni.devinet.model.LevelWithProgress;
import fr.eni.devinet.view_model.LevelViewModel;

public class LevelChoiceActivity extends MenuActivity {
    public static final String LEVEL_ID = "levelId";
    public static final String LEVEL = "level";

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

        // LiveData pour progression générale
        LiveData<Float> progress = vm.getAllProgress();

        progress.observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float allProgress) {
                int percent = Math.round(allProgress);

                // Ajoutement de la progressBar suivant la progression
                ProgressBar pbProgress = findViewById(R.id.pb_progress);
                pbProgress.setMax(100);
                pbProgress.setProgress(percent);

                // Affichage de la valeur
                TextView tvName = findViewById(R.id.tv_progress);
                tvName.setText(percent + "%");
            }
        });

        lvLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LevelChoiceActivity.this, WordListChoiceActivity.class);
                intent.putExtra(LEVEL, adapter.getItem(i).getLevel());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        this.setTitle(this.getTitle() +" - Selection niveau");
    }
}