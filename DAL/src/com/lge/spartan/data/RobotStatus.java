package com.lge.spartan.data;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sun.shin
 */
public class RobotStatus implements Serializable {

    private int orderNo;

    private int stn1Visited = 0;
    private int stn2Visited = 0;
    private int stn3Visited = 0;
    private int stn4Visited = 0;

    private int stn1Need = 0;
    private int stn2Need = 0;
    private int stn3Need = 0;
    private int stn4Need = 0;
    
    private int state = 0;
  
    public RobotState getState()   {
        return RobotState.values[state];
    }

    public String toString() {
        return orderNo + " " + orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderNo() {
        return this.orderNo;
    }

    public void setStn1Visited(int stn1Visited) {
        this.stn1Visited = stn1Visited;
    }

    public void setStn2Visited(int stn2Visited) {
        this.stn2Visited = stn2Visited;
    }

    public void setStn3Visited(int stn3Visited) {
        this.stn3Visited = stn3Visited;
    }

    public void setStn4Visited(int stn4Visited) {
        this.stn4Visited = stn4Visited;
    }

    public void setStn1Need(int stn1Need) {
        this.stn1Need = stn1Need;
    }

    public void setStn2Need(int stn2Need) {
        this.stn2Need = stn2Need;
    }

    public void setStn3Need(int stn3Need) {
        this.stn3Need = stn3Need;
    }

    public void setStn4Need(int stn4Need) {
        this.stn4Need = stn4Need;
    }

    public int getStn1Visited() {
        return this.stn1Visited;
    }

    public int getStn2Visited() {
        return this.stn2Visited;
    }

    public int getStn3Visited() {
        return this.stn3Visited;
    }

    public int getStn4Visited() {
        return this.stn4Visited;
    }

    public int getStn1Need() {
        return this.stn1Need;
    }

    public int getStn2Need() {
        return this.stn2Need;
    }

    public int getStn3Need() {
        return this.stn3Need;
    }

    public int getStn4Need() {
        return this.stn4Need;
    }
    
    public void setState(int state) {
        this.state = state;
    }
}
