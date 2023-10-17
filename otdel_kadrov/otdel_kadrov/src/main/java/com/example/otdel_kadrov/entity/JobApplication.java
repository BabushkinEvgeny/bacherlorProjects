package com.example.otdel_kadrov.entity;

public class JobApplication {
    private Integer id_candidate;
    private Integer id_status;
    private Integer id_position;
    private Integer id_examiner;
    private String description;

    public JobApplication(Integer id_candidate, Integer id_status, Integer id_position, Integer id_examiner, String description) {
        this.id_candidate = id_candidate;
        this.id_status = id_status;
        this.id_position = id_position;
        this.id_examiner = id_examiner;
        this.description = description;
    }

    public Integer getId_candidate() {
        return id_candidate;
    }

    public void setId_candidate(Integer id_candidate) {
        this.id_candidate = id_candidate;
    }

    public Integer getId_status() {
        return id_status;
    }

    public void setId_status(Integer id_status) {
        this.id_status = id_status;
    }

    public Integer getId_position() {
        return id_position;
    }

    public void setId_position(Integer id_position) {
        this.id_position = id_position;
    }

    public Integer getId_examiner() {
        return id_examiner;
    }

    public void setId_examiner(Integer id_examiner) {
        this.id_examiner = id_examiner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
