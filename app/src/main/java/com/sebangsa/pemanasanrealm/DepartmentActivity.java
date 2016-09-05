package com.sebangsa.pemanasanrealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sebangsa.pemanasanrealm.model.Department;
import com.sebangsa.pemanasanrealm.service.RealmService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.realm.RealmResults;

public class DepartmentActivity extends AppCompatActivity implements View.OnClickListener {
    String[] departmentNames = new String[]{};
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
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onDepartmentEvent(Department event) {
        realmService.addDepartment(event);
        RealmResults<Department> departments = realmService.getAllDepartments();
        if (departments.size() > 0) {
            departmentNames = new String[departments.size()];
            for (int i = 0; i < departments.size(); i++) {
                departmentNames[i] = departments.get(i).getName();
            }
            setAdapterList();
        }
    }

    private void setAdapterList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, departmentNames);
        listViewDepartment.setAdapter(adapter);
        listViewDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listViewDepartment.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(DepartmentActivity.this, DepartmentDetailActivity.class);
        i.putExtra("Action", "Add");
        startActivity(i);
    }
}
