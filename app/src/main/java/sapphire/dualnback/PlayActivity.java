package sapphire.dualnback;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.GridView;
import android.widget.ProgressBar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

public class PlayActivity extends AppCompatActivity {
    Vector<ImageButton> butVec = new Vector<>(9);
    Vector<Integer> posSeq = new Vector<>(0), dotPosSeq = new Vector<>(0);
    int[] score;
    int n, count, blockAmount = 15, wrong_count;
    boolean posMatch, colMatch, found;
    boolean butPosClick;
    ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		n = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("difficulty")));
		init();
	}

	public void setSeq() {

			Random ran = new Random();
			posSeq.clear();
			dotPosSeq.clear();
			for (int i = 0; i < n + blockAmount; i++) {
				posSeq.add(ran.nextInt(9));
				dotPosSeq.add(0);
			}
			int find_amound = 0;

			Vector<Integer> marks  = new Vector<>(n + blockAmount);
			for(int i = 0 ; i < n + blockAmount; ++i){
				marks.add(0);
			}

			//setting there are half of amount postion that can hit n back position
			//for game joy
			for(int i = 0 ; i< blockAmount / 2; ++i){
				int pos = ran.nextInt(blockAmount) + n;
				while(true){
					if(marks.get(pos - n) == 0){
						if(marks.get(pos) == 0){
							dotPosSeq.set(pos, ran.nextInt(9));
							marks.set(pos, 1);
						}
						int value = dotPosSeq.get(pos);
						dotPosSeq.set(pos - n, value);
						marks.set(pos - n, 1);
						break;
					}

					pos =( pos + 1 - n) % blockAmount + n;
				}
			}

			for(int i = 0; i < n + blockAmount ;++i){
				if(marks.get(i) == 0){
					dotPosSeq.set(i, ran.nextInt(9));
				}
			}

	}

	public void play(View view) {
		progressBar.setMax(n+blockAmount);
		progressBar.setProgress(count);

		setSeq();
		//score(pos correct, color correct, pos miss, color miss, pos wrong, color wrong) -- Reset sequence;
		score = new int[]{0,0,0,0,0,0};
		Log.e("scoreLen", String.valueOf(score.length));
		clickBut(findViewById(R.id.playBut));
		clickBut(findViewById(R.id.backBut));
		clickBut(findViewById(R.id.posBut));

		for (ImageButton b : butVec)
			b.setClickable(true);
		wrong_count = 0;
		count = -1;
		start();
	}

	private void start() {
		// count the score
		++count;
		if(count < Math.floor(n + blockAmount)) {
			posMatch = false;
			setButColor(butVec.get(posSeq.get(count)), dotPosSeq.get(count));
			if(count >= n){
				if (dotPosSeq.get(count).equals(dotPosSeq.get(count - n)))
					wrong_count += 1;
				unclickBut(this.findViewById(R.id.posBut));
			}
			final Handler handler = new Handler();
			handler.postDelayed(() -> {
				setButColor(butVec.get(posSeq.get(count)), -1);
				handler.postDelayed(() -> {
					progressBar.setProgress(count);
					start();
				}, 1500);
			}, 1500);
		}
		else {
			unclickBut(this.findViewById(R.id.playBut));
			unclickBut(this.findViewById(R.id.backBut));
			clickBut(this.findViewById(R.id.posBut));
			scoreRound();
		}
	}

    private void scoreRound() { // Prepare grid view
        GridView gridView = new GridView(this);
        List<String> scores = new ArrayList<>();
        scores.add(" ");
        scores.add("Position:");
        scores.add("");
        scores.add("Correct");
        int correct_count = count - wrong_count - n;
        scores.add(String.valueOf(correct_count));
        scores.add(String.valueOf(""));
        scores.add("Missed");
        scores.add(String.valueOf(""));
        scores.add(String.valueOf(""));
        scores.add("Wrong");
        scores.add(String.valueOf(wrong_count));
        scores.add(String.valueOf(""));
        scores.add(" ");
        scores.add(" ");
        scores.add(" ");
        scores.add("Total");
        int numerator = correct_count;
        int denominator = count - n;
        int pct = ( 100 * numerator )/denominator;
        addScoreDB(pct);
        scores.add(pct + "%");
		scores.add("");
        gridView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scores));
        gridView.setNumColumns(3);

        // Set grid view to alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(gridView);
        builder.setTitle("Scores");
        AlertDialog dialog = builder.create();
        dialog.show();
		gridView.setOnItemClickListener((parent, view, position, id) -> dialog.cancel());
    }

    public void posClick (View view){
        clickBut(this.findViewById(R.id.posBut));
		if (dotPosSeq.get(count).equals(dotPosSeq.get(count - n))){
			wrong_count -= 1;
		}
    }

    public void clickBut(Button b) {
        b.setTextColor(getResources().getColor(R.color.black));
        b.setClickable(false);
    }
    public void unclickBut(Button b) {
        b.setTextColor(getResources().getColor(R.color.white));
        b.setClickable(true);
    }

    public void goBack(View view) {
    	startActivity(new Intent(this, MainActivity.class));
	}

	public void addScoreDB(int pct) {
		ContentValues cv = new ContentValues();
		cv.put(DualProvider.COL_DATE_TIME, new SimpleDateFormat("MM-dd-YYYY hh:mm a", Locale.US).format(new Date()));
		cv.put(DualProvider.COL_SCORE, pct);
		cv.put(DualProvider.COL_LEVEL, n);
		getContentResolver().insert(DualProvider.CONTENT_URI, cv);
		tableData();
	}

//	public void dbTest(View view) {
//		ContentValues cv = new ContentValues();
//		cv.put(DualProvider.COL_DATE_TIME, new SimpleDateFormat("MM-dd-YYYY hh:mm a", Locale.US).format(new Date()));
//		cv.put(DualProvider.COL_SCORE, 10);
//		cv.put(DualProvider.COL_LEVEL, 1);
//		getContentResolver().insert(DualProvider.CONTENT_URI, cv);
//		tableData();
//	}
    public void setButColor(ImageButton b, int color) {
        switch(color) {
            case 0 :
            	b.setImageDrawable(getResources().getDrawable(R.drawable.dot1));
                break;
            case 1:
				b.setImageDrawable(getResources().getDrawable(R.drawable.dot2));
				break;
            case 2 :
				b.setImageDrawable(getResources().getDrawable(R.drawable.dot3));
				break;
			case 3:
				b.setImageDrawable(getResources().getDrawable(R.drawable.dot4));
					break;
            case 4 :
				b.setImageDrawable(getResources().getDrawable(R.drawable.dot5));
				break;
            case 5:
				b.setImageDrawable(getResources().getDrawable(R.drawable.dot6));
				break;
			case 6:
				b.setImageDrawable(getResources().getDrawable(R.drawable.dot7));
				break;
			case 7:
				b.setImageDrawable(getResources().getDrawable(R.drawable.dot8));
				break;
			case 8:
				b.setImageDrawable(getResources().getDrawable(R.drawable.dot9));
				break;
			case -1:
				b.setImageDrawable(getResources().getDrawable(R.drawable.dot_grey));
        }
    }
	private void init() {
		butVec.add(findViewById(R.id.but0));
		butVec.add(findViewById(R.id.but1));
		butVec.add(findViewById(R.id.but2));
		butVec.add(findViewById(R.id.but3));
		butVec.add(findViewById(R.id.but4));
		butVec.add(findViewById(R.id.but5));
		butVec.add(findViewById(R.id.but6));
		butVec.add(findViewById(R.id.but7));
		butVec.add(findViewById(R.id.but8));
		progressBar = findViewById(R.id.progressBar);
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