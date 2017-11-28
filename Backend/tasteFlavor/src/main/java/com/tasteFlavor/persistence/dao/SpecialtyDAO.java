/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tasteFlavor.persistence.dao;

import com.tasteFlavor.persistence.dto.Specialty;
import exception.DAOException;

/**
 *
 * @author Daniel
 */
public interface SpecialtyDAO {
    public Specialty getById(int idSpecialty) throws DAOException;
}
