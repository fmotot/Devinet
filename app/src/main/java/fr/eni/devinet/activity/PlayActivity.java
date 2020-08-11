package fr.eni.devinet.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProviders;

import java.util.Collections;
import java.util.List;

import fr.eni.devinet.R;
import fr.eni.devinet.model.Word;
import fr.eni.devinet.utils.Utils;
import fr.eni.devinet.view_model.PlayViewModel;

public class PlayActivity extends MenuActivity {

    private int numWord = 0;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

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
                Word word = words.get(numWord);

                // modification de l'image dynamiquement
                ImageView ivImage = findViewById(R.id.iv_img);
                ivImage.setImageResource(getResources().getIdentifier(word.getImg().split("\\.")[0], "drawable", getPackageName()));

                // TODO Affichage du mot à supprimer
                TextView tvTest = findViewById(R.id.tv_test);
                tvTest.setText(word.getWord());

                /**
                 * Gestion de la proposition du mot
                 */
                LinearLayout llProposal = findViewById(R.id.ll_proposal);

                // TODO pour test, à modifier
                String proposal = word.getWord();
                for(int i = 0; i < proposal.length(); i++) {
                    //EditText et = new EditText(this);
                    TextView tvLetter = new TextView(context);
                    tvLetter.setText(proposal.substring(i, i + 1).toUpperCase());
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
//                    et.setLayoutParams(params);
                    //et.setEnabled(false);
                    llProposal.addView(tvLetter);
                }

                /**
                 * Affichage des lettres du mot
                 */
                LinearLayout llLetters = findViewById(R.id.ll_letters);

                // suffle des lettres du mot
                List<String> letters = Utils.shuffleString(word.getWord().toUpperCase());

                // calcul du nombre de ligne & lettres / ligne
                // TODO voir pour un calcul plus homogène
                int nbLetters = letters.size();

                int nbLigne;
                int maxLetters;

                switch (nbLetters){
                    case 3 :
                        nbLigne = 1;
                        maxLetters = 3;
                        break;
                    case 4 :
                        maxLetters = 2;
                        nbLigne = 2;
                        break;
                    case 5 :
                    case 6 :
                        maxLetters = 3;
                        nbLigne = 2;
                        break;
                    default:
                        nbLigne = 3;
                        maxLetters = nbLetters / nbLigne;
                }

                // créer les layout et les remplir des lettres
                // ajouter action onClick sur "chaque lettre"

                ConstraintLayout constraintLayout = new ConstraintLayout(context);
                constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                ));
                llLetters.addView(constraintLayout);



                View previousItem = null;
                Log.i("MAT", "nb lettres : " + letters.size());
                Log.i("MAT", letters.toString());
                for (String letter : letters){

                    // Création du textView de la lettre
                    TextView tv = new TextView(context);
                    tv.setText(letter);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                    tv.setId(View.generateViewId());

                    // Ajout des contraintes sur le contenu
                    ConstraintLayout.LayoutParams wrap = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
                    wrap.setMargins(10,10,10,10);

                    tv.setLayoutParams(wrap);

                    ConstraintSet cs = new ConstraintSet();
                    // TODO vérifier le besoin
//                    cs.clone(constraintLayout);

                    boolean lastItem = letters.indexOf(letter) == letters.size() - 1;
                    Log.i("MAT", "id : " + tv.getId() + ", previous : " + (previousItem == null ? "0" : previousItem.getId()));
                    if(previousItem == null) {
                        Log.i("MAT", "parent : " + ConstraintSet.PARENT_ID);
                        Log.i("MAT", "first");
                        cs.connect(tv.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                    } else {
                        cs.connect(previousItem.getId(), ConstraintSet.RIGHT, tv.getId(), ConstraintSet.LEFT);
                        cs.connect(tv.getId(), ConstraintSet.LEFT, previousItem.getId(), ConstraintSet.RIGHT);
                        Log.i("MAT", "next");
                        if(lastItem) {
                            Log.i("MAT", "last");
                            cs.connect(tv.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                        }
                    }
                    constraintLayout.addView(tv);
                    cs.applyTo(constraintLayout);
                    previousItem = tv;
                }


                /**
                 * test
                 */
                ConstraintLayout layout = findViewById(R.id.play_activity);

                TextView tv = new TextView(context);
                tv.setText("prout");
                tv.setLayoutParams(new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                ));
                tv.setId(View.generateViewId());

                ConstraintSet cs = new ConstraintSet();
                cs.clone(layout);
//                cs.connect(R.id.tv_test, ConstraintSet.TOP, tv.getId(), ConstraintSet.BOTTOM);
                cs.connect(tv.getId(), ConstraintSet.BOTTOM, R.id.tv_test, ConstraintSet.TOP);
                cs.connect(tv.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                layout.addView(tv);
                cs.applyTo((ConstraintLayout) layout);





//                LinearLayout ll = new LinearLayout(context);
//                ll.setOrientation(LinearLayout.HORIZONTAL);
//                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


            }
        });
    }
}