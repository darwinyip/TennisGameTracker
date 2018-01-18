package local.darwin.tennisgametracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/*
    TODO:
    Tennis layout:
    - 2 sides
    - Name
    - Points - 1 button to increment 0, 15, 30, 40
    - Deuce
    - Sets - 4-3 (future enhancement)
    - Serving side indicator
    - fault count - After 2 faults, switch serving side
    - Reset
 */
public class MainActivity extends AppCompatActivity {

    private int scoreA = 0;
    private int scoreB = 0;
    private int faultA = 0;
    private int faultB = 0;
    private int currentSet = 0;
    private int[] setA = new int[3];
    private int[] setB = new int[3];
    private boolean serving = true;
    private TextView servingTextA;
    private TextView servingTextB;
    private TextView scoreTextA;
    private TextView scoreTextB;
    private TextView faultTextA;
    private TextView faultTextB;
    private TextView setTextA1;
    private TextView setTextA2;
    private TextView setTextA3;
    private TextView setTextB1;
    private TextView setTextB2;
    private TextView setTextB3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        servingTextA = findViewById(R.id.serve_a_text);
        servingTextB = findViewById(R.id.serve_b_text);
        scoreTextA = findViewById(R.id.player_a_score);
        scoreTextB = findViewById(R.id.player_b_score);
        faultTextA = findViewById(R.id.fault_a_text);
        faultTextB = findViewById(R.id.fault_b_text);
        setTextA1 = findViewById(R.id.score_a1_text);
        setTextA2 = findViewById(R.id.score_a2_text);
        setTextA3 = findViewById(R.id.score_a3_text);
        setTextB1 = findViewById(R.id.score_b1_text);
        setTextB2 = findViewById(R.id.score_b2_text);
        setTextB3 = findViewById(R.id.score_b3_text);

    }

    public void addPointsPlayerA(View view) {
        scoreA = increaseScore(scoreA);
        displayScoreA();
        resetFaults();
    }

    public void addPointsPlayerB(View view) {
        scoreB = increaseScore(scoreB);
        displayScoreB();
        resetFaults();
    }

    public void reset(View view) {
        scoreA = 0;
        scoreB = 0;
        displayScoreA();
        displayScoreB();
        resetFaults();
    }

    public void addFaultPlayerA(View view) {
        if (serving) {
            faultA += 1;
            faultTextA.setVisibility(View.VISIBLE);
            faultTextA.setText(faultA + " Fault");
            if (faultA >= 2) {
                scoreB = increaseScore(scoreB);
                displayScoreB();
                faultA = 0;
                faultTextA.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void addFaultPlayerB(View view) {
        if (!serving) {
            faultB += 1;
            faultTextB.setVisibility(View.VISIBLE);
            faultTextB.setText(faultB + " Fault");
            if (faultB >= 2) {
                scoreA = increaseScore(scoreA);
                displayScoreA();
                faultB = 0;
                faultTextB.setVisibility(View.INVISIBLE);
            }
        }
    }

    private int increaseScore(int score) {
        switch (score) {
            case 0:
                score = 15;
                break;
            case 15:
                score = 30;
                break;
            case 30:
                score = 40;
                break;
            case 40:
                score = 45;
                break;
        }
        return score;
    }

    private void switchServing() {
        serving = !serving;
        if (serving) {
            servingTextA.setVisibility(View.VISIBLE);
            servingTextB.setVisibility(View.INVISIBLE);
        } else {
            servingTextA.setVisibility(View.INVISIBLE);
            servingTextB.setVisibility(View.VISIBLE);
        }
    }

    private void displayScoreA() {
        if (scoreA != 45) {
            scoreTextA.setText(String.valueOf(scoreA));
        } else {
            scoreTextA.setText("Win!");
            switchServing();
        }
    }

    private void displayScoreB() {
        if (scoreB != 45) {
            scoreTextB.setText(String.valueOf(scoreB));
        } else {
            scoreTextB.setText("Win!");
            switchServing();
        }
    }

    private void resetFaults() {
        faultTextA.setVisibility(View.INVISIBLE);
        faultTextB.setVisibility(View.INVISIBLE);
        faultA = 0;
        faultB = 0;
    }
}
