package fr.eni.devinet.dal;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.eni.devinet.model.Category;
import fr.eni.devinet.model.Level;
import fr.eni.devinet.model.Word;
import fr.eni.devinet.model.WordList;
import fr.eni.devinet.repository.CategoryDBRepository;
import fr.eni.devinet.repository.ICategoryRepository;

@Database(
        entities = {
                Word.class,
                Category.class,
                Level.class,
                WordList.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(1);

    // Permet de n'avoir qu'une connexion à la base de données
    private static AppDatabase INSTANCE;

    //Permet de fournir une instance de la dao aux couches supérieures.
    public abstract WordDao getWordDao();
    public abstract WordListDao getWordListDao();
    public abstract LevelDao getLevelDao();
    public abstract CategoryDao getCategoryDao();

    /**
     * Singleton permettant de gérer l'instance unique de la connexion à la bdd.
     *
     * @param context Contexte de l'application.
     * @return AppDatabase Représente l'instance de la base de données.
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
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
    private static Callback roomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsync().execute(INSTANCE);
        }
    };

    /**
     * Contient le code qui insert dans la base de données de manière asynchrone.
     */
    private static class PopulateDBAsync extends AsyncTask<AppDatabase, Void, Void> {
        @Override
        protected Void doInBackground(AppDatabase... db) {
//            WordDao dao = voids[0].wordDao();
//            dao.insert(new Utilisateur(0,"Tare","Gui"));

            CategoryDao categoryDao = db[0].getCategoryDao();
            WordDao wordDao = db[0].getWordDao();
            WordListDao wordListDao = db[0].getWordListDao();
            LevelDao levelDao = db[0].getLevelDao();

            categoryDao.insert(new Category(0, "Animaux"));
            categoryDao.insert(new Category(0, "Nourriture"));
            categoryDao.insert(new Category(0, "Transport"));

            levelDao.insert(new Level(0, "1"));
            levelDao.insert(new Level(0, "2"));
            levelDao.insert(new Level(0, "3"));

            wordListDao.insert(new WordList(0, "1", levelDao.get("1").getId()));
            wordListDao.insert(new WordList(0, "2", levelDao.get("1").getId()));
            wordListDao.insert(new WordList(0, "3", levelDao.get("1").getId()));
            wordListDao.insert(new WordList(0, "1", levelDao.get("2").getId()));
            wordListDao.insert(new WordList(0, "2", levelDao.get("2").getId()));
            wordListDao.insert(new WordList(0, "3", levelDao.get("2").getId()));
            wordListDao.insert(new WordList(0, "1", levelDao.get("3").getId()));
            wordListDao.insert(new WordList(0, "2", levelDao.get("3").getId()));
            wordListDao.insert(new WordList(0, "3", levelDao.get("3").getId()));

            wordDao.insert(new Word(0, "bouc", "bouc.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("1",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "cerf", "cerf.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("1",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "chat", "chat.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("1",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "lama", "lama.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("1",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "lion", "lion.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("1",levelDao.get("1").getId()).getId()));

            wordDao.insert(new Word(0, "kiri", "kiri.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("2",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "kiwi", "kiwi.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("2",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "noix", "noix.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("2",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "fève", "fève.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("2",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "mûre", "mûre.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("2",levelDao.get("1").getId()).getId()));

            wordDao.insert(new Word(0, "loup", "loup.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("3",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "lynx", "lynx.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("3",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "ours", "ours.jpeg", null, categoryDao.get("Animaux").getId(), wordListDao.get("3",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "porc", "porc.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("3",levelDao.get("1").getId()).getId()));
            wordDao.insert(new Word(0, "veau", "veau.jpg", null, categoryDao.get("Animaux").getId(), wordListDao.get("3",levelDao.get("1").getId()).getId()));

            wordDao.insert(new Word(0, "avion", "avion.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("1",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "métro", "métro.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("1",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "fusée", "fusée.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("1",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "train", "train.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("1",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "canoé", "canoé.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("1",levelDao.get("2").getId()).getId()));

            wordDao.insert(new Word(0, "biche", "biche.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("2",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "bulot", "bulot.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("2",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "carpe", "carpe.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("2",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "cobra", "cobra.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("2",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "dinde", "dinde.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("2",levelDao.get("2").getId()).getId()));

            wordDao.insert(new Word(0, "wagon", "wagon.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("3",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "sulky", "sulky.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("3",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "skate", "skate.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("3",levelDao.get("2").getId()).getId()));
            wordDao.insert(new Word(0, "kayak", "kayak.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("3",levelDao.get("2").getId()).getId()));

            wordDao.insert(new Word(0, "avocat", "avocat.jpg", null, categoryDao.get("Nourriture").getId(), wordListDao.get("1",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "banane", "banane.jpg", null, categoryDao.get("Nourriture").getId(), wordListDao.get("1",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "cerise", "cerise.jpg", null, categoryDao.get("Nourriture").getId(), wordListDao.get("1",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "farine", "farine.jpg", null, categoryDao.get("Nourriture").getId(), wordListDao.get("1",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "fraise", "fraise.jpg", null, categoryDao.get("Nourriture").getId(), wordListDao.get("1",levelDao.get("3").getId()).getId()));

            wordDao.insert(new Word(0, "bateau", "bateau.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("2",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "camion", "camion.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("2",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "cheval", "cheval.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("2",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "tandem", "tandem.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("2",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "pédalo", "pédalo.jpg", null, categoryDao.get("Transport").getId(), wordListDao.get("2",levelDao.get("3").getId()).getId()));

            wordDao.insert(new Word(0, "mangue", "mangue.jpg", null, categoryDao.get("Nourriture").getId(), wordListDao.get("3",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "oignon", "oignon.jpg", null, categoryDao.get("Nourriture").getId(), wordListDao.get("3",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "orange", "orange.jpg", null, categoryDao.get("Nourriture").getId(), wordListDao.get("3",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "papaye", "papaye.jpg", null, categoryDao.get("Nourriture").getId(), wordListDao.get("3",levelDao.get("3").getId()).getId()));
            wordDao.insert(new Word(0, "raisin", "raisin.jpg", null, categoryDao.get("Nourriture").getId(), wordListDao.get("3",levelDao.get("3").getId()).getId()));

            return null;
        }
    }
}
