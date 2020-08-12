package fr.eni.devinet.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.widget.Toast;

public class Preferences {
    public static final String VIBRATIONS = "vibrations";
    public static final String SOUNDS = "sounds";

    private boolean sounds;
    private boolean vibrations;
    private Context context;

    private SharedPreferences parameters;
    private SharedPreferences.Editor editor;

    public Preferences(Context context) {
        this.context = context;
        this.parameters = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        this.editor = parameters.edit();
    }

    public boolean isSounds() {
        return this.parameters.getBoolean(SOUNDS, true);
    }

    public void switchSounds() {
        this.editor.putBoolean(SOUNDS, !isSounds());
        this.editor.commit();
    }

    public boolean isVibrations() {
        return this.parameters.getBoolean(VIBRATIONS, true);
    }

    public void switchVibrations() {
        this.editor.putBoolean(VIBRATIONS, !isVibrations());
        this.editor.commit();
    }
}
