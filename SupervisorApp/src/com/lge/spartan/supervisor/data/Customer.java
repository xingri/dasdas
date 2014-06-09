/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lge.spartan.supervisor.data;

/**
 *
 * @author gina.du
 */
public class Customer {
    String phone;
    String fname;
    String lname;
    String address;

    public Customer (String phone, String fname, String lname, String address) {
        this.phone = phone;
        this.fname = fname;
        this.lname = lname;
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAddress() {
        return address;
    }   
}
