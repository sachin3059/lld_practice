package Example1.Problematic;
public class Employee {
    private int id; // Employee ID
    private String name; // Employee Name
    private String address; // Employee Address

    public Employee(int id, String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public void printEmployeePerformanceReport(int id){
        // some function
        System.out.println("Employee Performance Report");
    }

    public double computeSalary(){
        // code to compute salary
        return 10000.0;
    }

    public void updateEmployeeData(){
        // code to update employee data
        System.out.println("Employee data updated successfully");
    }

    public void fetchEmployeeData(){
        // code to fetch Employee Data
        System.out.println("Employee data fetched successfully");
    }
}


// Note: this class is trying to do too many things 
// so, there are too many pieces to change this piece of cases


// e.g: lets say compute salary function changes the tax regime then computeSalary function will get affected.
// e.g: if data storage changes then update employee data function get affected
// e.g: if the print report format changes then this function will also get affected.



// so , because the above class is doing too many things and there are too many reason to update the  code of the class
// this violate SRP.


// SRP: states that there should be only one and only reason to change the code of the class.
