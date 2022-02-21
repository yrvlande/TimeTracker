package com.fis.timetracker.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Table("userSessionDetails")
public class UserSessionDetail {
    @PrimaryKey
    private String userId;
    @Column("businessDate")
    private LocalDate businessDate;
    @Column("userLoggedIn")
    private Timestamp userLoggedIn;
    @Column("userLoggedOut")
    private Timestamp userLoggedOut;
    @Column("activeHours")
    private float activeHours;
    @Column("insertedOn")
    private Timestamp insertedOn;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(LocalDate businessDate) {
        this.businessDate = businessDate;
    }

    public Timestamp getUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(Timestamp userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public Timestamp getUserLoggedOut() {
        return userLoggedOut;
    }

    public void setUserLoggedOut(Timestamp userLoggedOut) {
        this.userLoggedOut = userLoggedOut;
    }

    public float getActiveHours() {
        return activeHours;
    }

    public void setActiveHours(float activeHours) {
        this.activeHours = activeHours;
    }

    public Timestamp getInsertedOn() {
        return insertedOn;
    }

    public void setInsertedOn(Timestamp insertedOn) {
        this.insertedOn = insertedOn;
    }

    @Override
    public String toString() {
        return "UserSessionDetail{" +
                "userId='" + userId + '\'' +
                ", businessDate=" + businessDate +
                ", userLoggedIn=" + userLoggedIn +
                ", userLoggedOut=" + userLoggedOut +
                ", activeHours=" + activeHours +
                ", insertedOn=" + insertedOn +
                '}';
    }
}
