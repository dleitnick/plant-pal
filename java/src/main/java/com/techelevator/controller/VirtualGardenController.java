package com.techelevator.controller;

import com.techelevator.dao.ReviewDao;
import com.techelevator.dao.UserDao;
import com.techelevator.dao.VirtualGardenDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Plant;
import com.techelevator.model.PlantDto;
import com.techelevator.model.VirtualGarden;
import com.techelevator.service.PlantApi;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class VirtualGardenController {

    private VirtualGardenDao virtualGardenDao;
    private UserDao userDao;
    private final PlantApi plantService;
    private final ControllerConsoleService console = new ControllerConsoleService("VirtualGarden");

    public VirtualGardenController(VirtualGardenDao virtualGardenDao, UserDao userDao, PlantApi plantService) {
        this.virtualGardenDao = virtualGardenDao;
        this.userDao = userDao;
        this.plantService = plantService;
    }

    @RequestMapping(path = "/gardens/{id}", method = RequestMethod.GET)
    public List<PlantDto> getVirtualGardenByUser(@PathVariable int id) {
        List<VirtualGarden> virtualGardenList = new ArrayList<>();
        try {
            virtualGardenList = virtualGardenDao.getVirtualGardenByUserId(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No garden found");
        }
        List<PlantDto> virtualGardenPlants = new ArrayList<>();
        for (VirtualGarden virtualGardenPlant : virtualGardenList) {
            virtualGardenPlants.add(plantService.getPlantByIdForGarden(virtualGardenPlant.getPlantId()));
        }
        console.log(String.format("Pulled the garden for userID: %s", id));
        return virtualGardenPlants;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/gardens", method = RequestMethod.POST)
    public boolean addPlantToVirtualGarden(@RequestBody int plantId, Principal principal) {
        int userId = getUserId(principal.getName());
        VirtualGarden virtualGardenToAdd = new VirtualGarden(userId, plantId);
        try {
            boolean plantAddedToGarden = virtualGardenDao.addPlantToVirtualGarden(virtualGardenToAdd);
            if (!plantAddedToGarden) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add to virtual garden");
        } catch (DaoException e) {
            if (e.getMessage().equals("Duplicate plant")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plant already in garden.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getCause().toString());
        }
        console.log(String.format("Adding plant ID: %s to %s's garden.", plantId, principal.getName()));
        return true;
    }

    @RequestMapping(path = "/gardens/{id}", method = RequestMethod.DELETE)
    public boolean removePlantFromVirtualGarden(@PathVariable int id, Principal principal) {
        int userId = getUserId(principal.getName());
        VirtualGarden virtualGardenToRemove = new VirtualGarden(userId, id);
        try {
            boolean plantAddedToGarden = virtualGardenDao.deletePlantFromVirtualGarden(virtualGardenToRemove);
            if (!plantAddedToGarden) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete from virtual garden");
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete from virtual garden");
        }
        console.log(String.format("Removed plant ID: %s from %s's garden.", id, principal.getName()));
        return true;
    }

    private int getUserId(String username) {
        int userId = -1;
        try {
            userId = userDao.getUserByUsername(username).getId();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Logged in user not found");
        }
        return userId;
    }

}