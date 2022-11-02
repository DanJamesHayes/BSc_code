package core;

public class FilmDAOProxy implements core.FilmDAO {
  private String _endpoint = null;
  private core.FilmDAO filmDAO = null;
  
  public FilmDAOProxy() {
    _initFilmDAOProxy();
  }
  
  public FilmDAOProxy(String endpoint) {
    _endpoint = endpoint;
    _initFilmDAOProxy();
  }
  
  private void _initFilmDAOProxy() {
    try {
      filmDAO = (new core.FilmDAOServiceLocator()).getFilmDAO();
      if (filmDAO != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)filmDAO)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)filmDAO)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (filmDAO != null)
      ((javax.xml.rpc.Stub)filmDAO)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public core.FilmDAO getFilmDAO() {
    if (filmDAO == null)
      _initFilmDAOProxy();
    return filmDAO;
  }
  
  public core.Film[] getFilm(java.lang.String query) throws java.rmi.RemoteException{
    if (filmDAO == null)
      _initFilmDAOProxy();
    return filmDAO.getFilm(query);
  }
  
  public boolean insertFilm(core.Film f) throws java.rmi.RemoteException{
    if (filmDAO == null)
      _initFilmDAOProxy();
    return filmDAO.insertFilm(f);
  }
  
  public core.Film[] getFilmByID(int id) throws java.rmi.RemoteException{
    if (filmDAO == null)
      _initFilmDAOProxy();
    return filmDAO.getFilmByID(id);
  }
  
  public boolean deleteFilm(java.lang.String id) throws java.rmi.RemoteException{
    if (filmDAO == null)
      _initFilmDAOProxy();
    return filmDAO.deleteFilm(id);
  }
  
  public core.Film[] getAllFilms() throws java.rmi.RemoteException{
    if (filmDAO == null)
      _initFilmDAOProxy();
    return filmDAO.getAllFilms();
  }
  
  public boolean updateFilm(core.Film f) throws java.rmi.RemoteException{
    if (filmDAO == null)
      _initFilmDAOProxy();
    return filmDAO.updateFilm(f);
  }
  
  
}