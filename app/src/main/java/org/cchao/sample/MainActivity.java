package org.cchao.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import org.cchao.switchbutton.SwitchButton;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    private SwitchButton switchButton1;

    private SwitchButton switchButton2;

    private SwitchButton switchButton3;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchButton1 = (SwitchButton) findViewById(R.id.switchButton1);
        switchButton2 = (SwitchButton) findViewById(R.id.switchButton2);
        switchButton3 = (SwitchButton) findViewById(R.id.switchButton3);
        button = (Button) findViewById(R.id.button);

        switchButton3.setChecked(true);

        switchButton3.setOnSwitchChangeListener(new SwitchButton.OnSwitchChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                Log.d(TAG, "onChange: " + isChecked);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchButton1.setChecked(!switchButton1.isChecked());
                switchButton2.toggle();
                switchButton3.setChecked(!switchButton3.isChecked());
            }
        });
    }
}
