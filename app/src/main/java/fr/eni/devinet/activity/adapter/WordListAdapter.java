package fr.eni.devinet.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fr.eni.devinet.R;
import fr.eni.devinet.model.WordListWithProgress;
import fr.eni.devinet.utils.Utils;

public class WordListAdapter extends ArrayAdapter<WordListWithProgress> {

    private Context context;

    public WordListAdapter(@NonNull Context context, int resource, @NonNull List<WordListWithProgress> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View line = li.inflate(R.layout.style_word_list_list, parent, false);

        WordListWithProgress wordListWithProgress = getItem(position);

        ProgressBar pbProgress = line.findViewById(R.id.pb_progress);
        TextView tvName = line.findViewById(R.id.tv_name);
        TextView tvPercent = line.findViewById(R.id.tv_progress);

        assert wordListWithProgress != null;
        pbProgress.setMax(wordListWithProgress.getTotal());
        pbProgress.setProgress(wordListWithProgress.getProgress());

        float ratio = wordListWithProgress.getProgress() / (float) wordListWithProgress.getTotal();
        tvPercent.setText(new StringBuilder(String.valueOf((int)(ratio * 100))).append('%'));

        StringBuilder name = new StringBuilder(this.context.getString(R.string.word_list));
        name.append(" ").append(wordListWithProgress.getWordList().getName());
        tvName.setText(Utils.capitalize(name));

        return line;
    }
}
