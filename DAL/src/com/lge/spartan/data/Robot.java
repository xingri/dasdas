/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lge.spartan.data;

/**
 *
 * @author vijay.rachabattuni
 */
public class Robot {
    private int robotId;
    private String name;
    private String desc;
    private int warehouseId;
    private RobotState status;
    private String ipaddress;

    /**
     * @return the robotId
     */
    public int getRobotId() {
        return robotId;
    }

    /**
     * @param robotId the robotId to set
     */
    public void setRobotId(int robotId) {
        this.robotId = robotId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the warehouseId
     */
    public int getWarehouseId() {
        return warehouseId;
    }

    /**
     * @param warehouseId the warehouseId to set
     */
    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * @return the status
     */
    public RobotState getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(RobotState status) {
        this.status = status;
    }

    /**
     * @return the ipaddress
     */
    public String getIpaddress() {
        return ipaddress;
    }

    /**
     * @param ipaddress the ipaddress to set
     */
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
}
