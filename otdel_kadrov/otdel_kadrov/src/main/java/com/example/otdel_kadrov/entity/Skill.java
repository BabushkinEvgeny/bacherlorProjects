package com.example.otdel_kadrov.entity;

public class Skill {
    private Integer idSkills;
    private String requirement;

    public Skill(){}

    public Integer getIdSkills() {
        return idSkills;
    }

    public void setIdSkills(Integer idSkills) {
        this.idSkills = idSkills;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Skill(Integer idSkills, String requirement) {
        this.idSkills = idSkills;
        this.requirement = requirement;
    }
}
