/**
 * FilmDAO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package core;

public interface FilmDAO extends java.rmi.Remote {
    public core.Film[] getFilm(java.lang.String query) throws java.rmi.RemoteException;
    public boolean insertFilm(core.Film f) throws java.rmi.RemoteException;
    public core.Film[] getFilmByID(int id) throws java.rmi.RemoteException;
    public boolean deleteFilm(java.lang.String id) throws java.rmi.RemoteException;
    public core.Film[] getAllFilms() throws java.rmi.RemoteException;
    public boolean updateFilm(core.Film f) throws java.rmi.RemoteException;
}
