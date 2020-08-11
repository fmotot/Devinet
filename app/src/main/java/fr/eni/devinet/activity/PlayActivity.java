package fr.eni.devinet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import java.util.Collections;
import java.util.List;

import fr.eni.devinet.R;
import fr.eni.devinet.model.Word;
import fr.eni.devinet.view_model.PlayViewModel;

public class PlayActivity extends MenuActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        int wordListId = getIntent().getIntExtra(WordListChoiceActivity.WORDLIST_ID, 1);

        PlayViewModel vm = ViewModelProviders.of(this).get(PlayViewModel.class);

        LiveData<List<Word>> words = vm.getWordsFromWordList(wordListId);

        words = Transformations.map(words, list -> {
            Collections.shuffle(list);
            return list;
        });

        words.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {

            }
        });

        // modification de l'image dynamiquement
        ImageView iv_img_modif = findViewById(R.id.iv_img);
        iv_img_modif.setImageResource(R.drawable.avocat);

        // Zone de proposition



        /*
        LinearLayout layoutPlay = findViewById(R.id.play_activity);
        // ajout d'une liste view
        TextView textView = new TextView(this);
        textView.setText("I am added dynamically to the view");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textView.setLayoutParams(params);
        layoutPlay.addView(textView);

        */
    }
}