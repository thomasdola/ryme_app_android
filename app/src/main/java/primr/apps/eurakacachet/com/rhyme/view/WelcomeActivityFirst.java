package primr.apps.eurakacachet.com.rhyme.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import primr.apps.eurakacachet.com.rhyme.R;

public class WelcomeActivityFirst extends AppCompatActivity {

    protected Button mNextButton;
    protected TextView mLoginOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity_first);

        mNextButton = (Button) findViewById(R.id.register_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivityFirst.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mLoginOption = (TextView) findViewById(R.id.sign_in_option_text_view);
        mLoginOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivityFirst.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
