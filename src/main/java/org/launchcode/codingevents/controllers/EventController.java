package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.*;
import org.launchcode.codingevents.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {


    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");
        model.addAttribute("events", EventData.getAll());
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute Event newEvent) {
        EventData.add(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model){
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", EventData.getAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds){
        if (eventIds != null) {
            for (int id : eventIds){
                EventData.remove(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("edit/{event/Id}")//configure route to include path variable eventId
    public String displayEditForm(Model model, @PathVariable int eventId) {
        String eventName = EventData.getById(eventId).getName();
        model.addAttribute("title", "Edit Event '" + eventName + "' (id="+eventId+")");//add model
        model.addAttribute("events", EventData.getById(eventId));//use EventData method to find event object with given eventId and put in model
        return "events/edit";//return appropriate template string
    }

    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description) {
        Event event= EventData.getById(eventId);//query EventData for the event being edited w/id parameter
        event.setName(name);//update name with setter method
        event.setDescription(description);//update description with setter method
        return "redirect:";//redirect user to /events page
    }
}
