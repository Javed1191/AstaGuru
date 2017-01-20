package com.infomanav.astaguru;

/**
 * Created by fox-2 on 11/30/2016.
 */

public class Model_Careeer {
    String job_id;

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    String job_title;
    boolean is_front;
    boolean is_close;
    public Model_Careeer(String job_id, String job_title, String job_desc,String businessUnit,
                         String jobResponsibility,String functionalSkills,String technicalSkills,
                         String joiningTime,String jobSalary,String jobExperience,boolean is_front, boolean is_close) {
        this.job_id = job_id;
        this.job_title = job_title;
        this.job_desc = job_desc;
        this.businessUnit=businessUnit;
        this.jobResponsibility=jobResponsibility;
        this.functionalSkills=functionalSkills;
        this.technicalSkills=technicalSkills;
        this.joiningTime=joiningTime;
        this.jobSalary=jobSalary;
        this.jobExperience=jobExperience;
        this.is_front=is_front;
        this.is_close=is_close;
    }

    String job_desc;

    public String getJobExperience() {
        return jobExperience;
    }

    public void setJobExperience(String jobExperience) {
        this.jobExperience = jobExperience;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJoiningTime() {
        return joiningTime;
    }

    public void setJoiningTime(String joiningTime) {
        this.joiningTime = joiningTime;
    }

    public String getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(String technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public String getFunctionalSkills() {
        return functionalSkills;
    }

    public void setFunctionalSkills(String functionalSkills) {
        this.functionalSkills = functionalSkills;
    }

    public String getJobResponsibility() {
        return jobResponsibility;
    }

    public void setJobResponsibility(String jobResponsibility) {
        this.jobResponsibility = jobResponsibility;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }
    public boolean getIs_front() {
        return is_front;
    }

    public void setIs_front(boolean is_front) {
        this.is_front = is_front;
    }

    public boolean getIsClose() {
        return is_close;
    }

    public void setIsClose(boolean is_close) {
        this.is_close = is_close;
    }



    String businessUnit,jobResponsibility,functionalSkills,technicalSkills,joiningTime,jobSalary,jobExperience;
}
