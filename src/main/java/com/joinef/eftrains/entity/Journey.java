package com.joinef.eftrains.entity;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

/**
 * Created by Jamie on 07/02/2015.
 */
public class Journey {

    private String departureStation;

    private String arrivalStation;

    private int price;

    private DateTime departureTime;

    private DateTime arrivalTime;

    public Journey(Builder builder) {
        this.departureStation = builder.departureStation;
        this.arrivalStation = builder.arrivalStation;
        this.price = builder.price;
        this.departureTime = builder.departureTime;
        this.arrivalTime = builder.arrivalTime;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public DateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(DateTime departureTime) {
        this.departureTime = departureTime;
    }

    public DateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(DateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDuration()
    {
        return Minutes.minutesBetween(departureTime, arrivalTime).getMinutes();
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Journey{" +
                "price=" + price +
                ", departureStation=" + departureStation +
                ", arrivalStation=" + arrivalStation +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    public static class Builder {
        private String departureStation;
        private String arrivalStation;
        private int price;
        private DateTime departureTime;
        private DateTime arrivalTime;

        public Builder departureStation(String departureStation) {
            this.departureStation = departureStation;
            return this;
        }

        public Builder arrivalStation(String arrivalStation) {
            this.arrivalStation = arrivalStation;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder departureTime(DateTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public Builder arrivalTime(DateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public Journey build() {
            return new Journey(this);
        }
    }
}
