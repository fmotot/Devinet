package fr.eni.devinet.model;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    public static final String VIBRATIONS = "vibrations";
    public static final String SOUNDS = "sounds";

    private boolean sounds;
    private boolean vibrations;
    private Context context;

    private SharedPreferences preferencesFile;
    private SharedPreferences.Editor editor;

    public Preferences(Context context) {
        this.context = context;
        this.preferencesFile = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        this.editor = preferencesFile.edit();
    }

    public boolean isSounds() {
        return this.preferencesFile.getBoolean(SOUNDS, true);
    }

    public void switchSounds() {
        this.editor.putBoolean(SOUNDS, !isSounds());
        this.editor.commit();
    }

    public boolean isVibrations() {
        return this.preferencesFile.getBoolean(VIBRATIONS, true);
    }

    public void switchVibrations() {
        this.editor.putBoolean(VIBRATIONS, !isVibrations());
        this.editor.commit();
    }
}
