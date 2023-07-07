package com.safetyNet.alerts.api.entity;

import lombok.Data;

@Data
public class Firestation {

        Long id;
        String address;

        String station;

        public Firestation() {
        }

        public Firestation(String address, String station) {
            this.address = address;
            this.station = station;
        }
}
