package fr.eni.devinet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.stetho.Stetho;

import fr.eni.devinet.R;
import fr.eni.devinet.model.Category;
import fr.eni.devinet.repository.CategoryDBRepository;
import fr.eni.devinet.repository.ICategoryRepository;

public abstract class MenuActivity extends AppCompatActivity {

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
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.action_parametres:
                Toast.makeText(this, "parametres", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}