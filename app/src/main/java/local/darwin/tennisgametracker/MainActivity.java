package local.darwin.tennisgametracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private boolean faultA = false;
    private boolean faultB = false;
    private int currentSet = 0;
    private int[] setA = new int[]{0, 0, 0};
    private int[] setB = new int[]{0, 0, 0};
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
    private AlertDialog.Builder builder;


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
        builder = new AlertDialog.Builder(this);
    }

    public void addPointsPlayerA(View view) {
        scoreA = increaseScore(scoreA);
        update();
    }

    public void addPointsPlayerB(View view) {
        scoreB = increaseScore(scoreB);
        update();
    }

    public void addFaultPlayerA(View view) {
        if (serving) {
            faultTextA.setVisibility(View.VISIBLE);
            faultTextA.setText("Fault");
            if (faultA) {
                scoreB = increaseScore(scoreB);
                displayScore(scoreTextB, scoreB);
                faultTextA.setVisibility(View.INVISIBLE);
                update();
            }
            faultA = !faultA;
        }
    }

    public void addFaultPlayerB(View view) {
        if (!serving) {
            faultTextB.setVisibility(View.VISIBLE);
            faultTextB.setText("Fault");
            if (faultB) {
                scoreA = increaseScore(scoreA);
                displayScore(scoreTextA, scoreA);
                faultTextB.setVisibility(View.INVISIBLE);
                update();
            }
            faultB = !faultB;
        }
    }

    public void reset(View view) {
        resetScores();
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
            servingTextA.setVisibility(View.VISIBLE);
            servingTextB.setVisibility(View.INVISIBLE);
        } else {
            servingTextA.setVisibility(View.INVISIBLE);
            servingTextB.setVisibility(View.VISIBLE);
        }
    }

    private void displayScore(TextView scoreText, int score) {
        scoreText.setText(String.valueOf(score));
    }

    private void resetScores() {
        scoreA = 0;
        scoreB = 0;
        displayScore(scoreTextA, scoreA);
        displayScore(scoreTextB, scoreB);
    }

    private void resetFaults() {
        faultA = false;
        faultB = false;
        faultTextA.setVisibility(View.INVISIBLE);
        faultTextB.setVisibility(View.INVISIBLE);
    }

    private void update() {
        displayScore(scoreTextA, scoreA);
        displayScore(scoreTextB, scoreB);
        resetFaults();
        if (scoreA == 45) {
            showDialog("Player A");
            setA[currentSet] += 1;
            setTextA1.setText(String.valueOf(setA[currentSet]));
            resetScores();
            switchServing();
        } else if (scoreB == 45) {
            showDialog("Player B");
            setB[currentSet] += 1;
            setTextB1.setText(String.valueOf(setB[currentSet]));
            resetScores();
            switchServing();
        }
    }

    private void showDialog(String player) {
        builder.setTitle("Game for " + player);
        builder.setNeutralButton("Next Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
}
