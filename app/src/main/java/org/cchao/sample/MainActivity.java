package org.cchao.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.cchao.switchbutton.SwitchButton;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    private SwitchButton switchButton;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchButton = (SwitchButton) findViewById(R.id.switchButton);
        button = (Button) findViewById(R.id.button);

        switchButton.setOnSwitchChangeListener(new SwitchButton.OnSwitchChangeListener() {
            @Override
            public void onChange(boolean isSelect) {
                Log.d(TAG, "onChange: " + isSelect);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchButton.setCheck(!switchButton.isChecked());
            }
        });
    }
}
