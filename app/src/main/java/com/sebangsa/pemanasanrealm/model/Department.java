package com.sebangsa.pemanasanrealm.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by sebangsa on 9/5/16.
 */
public class Department extends RealmObject {
    private String departmentId;
    private String name;
    private RealmList<Employee> employees;

    public Department() {
    }

    public Department(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(RealmList<Employee> employees) {
        this.employees = employees;
    }
}
