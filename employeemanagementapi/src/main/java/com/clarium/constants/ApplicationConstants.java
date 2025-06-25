package com.clarium.constants;

import java.time.DayOfWeek;
import java.time.ZoneId;

public class ApplicationConstants {

    /*Date And Time Constants*/
    public static final ZoneId SYSTEM_DEFAULT_ZONE = ZoneId.systemDefault();
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    /*Report Constants*/
    public static final DayOfWeek WEEK_START_DAY = DayOfWeek.MONDAY;
    public static final DayOfWeek WEEK_END_DAY = DayOfWeek.FRIDAY;
    public static final double MILLISECONDS_TO_SECONDS = 1000.0;
    public static final double SECONDS_TO_MINUTES = 60.0;
    public static final double MINUTES_TO_HOURS = 60.0;
    public static final double MILLISECONDS_TO_HOURS = MILLISECONDS_TO_SECONDS * SECONDS_TO_MINUTES * MINUTES_TO_HOURS;
    public static final double PRECISION_MULTIPLIER = 100.0;
    public static final double MIN_WORK_HOURS_THRESHOLD = 0.0;
    public static final String JSON_ARRAY_START = "[";
    public static final String JSON_ARRAY_END = "]";
    public static final String JSON_SEPARATOR = ",";
    public static final String JSON_TEMPLATE = "{\"EmpId\":%d,\"TotalWorkHours\":%.2f}";
    public static final String MAIL_SENT_CONSOLE_MESSAGE = "Mail Sent";
    public static final String MAIL_SENT_SUCCESS_MESSAGE = "Mail Sent Successfully";
    public static final String EMPLOYEE_LOGIN_NOT_FOUND = "Employee Not Found";
    public static final String EMPLOYEE_LOGIN_IS_PRESENT = "Employee Already Logged In";
    public static final String EMPLOYEE_LOGIN_SUCCESSFUL = "Employee Login Successful";
    public static final String EMPLOYEE_LOGOUT_SUCCESSFUL = "Employee Logout Successful";

    /*Schedule Constants*/
    public static final String WEEKLY_REPORT_CRON = "0 0 18 * * FRI";

    /*Employee Service Constants*/
    public static final String EMPLOYEE_CREATION_SUCCESSFUL = "The Employee Data has been Saved Successfully";
    public static final String EMPLOYEE_CREATION_UNSUCCESSFUL = "Employee already Exists in Records";
    public static final String EMPLOYEE_UPDATE_SUCCESSFUL = "Employee Details Are Updated Successfully";
    public static final String EMPLOYEE_UPDATE_UNSUCCESSFUL = "Employee Details Update Unsuccessful";
    public static final String EMPLOYEE_DELETION_SUCCESSFUL = " Has been Deleted Successfully";
    public static final String EMPLOYEE_DELETION_UNSUCCESSFUL = "The Employee does not exists in the Table";


}
