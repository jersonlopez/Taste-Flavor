package com.tasteFlavor.persistence.dao.impl;

import com.tasteFlavor.persistence.dao.SpecialtyDAO;
import com.tasteFlavor.persistence.dto.Specialty;
import exception.DAOException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author Daniel
 */
public class SpecialtyDAOImpl implements SpecialtyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Specialty getById(int idSpecialty) throws DAOException {
        Specialty specialty = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            specialty=(Specialty) session.get(Specialty.class, idSpecialty);
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
        return specialty;
    }

}
