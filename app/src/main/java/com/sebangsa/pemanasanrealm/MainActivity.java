package com.sebangsa.pemanasanrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sebangsa.pemanasanrealm.model.Department;
import com.sebangsa.pemanasanrealm.model.Employee;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "MAIN ACTIVITY";
    private Button buttonAdd;
    private Button buttonRetrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);

        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(this);
        buttonRetrieve = (Button) findViewById(R.id.button_retrieve);
        buttonRetrieve.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Realm realm = Realm.getDefaultInstance();
        if (view.getId() == buttonAdd.getId()) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    Employee employee1 = bgRealm.createObject(Employee.class);
                    employee1.setEmployeeId("E-111");
                    employee1.setFirstName("Tri");
                    employee1.setLastName("Wahyu");
                    employee1.setAge(25);
                    employee1.setAddress("Karanganyar");

                    Employee employee2 = bgRealm.createObject(Employee.class);
                    employee2.setEmployeeId("E-222");
                    employee2.setFirstName("Wahyu");
                    employee2.setLastName("Prasetyo");
                    employee2.setAge(26);
                    employee2.setAddress("Solo");

                    Employee employee3 = bgRealm.createObject(Employee.class);
                    employee3.setEmployeeId("E-333");
                    employee3.setFirstName("Tri");
                    employee3.setLastName("Prasetyo");
                    employee3.setAge(27);
                    employee3.setAddress("Yogyakarta");

                    Department department = bgRealm.createObject(Department.class);
                    department.setDepartmentId("SB-Android");
                    department.setName("Android Development");
                    department.getEmployees().add(employee1);
                    department.getEmployees().add(employee2);
                    department.getEmployees().add(employee3);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    // Transaction was a success.
                    Log.i(LOG_TAG, "sucsess");
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    // Transaction failed and was automatically canceled.
                    Log.i(LOG_TAG, "error");
                }
            });
        } else {
            RealmResults<Employee> employees = realm.where(Employee.class).findAll();
            if (employees.size() > 0) {
                for (Employee employee : employees) {
                    Log.i(LOG_TAG, "E-Id " + employee.getEmployeeId());
                    Log.i(LOG_TAG, "Name " + employee.getFirstName() + " " + employee.getLastName());
                    Log.i(LOG_TAG, "Age " + employee.getAge());
                    Log.i(LOG_TAG, "Address " + employee.getAddress());
                    Log.i(LOG_TAG, "==========================");
                }
            } else {
                Log.i(LOG_TAG, "Employee Kosong");
            }
        }
    }
}
