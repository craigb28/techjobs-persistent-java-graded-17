package org.launchcode.techjobs.persistent.models;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Job extends AbstractEntity {
    @ManyToOne
    private Employer employer;

    @ManyToMany
    @JoinColumn (name="skills")
    private List<Skill> skills = new ArrayList<>();

    // Initialize the id and value fields.

    public Job(Employer employer, List<Skill> skills) {
        this.employer = employer;
        this.skills = skills;
    }

    public Job() {
    }

    // Getters and setters.

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
