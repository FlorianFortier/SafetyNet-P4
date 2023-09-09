package com.safetyNet.alerts.api.entity;

import lombok.Data;
import org.json.simple.JSONArray;

@Data
public class Firestation {


    private String address = "";
    private String station = "";

    /**
     * @param address Address of the firestation
     * @param station Station number of the firestation
     */
    public Firestation(String address, String station) {
        this.address = address;
        this.station = station;
    }

}
