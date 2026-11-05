package com.thiva.tutorial4;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.thiva.tutorial4.price.Car;
import com.thiva.tutorial4.price.Customer;
import com.thiva.tutorial4.price.Laptop;
import com.thiva.tutorial4.price.Price;
import com.thiva.tutorial4.price.Product;
import com.thiva.tutorial4.price.SmartPhone;

// Q4: SmartPhone, Laptop and Car all implement Price
public class PriceActivity extends AppCompatActivity {

    private static final int PHONE = 0, LAPTOP = 1, CAR = 2;
    private static final String[] HINT = {"Phone model", "Laptop model", "Car brand"};
    private static final String[] DEFAULT_NAME = {"Iphone 14 ProMax", "Lenovo Yoga", "BMW"};
    private static final String[] DEFAULT_PRICE = {"5500", "6000", "200000"};

    private int item = PHONE;
    private final StringBuilder history = new StringBuilder();

    private TextInputLayout tilItemName, tilItemPrice, tilCustomer, tilSalary;
    private EditText etItemName, etItemPrice, etCustomer, etSalary;
    private TextView tvRule, tvVerdictIcon, tvVerdict, tvItemLine, tvVerdictDetail, tvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        tilItemName = findViewById(R.id.tilItemName);
        tilItemPrice = findViewById(R.id.tilItemPrice);
        tilCustomer = findViewById(R.id.tilCustomer);
        tilSalary = findViewById(R.id.tilSalary);
        etItemName = findViewById(R.id.etItemName);
        etItemPrice = findViewById(R.id.etItemPrice);
        etCustomer = findViewById(R.id.etCustomer);
        etSalary = findViewById(R.id.etSalary);
        tvRule = findViewById(R.id.tvRule);
        tvVerdictIcon = findViewById(R.id.tvVerdictIcon);
        tvVerdict = findViewById(R.id.tvVerdict);
        tvItemLine = findViewById(R.id.tvItemLine);
        tvVerdictDetail = findViewById(R.id.tvVerdictDetail);
        tvHistory = findViewById(R.id.tvHistory);

        findViewById(R.id.tvBack).setOnClickListener(v -> finish());

        MaterialButtonToggleGroup toggle = findViewById(R.id.toggleItem);
        toggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) return;
            if (checkedId == R.id.btnPhone) item = PHONE;
            else if (checkedId == R.id.btnLaptop) item = LAPTOP;
            else item = CAR;
            tilItemName.setHint(HINT[item]);
            etItemName.setText(DEFAULT_NAME[item]);
            etItemPrice.setText(DEFAULT_PRICE[item]);
            tvRule.setText(item == CAR ? "Rule: salary > RM8000" : "Rule: salary > RM4000");
        });

        findViewById(R.id.btnCheck).setOnClickListener(v -> check());
        findViewById(R.id.btnDemo).setOnClickListener(v -> runTutorialDemo());
    }

    private void check() {
        String name = etItemName.getText().toString().trim();
        String customerName = etCustomer.getText().toString().trim();
        tilItemName.setError(name.isEmpty() ? "Required" : null);
        tilCustomer.setError(customerName.isEmpty() ? "Required" : null);
        Double price = readNumber(etItemPrice, tilItemPrice);
        Double salary = readNumber(etSalary, tilSalary);
        if (name.isEmpty() || customerName.isEmpty() || price == null || salary == null) return;

        Price p;
        if (item == PHONE) p = new SmartPhone("SmartPhone", name, price);
        else if (item == LAPTOP) p = new Laptop("Laptop", name, price);
        else p = new Car(name, price);

        showVerdict(p, new Customer(customerName, salary));
    }

    // Same three checks as the tutorial's PriceApp tester
    private void runTutorialDemo() {
        SmartPhone sp = new SmartPhone("SmartPhone", "Iphone 14 ProMax", 5500.00);
        Laptop lp = new Laptop("Laptop", "Lenovo Yoga", 6000.00);
        Car c = new Car("BMW", 200000);
        showVerdict(sp, new Customer("Sally", 3000.00));
        showVerdict(lp, new Customer("Aaron", 8000.00));
        showVerdict(c, new Customer("Simon", 10000.00));
    }

    // Any Price object works here; getPrice() runs the right version for each class
    private void showVerdict(Price p, Customer customer) {
        p.getPrice(customer);

        String result;
        boolean canBuy;
        if (p instanceof Product) {
            result = ((Product) p).getResult();
            canBuy = ((Product) p).isCanBuy();
        } else {
            result = ((Car) p).getResult();
            canBuy = ((Car) p).isCanBuy();
        }

        findViewById(R.id.cardVerdict).setBackgroundResource(
                canBuy ? R.drawable.bg_verdict_yes : R.drawable.bg_verdict_no);
        tvVerdictIcon.setText(canBuy ? "✓" : "✕");
        tvVerdictIcon.setBackgroundResource(canBuy ? R.drawable.bg_badge_dataset : R.drawable.bg_badge_edible);
        tvVerdict.setText(canBuy ? "Can buy" : "Cannot buy");
        tvVerdict.setTextColor(ContextCompat.getColor(this, canBuy ? R.color.success : R.color.danger));
        tvItemLine.setText(p.toString());
        tvVerdictDetail.setText(result);

        history.insert(0, (canBuy ? "✓  " : "✕  ") + p + "\n" + result + "\n\n");
        tvHistory.setText(history.toString().trim());
    }

    private Double readNumber(EditText field, TextInputLayout layout) {
        try {
            double value = Double.parseDouble(field.getText().toString().trim());
            layout.setError(null);
            return value;
        } catch (NumberFormatException e) {
            layout.setError("Enter a number");
            return null;
        }
    }
}
