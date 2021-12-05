package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {
    private Button addDeluxeButton,addPepperoniButton, addHawaiianButton, currentOrderButton, storeOrdersButton;
    private TextView welcome;
    private EditText phoneNumberTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome = findViewById(R.id.welcomeText);
        phoneNumberTextBox = findViewById(R.id.editTextPhone);
        addDeluxeButton = findViewById(R.id.orderDeluxeButton);
        addPepperoniButton = findViewById(R.id.orderPepperoniButton);
        addHawaiianButton = findViewById(R.id.orderHawaiianButton);
        currentOrderButton = findViewById(R.id.currentOrderButton);
        storeOrdersButton = findViewById(R.id.storeOrdersButton);
    }
}