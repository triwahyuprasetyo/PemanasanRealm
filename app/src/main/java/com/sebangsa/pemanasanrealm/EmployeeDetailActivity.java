package com.sebangsa.pemanasanrealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sebangsa.pemanasanrealm.model.Department;
import com.sebangsa.pemanasanrealm.model.Employee;
import com.sebangsa.pemanasanrealm.service.RealmService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class EmployeeDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinnerDepartment;
    private TextView textViewEmployeeDetail;
    private EditText editTextEmployeeId, editTextEmployeeFirstName, editTextEmployeeLastName, editTextEmployeeAge, editTextEmployeeAddress;
    private Button buttonSaveEmployee;
    private RealmService realmService;
    private String departmentId;
    private RealmResults<Department> departments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        textViewEmployeeDetail = (TextView) findViewById(R.id.textView_employee_detail);
        editTextEmployeeId = (EditText) findViewById(R.id.editText_employee_id);
        editTextEmployeeFirstName = (EditText) findViewById(R.id.editText_employee_first_name);
        editTextEmployeeLastName = (EditText) findViewById(R.id.editText_employee_last_name);
        editTextEmployeeAge = (EditText) findViewById(R.id.editText_employee_age);
        editTextEmployeeAddress = (EditText) findViewById(R.id.editText_employee_address);
        buttonSaveEmployee = (Button) findViewById(R.id.button_save_employee);
        buttonSaveEmployee.setOnClickListener(this);
        spinnerDepartment = (Spinner) findViewById(R.id.spinner_department);

        Intent i = getIntent();
        String action = i.getExtras().getString("Action");

        textViewEmployeeDetail.setText(action + " Employee");
        if (action.equals("Edit")) {
            editTextEmployeeId.setText(i.getExtras().getString("EmployeeId"));
            editTextEmployeeId.setEnabled(false);
            editTextEmployeeFirstName.setText(i.getExtras().getString("EmployeeFirstName"));
            editTextEmployeeLastName.setText(i.getExtras().getString("EmployeeLastName"));
            editTextEmployeeAge.setText(i.getExtras().getString("EmployeeAge"));
            editTextEmployeeAddress.setText(i.getExtras().getString("EmployeeAddress"));
            departmentId = i.getExtras().getString("EmployeeDepartmentId");
            Log.i("departmentId", departmentId);
            buttonSaveEmployee.setText("Save");
        } else {
            buttonSaveEmployee.setText(action);
        }

        realmService = RealmService.getRealmService(this);
        setAdapterSpinner();
        setTitle("Employee Form");
    }

    private void setAdapterSpinner() {
        departments = realmService.getAllDepartments();
        List<String> departmentListName = new ArrayList<String>();
        if (departments.size() > 0) {
            for (Department d : departments) {
                departmentListName.add(d.getName());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, departmentListName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(dataAdapter);
        if (departmentId != null && !departmentId.equals("")) {
            int index = 0;
            for (int i = 0; i < departments.size(); i++) {
                if (departmentId.equals(departments.get(i).getDepartmentId())) {
                    index = i;
                }
            }
            spinnerDepartment.setSelection(index);
            spinnerDepartment.setEnabled(false);
            Log.i("AdapterSpinner", "YES");
        } else {
            Log.i("AdapterSpinner", "NO");
        }
    }

    @Override
    public void onClick(View view) {
        String id = editTextEmployeeId.getText().toString().trim();
        String fnama = editTextEmployeeFirstName.getText().toString().trim();
        String lnama = editTextEmployeeLastName.getText().toString().trim();
        String age = editTextEmployeeAge.getText().toString().trim();
        String address = editTextEmployeeAddress.getText().toString().trim();
        int posisi = spinnerDepartment.getSelectedItemPosition();
        if (!id.equals("") && !fnama.equals("") && !lnama.equals("") && !age.equals("") && !address.equals("") && departments.size() > 0) {
            Employee e = new Employee();
            e.setEmployeeId(id);
            e.setFirstName(fnama);
            e.setLastName(lnama);
            e.setAge(Integer.parseInt(age));
            e.setAddress(address);
            e.setDepartmentId(departments.get(posisi).getDepartmentId());
            EventBus.getDefault().post(e);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_SHORT).show();
        }
    }
}
