package com.clarium.dto;

import java.util.List;

public class WeeklyReportDTO {

    private Integer empId;
    private List<DailyWorkReportDTO> weeklyReport;
    private double totalWorkHours;

    public WeeklyReportDTO() {
    }

    public WeeklyReportDTO(Integer empId, List<DailyWorkReportDTO> weeklyReport, double totalWorkHours) {
        this.empId = empId;
        this.weeklyReport = weeklyReport;
        this.totalWorkHours = totalWorkHours;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public List<DailyWorkReportDTO> getWeeklyReport() {
        return weeklyReport;
    }

    public void setWeeklyReport(List<DailyWorkReportDTO> weeklyReport) {
        this.weeklyReport = weeklyReport;
    }

    public double getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(double totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }
}
