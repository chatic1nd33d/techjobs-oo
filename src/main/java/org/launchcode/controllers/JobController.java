package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.Name;
import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job someJob = jobData.findById(id);
        model.addAttribute("job", someJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.



        String aName = jobForm.getName();
        int anEmployerId = jobForm.getEmployerId();
        int aLocationId = jobForm.getLocationId();
        int aPositionTypeId = jobForm.getPositionTypeId();
        int aCoreCompetencyId = jobForm.getCoreCompetencyId();

        Employer anEmployer = jobData.getEmployers().findById(anEmployerId);
        Location aLocation = jobData.getLocations().findById(aLocationId);
        PositionType aPositionType = jobData.getPositionTypes().findById(aPositionTypeId);
        CoreCompetency aCoreCompetency = jobData.getCoreCompetencies().findById(aCoreCompetencyId);

        Job newJob = new Job(aName, anEmployer, aLocation, aPositionType, aCoreCompetency);
        jobData.add(newJob);


        model.addAttribute("name", aName);
        model.addAttribute("employerId", anEmployerId);
        model.addAttribute("locationId", aLocationId);
        model.addAttribute("coreCompetencyId", aCoreCompetencyId);
        model.addAttribute("positionTypeId", aPositionTypeId);

        return "redirect:";

    }
}
