package tech.sqlabs.shadowrunhelper.character;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class characterDBAction extends SQLiteOpenHelper {
    // Версия базы данных
    private static final int DATABASE_VERSION = 1;

    // Имя базы данных
    private static final String DATABASE_NAME = "CHARACTERSTORE";

    public characterDBAction(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DBACTION = "CREATE TABLE CHARACTER ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "AGE INTEGER, "+
                "NAME TEXT, "+
                "NATIONALITY TEXT, "+
                "GENDER TEXT, "+
                "METATYPE TEXT )";
        db.execSQL(CREATE_DBACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CHARACTER");
        this.onCreate(db);
    }

    // Имя тиблицы
    private static final String TABLE_NAME = "CHARACTER";

    // Колонки в таблице
    private static final String KEY_ID = "ID";
    private static final String KEY_AGE = "AGE";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_NATIONALITY = "NATIONALITY";
    private static final String KEY_GENDER = "GENDER";
    private static final String KEY_METATYPE = "METATYPE";

    private static final String[] COLUMNS = {KEY_ID, KEY_AGE, KEY_NAME, KEY_NATIONALITY, KEY_GENDER, KEY_METATYPE};

    public void addRec(character d){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_AGE, d.getAGE());
        values.put(KEY_NAME, d.getNAME());
        values.put(KEY_NATIONALITY, d.getNATIONALITY());
        values.put(KEY_GENDER, d.getGENDER());
        values.put(KEY_METATYPE, d.getMETATYPE());

        db.insert(TABLE_NAME,null, values);

        db.close();
    }

    public character getRec(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_NAME, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
        if (cursor != null)
            cursor.moveToFirst();

        character d = new character();
        d.setID(Integer.parseInt(cursor.getString(0)));
        d.setAGE(Integer.parseInt(cursor.getString(1)));
        d.setNAME((cursor.getString(2)));
        d.setNATIONALITY((cursor.getString(3)));
        d.setGENDER((cursor.getString(4)));
        d.setMETATYPE((cursor.getString(5)));

        db.close();

        return d;
    }

    public List<character> getRecAll() {
        List<character> ds = new LinkedList<character>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        character d = null;

        if (cursor.moveToFirst()) {
            do {
                d = new character();
                d.setID(Integer.parseInt(cursor.getString(0)));
                d.setAGE(Integer.parseInt(cursor.getString(1)));
                d.setNAME((cursor.getString(2)));
                d.setNATIONALITY((cursor.getString(3)));
                d.setGENDER((cursor.getString(4)));
                d.setMETATYPE((cursor.getString(5)));

                ds.add(d);
            } while (cursor.moveToNext());
        }
        db.close();

        return ds;
    }

    /**
     * Редактировать запись в БД
     */
    public int edRec(character d) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("AGE", d.getAGE());
        values.put("NAME", d.getNAME());
        values.put("NATIONALITY", d.getNATIONALITY());
        values.put("GENDER", d.getGENDER());
        values.put("METATYPE", d.getMETATYPE());

        int i = db.update(TABLE_NAME, values,KEY_ID+" = ?", new String[] { String.valueOf(d.getID()) });

        db.close();

        return i;
    }

    /**
     * Удалить запись из БД
     */
    public void deleteRec(character d) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,KEY_ID+" = ?", new String[] { String.valueOf(d.getID()) });

        db.close();
    }

    /**
     * Удалить запись по идентификатору
     */
    public void deleteRec(int id) {
        character d=new character();
        d.setID(id);
        deleteRec(d);
    }
}
