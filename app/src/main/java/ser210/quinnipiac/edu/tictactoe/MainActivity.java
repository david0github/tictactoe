package ser210.quinnipiac.edu.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "message";
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String messageText = intent.getStringExtra(EXTRA_MESSAGE);
        TextView messageView = findViewById(R.id.message);
        messageView.setText(messageText);

        TextView p1turn = findViewById(R.id.player1Turn);
        p1turn.setText("Your Turn!");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[0][0] = (Button) findViewById(R.id.one);
                buttons[0][1] = (Button) findViewById(R.id.two);
                buttons[0][2] = (Button) findViewById(R.id.three);
                buttons[1][0] = (Button) findViewById(R.id.four);
                buttons[1][1] = (Button) findViewById(R.id.five);
                buttons[1][2] = (Button) findViewById(R.id.six);
                buttons[2][0] = (Button) findViewById(R.id.seven);
                buttons[2][1] = (Button) findViewById(R.id.eight);
                buttons[2][2] = (Button) findViewById(R.id.nine);
                buttons[i][j].setOnClickListener(this);
            }
        }
        ((Button)findViewById(R.id.reset)).setEnabled(false);
        Button buttonReset = findViewById(R.id.reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        //check if this button contain a empty string, and if it is full it is return
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) view).setText("X");
            TextView aiturn = findViewById(R.id.aiTurn);
            aiturn.setText("AI Turn!");
            TextView p1turn = findViewById(R.id.player1Turn);
            p1turn.setText("");
        } else {
            ((Button) view).setText("O");
            TextView p1turn = findViewById(R.id.player1Turn);
            p1turn.setText("Your Turn!");
            TextView aiturn = findViewById(R.id.aiTurn);
            aiturn.setText("");

        }
        roundCount++;

        if(checkForWin()){
            if(player1Turn){
                player1Wins();
            } else {
                aiWin();
            }
        } else if (roundCount == 9){
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        //check all three rows
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][1].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }

        }
        //check all three column
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[1][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }
        //check the dia. to the right
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }
        //check the dia. to the left
        if (field[2][0].equals(field[1][1]) && field[2][0].equals(field[0][2]) && !field[2][0].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins(){
        TextView winMsg = findViewById(R.id.winMsg);
        winMsg.setText("GameOver: You Won");
        ((Button)findViewById(R.id.reset)).setEnabled(true);
        TextView p1turn = findViewById(R.id.player1Turn);
        p1turn.setText("");
        TextView aiturn = findViewById(R.id.aiTurn);
        aiturn.setText("");
    }

    private void aiWin(){
        TextView loseMsg = findViewById(R.id.loseMsg);
        loseMsg.setText("GameOver: You Lost!");
        ((Button)findViewById(R.id.reset)).setEnabled(true);
        TextView p1turn = findViewById(R.id.player1Turn);
        p1turn.setText("");
        TextView aiturn = findViewById(R.id.aiTurn);
        aiturn.setText("");
    }

    private void draw(){
        TextView drawMsg = findViewById(R.id.drawMsg);
        drawMsg.setText("GameOver: Its a Draw!");
        ((Button)findViewById(R.id.reset)).setEnabled(true);
        TextView p1turn = findViewById(R.id.player1Turn);
        p1turn.setText("");
        TextView aiturn = findViewById(R.id.aiTurn);
        aiturn.setText("");
    }

    private void resetBoard(){
        for( int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
                TextView winMsg = findViewById(R.id.winMsg);
                winMsg.setText("");
                TextView loseMsg = findViewById(R.id.loseMsg);
                loseMsg.setText("");
                TextView drawMsg = findViewById(R.id.drawMsg);
                drawMsg.setText("");
                ((Button)findViewById(R.id.reset)).setEnabled(false);
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame(){
        roundCount = 0;
        resetBoard();
        ((Button)findViewById(R.id.reset)).setEnabled(false);
        TextView p1turn = findViewById(R.id.player1Turn);
        p1turn.setText("Your Turn!");
    }

    //is called when the device is rotated and will save the variables
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putBoolean("player1Turn", player1Turn);
    }

    //this is called to restore the values and don't lose the game progress after the device is rotated
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
