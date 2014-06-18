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
public class Warehouse {
    private int warehouseId;
    private String name;
    private String desc;
    private int noOfInvStations;
    private int noOfShippingStations;
    private int noOfRobots;
    private String ipaddress;
    private int status;

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
     * @return the noOfInvStations
     */
    public int getNoOfInvStations() {
        return noOfInvStations;
    }

    /**
     * @param noOfInvStations the noOfInvStations to set
     */
    public void setNoOfInvStations(int noOfInvStations) {
        this.noOfInvStations = noOfInvStations;
    }

    /**
     * @return the noOfShippingStations
     */
    public int getNoOfShippingStations() {
        return noOfShippingStations;
    }

    /**
     * @param noOfShippingStations the noOfShippingStations to set
     */
    public void setNoOfShippingStations(int noOfShippingStations) {
        this.noOfShippingStations = noOfShippingStations;
    }

    /**
     * @return the noOfRobots
     */
    public int getNoOfRobots() {
        return noOfRobots;
    }

    /**
     * @param noOfRobots the noOfRobots to set
     */
    public void setNoOfRobots(int noOfRobots) {
        this.noOfRobots = noOfRobots;
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

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
