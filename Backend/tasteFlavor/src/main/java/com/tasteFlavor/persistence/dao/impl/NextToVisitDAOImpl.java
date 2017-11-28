/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tasteFlavor.persistence.dao.impl;

import com.tasteFlavor.persistence.dao.EntidadDAO;
import com.tasteFlavor.persistence.dao.NextToVisitDAO;
import com.tasteFlavor.persistence.dto.Customer;
import com.tasteFlavor.persistence.dto.FoodPlace;
import com.tasteFlavor.persistence.dto.NextToVisit;
import exception.DAOException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
@Transactional
public class NextToVisitDAOImpl implements NextToVisitDAO {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    @Qualifier("entidadDao")
    private EntidadDAO<NextToVisit> entidadDao;

    @Override
    public NextToVisit save(NextToVisit nextToVisit) throws DAOException {
        return entidadDao.save(nextToVisit);
    }

    @Override
    public void delete(NextToVisit nextToVisit) throws DAOException {
        entidadDao.delete(nextToVisit);
    }
    

}
