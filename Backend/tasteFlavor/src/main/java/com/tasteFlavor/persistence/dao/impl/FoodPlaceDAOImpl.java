/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tasteFlavor.persistence.dao.impl;

import com.tasteFlavor.persistence.dao.EntidadDAO;
import com.tasteFlavor.persistence.dao.FoodPlaceDAO;
import com.tasteFlavor.persistence.dto.FoodPlace;
import exception.DAOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
@Transactional
public class FoodPlaceDAOImpl implements FoodPlaceDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    @Qualifier("entidadDao")
    private EntidadDAO<FoodPlace> entidadDao;

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<FoodPlace> getAllFoodPlace() throws DAOException {
        List<FoodPlace> foodPlaces=new ArrayList<>();
		Session session=null;
		try {
			session=sessionFactory.openSession();
			TypedQuery<FoodPlace> query=(TypedQuery<FoodPlace>)session.createQuery("from food_place");
                        foodPlaces= query.getResultList();      
                        
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
		return (ArrayList<FoodPlace>) foodPlaces;
        
    }

    @Override
    public FoodPlace getById(int idPlace) throws DAOException {
        FoodPlace place = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            place = (FoodPlace) session.get(FoodPlace.class, idPlace);
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
        return place;
    }
    
    @Override
    public ArrayList<FoodPlace> getByCategory(String category ) throws DAOException {
        List<FoodPlace> foodPlaces=new ArrayList<>();
        FoodPlace place = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            //place = (FoodPlace) session.get(FoodPlace.class, category);
            String consulta = "select * from food_place where placeType=" + category + " or name=" 
                    + category + " or address=" + category + " or phone=" + category;
            TypedQuery<FoodPlace> query=(TypedQuery<FoodPlace>)session.createQuery(consulta);
            foodPlaces= query.getResultList(); 
          
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
        return (ArrayList<FoodPlace>) foodPlaces;
    }
}
