package ser210.quinnipiac.edu.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;


public class PlayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    //Call onSendMessage() when the button is clicked to start the game
    public void onSendMessage(View view){
        EditText messageView = findViewById(R.id.message);
        //get the text from the editable text field with an id of message
        String messageText = messageView.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        //add the text to the intent giving it a name of "message"
        intent.putExtra("message", messageText);
        startActivity(intent);

    }
}
