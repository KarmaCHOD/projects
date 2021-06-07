package com.gcitcomplaint.voice;

public class Complaints {
    private String person_name, person_id, complaint_title, complaint_type, description, complaint_status;

    public Complaints() {
    }

    public Complaints(String person_name, String person_id, String complaint_title, String complaint_type, String description, String complaint_status) {
        this.person_name = person_name;
        this.person_id = person_id;
        this.complaint_title = complaint_title;
        this.complaint_type = complaint_type;
        this.description = description;
        this.complaint_status = complaint_status;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getComplaint_title() {
        return complaint_title;
    }

    public void setComplaint_title(String complaint_title) {
        this.complaint_title = complaint_title;
    }

    public String getComplaint_type() {
        return complaint_type;
    }

    public void setComplaint_type(String complaint_type) {
        this.complaint_type = complaint_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComplaint_status() {
        return complaint_status;
    }

    public void setComplaint_status(String complaint_status) {
        this.complaint_status = complaint_status;
    }
}
