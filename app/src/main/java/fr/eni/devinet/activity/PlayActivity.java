package fr.eni.devinet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.eni.devinet.R;

public class PlayActivity extends MenuActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

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