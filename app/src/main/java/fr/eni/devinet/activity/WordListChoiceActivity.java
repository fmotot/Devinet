package fr.eni.devinet.activity;

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
import fr.eni.devinet.activity.adapter.WordListAdapter;
import fr.eni.devinet.model.Level;
import fr.eni.devinet.model.WordListWithProgress;
import fr.eni.devinet.view_model.WordListViewModel;

public class WordListChoiceActivity extends MenuActivity {

    public static final String WORDLIST = "wordList";
    public static final String WORDLIST_ID = "wordListId";

    private ListView lvWordList;
    private WordListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list_choice);

        WordListViewModel vm = ViewModelProviders.of(this).get(WordListViewModel.class);
        lvWordList = findViewById(R.id.lv_word_list);

        Level level = getIntent().getParcelableExtra(LevelChoiceActivity.LEVEL);

        LiveData<List<WordListWithProgress>> wordLists = vm.getWordListWithProgress(level.getId());

        wordLists.observe(this, new Observer<List<WordListWithProgress>>() {
            @Override
            public void onChanged(List<WordListWithProgress> wordListWithProgresses) {
                adapter = new WordListAdapter(WordListChoiceActivity.this, R.layout.style_word_list_list, wordListWithProgresses);
                lvWordList.setAdapter(adapter);
            }
        });

        lvWordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(WordListChoiceActivity.this, PlayActivity.class);
                intent.putExtra(WORDLIST, adapter.getItem(i).getWordList());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        this.setTitle(this.getTitle() +" - Niveau " + level.getName());
    }
}