package com.techelevator.dao;

import com.techelevator.model.VirtualGarden;

import java.util.List;

public interface VirtualGardenDao {

    List<VirtualGarden> getVirtualGardenByUserId(int userId);

    boolean addPlantToVirtualGarden(VirtualGarden virtualGarden);

    boolean deletePlantFromVirtualGarden(VirtualGarden virtualGarden);
}
