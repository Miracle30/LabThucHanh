package a1711062183.Restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RestaurantHelper extends SQLiteOpenHelper {
    //khai bao ten db va schema
    private static final String DB_NAME = "lunchlist.db";
    private static final int SCHEMA_VER = 1;

    public RestaurantHelper(Context context){
        super(context, DB_NAME, null, SCHEMA_VER);
    }

    public RestaurantHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    //tao bang du lieu de luu tru
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE restaurants(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, type TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //insert row
    public void insert(String name, String address, String type){
        ContentValues cv = new ContentValues();
        //dua du lieu vao theo ten field
        cv.put("name", name);
        cv.put("address", address);
        cv.put("type", type);
        //yeu cau sqlite insert vao db -> gioongs vowis ten bang tren onCreate
        getWritableDatabase().insert("restaurants","name",cv);
    }

    public String getName(Cursor c) {
        return (c.getString(1));
    }

    public String getAddress(Cursor c) {
        return (c.getString(2));
    }

    public String getType(Cursor c) {
        return (c.getString(3));
    }

    public Cursor getAll(){
        Cursor cur;
        cur = getReadableDatabase().rawQuery("SELECT _id, name, address, type FROM restaurants ORDER BY name",null);
        return (cur);
    }
}
