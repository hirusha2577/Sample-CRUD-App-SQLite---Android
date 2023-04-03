package com.example.testsqlite2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EmployeeActivity extends AppCompatActivity {

    private EditText name, email;
    private RadioButton male, female;
    private Button addEmployee;
    private String employeeId, employeeName, employeeEmail, employeeGender;
    private boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        Intent intent = getIntent();
        status = intent.getBooleanExtra("status", true);
        employeeId = intent.getStringExtra("id");
        employeeName = intent.getStringExtra("name");
        employeeEmail = intent.getStringExtra("email");
        employeeGender = intent.getStringExtra("gender");

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        addEmployee = findViewById(R.id.addEmployee);

        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textName = name.getText().toString();
                String textEmail = email.getText().toString();
                String textGender = "";
                if (male.isChecked()) {
                    textGender = male.getText().toString();
                }
                if (female.isChecked()) {
                    textGender = female.getText().toString();
                }
                if (textName.isEmpty() || textEmail.isEmpty() || textGender.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All field are required", Toast.LENGTH_LONG).show();
                } else {
                    addEmployee(textName, textEmail, textGender);
                }
            }
        });

        if (status) {
            addEmployee.setText("Add Employee");
        } else {
            status = true;
            name.setText(employeeName);
            email.setText(employeeEmail);
            if (employeeGender.equals("Male")) {
                male.setChecked(true);
            } else {
                female.setChecked(true);
            }
            addEmployee.setText("Update Employee");
        }

    }

    private void addEmployee(String textName, String textEmail, String textGender) {
        DBHelper dbHelper = new DBHelper(getApplicationContext());

        if (employeeId == null) {
            long status = dbHelper.addEmployee(textName, textEmail, textGender);
            if (status > 0) {
                Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
                name.setText("");
                email.setText("");
                male.setChecked(false);
                female.setChecked(false);

                Intent intent = new Intent(EmployeeActivity.this, MainActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Not Inserted", Toast.LENGTH_LONG).show();
            }
        } else {
            long status = dbHelper.updateEmployee(employeeId, textName, textEmail, textGender);
            if (status > 0) {
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                name.setText("");
                email.setText("");
                male.setChecked(false);
                female.setChecked(false);
                Intent intent = new Intent(EmployeeActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_LONG).show();
            }
        }

    }
}