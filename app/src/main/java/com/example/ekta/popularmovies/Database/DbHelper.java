package com.example.ekta.popularmovies.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ekta.popularmovies.Model.Movie;

import java.util.ArrayList;

/**
 * Created by Ekta on 06-10-2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    Cursor cursor;

    private static final int DATABASE_VERSION = 17;
    public static final String TABLE_MOVIES = "moviesTable";
    public static final String COLUMN_ID = "_id";


    private static final String DATABASE_NAME = "movies.db";

    public static final  String ID="movieId";
    public static final String  TITLE  ="title";
    public static final String URLSELF ="urlSelf";
    public static final String COVERIMAGE="coverimage";
    public static final String AUDIENCESCORE ="audienceScore";
    public static final String POPULARITY="popularity";
    public static final String   RELEASEDATE=   "releaseDateTheater";
    public static final String OVERVIEW = "overview";
    public static final String TAGLINE ="tagline";
    public static final String DURATION="duration";
    public static final String GENRE="genre";




    SQLiteDatabase db;
    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            +TABLE_MOVIES + "( "
            +ID
            + " integer primary key ,"
            + TITLE
            +  " text ,"
            +URLSELF
            +  " text ,"
            +COVERIMAGE
            + " text ,"
            +AUDIENCESCORE
            + " text ,"
            +POPULARITY
            + " text ,"
            +TAGLINE
            + " text ,"
            +RELEASEDATE
            + " text ,"
            +DURATION
            + " text ,"
            +GENRE
            + " text ,"
            +OVERVIEW
            +  " text);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

   /* public ArrayList<Movie> getAllCotacts()
    {
        ArrayList<Movie> array_list = new ArrayList<Movie>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from moviesDb", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex()));
            res.moveToNext();
        }
        return array_list;
    }
    */

    public boolean insertInDatabase(int id,String title,String urlSelf,String coverImage,String audienceScore,String popularity,String tagLine,String releaseDate,String duration,String genre,String overview)
    {
        Boolean isInDb= isInDatabase(id);
        if(!isInDb) {
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, id);
            contentValues.put(TITLE, title);
            contentValues.put(URLSELF, urlSelf);
            contentValues.put(COVERIMAGE, coverImage);
            contentValues.put(AUDIENCESCORE, audienceScore);
            contentValues.put(POPULARITY, popularity);
            contentValues.put(TAGLINE, tagLine);
            contentValues.put(RELEASEDATE, releaseDate);
            contentValues.put(DURATION, duration);
            contentValues.put(GENRE, genre);
            contentValues.put(OVERVIEW, overview);

            Log.d("database", "inserted");

            db.insert(TABLE_MOVIES, null, contentValues);
            // activity.getContentResolver().insert(MoviesProvider.BASE_CONTENT_URI, values);
        }
        return true;
    }
    public boolean isInDatabase(int id)
    {
         db= this.getReadableDatabase();
        Cursor c=  db.rawQuery( "select "+ ID+" from " + TABLE_MOVIES +" where " +ID +" = " + id , null );
        c.moveToFirst();
        if(c.getCount()<= 0){
            c.close();
            return false;
        }
        c.close();
        return true;


    }

    public void deleteMovie(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, ID + "=" +id , null);
        db.close();
    }

    public ArrayList<Movie> getAllComments() {
        ArrayList<Movie> movies = new ArrayList<Movie>();


        SQLiteDatabase db= this.getReadableDatabase();
        Cursor c=  db.rawQuery( "select * from " + TABLE_MOVIES, null );
        c.moveToFirst();

        if(c.moveToFirst()){
            do{

                Movie movie = new Movie(
                        c.getInt(c.getColumnIndex(ID)),
                        c.getString(c.getColumnIndex(TITLE)),
                        c.getString(c.getColumnIndex(URLSELF)),
                        c.getString(c.getColumnIndex(COVERIMAGE)),
                        c.getString(c.getColumnIndex(AUDIENCESCORE)),
                        c.getString(c.getColumnIndex(POPULARITY)),
                        c.getString(c.getColumnIndex(TAGLINE)),
                        c.getString(c.getColumnIndex(RELEASEDATE)),
                        c.getString(c.getColumnIndex(DURATION)),
                        c.getString(c.getColumnIndex(GENRE)),
                        c.getString(c.getColumnIndex(OVERVIEW))



                );
                movies.add(movie);
            }while(c.moveToNext());
        }
        c.close();


        return movies;

    }


    public Movie getMovieFromDatabase(String id) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Movie movie=new Movie();
        SQLiteDatabase     db= this.getReadableDatabase();
        Cursor c=  db.rawQuery( "select * from " + TABLE_MOVIES +" where " +ID +" = " +id, null );
        c.moveToFirst();

        if (c.moveToFirst()) {
            do {

                movie = new Movie(

                        c.getInt(c.getColumnIndex(ID)),
                        c.getString(c.getColumnIndex(TITLE)),
                        c.getString(c.getColumnIndex(URLSELF)),
                        c.getString(c.getColumnIndex(COVERIMAGE)),
                        c.getString(c.getColumnIndex(AUDIENCESCORE)),
                        c.getString(c.getColumnIndex(POPULARITY)),
                        c.getString(c.getColumnIndex(TAGLINE)),
                        c.getString(c.getColumnIndex(RELEASEDATE)),
                        c.getString(c.getColumnIndex(DURATION)),
                        c.getString(c.getColumnIndex(GENRE)),
                        c.getString(c.getColumnIndex(OVERVIEW))

                );

            } while (c.moveToNext());
        }
        c.close();


        return movie;

    }

}

