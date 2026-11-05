package com.thiva.tutorial4;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.thiva.tutorial4.dataset.BankAccount;
import com.thiva.tutorial4.dataset.Coin;
import com.thiva.tutorial4.dataset.DataSet;
import com.thiva.tutorial4.dataset.Measurable;
import com.thiva.tutorial4.dataset.Student;

import java.util.Locale;

// Q1: one DataSet class handles BankAccount, Coin and Student
// because all three implement Measurable.
public class DataSetActivity extends AppCompatActivity {

    private static final int BANK = 0, COIN = 1, STUDENT = 2;

    // a separate DataSet (and item list) for each category
    private final DataSet[] sets = {new DataSet(), new DataSet(), new DataSet()};
    private final StringBuilder[] lists = {new StringBuilder(), new StringBuilder(), new StringBuilder()};
    private int mode = BANK;

    private TextInputLayout tilName, tilValue;
    private EditText etName, etValue;
    private TextView tvFormTitle, tvFormHint, tvCount, tvAverage, tvHighest, tvTopLabel, tvTopName, tvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataset);

        tilName = findViewById(R.id.tilName);
        tilValue = findViewById(R.id.tilValue);
        etName = findViewById(R.id.etName);
        etValue = findViewById(R.id.etValue);
        tvFormTitle = findViewById(R.id.tvFormTitle);
        tvFormHint = findViewById(R.id.tvFormHint);
        tvCount = findViewById(R.id.tvCount);
        tvAverage = findViewById(R.id.tvAverage);
        tvHighest = findViewById(R.id.tvHighest);
        tvTopLabel = findViewById(R.id.tvTopLabel);
        tvTopName = findViewById(R.id.tvTopName);
        tvItems = findViewById(R.id.tvItems);

        findViewById(R.id.tvBack).setOnClickListener(v -> finish());

        MaterialButtonToggleGroup toggle = findViewById(R.id.toggleCategory);
        toggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) return;
            if (checkedId == R.id.btnBank) mode = BANK;
            else if (checkedId == R.id.btnCoin) mode = COIN;
            else mode = STUDENT;
            updateForm();
            refresh();
        });

        findViewById(R.id.btnAdd).setOnClickListener(v -> addItem());
        findViewById(R.id.btnSample).setOnClickListener(v -> loadTutorialData());
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            sets[mode] = new DataSet();
            lists[mode].setLength(0);
            refresh();
        });

        updateForm();
        refresh();
    }

    // Change the form to suit the selected category
    private void updateForm() {
        tilName.setError(null);
        tilValue.setError(null);
        etName.setText("");
        etValue.setText("");
        if (mode == BANK) {
            tvFormTitle.setText("New BankAccount");
            tilName.setVisibility(View.GONE);
            tilValue.setHint("Balance");
            tilValue.setPrefixText("RM ");
            tilValue.setSuffixText(null);
            tvFormHint.setText("Account numbers are assigned automatically (1001, 1002, …)");
            tvTopLabel.setText("Highest balance");
        } else if (mode == COIN) {
            tvFormTitle.setText("New Coin");
            tilName.setVisibility(View.VISIBLE);
            tilName.setHint("Coin name");
            tilValue.setHint("Value");
            tilValue.setPrefixText("RM ");
            tilValue.setSuffixText(null);
            tvFormHint.setText("Example: Quarter = 0.25, Dime = 0.10");
            tvTopLabel.setText("Highest coin");
        } else {
            tvFormTitle.setText("New Student");
            tilName.setVisibility(View.VISIBLE);
            tilName.setHint("Student name");
            tilValue.setHint("Height");
            tilValue.setPrefixText(null);
            tilValue.setSuffixText("cm");
            tvFormHint.setText("Example: Ali = 157.50 cm");
            tvTopLabel.setText("Tallest student");
        }
    }

    private void addItem() {
        String name = etName.getText().toString().trim();
        String valueText = etValue.getText().toString().trim();
        tilName.setError(null);
        tilValue.setError(null);

        if (mode != BANK && name.isEmpty()) {
            tilName.setError("Please enter a name");
            return;
        }
        double value;
        try {
            value = Double.parseDouble(valueText);
        } catch (NumberFormatException e) {
            tilValue.setError("Please enter a number");
            return;
        }

        Measurable item;
        if (mode == BANK) item = new BankAccount(value);
        else if (mode == COIN) item = new Coin(value, name);
        else item = new Student(name, value);

        add(item);
        etName.setText("");
        etValue.setText("");
        refresh();
    }

    // Same data as the tutorial's DataSetApp tester
    private void loadTutorialData() {
        sets[mode] = new DataSet();
        lists[mode].setLength(0);
        if (mode == BANK) {
            add(new BankAccount(0));
            add(new BankAccount(10000));
            add(new BankAccount(2000));
        } else if (mode == COIN) {
            add(new Coin(0.25, "Quarter"));
            add(new Coin(0.1, "Dime"));
            add(new Coin(0.05, "Nickel"));
        } else {
            add(new Student("Ali", 157.50));
            add(new Student("Alex", 165.00));
            add(new Student("Maria", 147.00));
        }
        refresh();
    }

    // The DataSet only sees a Measurable: it never needs to know the real class
    private void add(Measurable item) {
        sets[mode].add(item);
        lists[mode].append(String.format(Locale.US, "%-12s %12s%n", item.getName(), format(item.getMeasure())));
    }

    private void refresh() {
        DataSet set = sets[mode];
        tvCount.setText(String.valueOf(set.getCount()));
        if (set.getCount() == 0) {
            tvAverage.setText("—");
            tvHighest.setText("—");
            tvTopName.setText("Nothing yet");
            tvItems.setText("No items yet. Add one above, or load the tutorial data.");
            return;
        }
        Measurable max = set.getMax();
        tvAverage.setText(format(set.getAverage()));
        tvHighest.setText(format(max.getMeasure()));
        tvTopName.setText(max.getName());
        tvItems.setText(lists[mode].toString().trim());
    }

    private String format(double value) {
        if (mode == STUDENT) return String.format(Locale.US, "%.2f cm", value);
        return String.format(Locale.US, "RM%.2f", value);
    }
}
