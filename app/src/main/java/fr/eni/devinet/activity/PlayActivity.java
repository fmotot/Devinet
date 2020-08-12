package fr.eni.devinet.activity;

import android.content.Context;
import android.content.res.ColorStateList;
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

    private static final int TV_LETTER_ID = 1;
    private static final int IV_BACKGROUND_ID = 0;

    private int numWord = 0;
    private List<Word> words;
    private Context context;
    private MutableLiveData<String> proposal = new MutableLiveData<>();
    private MutableLiveData<Word> wordMutableLiveData = new MutableLiveData<>();
    private PlayViewModel vm;
    private float pixelFactor;
    private boolean isListInitialised = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        context = this;
        pixelFactor = this.getResources().getDisplayMetrics().density;
        int wordListId = getIntent().getIntExtra(WordListChoiceActivity.WORDLIST_ID, 1);

        vm = ViewModelProviders.of(this).get(PlayViewModel.class);

        LiveData<List<Word>> listLiveData = vm.getWordsFromWordList(wordListId);

        listLiveData = Transformations.map(listLiveData, list -> {
            Collections.shuffle(list);
            return list;
        });

        // initialisation uniquement au premier chargement (pour éviter de recharger la liste sur un update d'un mot)
        listLiveData.observe(this, words -> {
            if (!isListInitialised) {
                PlayActivity.this.words = words;
                wordMutableLiveData.setValue(words.get(numWord));
                isListInitialised = true;
            }
        });

        wordMutableLiveData.observe(this, word -> {
            // modification de l'image dynamiquement
            ImageView ivImage = findViewById(R.id.iv_img);
            ivImage.setImageResource(getResources().getIdentifier(word.getImg().split("\\.")[0], "drawable", getPackageName()));
            proposal.setValue(word.getProposal() != null ? word.getProposal() : "");

            setProposalView();
            setLetters();
        });

        proposal.observe(this, s -> setProposalView());
    }

    /**
     * Ajoute une lettre au mot proposé en cours
     *
     * @param s la lettre qui sera ajoutée au mot proposé
     */
    private void setProposal(String s) {
        proposal.setValue(proposal.getValue() + s);
    }

    /**
     * Affichage des lettres du mot
     */
    private void setLetters() {

        // suffle des lettres du mot
        List<String> letters = Utils.shuffleString(Objects.requireNonNull(wordMutableLiveData.getValue()).getWord().toUpperCase());

        ConstraintLayout clLetters = findViewById(R.id.cl_letters);
        clLetters.removeAllViews();

        // calcul du nombre de ligne & lettres / ligne
        // TODO voir pour un affichage sur plusieurs ligne
        /*
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
        */

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

        View.OnClickListener onClick = view -> {
            if (!(Boolean) view.getTag()) {
                TextView tv = (TextView) ((ViewGroup) view).getChildAt(TV_LETTER_ID);
                setProposal(tv.getText().toString());
                ImageView iv = (ImageView) ((ViewGroup) view).getChildAt(IV_BACKGROUND_ID);
                iv.setImageTintList(ColorStateList.valueOf(getColor(R.color.orange)));
                // lettre utilisée
                view.setTag(true);
            }
        };

        for (String letter : letters) {

            // ! Bien ajouter les view avant de CREER les constraints de placement (avant le clone)
            View tv = createViewShuffle(letter);
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

    /**
     * Ajoute une vue contenant la lettre avec une image de fond
     *
     * @param letter la lettre à afficher dans la liste des lettres du mot
     * @return une view contenant la lettre et une image de fond
     */
    private View createViewShuffle(String letter) {
        // Si proposition déjà en base, mettre le tag à true et la couleur des lettres sur orange
        boolean isWordProposed = !Objects.requireNonNull(proposal.getValue()).isEmpty();
        Log.i("PROP", ((Boolean) isWordProposed).toString());

        // Création d'un layout pour contenir le fond et la lettre
        ConstraintLayout clLetter = new ConstraintLayout(context);
        clLetter.setId(View.generateViewId());
        clLetter.setTag(isWordProposed);
        ConstraintLayout.LayoutParams wrap = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        wrap.setMargins(50, 10, 50, 10);
        clLetter.setLayoutParams(wrap);

        // ajout de l'image
        ImageView ivBackground = new ImageView(context);
        ivBackground.setId(View.generateViewId());
        ivBackground.setImageDrawable(getDrawable(R.drawable.ic_paper_letter2));
        ivBackground.setRotation(90);
        int color = isWordProposed ? getColor(R.color.orange) : getColor(R.color.green);
        ivBackground.setImageTintList(ColorStateList.valueOf(color));
        ivBackground.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        ivBackground.getLayoutParams().height = (int) (60 * pixelFactor);
        ivBackground.getLayoutParams().width = (int) (40 * pixelFactor);
        clLetter.addView(ivBackground);

        // Création du textView de la lettre
        TextView tv = new TextView(context);
        tv.setId(View.generateViewId());
        tv.setText(letter);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        wrap = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        tv.setLayoutParams(wrap);
        clLetter.addView(tv);

        // Contrainte de la lettre dans le layout
        ConstraintSet cs = new ConstraintSet();
        cs.clone(clLetter);
        cs.connect(tv.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        cs.connect(tv.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        cs.connect(tv.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        cs.connect(tv.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        cs.applyTo(clLetter);

        return clLetter;
    }

    /**
     * Gestion de la proposition du mot
     */
    private void setProposalView() {
        ConstraintLayout clLineProposal = findViewById(R.id.cl_line_proposal);
        clLineProposal.removeAllViews();
        ConstraintSet cs = new ConstraintSet();


        List<String> prop = new ArrayList<>();
        for (char c : Objects.requireNonNull(proposal.getValue()).toUpperCase().toCharArray()) {
            prop.add(String.valueOf(c));
        }

        Log.i("PROPOSAL", prop.toString());

        View previousItem = null;
        for (String letter : prop) {

            // Création du textView de la lettrez
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
        clLineProposal.removeAllViews();

        // Réinitialiser la proposition
        proposal.setValue("");

        // Réinitialiser la couleur d'origine et l'utilisation de la lettre
        ViewGroup clLetters = findViewById(R.id.cl_letters);
        // pour toute les lignes de lettre
        for (int indexLine = 0; indexLine < clLetters.getChildCount(); indexLine++) {
            ViewGroup line = (ViewGroup) clLetters.getChildAt(indexLine);
            //pour chaque lettre (layout) dans la ligne
            for (int indexChild = 0; indexChild < line.getChildCount(); indexChild++) {
                View letterContainer = line.getChildAt(indexChild);
                letterContainer.setTag(false);
                ImageView iv = (ImageView) ((ViewGroup) letterContainer).getChildAt(IV_BACKGROUND_ID);
                iv.setImageTintList(ColorStateList.valueOf(getColor(R.color.green)));
            }
        }
    }

    public void onClickNext(View view) {
        if (numWord < words.size() - 1) {
            onClickEraseProposal(view);
            wordMutableLiveData.setValue(words.get(++numWord));
        } else {
            finish();
        }
    }

    public void onClickValidate(View view) {
        if (Objects.requireNonNull(proposal.getValue()).length() == Objects.requireNonNull(wordMutableLiveData.getValue()).getWord().length()) {
            wordMutableLiveData.getValue().setProposal(proposal.getValue().toLowerCase());
            vm.updateWord(wordMutableLiveData.getValue());
            onClickNext(view);
        } else {
            Toast.makeText(context, "Veuillez utiliser toutes les lettres à disposition pour valider le mot", Toast.LENGTH_LONG).show();
        }
    }
}