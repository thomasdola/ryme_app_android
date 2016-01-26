package primr.apps.eurakacachet.com.rhyme.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import primr.apps.eurakacachet.com.rhyme.R;

public class WelcomeActivityFirst extends AppCompatActivity {

    protected Button mRegisterButton;
    protected TextView mLoginOption;
    protected AutoCompleteTextView mDialCode;
    private static final String[] CODES = new String[] {
            "+233", "+228", "+1"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity_first);

        mRegisterButton = (Button) findViewById(R.id.register_next_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
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

        mDialCode = (AutoCompleteTextView) findViewById(R.id.dial_code);
        ArrayAdapter<String> codesAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, CODES);
        mDialCode.setAdapter(codesAdapter);
    }
}
