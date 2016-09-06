package com.sebangsa.pemanasanrealm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.sebangsa.pemanasanrealm.model.Department;
import com.sebangsa.pemanasanrealm.service.RealmService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class DepartmentActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listViewDepartment;
    private Button buttonAddDepartment;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        listViewDepartment = (ListView) findViewById(R.id.listView_department);
        buttonAddDepartment = (Button) findViewById(R.id.button_add_department);
        buttonAddDepartment.setOnClickListener(this);
        EventBus.getDefault().register(this);
        realmService = RealmService.getRealmService(this);
        setAdapterList();
        setTitle("Department List");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onDepartmentEvent(Department d) {
//        if (realmService.getDepartment(d.getDepartmentId()) == null) {
//            realmService.addDepartment(d);
//        } else {
//            realmService.updateDepartmentName(d);
//        }
        realmService.saveDepartment(d);
        setAdapterList();
    }

    private void setAdapterList() {
        String[] departmentNames = new String[]{};
        final List<Department> departmentList = new ArrayList<Department>();
        RealmResults<Department> departments = realmService.getAllDepartments();
        if (departments.size() > 0) {
            departmentNames = new String[departments.size()];
            for (int i = 0; i < departments.size(); i++) {
                departmentNames[i] = departments.get(i).getName();
                departmentList.add(departments.get(i));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, departmentNames);
        listViewDepartment.setAdapter(adapter);
        listViewDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showDialog(departmentList.get(position));
            }

        });
    }

    private void showDialog(final Department department) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DepartmentActivity.this);
        alertDialog.setTitle("Action");
        alertDialog.setMessage(department.getName());
        alertDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(DepartmentActivity.this, DepartmentDetailActivity.class);
                i.putExtra("Action", "Edit");
                i.putExtra("DepartmentId", department.getDepartmentId());
                i.putExtra("DepartmentName", department.getName());
                startActivity(i);
            }
        });
        alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                realmService.deleteDepartment(department.getDepartmentId());
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
        Intent i = new Intent(DepartmentActivity.this, DepartmentDetailActivity.class);
        i.putExtra("Action", "Add");
        startActivity(i);
    }
}
