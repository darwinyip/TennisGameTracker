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

    int scoreA = 0;
    int scoreB = 0;
    int faultA = 0;
    int faultB = 0;
    boolean serving = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            TextView servingA = findViewById(R.id.serve_a_text);
            servingA.setVisibility(View.VISIBLE);
            TextView servingB = findViewById(R.id.serve_b_text);
            servingB.setVisibility(View.INVISIBLE);
        } else {
            TextView servingA = findViewById(R.id.serve_a_text);
            servingA.setVisibility(View.INVISIBLE);
            TextView servingB = findViewById(R.id.serve_b_text);
            servingB.setVisibility(View.VISIBLE);
        }
    }

    private void displayScoreA() {
        TextView scoreTextA = findViewById(R.id.player_a_score);
        if (scoreA != 45) {
            scoreTextA.setText(String.valueOf(scoreA));
        } else {
            scoreTextA.setText("Win!");
            switchServing();
        }
    }

    private void displayScoreB() {
        TextView scoreTextB = findViewById(R.id.player_b_score);
        if (scoreB != 45) {
            scoreTextB.setText(String.valueOf(scoreB));
        } else {
            scoreTextB.setText("Win!");
            switchServing();
        }
    }

    public void reset(View view) {
        scoreA = 0;
        scoreB = 0;
        displayScoreA();
        displayScoreB();
        resetFaults();
    }

    public void resetFaults() {
        TextView faultTextA = findViewById(R.id.fault_a_text);
        TextView faultTextB = findViewById(R.id.fault_b_text);
        faultTextA.setVisibility(View.INVISIBLE);
        faultTextB.setVisibility(View.INVISIBLE);
        faultA = 0;
        faultB = 0;
    }

    public void addFaultPlayerA(View view) {
        TextView faultTextA = findViewById(R.id.fault_a_text);
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
        TextView faultTextB = findViewById(R.id.fault_b_text);
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
}
