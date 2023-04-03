package com.example.testsqlite2.adapter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testsqlite2.DBHelper;
import com.example.testsqlite2.EmployeeActivity;
import com.example.testsqlite2.MainActivity;
import com.example.testsqlite2.R;
import com.example.testsqlite2.model.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyHolder> {

    Context context;
    List<Employee> employeeList;

    public EmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final String id = employeeList.get(position).getId();
        final String name = employeeList.get(position).getName();
        final String email = employeeList.get(position).getEmail();
        final String gender = employeeList.get(position).getGender();

        holder.name.setText(name);
        holder.email.setText("Email   : " + email);
        holder.gender.setText("Gender  : " + gender);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] options = {"Yes", "No"};
                AlertDialog.Builder alertBox = new AlertDialog.Builder(v.getRootView().getContext());
                alertBox.setTitle("Are you sure to delete");
                alertBox.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            deleteEmployee(id);
                        }
                        if (which == 1) {
                            dialog.dismiss();
                        }
                    }
                });
                alertBox.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), EmployeeActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("status", false);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("gender",gender);
                context.getApplicationContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, email, gender;
        ImageView edit, delete;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            gender = itemView.findViewById(R.id.gender);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

        }
    }


    private void deleteEmployee(final String id) {
        DBHelper dbHelper = new DBHelper(context);
        if (id.isEmpty()) {
            Toast.makeText(context, "select the employee", Toast.LENGTH_LONG).show();
        } else {
            if (dbHelper.deleteEmployee(id)) {
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            } else {
                Toast.makeText(context, "Deleted Unsuccessfully", Toast.LENGTH_SHORT).show();
            }
        }
    }


}