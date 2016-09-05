package com.sebangsa.pemanasanrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sebangsa.pemanasanrealm.model.Department;
import com.sebangsa.pemanasanrealm.service.RealmService;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "MAIN ACTIVITY";
    private Button buttonAdd;
    private Button buttonRetrieve;
    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonAddDept;
    private Button buttonViewDept;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
//        Realm.setDefaultConfiguration(realmConfig);

        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(this);
        buttonRetrieve = (Button) findViewById(R.id.button_retrieve);
        buttonRetrieve.setOnClickListener(this);
        buttonUpdate = (Button) findViewById(R.id.button_update);
        buttonUpdate.setOnClickListener(this);
        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(this);
        buttonAddDept = (Button) findViewById(R.id.button_add_dept);
        buttonAddDept.setOnClickListener(this);
        buttonViewDept = (Button) findViewById(R.id.button_view_dept);
        buttonViewDept.setOnClickListener(this);

        realmService = RealmService.getRealmService(this);
    }

    @Override
    public void onClick(View view) {
//        Realm realm = Realm.getDefaultInstance();
        if (view.getId() == buttonAdd.getId()) {
//            realm.executeTransactionAsync(new Realm.Transaction() {
//                @Override
//                public void execute(Realm bgRealm) {
//                    Employee employee1 = bgRealm.createObject(Employee.class);
//                    employee1.setEmployeeId("E-111");
//                    employee1.setFirstName("Tri");
//                    employee1.setLastName("Wahyu");
//                    employee1.setAge(25);
//                    employee1.setAddress("Karanganyar");
//
//                    Employee employee2 = bgRealm.createObject(Employee.class);
//                    employee2.setEmployeeId("E-222");
//                    employee2.setFirstName("Wahyu");
//                    employee2.setLastName("Prasetyo");
//                    employee2.setAge(26);
//                    employee2.setAddress("Solo");
//
//                    Employee employee3 = bgRealm.createObject(Employee.class);
//                    employee3.setEmployeeId("E-333");
//                    employee3.setFirstName("Tri");
//                    employee3.setLastName("Prasetyo");
//                    employee3.setAge(27);
//                    employee3.setAddress("Yogyakarta");
//
//                    Department department = bgRealm.createObject(Department.class);
//                    department.setDepartmentId("SB-Android");
//                    department.setName("Android Development");
//                    department.getEmployees().add(employee1);
//                    department.getEmployees().add(employee2);
//                    department.getEmployees().add(employee3);
//                }
//            }, new Realm.Transaction.OnSuccess() {
//                @Override
//                public void onSuccess() {
//                    // Transaction was a success.
//                    Log.i(LOG_TAG, "sucsess");
//                }
//            }, new Realm.Transaction.OnError() {
//                @Override
//                public void onError(Throwable error) {
//                    // Transaction failed and was automatically canceled.
//                    Log.i(LOG_TAG, "error");
//                }
//            });
        } else if (view.getId() == buttonRetrieve.getId()) {
//            RealmResults<Employee> employees = realm.where(Employee.class).findAll();
//            if (employees.size() > 0) {
//                for (Employee employee : employees) {
//                    Log.i(LOG_TAG, "E-Id " + employee.getEmployeeId());
//                    Log.i(LOG_TAG, "Name " + employee.getFirstName() + " " + employee.getLastName());
//                    Log.i(LOG_TAG, "Age " + employee.getAge());
//                    Log.i(LOG_TAG, "Address " + employee.getAddress());
//                    Log.i(LOG_TAG, "==========================");
//                }
//            } else {
//                Log.i(LOG_TAG, "Employee Kosong");
//            }
        } else if (view.getId() == buttonUpdate.getId()) {
//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    Employee employee = realm.where(Employee.class).equalTo("employeeId", "E-333").findFirst();
//                    if (employee != null) {
//                        employee.setFirstName("Valentino");
//                        employee.setLastName("Rossi");
//                        employee.setAge(37);
//                        employee.setAddress("Italia");
//                    } else {
//                        Log.i(LOG_TAG, "Data tidak ditemukan");
//                    }
//                }
//            });
        } else if (view.getId() == buttonDelete.getId()) {
//            final RealmResults<Employee> employees = realm.where(Employee.class).findAll();
//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    if (employees.size() > 0) {
//                        employees.deleteFromRealm(0); // Delete and remove object directly
//                    } else {
//                        Log.i(LOG_TAG, "Employee Kosong");
//                    }
//                }
//            });
            Department department3 = new Department();
            department3.setDepartmentId("SB-Dept-Web");
            department3.setName("Web Development Devision");

            realmService.deleteDepartment(department3);

        } else if (view.getId() == buttonAddDept.getId()) {
            Department department1 = new Department();
            department1.setDepartmentId("SB-Dept-Android");
            department1.setName("Android Development");

            Department department2 = new Department();
            department2.setDepartmentId("SB-Dept-API");
            department2.setName("API Development");

            Department department3 = new Department();
            department3.setDepartmentId("SB-Dept-Web");
            department3.setName("Web Development");

            realmService.addDepartment(department1);
            realmService.addDepartment(department2);
            realmService.addDepartment(department3);
        } else {
//            Department department3 = new Department();
//            department3.setDepartmentId("SB-Dept-Web");
//            department3.setName("Web Development Devision");
//
//            realmService.updateDepartmentName(department3);

            RealmResults<Department> departments = realmService.getAllDepartments();
            if (departments.size() > 0) {
                for (Department department : departments) {
                    Log.i(LOG_TAG, "" + department.getDepartmentId() + " ---- " + department.getName());
                }
            } else {
                Log.i(LOG_TAG, "Department Kosong");
            }
        }
    }
}
