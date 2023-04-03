package com.example.testsqlite2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.testsqlite2.adapter.EmployeeAdapter;
import com.example.testsqlite2.model.Employee;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addEmployee;
    private EditText searchText;
    private ImageButton searchButton;


    private List<Employee> employeeList;
    private EmployeeAdapter employeeAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addEmployee = findViewById(R.id.addEmployee);
        searchText = findViewById(R.id.searchText);
        searchButton = findViewById(R.id.searchButton);

        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                intent.putExtra("status", true);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textSearchText = searchText.getText().toString();
                if(!textSearchText.isEmpty()){
                    employeeList.clear();
                    if(getEmployee(textSearchText)==0){
                        Toast.makeText(getApplicationContext(), textSearchText+" name not in the database", Toast.LENGTH_LONG).show();
                    }
                }else{
                    employeeList.clear();
                    getEmployee("");
                }
            }
        });

        recyclerView = findViewById(R.id.employeeRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        employeeList = new ArrayList<Employee>();

        getEmployee("");
    }

    private int getEmployee(String name) {
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.readEmployee(name);
        while(cursor.moveToNext()){
            employeeList.add(new Employee(
                    cursor.getString(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("gender"))
            ));
        }
        cursor.close();
        employeeAdapter = new EmployeeAdapter(getApplicationContext(), employeeList);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setAdapter(employeeAdapter);
        return employeeList.size();
    }


}