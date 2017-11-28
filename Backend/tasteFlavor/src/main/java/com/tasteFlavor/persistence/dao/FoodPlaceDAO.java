/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tasteFlavor.persistence.dao;

import com.tasteFlavor.persistence.dto.FoodPlace;
import exception.DAOException;
import java.util.ArrayList;



/**
 *
 * @author Daniel
 */
public interface FoodPlaceDAO {

    public ArrayList<FoodPlace> getAllFoodPlace() throws DAOException;
}
