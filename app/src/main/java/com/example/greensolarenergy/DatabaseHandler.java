package com.example.greensolarenergy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    // Database Version
    private  static final int DATABASE_VERSION = 1;

    //Database Name
    private static  final String DATABASE_NAME = "greenS_E.db";

    //Table Name
    private static final String TABLE_NAME ="raktar";

    private static final String TABLE_NAME2 ="megrendelesek";

    //Table Fields
    private  static  final String SERIALNUMBER = "Serialnumber";
    private static  final String MEGNEVEZES= "Megnevezes";
    private static  final String DARABSZAM ="Darabszam";
    private  static  final  String AR= "Ar";
    private static final String ELHELYEZKEDES="Elhelyezkedes";

    //Table 2  Fields
    private  static  final String ID = "Id";
    private static  final String MEGRENDELO= "Megrendelo";
    private static  final String Datum ="Datum";
    private  static  final  String osszeg= "Osszeg";



    SQLiteDatabase database;

   public DatabaseHandler(Context context){
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
    this.context = context;
    database = this.getWritableDatabase();

 }

@Override
public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+SERIALNUMBER+" INTEGER PRIMARY KEY, "+MEGNEVEZES+" TEXT,"+DARABSZAM+ " INTEGER, "+AR+" INTEGER,"+ELHELYEZKEDES+" TEXT)");

    db.execSQL("CREATE TABLE "+TABLE_NAME2+" ( "+ID+" INTEGER PRIMARY KEY, "+MEGRENDELO+" TEXT,"+Datum+ " TEXT, "+osszeg+" INTEGER)");
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);


        onCreate(db);
  }

   //hozzaad a raktarthoz
  void addraktar(String megnevezes,int darab, int ar,String elhelyez){
       SQLiteDatabase db=this.getWritableDatabase();
      ContentValues cv = new ContentValues();

      cv.put(MEGNEVEZES,megnevezes);
      cv.put(DARABSZAM,darab);
      cv.put(AR,ar);
      cv.put(ELHELYEZKEDES,elhelyez);

      long result = db.insert(TABLE_NAME,null,cv);
      if(result == -1){
          Toast.makeText(context,"Sikertelen",Toast.LENGTH_SHORT).show();

      }else{
          Toast.makeText(context,"Sikeres",Toast.LENGTH_SHORT).show();

      }
  }

   // hozzaad megrendelesek

    void addMegrendeles(String megrendelo, String datum, int arak) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MEGRENDELO, megrendelo);
        cv.put(Datum, datum);
        cv.put(osszeg, arak);

        long result = db.insert(TABLE_NAME2, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Sikertelen", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sikeres", Toast.LENGTH_SHORT).show();
        }

        Log.d("DatabaseHandler", "addMegrendeles result: " + result);
    }



    //kiir

    List<String> getRaktarAdatok() {
        List<String> adatokListaja = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") int serialNumber = cursor.getInt(cursor.getColumnIndex(SERIALNUMBER));
                @SuppressLint("Range") String megnevezes = cursor.getString(cursor.getColumnIndex(MEGNEVEZES));
                @SuppressLint("Range") int darabszam = cursor.getInt(cursor.getColumnIndex(DARABSZAM));
                @SuppressLint("Range") int ar = cursor.getInt(cursor.getColumnIndex(AR));
                @SuppressLint("Range") String elhelyezkedes = cursor.getString(cursor.getColumnIndex(ELHELYEZKEDES));
                adatokListaja.add(serialNumber + " " + megnevezes + " " + darabszam + " " + ar + " " + elhelyezkedes);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return adatokListaja;
    }

    //törles serialnumber alapjan raktar
    void deleteRaktarData(int serialNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, SERIALNUMBER + "=?", new String[]{String.valueOf(serialNumber)});
        if(result == 0){
            Toast.makeText(context,"Sikertelen",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Sikeres",Toast.LENGTH_SHORT).show();
        }
        db.close();
    }


   // list view megrendelesek

    public Cursor viewData(){
       SQLiteDatabase DB =this.getReadableDatabase();
       String query = "Select * FROM "+ TABLE_NAME2;
       Cursor cursor = DB.rawQuery(query,null);
       return cursor;
    }

    // list view raktar
    public Cursor solarData(){
        SQLiteDatabase db =this.getReadableDatabase();
        String query2 = "Select * FROM "+ TABLE_NAME+ " WHERE "+MEGNEVEZES+" LIKE '%panel%'";
        Cursor cur = db.rawQuery(query2,null);
        return cur;
    }



    // list view raktar
    public Cursor inverterData(){
        SQLiteDatabase db =this.getReadableDatabase();
        String query2 = "Select * FROM "+ TABLE_NAME+ " WHERE "+MEGNEVEZES+" LIKE '%Inverter%'";
        Cursor cur = db.rawQuery(query2,null);
        return cur;
    }

    // list raktar
    public Cursor listraktarinArray(){
        SQLiteDatabase db =this.getReadableDatabase();
        String query2 = "Select * FROM "+ TABLE_NAME;
        Cursor cur = db.rawQuery(query2,null);
        return cur;
    }

}
