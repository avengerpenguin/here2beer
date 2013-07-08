package uk.co.rossfenning.android.here2beer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import uk.co.rossfenning.android.here2beer.model.Pub;

import java.util.LinkedList;

public class FavouritesDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String FAVOURITES_TABLE = "favourites";
    private static final String FAVOURITES_TABLE_CREATE =
        "CREATE TABLE " + FAVOURITES_TABLE + " (id varchar(50) PRIMARY KEY, name varchar(100), vicinity varchar(300));";

    public FavouritesDAO(final Context context) {
        super(context, "here2beer", null, DATABASE_VERSION);
    }

    public void addToFavourites(final Pub pub) {
        if (!isFavourite(pub)) {
            final ContentValues values = new ContentValues();
            values.put("id", pub.getId());
            values.put("name", pub.getName());
            values.put("vicinity", pub.getVicinity());

            final SQLiteDatabase database = getWritableDatabase();
            database.insertWithOnConflict(
                FAVOURITES_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            database.close();
        }
    }

    public void removeFromFavourites(final Pub pub) {
        final SQLiteDatabase database = getWritableDatabase();
        database.delete(FAVOURITES_TABLE, "id=?", new String[]{pub.getId()});
        database.close();
    }
    
    public boolean isFavourite(final Pub pub) {
        final SQLiteDatabase database = getReadableDatabase();
        final Cursor query = database.query(
            FAVOURITES_TABLE,
            null,
            "id=?",
            new String[]{pub.getId()},
            null, null, null);
        
        
        int count = query.getCount();
        query.close();
        database.close();
        return count > 0;
    }
    
    public LinkedList<Pub> getAllFavourites() {
        final SQLiteDatabase database = getReadableDatabase();
        final Cursor cursor = database.query(
            FAVOURITES_TABLE, new String[]{"id", "name", "vicinity"}, null, null, null, null, null);

        cursor.moveToFirst();
        
        final LinkedList<Pub> favourites = new LinkedList<Pub>();
        while (!cursor.isAfterLast()) {
            final Pub pub = new Pub();
            pub.setId(cursor.getString(0));
            pub.setName(cursor.getString(1));
            pub.setVicinity(cursor.getString(2));
            
            favourites.add(pub);
            
            Log.d(getClass().getSimpleName(), "Found favourite: " + pub);
            cursor.moveToNext();
        }
        
        cursor.close();
        database.close();
        return favourites;
    }
    
    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(FAVOURITES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
