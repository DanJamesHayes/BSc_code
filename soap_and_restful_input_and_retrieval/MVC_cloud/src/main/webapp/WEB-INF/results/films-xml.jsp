<%@ page import ="javax.xml.bind.JAXBContext" %>
<%@ page import ="javax.xml.bind.JAXBException" %>
<%@ page import ="javax.xml.bind.Marshaller" %>
<%@ page import ="java.util.List" %>
<%@ page import ="model.FilmList" %>
<%@ page import ="model.Film" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
// Use JAXB and Marshaller to parse films into XML format
List<Film> films = (List<Film>) request.getAttribute("films");
FilmList fl = new FilmList();
fl.setFilmList(films);
try {
    JAXBContext context = JAXBContext.newInstance(FilmList.class);
	Marshaller m = context.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    m.marshal(fl, out);
} catch (JAXBException e) {
	e.printStackTrace();
}
%>