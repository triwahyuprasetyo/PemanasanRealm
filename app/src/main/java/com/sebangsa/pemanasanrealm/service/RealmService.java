package com.sebangsa.pemanasanrealm.service;

import android.content.Context;
import android.util.Log;

import com.sebangsa.pemanasanrealm.model.Department;
import com.sebangsa.pemanasanrealm.model.Employee;

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
//        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        if (realmService == null) {
            realmService = new RealmService();
        }
        return realmService;
    }

    public void addDepartment(final Department department) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Department d = bgRealm.createObject(Department.class);
                d.setDepartmentId(department.getDepartmentId());
                d.setName(department.getName());
            }
        });
    }

    public void addDepartmentAsync(final Department department) {
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

    public Department getDepartment(String departmentId) {
        return realm.where(Department.class).equalTo("departmentId", departmentId).findFirst();
    }

    public void updateDepartmentName(final Department department) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Department d = realm.where(Department.class).equalTo("departmentId", department.getDepartmentId()).findFirst();
                d.setName(department.getName());
            }
        });
    }

    public void updateDepartmentNameAsync(final Department department) {
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

    public void deleteDepartment(final String departmentId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Department d = realm.where(Department.class).equalTo("departmentId", departmentId).findFirst();
                if (d != null) {
                    d.deleteFromRealm();
                } else {
                    Log.i(LOG_TAG, "delete - department tidak ditemukan");
                }
            }
        });
    }

    public void deleteDepartmentAsync(final String departmentId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Department d = realm.where(Department.class).equalTo("departmentId", departmentId).findFirst();
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

    public void addEmployee(final Employee employee) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Employee e = realm.createObject(Employee.class);
                e.setEmployeeId(employee.getEmployeeId());
                e.setFirstName(employee.getFirstName());
                e.setLastName(employee.getLastName());
                e.setAge(employee.getAge());
                e.setAddress(employee.getAddress());
                Department d = realm.where(Department.class).equalTo("departmentId", employee.getDepartment().getDepartmentId()).findFirst();
                d.getEmployees().add(e);
            }
        });
    }

    public void addEmployeeAsync(final Employee employee) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Employee e = realm.createObject(Employee.class);
                e.setEmployeeId(employee.getEmployeeId());
                e.setFirstName(employee.getFirstName());
                e.setLastName(employee.getLastName());
                e.setAge(employee.getAge());
                e.setAddress(employee.getAddress());
                Department d = realm.where(Department.class).equalTo("departmentId", employee.getDepartment().getDepartmentId()).findFirst();
                d.getEmployees().add(e);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i(LOG_TAG, "add Employee sucsess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.i(LOG_TAG, "add Employee error");
            }
        });
    }

    public RealmResults<Employee> getAllEmployee() {
        return realm.where(Employee.class).findAll();
    }

    public Employee getEmployee(String employeeId) {
        return realm.where(Employee.class).equalTo("employeeId", employeeId).findFirst();
    }

    public void updateEmployee(final Employee employee) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Employee e = realm.where(Employee.class).equalTo("employeeId", employee.getEmployeeId()).findFirst();
                e.setFirstName(employee.getFirstName());
                e.setLastName(employee.getLastName());
                e.setAge(employee.getAge());
                e.setAddress(employee.getAddress());
            }
        });
    }

    public void updateEmployeeAsync(final Employee employee) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Employee e = realm.where(Employee.class).equalTo("employeeId", employee.getEmployeeId()).findFirst();
                e.setFirstName(employee.getFirstName());
                e.setLastName(employee.getLastName());
                e.setAge(employee.getAge());
                e.setAddress(employee.getAddress());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i(LOG_TAG, "update employee sucsess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.i(LOG_TAG, "update employee error");
            }
        });
    }

    public void deleteEmployee(final String employeeId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Employee e = realm.where(Employee.class).equalTo("employeeId", employeeId).findFirst();
                if (e != null) {
                    e.deleteFromRealm();
                } else {
                    Log.i(LOG_TAG, "delete - employee tidak ditemukan");
                }
            }
        });
    }

    public void deleteEmployeeAsync(final String employeeId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Employee e = realm.where(Employee.class).equalTo("employeeId", employeeId).findFirst();
                if (e != null) {
                    e.deleteFromRealm();
                } else {
                    Log.i(LOG_TAG, "delete - employee tidak ditemukan");
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i(LOG_TAG, "delete employee sucsess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.i(LOG_TAG, "delete employee error : ");
                error.printStackTrace();
            }
        });
    }
}
