package sapphire.dualnback;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;

public class PlayActivity extends AppCompatActivity {
    Vector<Button> butVec = new Vector<>(9);
    Vector<Integer> numSeq = new Vector<>(0);
    Vector<Integer> colSeq = new Vector<>(0);
    int n;
    int[] score;
    int count;

  //  Integer[] numTrialsKey = {0,21,24,29,36,45,56,69,84,101,120};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        init();
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
    }

    public void posClick (View view){
        clickBut((Button)this.findViewById(R.id.posBut));
        if(count < n)
            score[2] += 1;
    }
    public void colClick (View view){
        clickBut((Button)this.findViewById(R.id.colBut));
        if(count < n)
            score[3] += 1;
    }

    public void play(View view) {
        //grey out play
        clickBut((Button)this.findViewById(R.id.playBut));
        //reset score(pos correct, color correct, pos miss, color miss, pos wrong, color wrong) -- Reset sequence
        score = new int[]{0, 0, 0, 0, 0, 0};
        numSeq.clear();
        colSeq.clear();
        //get n
        n = Integer.parseInt(((TextView)this.findViewById(R.id.nValue)).getText().toString());
        count = 0;
        Random random = new Random();
        lightOn(random);
    }

    public void clickBut(Button b) {
        b.setTextColor(getResources().getColor(R.color.light_grey));
        b.setClickable(false);
    }
    public void unclickBut(Button b) {
        b.setTextColor(getResources().getColor(R.color.black));
        b.setClickable(true);
    }

    private void lightOn(final Random random) {
        if(count != n+5) {
            unclickBut((Button)this.findViewById(R.id.posBut));
            unclickBut((Button)this.findViewById(R.id.colBut));
            //clear sequence on first iteration
            final int curPosInt = random.nextInt(8);
            numSeq.add(curPosInt);
            final int curColInt = random.nextInt(7);
            colSeq.add(curColInt);
            setButColor(butVec.get(curPosInt), curColInt);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    butVec.get(curPosInt).setBackgroundColor(getResources().getColor(R.color.grey));
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            count +=1 ;
                            lightOn(random);
                        }
                    }, 1500);
                }
            }, 800);
        }
        else {
            unclickBut((Button)this.findViewById(R.id.playBut));
            clickBut((Button)this.findViewById(R.id.posBut));
            clickBut((Button)this.findViewById(R.id.colBut));
            for(int i = 0; i < 4; i++){
                System.out.println(score[i]);
            }
            for(int i = 0; i < numSeq.size(); i ++)
                System.out.println("Seq " + i + ": " + numSeq.get(i));
        }
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
}