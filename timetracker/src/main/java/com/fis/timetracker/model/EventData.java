package com.fis.timetracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class EventData {
    private String userId;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Kolkata")
    private Timestamp mouseClickTime;
    private int xCoordinate;
    private int yCoordinate;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getMouseClickTime() {
        return mouseClickTime;
    }

    public void setMouseClickTime(Timestamp mouseClickTime) {
        this.mouseClickTime = mouseClickTime;
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

    @Override
    public String toString() {
        return "EventData{" +
                "userId='" + userId + '\'' +
                ", mouseClickTime=" + mouseClickTime +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }
}
