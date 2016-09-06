package com.sebangsa.pemanasanrealm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.sebangsa.pemanasanrealm.model.Department;
import com.sebangsa.pemanasanrealm.model.Employee;
import com.sebangsa.pemanasanrealm.service.RealmService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class EmployeeActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView listViewEmployee;
    private Button buttonAddEmployee;
    private RealmService realmService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        listViewEmployee = (ListView) findViewById(R.id.listView_employee);
        buttonAddEmployee = (Button) findViewById(R.id.button_add_employee);
        buttonAddEmployee.setOnClickListener(this);
        EventBus.getDefault().register(this);
        realmService = RealmService.getRealmService(this);
        setAdapterList();
        setTitle("Employee List");
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEmployeeEvent(Employee e) {
        if (realmService.getEmployee(e.getEmployeeId()) == null) {
            realmService.addEmployee(e);
        } else {
            realmService.updateEmployee(e);
        }
        setAdapterList();
    }
    private void setAdapterList() {
        String[] employeeNames = new String[]{};
        final List<Employee> employeeList = new ArrayList<Employee>();
        RealmResults<Employee> employees = realmService.getAllEmployee();
        if (employees.size() > 0) {
            employeeNames = new String[employees.size()];
            for (int i = 0; i < employees.size(); i++) {
                employeeNames[i] = employees.get(i).getFirstName()+" "+employees.get(i).getLastName();
                employeeList.add(employees.get(i));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, employeeNames);
        listViewEmployee.setAdapter(adapter);
        listViewEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showDialog(employeeList.get(position));
            }

        });
    }

    private void showDialog(final Employee employee) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EmployeeActivity.this);
        alertDialog.setTitle("Action");
        alertDialog.setMessage(employee.getFirstName()+" "+employee.getLastName());
        alertDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(EmployeeActivity.this, EmployeeDetailActivity.class);
                i.putExtra("Action", "Edit");
                i.putExtra("EmployeeId", employee.getEmployeeId());
                i.putExtra("EmployeeFirstName", employee.getFirstName());
                i.putExtra("EmployeeLastName", employee.getLastName());
                i.putExtra("EmployeeAge", employee.getAge()+"");
                i.putExtra("EmployeeAddress", employee.getAddress());
                i.putExtra("EmployeeDepartmentId", employee.getDepartmentId());
                startActivity(i);
            }
        });
        alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                realmService.deleteEmployee(employee.getEmployeeId());
                setAdapterList();
            }
        });
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
    @Override
    public void onClick(View view) {
        Intent i = new Intent(EmployeeActivity.this, EmployeeDetailActivity.class);
        i.putExtra("Action", "Add");
        startActivity(i);
    }
}
