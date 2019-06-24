package emef4z.gmail.com.sampledataapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by USER on 13-Oct-16.
 */

public class SampleDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "sample_db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_SAMPLEDATA = "CREATE TABLE sample_table(_id INT PRIMARY KEY ,"
            +"name TEXT,phone_no TEXT,email TEXT);";

    public static final String REMOVE__CUSTOMER = "DROP TABLE customer;";

    public SampleDB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        {
            db.execSQL(CREATE_SAMPLEDATA);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String addSampleData(String name,String phone,String email)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        try{
            String INSERT_SQL = "INSERT INTO sample_table(name,phone_no,email)"+
                    "VALUES('"+name+"','"+phone+"','"+email+"')";
            db.execSQL(INSERT_SQL);

            return "Data Inserted Successfully";
        } catch (Exception ex)
        {
            return "Error!! "+ ex.getMessage();
        }
    }



    public String DeleteSampleData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            String DELETE_QUERY= "DELETE FROM sample_table";
            db.execSQL(DELETE_QUERY);

            return "Data Deleted Successfully";
        } catch (Exception ex)
        {
            return "Error!! "+ ex.getMessage();
        }
    }




    public Cursor getSampleData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_SQL = "SELECT * FROM sample_table;";
        return  db.rawQuery(SELECT_SQL,null);
    }


}
