/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tasteFlavor.persistence.dao;

import exception.DAOException;

/**
 *
 * @author Daniel
 */
public interface EntidadDAO<T> {

    public T save(T entidad) throws DAOException;

    public void delete(T entidad) throws DAOException;
}
