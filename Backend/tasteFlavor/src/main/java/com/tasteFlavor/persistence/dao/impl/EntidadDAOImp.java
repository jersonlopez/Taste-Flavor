/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tasteFlavor.persistence.dao.impl;

import com.tasteFlavor.persistence.dao.EntidadDAO;
import exception.DAOException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Daniel
 */
public class EntidadDAOImp implements EntidadDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Object save(Object entidad) throws DAOException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(entidad);
            tx.commit();
        } catch (HibernateException e) {
            throw new DAOException(e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    throw new DAOException(e);
                }
            }
        }
        return entidad;
    }

    @Override
    public void delete(Object entidad) throws DAOException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(entidad);
            tx.commit();
        } catch (HibernateException e) {
            throw new DAOException(e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    throw new DAOException(e);
                }
            }
        }
    }
}
