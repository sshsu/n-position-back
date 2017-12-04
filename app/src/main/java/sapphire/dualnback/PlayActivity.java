package sapphire.dualnback;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;

public class PlayActivity extends AppCompatActivity {
    Vector<Button> butVec = new Vector<>(9);
    Vector<Integer> posSeq = new Vector<>(0), colSeq = new Vector<>(0);
    int[] score;
    int n, count;
    boolean posMatch, colMatch, found;
    ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		n = Integer.parseInt(getIntent().getStringExtra("difficulty"));
		init();
	}

	public void setSeq() {
		found = false;
		Random ran = new Random();
		while(!found) {
			posSeq.clear();
			colSeq.clear();
			for (int i = 0; i < n + 5; i++)
				posSeq.add(ran.nextInt(8));
			for (int i = 0; i < n + 5; i++)
				colSeq.add(ran.nextInt(5));
			for (int i = n; i < posSeq.size(); i++)
				if ((posSeq.get(i) == posSeq.get(i - n)) || (colSeq.get(i) == colSeq.get(i - n)))
					found = true;
		}
	}

	public void play(View view) {
		progressBar.setMax(n+5);
		setSeq();
		//score(pos correct, color correct, pos miss, color miss, pos wrong, color wrong) -- Reset sequence;
		score = new int[]{0,0,0,0,0,0};
		Log.e("scoreLen", String.valueOf(score.length));
		((Button)this.findViewById(R.id.playBut)).setClickable(false);
		for (Button b : butVec)
			b.setClickable(true);
		count = 0;
		start();
	}

	private void start() {

		if(count != Math.floor(n + 5)) {
			posMatch = false;
			colMatch = false;
			unclickBut((Button)this.findViewById(R.id.posBut));
			unclickBut((Button)this.findViewById(R.id.colBut));
			if(count-n >= 0) {
				if (posSeq.get(count) == posSeq.get(count-n))
					posMatch = true;
				if (colSeq.get(count) == colSeq.get(count-n))
					colMatch = true;
			}
			setButColor(butVec.get(posSeq.get(count)), colSeq.get(count));
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					butVec.get(posSeq.get(count)).setBackgroundColor(getResources().getColor(R.color.grey));
					handler.postDelayed(new Runnable() {
						public void run() {
							if(posMatch)
								score[4] += 1;
							if(colMatch)
								score[5] += 1;
							count +=1;
							progressBar.setProgress(count);
							start();
						}
					}, 1200);
				}
			}, 1200);
		}
		else {
			unclickBut((Button)this.findViewById(R.id.playBut));
			clickBut((Button)this.findViewById(R.id.posBut));
			clickBut((Button)this.findViewById(R.id.colBut));
			for(int i = 0; i < 6; i++){
				System.out.println(score[i]);
			}
			for(int i = 0; i < posSeq.size(); i ++) {
				System.out.println("posSeq " + i + ": " + posSeq.get(i));
				System.out.println("colSeq " + i + ": " + colSeq.get(i));
			}
			for(int i : score)
				Log.e("score", String.valueOf(i));
			//addScoreDB();
			showAlertDialog();
		}
	}

    private void showAlertDialog() { // Prepare grid view
        GridView gridView = new GridView(this);
        List<String> scores = new ArrayList<>();
        scores.add(" ");
        scores.add("Position:");
        scores.add("Color:");
        scores.add("Correct");
        scores.add(String.valueOf(score[0]));
        scores.add(String.valueOf(score[1]));
        scores.add("Missed");
        scores.add(String.valueOf(score[2]));
        scores.add(String.valueOf(score[3]));
        scores.add("Wrong");
        scores.add(String.valueOf(score[4]));
        scores.add(String.valueOf(score[5]));
        scores.add(" ");
        scores.add(" ");
        scores.add(" ");
        scores.add("Total");
        int numerator = score[0] + score[1];
        int denominator = score[0]+score[1] + score[2]+ score[3]+score[4]+score[5];
        int pct = (100*numerator)/denominator;
        addScoreDB(pct);
        scores.add(String.valueOf(numerator) + "/" + String.valueOf(denominator));
        scores.add(String.valueOf(pct) + "%");
        gridView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, scores));
        gridView.setNumColumns(3);

        // Set grid view to alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(gridView);
        builder.setTitle("Scores");
        AlertDialog dialog = builder.create();
        dialog.show();
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialog.cancel();
			}
		});

    }

    public void posClick (View view){
        clickBut((Button)this.findViewById(R.id.posBut));
        if(posMatch)
            score[0] += 1;
        else
            score[2] += 1;
        posMatch = false;
    }
    public void colClick (View view){
        clickBut((Button)this.findViewById(R.id.colBut));
        if(colMatch)
            score[1] += 1;
        else
            score[3] += 1;
        colMatch = false;
    }
    public void clickBut(Button b) {
        b.setTextColor(getResources().getColor(R.color.light_grey));
        b.setClickable(false);
    }
    public void unclickBut(Button b) {
        b.setTextColor(getResources().getColor(R.color.black));
        b.setClickable(true);
    }

	public void addScoreDB(int pct) {
		ContentValues cv = new ContentValues();
		cv.put(DualProvider.COL_DATE_TIME, new SimpleDateFormat("MM-dd-YYYY hh:mm a", Locale.US).format(new Date()));
		cv.put(DualProvider.COL_SCORE, pct);
		cv.put(DualProvider.COL_LEVEL, n);
		getContentResolver().insert(DualProvider.CONTENT_URI, cv);
		//tableData();
	}
    public void setButColor(Button b, int color) {
        switch(color) {
            case 0 :
                b.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case 1:
                b.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case 2 :
                b.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case 3:
                b.setBackgroundColor(getResources().getColor(R.color.light_blue));
                break;
            case 4 :
                b.setBackgroundColor(getResources().getColor(R.color.pink));
                break;
            case 5:
                b.setBackgroundColor(getResources().getColor(R.color.orange));
                break;
            case 6:
                b.setBackgroundColor(getResources().getColor(R.color.purple));
                break;
            default:
                b.setBackgroundColor(getResources().getColor(R.color.black));
                break;
        }
    }
	private void init() {
		butVec.add((Button) findViewById(R.id.but0));
		butVec.add((Button) findViewById(R.id.but1));
		butVec.add((Button) findViewById(R.id.but2));
		butVec.add((Button) findViewById(R.id.but3));
		butVec.add((Button) findViewById(R.id.but4));
		butVec.add((Button) findViewById(R.id.but5));
		butVec.add((Button) findViewById(R.id.but6));
		butVec.add((Button) findViewById(R.id.but7));
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
	}

	public void tableData() {
		String[] projection = {DualProvider.COL_ID, DualProvider.COL_DATE_TIME, DualProvider.COL_SCORE, DualProvider.COL_LEVEL};
		Cursor cursor = getContentResolver().query(DualProvider.CONTENT_URI,projection,null,null,"_ID DESC");
		if(cursor != null) {
			if(cursor.getCount() > 0){
				for(int i = 0; i < cursor.getCount(); i++) {
					cursor.moveToPosition(i);
					Log.e("Cursor pos(i) ", String.valueOf(i));
					Log.e("ID ", String.valueOf(cursor.getInt(0)));
					Log.e("Date/Time ", cursor.getString(1));
					Log.e("Score ", String.valueOf(cursor.getInt(2)));
					Log.e("Level ", String.valueOf(cursor.getInt(3)));
				}
			}
			cursor.close();
		}
	}
}