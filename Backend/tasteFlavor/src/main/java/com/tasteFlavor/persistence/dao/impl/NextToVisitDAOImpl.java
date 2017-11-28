/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tasteFlavor.persistence.dao.impl;

import com.tasteFlavor.persistence.dao.EntidadDAO;
import com.tasteFlavor.persistence.dao.NextToVisitDAO;
import com.tasteFlavor.persistence.dto.FoodPlace;
import com.tasteFlavor.persistence.dto.Customer;
import com.tasteFlavor.persistence.dto.FoodPlace;
import com.tasteFlavor.persistence.dto.NextToVisit;
import exception.DAOException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
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
public class NextToVisitDAOImpl implements NextToVisitDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    @Qualifier("entidadDao")
    private EntidadDAO<NextToVisit> entidadDao;

    @SuppressWarnings("unchecked")
    @Override
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

    @Override
    public NextToVisit save(NextToVisit nextToVisit) throws DAOException {
        return entidadDao.save(nextToVisit);
    }

    @Override
    public void delete(NextToVisit nextToVisit) throws DAOException {
        entidadDao.delete(nextToVisit);
    }

    @Override
    public void markAsVisited(NextToVisit nextToVisit) throws DAOException {
		Session session=null;
		try {
                        
			session=sessionFactory.openSession();
			Query query=session.createQuery("update next_to_visit set state=visited where foodPlace =: foodPlace and customer=: customer");
                        query.setParameter("foodPlace", nextToVisit.getFoodPlace());
                        query.setParameter("customer", nextToVisit.getCustomer());
                        
                        
                        
             
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
