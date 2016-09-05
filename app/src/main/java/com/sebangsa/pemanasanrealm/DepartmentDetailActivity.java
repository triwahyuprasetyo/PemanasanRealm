package com.sebangsa.pemanasanrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    }

    @Override
    public void onClick(View view) {

    }
}
