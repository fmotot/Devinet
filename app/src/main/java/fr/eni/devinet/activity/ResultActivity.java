package fr.eni.devinet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import fr.eni.devinet.R;
import fr.eni.devinet.activity.adapter.LevelAdapter;
import fr.eni.devinet.activity.adapter.ResultAdapter;
import fr.eni.devinet.activity.adapter.WordListAdapter;
import fr.eni.devinet.model.LevelWithProgress;
import fr.eni.devinet.model.Word;
import fr.eni.devinet.model.WordListWithProgress;
import fr.eni.devinet.view_model.LevelViewModel;
import fr.eni.devinet.view_model.PlayViewModel;
import fr.eni.devinet.view_model.ResultViewModel;
import fr.eni.devinet.view_model.WordListViewModel;

public class ResultActivity extends MenuActivity {

    private ListView lvWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int wordListId = getIntent().getIntExtra(WordListChoiceActivity.WORDLIST_ID, 1);

        lvWordList = findViewById(R.id.lv_result);
        ResultViewModel vm = ViewModelProviders.of(this).get(ResultViewModel.class);
        LiveData<List<Word>> words = vm.getWordsFromWordList(wordListId);

        words.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                ResultAdapter ra = new ResultAdapter(ResultActivity.this, R.layout.style_result, words);
                lvWordList.setAdapter(ra);
            }
        });

    }
}