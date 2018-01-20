package local.darwin.tennisgametracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
    TODO:
    - Deuce
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

    private TextView[] setTextA = new TextView[3];
    private TextView[] setTextB = new TextView[3];
    private TextView servingTextA;
    private TextView servingTextB;
    private TextView scoreTextA;
    private TextView scoreTextB;
    private TextView faultTextA;
    private TextView faultTextB;
    private Button pointButtonA;
    private Button pointButtonB;
    private Button faultButtonA;
    private Button faultButtonB;
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
        setTextA[0] = findViewById(R.id.score_a1_text);
        setTextA[1] = findViewById(R.id.score_a2_text);
        setTextA[2] = findViewById(R.id.score_a3_text);
        setTextB[0] = findViewById(R.id.score_b1_text);
        setTextB[1] = findViewById(R.id.score_b2_text);
        setTextB[2] = findViewById(R.id.score_b3_text);
        pointButtonA = findViewById(R.id.player_a_point_button);
        pointButtonB = findViewById(R.id.player_b_point_button);
        faultButtonA = findViewById(R.id.player_a_fault_button);
        faultButtonB = findViewById(R.id.player_b_fault_button);

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
            if (faultA) {
                scoreB = increaseScore(scoreB);
                faultTextA.setVisibility(View.INVISIBLE);
                update();
            } else {
                faultTextA.setVisibility(View.VISIBLE);
                faultTextA.setText("Fault");
                faultA = true;
            }
        }
    }

    public void addFaultPlayerB(View view) {
        if (!serving) {
            if (faultB) {
                scoreA = increaseScore(scoreA);
                faultTextB.setVisibility(View.INVISIBLE);
                update();
            } else {
                faultTextB.setVisibility(View.VISIBLE);
                faultTextB.setText("Fault");
                faultB = true;
            }
        }
    }

    public void reset(View view) {
        resetScores();
        resetFaults();
        resetSets();
        pointButtonA.setEnabled(true);
        pointButtonB.setEnabled(true);
        faultButtonA.setEnabled(true);
        faultButtonB.setEnabled(true);
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

    private void resetSets() {
        currentSet = 0;
        setA = new int[]{0, 0, 0};
        setB = new int[]{0, 0, 0};
        for (int i = 0; i < 3; i++) {
            setTextA[i].setText(String.valueOf(0));
            setTextB[i].setText(String.valueOf(0));
        }
    }

    private void update() {
        displayScore(scoreTextA, scoreA);
        displayScore(scoreTextB, scoreB);
        resetFaults();
        if (scoreA == 45) {
            showDialog("Game for Player A");
            setA[currentSet] += 1;
            setTextA[currentSet].setText(String.valueOf(setA[currentSet]));
            resetScores();
            switchServing();
        } else if (scoreB == 45) {
            showDialog("Game for Player B");
            setB[currentSet] += 1;
            setTextB[currentSet].setText(String.valueOf(setB[currentSet]));
            resetScores();
            switchServing();
        }
        if (setA[currentSet] == 6 || setB[currentSet] == 6) {
            currentSet += 1;
            resetFaults();
            resetScores();
        }
        if (currentSet >= 3) {
            pointButtonA.setEnabled(false);
            pointButtonB.setEnabled(false);
            faultButtonA.setEnabled(false);
            faultButtonB.setEnabled(false);
            showDialog("Game Over");
        }
    }

    // Use a queue to display stacking dialogs?
    private void showDialog(String title) {
        builder.setTitle(title);
        builder.setNeutralButton("Next Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
}
