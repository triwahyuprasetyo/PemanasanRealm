package com.sebangsa.pemanasanrealm.service;

import android.content.Context;
import android.util.Log;

import com.sebangsa.pemanasanrealm.model.Department;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by sebangsa on 9/5/16.
 */
public class RealmService {
    private static RealmService realmService;
    private final String LOG_TAG = "REALM SERVICE";
    private Realm realm;

    public RealmService() {
        realm = Realm.getDefaultInstance();
    }

    public static RealmService getRealmService(Context context) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        if (realmService == null) {
            realmService = new RealmService();
        }
        return realmService;
    }

    public void addDepartment(final Department department) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Department d = bgRealm.createObject(Department.class);
                d.setDepartmentId(department.getDepartmentId());
                d.setName(department.getName());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i(LOG_TAG, "add department sucsess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.i(LOG_TAG, "add department error");
            }
        });
    }

    public RealmResults<Department> getAllDepartments() {
        return realm.where(Department.class).findAll();
    }

    public void updateDepartmentName(final Department department) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Department d = realm.where(Department.class).equalTo("departmentId", department.getDepartmentId()).findFirst();
                d.setName(department.getName());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i(LOG_TAG, "update department sucsess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.i(LOG_TAG, "update department error");
            }
        });
    }

    public void deleteDepartment(final Department department) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Department d = realm.where(Department.class).equalTo("departmentId", department.getDepartmentId()).findFirst();
                if (d != null) {
                    d.deleteFromRealm();
                } else {
                    Log.i(LOG_TAG, "delete - department tidak ditemukan");
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i(LOG_TAG, "delete department sucsess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.i(LOG_TAG, "delete department error : ");
                error.printStackTrace();
            }
        });
    }
}
