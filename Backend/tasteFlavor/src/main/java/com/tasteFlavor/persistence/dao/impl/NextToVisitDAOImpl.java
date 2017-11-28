/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tasteFlavor.persistence.dao.impl;

import com.tasteFlavor.persistence.dao.EntidadDAO;
import com.tasteFlavor.persistence.dao.NextToVisitDAO;
import com.tasteFlavor.persistence.dto.FoodPlace;
import exception.DAOException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author Daniel
 */
public class NextToVisitDAOImpl implements NextToVisitDAO{
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    @Qualifier("entidadDao")
    private EntidadDAO<FoodPlace> entidadDao;

    @SuppressWarnings("unchecked")
    public ArrayList<FoodPlace> getAllFavorite(int idCustomer) throws DAOException {
        List<FoodPlace> foodPlacesFavorite=new ArrayList<>();
        Session session=null;
        try {
            session=sessionFactory.openSession();
            Query query=session.createQuery("select * from next_to_visit where customer = idCustomer");


        } catch (Exception e) {
            throw new DAOException(e);
        }finally {
            if(session!=null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    throw new DAOException(e);
                }
            }
        }
        return (ArrayList<FoodPlace>) foodPlacesFavorite;
        
    }
    
}
