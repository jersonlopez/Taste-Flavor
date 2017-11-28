/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tasteFlavor.persistence.dao;

import com.tasteFlavor.persistence.dto.Customer;
import com.tasteFlavor.persistence.dto.FoodPlace;
import com.tasteFlavor.persistence.dto.NextToVisit;
import exception.DAOException;

/**
 *
 * @author Daniel
 */
public interface NextToVisitDAO {
    public NextToVisit save(NextToVisit nextToVisit) throws DAOException;
    public void delete(NextToVisit nextToVisit) throws DAOException;
    public void markAsVisited(NextToVisit nextToVisit) throws DAOException;
}
