package com.edison.springbootdemo;

import java.math.BigDecimal;

public class Coordinate {
     BigDecimal longitude;
     BigDecimal latitude;

    public Coordinate(BigDecimal longitude, BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
