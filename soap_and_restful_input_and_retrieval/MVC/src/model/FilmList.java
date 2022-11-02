package model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * Class to create list of films in xml format
 * 
 * @author Daniel Hayes
 *
 */
@XmlRootElement(name = "filmList")
@XmlAccessorType(XmlAccessType.FIELD)
public class FilmList {
	
    @XmlElement(name = "film")
    private List<Film> films;

    /**
     * Set films attribute with passed list of films
     * 
     * @param films					List of films to be parsed to XML
     */
    public void setFilmList(List<Film> films) {
        this.films = films;
    }
    
    /**
     * Public constructor to instantiate FilmList object
     */
	public FilmList() {
		super();
	}    
}