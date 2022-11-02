package model;

/**
 * Class of Film object model
 * 
 * @author Daniel Hayes
 *
 */
public class Film {
	
	private int id;
	private String title;
	private int year;
	private String director;
	private String stars;
	private String review;
	
	/**
	 * Creates a film object with film details
	 * 
	 * @param id				ID of film
	 * @param title				Title of film
	 * @param year				Year film was released
	 * @param director			Director of film
	 * @param stars				Main stars of the film
	 * @param review			Review of the film
	 */
	public Film(int id, String title, int year, String director, String stars,
			String review) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.stars = stars;
		this.review = review;
	}
	
	/**
	 * Creates an empty film object
	 */
	public Film() {
		super();
	}
	
	/**
	 * Gets film ID
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets film id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets title of film
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets film title
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Get year of film release
	 * 
	 * @return int
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Set year if film
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * Get director of film
	 * 
	 * @return String
	 */
	public String getDirector() {
		return director;
	}
	
	/**
	 * Set director of film
	 * 
	 * @param director
	 */
	public void setDirector(String director) {
		this.director = director;
	}
	
	/**
	 * Get main actors of the film
	 * 
	 * @return String
	 */
	public String getStars() {
		return stars;
	}
	
	/**
	 * Set main actors of film
	 * 
	 * @param stars
	 */
	public void setStars(String stars) {
		this.stars = stars;
	}
	
	/**
	 * Get film review
	 * 
	 * @return String
	 */
	public String getReview() {
		return review;
	}
	
	/**
	 * Set film review
	 * 
	 * @param review
	 */
	public void setReview(String review) {
		this.review = review;
	}
	
	/**
	 * Overridden toString method
	 * 
	 * @return String				Array format of film details
	 */
	@Override
	public String toString() {
		return "Film [id=" + id + ", title=" + title + ", year=" + year
				+ ", director=" + director + ", stars=" + stars + ", review="
				+ review + "]";
	}   
}
