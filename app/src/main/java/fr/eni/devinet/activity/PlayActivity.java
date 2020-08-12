package fr.eni.devinet.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import fr.eni.devinet.R;
import fr.eni.devinet.model.Word;
import fr.eni.devinet.utils.Utils;
import fr.eni.devinet.view_model.PlayViewModel;

public class PlayActivity extends MenuActivity {

    private int numWord = 0;
    private Word currentWord;
    private Context context;
    private MutableLiveData<String> proposal = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        proposal.setValue("");
        context = this;
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
                currentWord = words.get(numWord);

                // modification de l'image dynamiquement
                ImageView ivImage = findViewById(R.id.iv_img);
                ivImage.setImageResource(getResources().getIdentifier(currentWord.getImg().split("\\.")[0], "drawable", getPackageName()));

                // TODO Affichage du mot à supprimer
                TextView tvTest = findViewById(R.id.tv_test);
                tvTest.setText(currentWord.getWord());


                /**
                 * Gestion de la proposition du mot
                 */
                setProposalView();

                /**
                 * Affichage des lettres du mot
                 */
                setLetters();
            }
        });

        proposal.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(context, "Mot : " + s, Toast.LENGTH_LONG).show();
                setProposalView();
            }
        });
    }

    private void setProposal(String s){
        proposal.setValue(proposal.getValue() + s);
    }

    private void setLetters() {

        // suffle des lettres du mot
        List<String> letters = Utils.shuffleString(currentWord.getWord().toUpperCase());
        int nbLetters = letters.size();

        ConstraintLayout clLetters = findViewById(R.id.cl_letters);


        // calcul du nombre de ligne & lettres / ligne
        // TODO voir pour un calcul plus homogène

        int nbLigne;
        int maxLetters;

        switch (nbLetters) {
            case 3:
                nbLigne = 1;
                maxLetters = 3;
                break;
            case 4:
                maxLetters = 2;
                nbLigne = 2;
                break;
            case 5:
            case 6:
                maxLetters = 3;
                nbLigne = 2;
                break;
            default:
                nbLigne = 3;
                maxLetters = nbLetters / nbLigne;
        }

        // créer les layout et les remplir des lettres
        // ajouter action onClick sur "chaque lettre"

        ConstraintLayout line = new ConstraintLayout(context);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        line.setLayoutParams(params);

        line.setId(View.generateViewId());
        clLetters.addView(line);

        ConstraintSet cs = new ConstraintSet();
        cs.clone(clLetters);
        cs.connect(line.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        cs.connect(line.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);

        cs.applyTo(clLetters);

        View previousLetter = null;

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Click sur la lettre : " + ((TextView)view).getText(), Toast.LENGTH_LONG).show();
                setProposal(((TextView)view).getText().toString());
                ((TextView) view).setTextColor(getColor(R.color.yellow));
            }
        };


        for (String letter : letters) {

            // Création du textView de la lettre
            TextView tv = new TextView(context);
            tv.setText(letter);
            tv.setTextColor(getColor(R.color.green));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            tv.setId(View.generateViewId());

            // Ajout des contraintes sur le contenu
            ConstraintLayout.LayoutParams wrap = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            wrap.setMargins(50, 10, 50, 10);

            tv.setLayoutParams(wrap);

            // ! Bien ajouter les view avant de CREER les constraints de placement (avant le clone)
            line.addView(tv);
            tv.setOnClickListener(onClick);

            cs.clone(line);
            boolean lastItem = letters.indexOf(letter) == letters.size() - 1;
            if (previousLetter == null) {
                cs.connect(tv.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
            } else {
                cs.connect(tv.getId(), ConstraintSet.LEFT, previousLetter.getId(), ConstraintSet.RIGHT);
                if (lastItem) {
                    Log.i("MAT", "last");
                    cs.connect(tv.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                }
            }
            cs.applyTo(line);
            previousLetter = tv;
        }
    }

    private void setProposalView() {
        ConstraintLayout clLineProposal = findViewById(R.id.cl_line_proposal);
        ((ViewGroup) clLineProposal).removeAllViews();
        ConstraintSet cs = new ConstraintSet();


        List<String> prop = new ArrayList<>();
        // TODO pour test, à modifier
        // TODO voir pour créer un observer sur proposal
        for (char c : Objects.requireNonNull(proposal.getValue()).toCharArray()) {
            prop.add(String.valueOf(c));
        }

        View previousItem = null;
        for (String letter : prop) {

            // Création du textView de la lettre
            TextView tv = new TextView(context);
            tv.setText(letter);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            tv.setId(View.generateViewId());

            // Ajout des contraintes sur le contenu
            ConstraintLayout.LayoutParams wrap = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            wrap.setMargins(previousItem == null ? 0 : 30, 10, 30, 10);

            tv.setLayoutParams(wrap);

            // ! Bien ajouter les view avant de CREER les constraints (avant le clone)
            clLineProposal.addView(tv);

            cs.clone(clLineProposal);
            boolean lastItem = prop.indexOf(letter) == prop.size() - 1;
            if (previousItem == null) {
                cs.connect(tv.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
            } else {
                cs.connect(tv.getId(), ConstraintSet.LEFT, previousItem.getId(), ConstraintSet.RIGHT);
                if (lastItem) {
                    Log.i("MAT", "last");
                    cs.connect(tv.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                }
            }
            cs.applyTo(clLineProposal);
            previousItem = tv;
        }
    }

    public void onClickEraseProposal(View view) {

        // Supprimer les élements dynamiques
        ConstraintLayout clLineProposal = findViewById(R.id.cl_line_proposal);
        ((ViewGroup) clLineProposal).removeAllViews();

        // Réinitialiser la proposition
        proposal.setValue("");

        // Réinitialiser la couleur d'origine

    }
}