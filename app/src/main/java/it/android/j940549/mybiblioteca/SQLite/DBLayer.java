package it.android.j940549.mybiblioteca.SQLite;

/**
 * Created by J940549 on 22/04/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by J940549 on 22/04/2017.
 */

public class DBLayer {

    private static final String DATABASE_NAME = "myBiblioteca.db";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private  static Context ourContext;
    private SQLiteDatabase ourDatabase;
    //private static Crypto crypto;

    public DBLayer(Context c){
        this.ourContext = c;
    }

    private static class DbHelper extends SQLiteOpenHelper {


        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL("CREATE TABLE libri (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "isbn INTEGER," +
                        "titolo TEXT, autore TEXT, genere TEXT, thumbnail TEXT,descrizione TEXT,recensione TEXT," +
                        "stelle INTEGER, dove TEXT);");
                db.execSQL("CREATE TABLE prestiti (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " isbn INTEGER, dataprestito TEXT, achi TEXT, note TEXT);");
            }catch (SQLException ex){
                Toast.makeText(ourContext, ""+ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS libri;");
            db.execSQL("DROP TABLE IF EXISTS prestiti;");
            onCreate(db);
        }
    }


    public DBLayer open() throws SQLException {
        this.ourHelper = new DbHelper(ourContext);
        this.ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        this.ourHelper.close();
    }

    public Cursor getCatalogo(){

        Cursor c = ourDatabase.rawQuery("select * from libri order by titolo asc",null);

        return c;

    }
    public Cursor getOneDataSearch( String fulltext){
        fulltext="\"%"+fulltext+"%\"";

        Cursor c = ourDatabase.rawQuery("select * from libri where " +
                "titolo like " + fulltext+" or " +
                "autore like " + fulltext+" or " +
                "genere like " + fulltext+"" , null);


        return c;

    }

    public int modificaLibro(String isbn, String titolo, String autore, String genere,
                             String thumbnail, String descrizione,String recensione, String stelle, String dove){

        int c=-1;
        ContentValues contentValues= new ContentValues();
        contentValues.put("titolo", titolo);
        contentValues.put("autore", autore);
        contentValues.put("genere", genere);
        contentValues.put("thumbnail", thumbnail);
        contentValues.put("descrizione", descrizione);
        contentValues.put("recensione", recensione);
        contentValues.put("stelle", stelle);
        contentValues.put("dove", dove);

        try{
            c=ourDatabase.update("libri",contentValues,"isbn="+"\""+isbn+"\"",null);

        }catch (Exception e){

            Log.d("errore modicaLibro  ",e.toString());

        }

        return c;
    }


    public int updateRating(String isbn, double rating){

        int c=-1;
        ContentValues contentValues= new ContentValues();
        contentValues.put("stelle", rating);
        try{
            c=ourDatabase.update("libri",contentValues,"isbn="+"\""+isbn+"\"",null);

        }catch (Exception e){

            Log.d("errore modicaLibro  ",e.toString());

        }

        return c;
    }



    public boolean inserisciNewLibro(String isbn, String titolo, String autore, String genere,
                                        String thumbnail, String descrizione,String recensione, String stelle, String dove){
        isbn="\""+isbn+"\"";
        titolo="\""+titolo+"\"";
        autore="\""+autore+"\"";
        genere="\""+genere+"\"";
        thumbnail="\""+thumbnail+"\"";
        descrizione=descrizione.replaceAll("\"","'");
        descrizione="\""+descrizione+"\"";
        recensione=recensione.replaceAll("\"","'");
        recensione="\""+recensione+"\"";
        stelle="\""+stelle+"\"";
        dove="\""+dove+"\"";

        boolean c = false;
        String Query="insert into libri (_id,isbn,titolo, autore , genere ,thumbnail,descrizione,recensione, stelle,dove)" +
                "values (null,"+isbn+","+titolo+","+autore+","+genere+","+thumbnail+","+descrizione+","+recensione+","+stelle+","+dove+");";
        Log.i("query",Query);
        try{
                    ourDatabase.execSQL(Query);
        c=true;
        }catch (Exception e){
            c=false;
        }

        return c;
    }




    public int deleteLibro(String isbn){
    isbn="\""+isbn+"\"";
    int res=ourDatabase.delete("libri", "isbn="+isbn,null);
    //db.delete(DATABASE_TABLE, KEY_ROWID + "=" + row, null);
    return res;
}

    public Cursor getLibro(String isbn,String titolo, String autore, String genere, String fulltext){

        isbn="\""+isbn+"\"";
        titolo="\"%"+titolo+"%\"";
        autore="\"%"+autore+"%\"";
        genere="\"%"+genere+"%\"";
        fulltext="\"%"+fulltext+"%\"";

        Cursor c=null;
        if(!isbn.equals("\"\"")) {
            Log.i("queryGen. isbn",isbn+titolo+autore+genere+fulltext);

            c = ourDatabase.rawQuery("select * from libri where isbn= " + isbn, null);
        }else if(!fulltext.equals("\"%%\"")){
            Log.i("queryGen. full ",isbn+titolo+autore+genere+fulltext);

            c = ourDatabase.rawQuery("select * from libri where " +
                    "titolo like " + fulltext+" or " +
                    "autore like " + fulltext+" or " +
                    "genere like " + fulltext+"" , null);
        }else{
            Log.i("queryGen. else ",isbn+titolo+autore+genere+fulltext);

            String wherecause="";
            String andGenere="";
            String andTitolo="";
            String andAutore="";
            if(!genere.equals("\"%%\"")){
                andGenere="genere like "+genere+" and ";
            }
            if(!titolo.equals("\"%%\"")){
                andTitolo="titolo like "+titolo+" and ";
            }
            if(!autore.equals("\"%%\"")){
                andAutore="autore like "+autore+" and ";
            }
            wherecause=andGenere+andTitolo+andAutore;
            wherecause="where "+wherecause.substring(0,wherecause.length()-4);
            Log.i("queryGen",wherecause);
            c = ourDatabase.rawQuery("select * from libri "   + wherecause, null);
        }
return c;


        }

    public Cursor getAllPrestiti(){

        Cursor c = ourDatabase.rawQuery("select * from prestiti ",null);

        return c;

    }

    public Cursor getPrestito(String isbn){
        isbn="\""+isbn+"\"";
        Cursor c = ourDatabase.rawQuery("select * from libri,prestiti where prestiti.isbn="+isbn+"",null);

        return c;

    }


    public boolean inserisciNewPrestito(String isbn, String dataprestito, String achi){
        isbn="\""+isbn+"\"";
        dataprestito="\""+dataprestito+"\"";
        achi="\""+achi+"\"";
        boolean c = false;
        String Query="insert into prestiti (_id,isbn,dataprestito, achi )" +
                "values (null,"+isbn+","+dataprestito+","+achi+");";
        Log.i("query",Query);
        try{
            ourDatabase.execSQL(Query);
            c=true;
        }catch (Exception e){
            c=false;
        }

        return c;
    }




    public int deletePrestito(String isbn){
        isbn="\""+isbn+"\"";
        int res=ourDatabase.delete("prestiti", "isbn="+isbn,null);
        //db.delete(DATABASE_TABLE, KEY_ROWID + "=" + row, null);
        return res;
    }


    ////////


public SQLiteDatabase getDB(){
    return this.ourDatabase;

}


}