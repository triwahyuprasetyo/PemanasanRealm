package com.sebangsa.pemanasanrealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sebangsa.pemanasanrealm.service.RealmService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "MAIN ACTIVITY";
    private Button buttonDeptartment;
    private Button buttonEmployee;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonDeptartment = (Button) findViewById(R.id.button_department);
        buttonDeptartment.setOnClickListener(this);
        buttonEmployee = (Button) findViewById(R.id.button_employee);
        buttonEmployee.setOnClickListener(this);
        realmService = RealmService.getRealmService(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == buttonDeptartment.getId()) {
            Intent i = new Intent(MainActivity.this, DepartmentActivity.class);
            startActivity(i);
        } else if (view.getId() == buttonEmployee.getId()) {
            Intent i = new Intent(MainActivity.this, EmployeeActivity.class);
            startActivity(i);
        }
    }
}

