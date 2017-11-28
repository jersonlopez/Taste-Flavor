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
			Query query=session.createQuery("select * from food_place");
                        
             
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
    
    public FoodPlace seeDetail(Integer idPlace) throws DAOException{
        FoodPlace foodPlace;
        foodPlace = new FoodPlace();
        Session session=null;
            try {
		session=sessionFactory.openSession();
		Query query=session.createQuery("select id,name,"
                        + "address, phone, schedule, description,"
                        + "urlPage, placeType from food_place where id=idPlace");
                     
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
        
        return foodPlace;
    }
    
}
