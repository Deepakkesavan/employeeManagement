package com.clarium.dto;

import java.util.Date;

public class DailyWorkReportDTO {

    private Date date;
    private double workHours;

    public DailyWorkReportDTO() {
    }

    public DailyWorkReportDTO(Date date, double workHours) {
        this.date = date;
        this.workHours = workHours;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }
}
