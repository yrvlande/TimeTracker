package com.fis.timetracker.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


import java.sql.Timestamp;

@Table("timeEvents")
public class TimeEvent {

@PrimaryKey
private long id;
@Column("userId")
private String userId;
@Column("xCoordinate")
private int xCoordinate;
@Column("yCoordinate")
private int yCoordinate;
@Column("mouseClickTime")
private Timestamp mouseClickTime;
@Column("insertedOn")
private Timestamp insertedOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Timestamp getMouseClickTime() {
        return mouseClickTime;
    }

    public void setMouseClickTime(Timestamp mouseClickTime) {
        this.mouseClickTime = mouseClickTime;
    }

    public Timestamp getInsertedOn() {
        return insertedOn;
    }

    public void setInsertedOn(Timestamp insertedOn) {
        this.insertedOn = insertedOn;
    }

    @Override
    public String toString() {
        return "TimeEvent{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", mouseClickTime=" + mouseClickTime +
                ", insertedOn=" + insertedOn +
                '}';
    }
}
