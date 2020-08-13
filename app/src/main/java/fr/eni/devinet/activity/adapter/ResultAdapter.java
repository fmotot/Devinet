package fr.eni.devinet.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

import fr.eni.devinet.R;
import fr.eni.devinet.model.Word;


public class ResultAdapter extends ArrayAdapter<Word> {

    private Context context;

    public ResultAdapter(@NonNull Context context, int resource, @NonNull List<Word> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View line = li.inflate(R.layout.style_result, parent, false);

        Word item = getItem(position);

        String wordResult = item.getWord().toUpperCase();

        /**
         * Gestion des données
         */

        ImageView wordImg = line.findViewById(R.id.iv_img);
        wordImg.setImageResource(context.getResources().getIdentifier(item.getImg().split("\\.")[0], "drawable", context.getPackageName()));

        TextView wordText = line.findViewById(R.id.tv_line1);
        wordText.setText(wordResult);

        // recupération de votre proposition et ajout de couleur suivant le resultat
        TextView proposalText = line.findViewById(R.id.tv_line3);
        String wordProposal = null;
        if (item.getProposal() != null){
            wordProposal = item.getProposal().toUpperCase();
            proposalText.setText(wordProposal);

            if (wordProposal.equals(wordResult)){
                proposalText.setTextColor(ContextCompat.getColor(context, R.color.green));
            }else {
                proposalText.setTextColor(ContextCompat.getColor(context, R.color.magenta));
            }
        }else {
            proposalText.setText("-");
        }

        // Pour l'affichage du checked si la reponse est bonne
        ImageView ivResult = line.findViewById(R.id.iv_result);

        if (wordResult.equals(wordProposal)){
            ivResult.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_done_green_24));
        }else {
            ivResult.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_cancel_red_24));
        }

        return line;

    }
}
