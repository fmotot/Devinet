package fr.eni.devinet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fr.eni.devinet.R;
import fr.eni.devinet.model.Preferences;

public abstract class MenuActivity extends AppCompatActivity {

    protected Preferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new Preferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setClickableSound((ViewGroup) (getWindow().getDecorView().getRootView()));
    }

    protected void setClickableSound(View v){
        Log.d("APPNAME", "new group");
            Log.d("APPNAME",v.toString());
        ViewGroup viewGroup = (ViewGroup)v;
        for (int i = 0; i < viewGroup.getChildCount(); i++){
            View view = viewGroup.getChildAt(i);
            if (view.isClickable()){
                view.setSoundEffectsEnabled(preferences.isSounds());
            }
            if (view instanceof ViewGroup) setClickableSound(view);
        }
    }


    /*************************************************/
    /********** GESTION DE L'ACTION BARRE ************/
    /*************************************************/

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mon_menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent iMain = new Intent(this,MainActivity.class);
                iMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iMain);
                return true;

            case R.id.action_preferences:
                Intent iPreferences = new Intent(this,PreferencesActivity.class);
                iPreferences.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iPreferences);
                return true;

            case R.id.action_about:
                Intent iAbout = new Intent(this,AboutActivity.class);
                iAbout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iAbout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}