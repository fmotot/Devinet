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
import fr.eni.devinet.model.LevelWithProgress;
import fr.eni.devinet.utils.Utils;

public class LevelAdapter extends ArrayAdapter<LevelWithProgress> {

    private Context context;

    public LevelAdapter(@NonNull Context context, int resource, @NonNull List<LevelWithProgress> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View line = li.inflate(R.layout.style_level_list, parent, false);

        LevelWithProgress level = getItem(position);

        ProgressBar pbProgress = line.findViewById(R.id.pb_progress);
        TextView tvName = line.findViewById(R.id.tv_name);
        TextView tvpercent = line.findViewById(R.id.tv_progress);

        assert level != null;
        pbProgress.setMax(level.getTotal());
        pbProgress.setProgress(level.getProgress());

        float ratio = level.getProgress() / (float) level.getTotal();
        tvpercent.setText(new StringBuilder(String.valueOf((int)(ratio * 100))).append('%'));

        StringBuilder name = new StringBuilder(this.context.getString(R.string.level));
        name.append(" ").append(level.getLevel().getName());
        tvName.setText(Utils.capitalize(name));

        return line;
    }
}
