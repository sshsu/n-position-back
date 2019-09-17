package sapphire.dualnback;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Objects;

public class DualProvider extends ContentProvider {
	private static String LOGTAG = "DualProvider:";
	private static final String DBNAME = "Dual";
	private static final String AUTHORITY = "sapphire.dualnback";
	private static final String TABLE_NAME = "Scores";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"+TABLE_NAME);
	public static final String COL_SCORE="SCORE", COL_DATE_TIME="DATE_TIME", COL_LEVEL="LEVEL", COL_ID="_ID";
	//Table create string based on column names
	private static final String SQL_CREATE_MAIN ="CREATE TABLE "+TABLE_NAME+" "+"(" + COL_ID +
			" INTEGER PRIMARY KEY, "+COL_DATE_TIME+" DATETIME,"+COL_SCORE+" INTEGER,"+COL_LEVEL+" INTEGER DEFAULT 0)";

	//URI Matcher object to facilitate switch cases between URIs
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private MainDatabaseHelper mOpenHelper;

	//Constructor adds two URIs, use for case statements
	public DualProvider() {
		//Match to the authority and the table name, assign 1
		sUriMatcher.addURI(AUTHORITY,TABLE_NAME,1);
		//Match to the authority and the table name, and an ID, assign 2
		sUriMatcher.addURI(AUTHORITY,TABLE_NAME+"/#",2);
	}

	//Delete functionality for the Content Provider. Pass the URI for the table and the ID to be deleted
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.e("uri: ", uri.toString());
		// for(int i = 0; i < selectionArgs.length; i++)
		//   System.out.println("selection args: " + selectionArgs[0]);
		Log.e("del num: ", uri.getPathSegments().get(1));
		//Match on URI with ID
		if (sUriMatcher.match(uri) == 2) {
			String id = uri.getPathSegments().get(1);
			selection = COL_ID + "=" + id + (!TextUtils.isEmpty(selection) ? "AND (" + selection + ")" : "");
		} else {//Else, error is thrown
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		//Delete from the database, return integer value for how many rows are deleted
		int deleteCount = mOpenHelper.getWritableDatabase().delete(TABLE_NAME,selection,selectionArgs);

		//Notify calling context
		Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
		//Return number of rows deleted (if any)
		return deleteCount;
	}

	//Insert functionality of the Content Provider. Pass ContentValues object with values to be inserted
	@Override
	public Uri insert(@NonNull Uri uri, ContentValues values) {
		//Match against a URI with just the table name
		if (!(sUriMatcher.match(uri) ==1))
			Log.e(LOGTAG, "URI not recognized " + uri);
		//Insert into the table, return the id of the inserted row
		long id = mOpenHelper.getWritableDatabase().insert(TABLE_NAME,null,values);
		Log.e("insert row", String.valueOf(id));

		//Notify context of change
		Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
		//Return the URI with the ID at the end
		return Uri.parse(CONTENT_URI+"/" + id);
	}

	//Create Content Provider. Checks for db/table. If not, creates. Implemented in MainDatabaseHelper Class
	@Override
	public boolean onCreate() {

		//Create new helper obj. Notice db isn't created/opened til SQLiteOpenHelper.getWritableDatabase called
		mOpenHelper = new MainDatabaseHelper(getContext());
		return true;
	}

	//Query Functionality for Content Provider. Pass either table name, or the table name with an ID
	@Override
	public Cursor query(@NonNull Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {

		//Use an SQLiteQueryBuilder object to create the query
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		//Set the table to be queried
		queryBuilder.setTables(TABLE_NAME);

		//Match on either the URI with or without an ID
		switch (sUriMatcher.match(uri)){
			case 1:
				//If no ID, and no sort order, specify the sort order as by ID Ascending
				if(TextUtils.isEmpty(sortOrder)) sortOrder="_ID ASC";
				break;
			case 2:
				//Otherwise, set the selection of the URI to the ID
				selection = selection + "_ID = " + uri.getLastPathSegment();
				break;
			default:
				Log.e(LOGTAG, "URI not recognized " + uri);
		}
		//Query the db based on columns to be returned, selection criteria, arguments, and sort order
		return queryBuilder.query(mOpenHelper.getWritableDatabase(),projection,selection,
				selectionArgs,null,null,sortOrder);
	}

	//Update functionality for the Content Provider
	@Override
	public int update(@NonNull Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		switch (sUriMatcher.match(uri)){
			case 1:
				//Allow update based on multiple selections
				break;
			case 2:
				//Allow updates based on a single ID
				String id = uri.getPathSegments().get(1);
				selection = COL_ID + "=" + id +
						(!TextUtils.isEmpty(selection) ?
								"AND (" + selection + ")" : "");
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		//Perform updates and return the number that were updated
		int updateCount = mOpenHelper.getWritableDatabase().update(TABLE_NAME,values,
				selection,selectionArgs);

		//Notify the context
		Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
		//Return the number of rows updated
		return updateCount;
	}

	//Class for creating instance of SQLiteOpenHelper. Performs creation of SQLite DB if none exists
	protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
		//Instantiates open helper for provider's SQLite data repo. Do not do db creation/upgrade here.
		MainDatabaseHelper(Context context) {
			super(context, DBNAME, null, 1);
		}

		//Creates data repo. This is called when provider opens the repo and SQLite reports !exist.
		public void onCreate(SQLiteDatabase db) {
			// Creates the main table
			db.execSQL(SQL_CREATE_MAIN);
		}

		public void onUpgrade(SQLiteDatabase db, int int1, int int2){
			db.execSQL("DROP TABLE IF EXISTS ToDoList");
			onCreate(db);
		}
	}
	//*******DON'T CARE PILE
	@Override
	public String getType(@NonNull Uri uri) { //Not implemented. Would return the MIME type requests
		throw new UnsupportedOperationException("Not yet implemented");
	}
}