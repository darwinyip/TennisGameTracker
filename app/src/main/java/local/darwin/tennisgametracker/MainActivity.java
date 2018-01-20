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

    private int score[] = new int[]{0, 0};
    private boolean[] fault = new boolean[]{false, false};
    private int[][] set = new int[][]{{0, 0, 0}, {0, 0, 0}};
    private int currentSet = 0;
    private boolean serving = true;
    private TextView[][] setText = new TextView[2][3];
    private TextView[] servingText = new TextView[2];
    private TextView[] scoreText = new TextView[2];
    private TextView[] faultText = new TextView[2];
    private Button pointButton[] = new Button[2];
    private Button faultButton[] = new Button[2];
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        servingText[Player.PLAYER_A] = findViewById(R.id.serve_a_text);
        servingText[Player.PLAYER_B] = findViewById(R.id.serve_b_text);
        scoreText[Player.PLAYER_A] = findViewById(R.id.player_a_score);
        scoreText[Player.PLAYER_B] = findViewById(R.id.player_b_score);
        faultText[Player.PLAYER_A] = findViewById(R.id.fault_a_text);
        faultText[Player.PLAYER_B] = findViewById(R.id.fault_b_text);
        setText[Player.PLAYER_A][0] = findViewById(R.id.score_a1_text);
        setText[Player.PLAYER_A][1] = findViewById(R.id.score_a2_text);
        setText[Player.PLAYER_A][2] = findViewById(R.id.score_a3_text);
        setText[Player.PLAYER_B][0] = findViewById(R.id.score_b1_text);
        setText[Player.PLAYER_B][1] = findViewById(R.id.score_b2_text);
        setText[Player.PLAYER_B][2] = findViewById(R.id.score_b3_text);
        pointButton[Player.PLAYER_A] = findViewById(R.id.player_a_point_button);
        pointButton[Player.PLAYER_B] = findViewById(R.id.player_b_point_button);
        faultButton[Player.PLAYER_A] = findViewById(R.id.player_a_fault_button);
        faultButton[Player.PLAYER_B] = findViewById(R.id.player_b_fault_button);

        builder = new AlertDialog.Builder(this);
    }

    public void addPointsPlayerA(View view) {
        score[Player.PLAYER_A] = increaseScore(score[Player.PLAYER_A]);
        update();
    }

    public void addPointsPlayerB(View view) {
        score[Player.PLAYER_B] = increaseScore(score[Player.PLAYER_B]);
        update();
    }

    public void addFaultPlayerA(View view) {
        if (serving) {
            if (fault[Player.PLAYER_A]) {
                score[Player.PLAYER_B] = increaseScore(score[Player.PLAYER_B]);
                faultText[Player.PLAYER_A].setVisibility(View.INVISIBLE);
                update();
            } else {
                faultText[Player.PLAYER_A].setVisibility(View.VISIBLE);
                faultText[Player.PLAYER_A].setText("Fault");
                fault[Player.PLAYER_A] = true;
            }
        }
    }

    public void addFaultPlayerB(View view) {
        if (!serving) {
            if (fault[Player.PLAYER_B]) {
                score[Player.PLAYER_A] = increaseScore(score[Player.PLAYER_A]);
                faultText[Player.PLAYER_B].setVisibility(View.INVISIBLE);
                update();
            } else {
                faultText[Player.PLAYER_B].setVisibility(View.VISIBLE);
                faultText[Player.PLAYER_B].setText("Fault");
                fault[Player.PLAYER_B] = true;
            }
        }
    }

    public void reset(View view) {
        resetScores();
        resetFaults();
        resetSets();
        pointButton[Player.PLAYER_A].setEnabled(true);
        pointButton[Player.PLAYER_B].setEnabled(true);
        faultButton[Player.PLAYER_A].setEnabled(true);
        faultButton[Player.PLAYER_B].setEnabled(true);
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
            servingText[Player.PLAYER_A].setVisibility(View.VISIBLE);
            servingText[Player.PLAYER_B].setVisibility(View.INVISIBLE);
        } else {
            servingText[Player.PLAYER_A].setVisibility(View.INVISIBLE);
            servingText[Player.PLAYER_B].setVisibility(View.VISIBLE);
        }
    }

    private void displayScore(TextView scoreText, int score) {
        scoreText.setText(String.valueOf(score));
    }

    private void winGame(int player) {
        set[player][currentSet] += 1;
        setText[player][currentSet].setText(String.valueOf(set[player][currentSet]));
        resetScores();
        switchServing();
    }

    private void resetScores() {
        score[Player.PLAYER_A] = 0;
        score[Player.PLAYER_B] = 0;
        displayScore(scoreText[Player.PLAYER_A], score[Player.PLAYER_A]);
        displayScore(scoreText[Player.PLAYER_B], score[Player.PLAYER_B]);
    }

    private void resetFaults() {
        fault[Player.PLAYER_A] = false;
        fault[Player.PLAYER_B] = false;
        faultText[Player.PLAYER_A].setVisibility(View.INVISIBLE);
        faultText[Player.PLAYER_B].setVisibility(View.INVISIBLE);
    }

    private void resetSets() {
        currentSet = 0;
        set[Player.PLAYER_A] = new int[]{0, 0, 0};
        set[Player.PLAYER_B] = new int[]{0, 0, 0};
        for (int i = 0; i < 3; i++) {
            setText[Player.PLAYER_A][i].setText(String.valueOf(0));
            setText[Player.PLAYER_B][i].setText(String.valueOf(0));
        }
    }

    private void update() {
        displayScore(scoreText[Player.PLAYER_A], score[Player.PLAYER_A]);
        displayScore(scoreText[Player.PLAYER_B], score[Player.PLAYER_B]);
        resetFaults();
        if (score[Player.PLAYER_A] == 45) {
            showDialog("Game for Player A");
            winGame(Player.PLAYER_A);
        } else if (score[Player.PLAYER_B] == 45) {
            showDialog("Game for Player B");
            winGame(Player.PLAYER_B);
        }
        if (set[Player.PLAYER_A][currentSet] == 6 || set[Player.PLAYER_B][currentSet] == 6) {
            currentSet += 1;
            resetFaults();
            resetScores();
        }
        if (currentSet >= 3) {
            pointButton[Player.PLAYER_A].setEnabled(false);
            pointButton[Player.PLAYER_B].setEnabled(false);
            faultButton[Player.PLAYER_A].setEnabled(false);
            faultButton[Player.PLAYER_B].setEnabled(false);
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

    static class Player {
        private static final int PLAYER_A = 0;
        private static final int PLAYER_B = 1;
    }
}
