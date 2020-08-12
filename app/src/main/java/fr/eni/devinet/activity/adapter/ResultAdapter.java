package fr.eni.devinet.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        Word word = getItem(position);

        TextView wordText = line.findViewById(R.id.tv_line1);
        wordText.setText(word.getWord());

        return line;

    }
}
