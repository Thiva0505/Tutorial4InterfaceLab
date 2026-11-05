package com.thiva.tutorial4;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;
import com.thiva.tutorial4.mobile.BasicPhone;
import com.thiva.tutorial4.mobile.MobileDevice;
import com.thiva.tutorial4.mobile.SmartPhone;

import java.util.Locale;

// Q3: every MobileDevice implements RadioTuner, so any phone can be tuned
public class MobileActivity extends AppCompatActivity {

    private boolean smart = true;

    private TextInputLayout tilManufacturer, tilModel, tilType;
    private EditText etManufacturer, etModel, etType, etStation;
    private TextView tvFrequency, tvStationLcd, tvDetails;
    private Slider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        tilManufacturer = findViewById(R.id.tilManufacturer);
        tilModel = findViewById(R.id.tilModel);
        tilType = findViewById(R.id.tilType);
        etManufacturer = findViewById(R.id.etManufacturer);
        etModel = findViewById(R.id.etModel);
        etType = findViewById(R.id.etType);
        etStation = findViewById(R.id.etStation);
        tvFrequency = findViewById(R.id.tvFrequency);
        tvStationLcd = findViewById(R.id.tvStationLcd);
        tvDetails = findViewById(R.id.tvDetails);
        slider = findViewById(R.id.sliderFrequency);

        findViewById(R.id.tvBack).setOnClickListener(v -> finish());

        MaterialButtonToggleGroup toggle = findViewById(R.id.toggleDevice);
        toggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) return;
            smart = checkedId == R.id.btnSmart;
            tilType.setVisibility(smart ? View.VISIBLE : View.GONE);
        });

        // live radio display
        slider.addOnChangeListener((s, value, fromUser) ->
                tvFrequency.setText(String.format(Locale.US, "%.1f", value)));
        etStation.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) { }
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) { }
            @Override public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                tvStationLcd.setText(text.isEmpty() ? "— — —" : text);
            }
        });

        findViewById(R.id.btnBuild).setOnClickListener(v -> buildDevice());
        findViewById(R.id.btnDemo).setOnClickListener(v -> runTutorialDemo());
    }

    private void buildDevice() {
        String manufacturer = etManufacturer.getText().toString().trim();
        String model = etModel.getText().toString().trim();
        String type = etType.getText().toString().trim();
        String station = etStation.getText().toString().trim();

        tilManufacturer.setError(manufacturer.isEmpty() ? "Required" : null);
        tilModel.setError(model.isEmpty() ? "Required" : null);
        if (manufacturer.isEmpty() || model.isEmpty()) return;

        MobileDevice device = smart
                ? new SmartPhone(manufacturer, model, type)
                : new BasicPhone(manufacturer, model);

        // RadioTuner methods
        device.setStation(station.isEmpty() ? "No station" : station);
        device.setFrequency(Math.round(slider.getValue() * 10) / 10.0);

        tvDetails.setText(describe(device));
    }

    // Same as the tutorial's MobileApp tester
    private void runTutorialDemo() {
        MobileDevice m1 = new SmartPhone("Samsung", "S20", "Galaxy");
        MobileDevice m2 = new BasicPhone("Sonny Ericson", "Walkman");
        m1.setStation("Hot FM");
        m1.setFrequency(97.60);
        m2.setStation("Fly FM");
        m2.setFrequency(109.8);

        tvDetails.setText(describe(m1) + "\n\n" + describe(m2));
        slider.setValue(109.8f);
        etStation.setText("Fly FM");
    }

    private String describe(MobileDevice d) {
        return d.getDetails() + "\nStation: " + d.station + "\nFrequency: " + d.frequency + " MHz";
    }
}
