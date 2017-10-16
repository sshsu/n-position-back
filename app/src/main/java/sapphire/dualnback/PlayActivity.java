package sapphire.dualnback;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.Vector;

public class PlayActivity extends AppCompatActivity {
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    Vector<Button> butVec = new Vector<Button>(9);
    Vector<Integer> sequence = new Vector<Integer>(0);

    Integer[] numTrialsKey = {0,21,24,29,36,45,56,69,84,101,120};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        butVec.add(b1 = (Button) findViewById(R.id.but1));
        butVec.add(b2 = (Button) findViewById(R.id.but2));
        butVec.add(b3 = (Button) findViewById(R.id.but3));
        butVec.add(b4 = (Button) findViewById(R.id.but4));
        butVec.add(b5 = (Button) findViewById(R.id.but5));
        butVec.add(b6 = (Button) findViewById(R.id.but6));
        butVec.add(b7 = (Button) findViewById(R.id.but7));
        butVec.add(b8 = (Button) findViewById(R.id.but8));
        butVec.add(b9 = (Button) findViewById(R.id.but9));
    }

    public void colorClick (){
        new ClickGet();
    }

    public void nap() {

    }
    public void play(View view) {
        Random random = new Random();
        buttonsOff();
        lightOneforSec(0, random);
    }

    private void buttonsOff() {
        for (int i = 0; i < 9; i++)
            butVec.get(i).setBackgroundColor(Color.parseColor("#6a6969"));
    }

    private void lightOneforSec(final int butNum,final Random random) {
        if(butNum != 5) {
            final int curRandomInt = random.nextInt(8) + 1;
            System.out.println("butNum: " + butNum + " curRandomInt: " + curRandomInt);
            butVec.get(curRandomInt).setBackgroundColor(Color.parseColor("#1ede2f"));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    sequence.add(Integer.valueOf(curRandomInt));
                    butVec.get(curRandomInt).setBackgroundColor(Color.parseColor("#6a6969"));
                    lightOneforSec(butNum + 1, random);
                }
            }, 2000);
        }
        else {
            for(int i = 0; i < sequence.size(); i ++)
                System.out.println("Seq " + i + ": " + sequence.get(i));
        }
    }
}