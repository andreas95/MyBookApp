package ro.unibuc.MyLibrary.main.java.model;

import java.util.Date;

/**
 * @author antoneandreas
 */

public class Report {

    private String category;
    private String description;
    private String screenshot;
    private Date reportDate;
    private Boolean status;

    public Report(String category, String description, String screenshot, Boolean status) {
        this.category = category;
        this.description = description;
        this.screenshot = screenshot;
        this.status = status;
    }

    public String getCategory() {return category;}

    public String getDescription() {return description;}

    public String getScreenshot() {return screenshot;}

    public Boolean getStatus() {return status;}

    public Date getReportDate() {return reportDate;}

    public void setCategory(String category) {this.category = category;}

    public void setDescription(String description) {this.description = description;}

    public void setReportDate(Date reportDate) {this.reportDate = reportDate;}

    public void setStatus(Boolean status) {this.status = status;}

    public void setScreenshot(String screenshot) {this.screenshot = screenshot;}

}