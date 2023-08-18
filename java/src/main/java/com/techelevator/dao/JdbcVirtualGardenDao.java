package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Review;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.VirtualGarden;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JdbcVirtualGardenDao implements VirtualGardenDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVirtualGardenDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<VirtualGarden> getVirtualGardenByUserId(int userId) {
        List<VirtualGarden> virtualGardenPlants = new ArrayList<>();
        String sql = "SELECT * FROM virtual_gardens WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                virtualGardenPlants.add(mapRowToVirtualGarden(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return virtualGardenPlants;
    }

    @Override
    public boolean addPlantToVirtualGarden(VirtualGarden virtualGarden) {
       boolean gardenAdded = false;
       List<VirtualGarden> usersGarden = getVirtualGardenByUserId(virtualGarden.getUserId());
        for (VirtualGarden garden : usersGarden) {
            if (garden.getPlantId() == virtualGarden.getPlantId()) throw new DaoException("Duplicate plant");
        }
       String sql = "INSERT INTO virtual_gardens (user_id, plant_id) VALUES (?, ?)";
       try{
         int rowsAdded = jdbcTemplate.update(sql, virtualGarden.getUserId(), virtualGarden.getPlantId());
         gardenAdded = rowsAdded > 0;
       } catch (CannotGetJdbcConnectionException e) {
           throw new DaoException("Unable to connect to server or database", e);
       } catch (DataIntegrityViolationException e) {;
           throw new DaoException("Data integrity violation", e);
       }
       return gardenAdded;
    }

    @Override
    public boolean deletePlantFromVirtualGarden(VirtualGarden virtualGarden){
        boolean plantDeleted = false;
        String sql = "DELETE FROM virtual_gardens WHERE user_id = ? AND plant_id = ?";
        try{
            int rowsAffected = jdbcTemplate.update(sql, virtualGarden.getUserId(), virtualGarden.getPlantId());
            plantDeleted = rowsAffected > 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return plantDeleted;
    }


    private VirtualGarden mapRowToVirtualGarden(SqlRowSet results){
        VirtualGarden virtualGarden = new VirtualGarden();
        virtualGarden.setUserId(results.getInt("user_id"));
        virtualGarden.setPlantId(results.getInt("plant_id"));
        return virtualGarden;
    }
}
