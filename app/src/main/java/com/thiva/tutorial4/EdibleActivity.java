package com.thiva.tutorial4;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.thiva.tutorial4.edible.Animal;
import com.thiva.tutorial4.edible.Apple;
import com.thiva.tutorial4.edible.Edible;
import com.thiva.tutorial4.edible.Orange;
import com.thiva.tutorial4.edible.Person;

// Q2: a Person can eat ANY Edible object (Animal, Apple, Orange)
public class EdibleActivity extends AppCompatActivity {

    private static final int ANIMAL = 0, APPLE = 1, ORANGE = 2;
    private static final String[] DEFAULT_FOOD = {"Chicken", "Red Apple", "Navel Orange"};
    private static final String[] CLASS_CHAIN = {
            "class Animal implements Edible",
            "class Apple extends Fruit implements Edible",
            "class Orange extends Fruit implements Edible"
    };

    private int food = ANIMAL;
    private final StringBuilder history = new StringBuilder();

    private TextInputLayout tilPerson, tilFood;
    private EditText etPerson, etFood;
    private TextView tvClassChain, tvResult, tvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edible);

        tilPerson = findViewById(R.id.tilPerson);
        tilFood = findViewById(R.id.tilFood);
        etPerson = findViewById(R.id.etPerson);
        etFood = findViewById(R.id.etFood);
        tvClassChain = findViewById(R.id.tvClassChain);
        tvResult = findViewById(R.id.tvResult);
        tvHistory = findViewById(R.id.tvHistory);

        findViewById(R.id.tvBack).setOnClickListener(v -> finish());

        MaterialButtonToggleGroup toggle = findViewById(R.id.toggleFood);
        toggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) return;
            if (checkedId == R.id.btnAnimal) food = ANIMAL;
            else if (checkedId == R.id.btnApple) food = APPLE;
            else food = ORANGE;
            etFood.setText(DEFAULT_FOOD[food]);
            tvClassChain.setText(CLASS_CHAIN[food]);
        });

        findViewById(R.id.btnEat).setOnClickListener(v -> eat());
        findViewById(R.id.btnDemo).setOnClickListener(v -> runTutorialDemo());
    }

    private void eat() {
        String personName = etPerson.getText().toString().trim();
        String foodName = etFood.getText().toString().trim();
        tilPerson.setError(personName.isEmpty() ? "Who is eating?" : null);
        tilFood.setError(foodName.isEmpty() ? "What are they eating?" : null);
        if (personName.isEmpty() || foodName.isEmpty()) return;

        Edible item;
        if (food == ANIMAL) item = new Animal(foodName);
        else if (food == APPLE) item = new Apple(foodName);
        else item = new Orange(foodName);

        Person person = new Person(personName);
        show(person.eating(item));
    }

    // Same as the tutorial's EdibleApp: Ali eats animals, Sarah eats fruit
    private void runTutorialDemo() {
        Edible[] objects = {new Animal("Chicken"), new Apple("Red Apple"), new Orange("Navel Orange")};
        Person p1 = new Person("Ali");
        Person p2 = new Person("Sarah");
        for (Edible object : objects) {
            if (object instanceof Animal) show(p1.eating(object));
            else show(p2.eating(object));
        }
    }

    private void show(String line) {
        tvResult.setText(line);
        history.insert(0, "•  " + line + "\n\n");
        tvHistory.setText(history.toString().trim());
    }
}
