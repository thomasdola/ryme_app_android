package primr.apps.eurakacachet.com.rhyme.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import primr.apps.eurakacachet.com.rhyme.R;

public class RegisterActivity extends AppCompatActivity {

    private Button mJoinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mJoinButton = (Button) findViewById(R.id.verifyOpt);
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, FollowCategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
