/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tasteFlavor.persistence.dao.impl;

import com.tasteFlavor.persistence.dao.CalificationPerUserDAO;
import exception.DAOException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
@Transactional
public class CalificationPerUserDAOImpl implements CalificationPerUserDAO{
    @Autowired
    private SessionFactory sessionFactory;    
    
    public void calificate(int idPlace, int idCustomer, int calificationUser) throws DAOException{
        
        
        Session session=null;
        try {
            session=sessionFactory.openSession();
            Query query=session.createQuery("INSERT INTO calification_per_user "
                    + "(foodPlace, customer, calification)"
                    + "VALUES (idPlace, idCustomer, calificationUser)" );
                     
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
    }
    
}


