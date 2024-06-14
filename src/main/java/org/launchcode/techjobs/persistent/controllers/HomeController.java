package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    JobRepository jobRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");
        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute("name", "");
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("job", new Job());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }

        Optional<Employer> employerOptional = employerRepository.findById(employerId);
        if (employerOptional.isPresent()) {
            Employer employer = employerOptional.get();
            newJob.setEmployer(employer);
        } else {
            model.addAttribute("errorMessage", "Employer not found for ID: " + employerId);
            return "add";
        }

        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);

        jobRepository.save(newJob);

        return "redirect:";
    }

//    @PostMapping("add")
//    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
//                                    Errors errors, Model model, @RequestParam int employerId,
//                                    @RequestParam List<Integer> skills) {
//
//        if(errors.hasErrors()){
//            model.addAttribute("title","Add Job");
//            model.addAttribute("employers",employerRepository.findAll());
//            model.addAttribute("skills",skillRepository.findAll());
//            return "add";
//        }
//        Optional<Employer> result = employerRepository.findById(employerId);
//
//        if(result.isEmpty()){
//            model.addAttribute("title","Invalid Employer ID: "+employerId);
//            return "add";
//        }else{
//            Employer employer = result.get();
//            newJob.setEmployer(employer);
//
//          List<Skill> skillResults =  skillRepository.findAllById(skills);
//            List<Skill> skillObjs = (List<Skill>) skillResults;
//            newJob.setSkills(skillObjs);
//
//            jobRepository.save(newJob);
//
//            return "redirect:";
//        }
//
//    }


    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            model.addAttribute("job", job);
        } else {
            model.addAttribute("errorMessage", "Job not found for ID: " + jobId);
        }


        return "view";
    }

}
