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
public class Station {
       private int stationId;
    private String name;
    private String desc;
    private int warehouseId;
    private StationType type;

    /**
     * @return the robotId
     */
    public int getStationId() {
        return stationId;
    }

    /**
     * @param robotId the robotId to set
     */
    public void setStationId(int stationId) {
        this.stationId = stationId;
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
     * @return the type
     */
    public StationType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(StationType type) {
        this.type = type;
    }
}
