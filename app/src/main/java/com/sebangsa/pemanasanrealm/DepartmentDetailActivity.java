package com.sebangsa.pemanasanrealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sebangsa.pemanasanrealm.model.Department;

import org.greenrobot.eventbus.EventBus;

public class DepartmentDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewDepartmentDetail;
    private EditText editTextDepartmentId, editTextDepartmentName;
    private Button buttonSaveDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_detail);
        textViewDepartmentDetail = (TextView) findViewById(R.id.textView_departmentdetail);
        editTextDepartmentId = (EditText) findViewById(R.id.editText_department_id);
        editTextDepartmentName = (EditText) findViewById(R.id.editText_department_name);
        buttonSaveDepartment = (Button) findViewById(R.id.button_save_department);
        buttonSaveDepartment.setOnClickListener(this);
        Intent i = getIntent();
        String action = i.getExtras().getString("Action");
        textViewDepartmentDetail.setText(action + " Department");
        if (action.equals("Edit")) {
            editTextDepartmentId.setText(i.getExtras().getString("DepartmentId"));
            editTextDepartmentId.setEnabled(false);
            editTextDepartmentName.setText(i.getExtras().getString("DepartmentName"));
            buttonSaveDepartment.setText("Save");
        }else{
            buttonSaveDepartment.setText(action);
        }
    }

    @Override
    public void onClick(View view) {
        String id = editTextDepartmentId.getText().toString().trim();
        String nama = editTextDepartmentName.getText().toString().trim();
        if (!id.equals("") && !nama.equals("")) {
            Department d = new Department();
            d.setDepartmentId(id);
            d.setName(nama);
            EventBus.getDefault().post(d);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_SHORT).show();
        }
    }
}
