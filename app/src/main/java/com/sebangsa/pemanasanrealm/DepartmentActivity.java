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

public class DepartmentActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listViewDepartment;
    private Button buttonAddDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        listViewDepartment = (ListView) findViewById(R.id.listView_department);
        String[] values = new String[]{};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
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
        buttonAddDepartment = (Button) findViewById(R.id.button_add_department);
        buttonAddDepartment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(DepartmentActivity.this, DepartmentDetailActivity.class);
        startActivity(i);
    }
}
