package fr.eni.devinet.dal;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.eni.devinet.model.Word;
import fr.eni.devinet.model.WordList;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(1);

    // Permet de n'avoir qu'une connexion à la base de données
    private static AppDatabase INSTANCE;

    //Permet de fournir une instance de la dao aux couches supérieures.
    public abstract WordDao wordDao();
    public abstract WordListDao wordListDao();

    /**
     * Singleton permettant de gérer l'instance unique de la connexion à la bdd.
     *
     * @param context Contexte de l'application.
     * @return AppDatabase Représente l'instance de la base de données.
     */
    public static synchronized AppDatabase getInstance(Context context)
    {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "devinet.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return INSTANCE;
    }

    /**
     * Permet de remplir la base de données au moment de sa création.
     */
    private static Callback roomCallBack = new Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateBddAsync().execute(INSTANCE);
        }
    };

    /**
     * Contient le code qui insert dans la base de données de manière asynchrone.
     */
    private static class PopulateBddAsync extends AsyncTask<AppDatabase, Void, Void>
    {
        @Override
        protected Void doInBackground(AppDatabase... voids)
        {
//            WordDao dao = voids[0].wordDao();
//            dao.insert(new Utilisateur(0,"Tare","Gui"));
            return null;
        }
    }
}
