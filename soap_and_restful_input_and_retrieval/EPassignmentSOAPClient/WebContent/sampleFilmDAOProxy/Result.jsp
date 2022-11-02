<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleFilmDAOProxyid" scope="session" class="core.FilmDAOProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
sampleFilmDAOProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = sampleFilmDAOProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
}
break;
case 5:
        gotMethod = true;
        String endpoint_0id=  request.getParameter("endpoint8");
            java.lang.String endpoint_0idTemp = null;
        if(!endpoint_0id.equals("")){
         endpoint_0idTemp  = endpoint_0id;
        }
        sampleFilmDAOProxyid.setEndpoint(endpoint_0idTemp);
break;
case 10:
        gotMethod = true;
        core.FilmDAO getFilmDAO10mtemp = sampleFilmDAOProxyid.getFilmDAO();
if(getFilmDAO10mtemp == null){
%>
<%=getFilmDAO10mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
</TABLE>
<%
}
break;
case 15:
        gotMethod = true;
        String query_1id=  request.getParameter("query18");
            java.lang.String query_1idTemp = null;
        if(!query_1id.equals("")){
         query_1idTemp  = query_1id;
        }
        core.Film[] getFilm15mtemp = sampleFilmDAOProxyid.getFilm(query_1idTemp);
if(getFilm15mtemp == null){
%>
<%=getFilm15mtemp %>
<%
}else{
        String tempreturnp16 = null;
        if(getFilm15mtemp != null){
        java.util.List listreturnp16= java.util.Arrays.asList(getFilm15mtemp);
        tempreturnp16 = listreturnp16.toString();
        }
        %>
        <%=tempreturnp16%>
        <%
}
break;
case 20:
        gotMethod = true;
        String stars_3id=  request.getParameter("stars25");
            java.lang.String stars_3idTemp = null;
        if(!stars_3id.equals("")){
         stars_3idTemp  = stars_3id;
        }
        String review_4id=  request.getParameter("review27");
            java.lang.String review_4idTemp = null;
        if(!review_4id.equals("")){
         review_4idTemp  = review_4id;
        }
        String director_5id=  request.getParameter("director29");
            java.lang.String director_5idTemp = null;
        if(!director_5id.equals("")){
         director_5idTemp  = director_5id;
        }
        String year_6id=  request.getParameter("year31");
        int year_6idTemp  = Integer.parseInt(year_6id);
        String title_7id=  request.getParameter("title33");
            java.lang.String title_7idTemp = null;
        if(!title_7id.equals("")){
         title_7idTemp  = title_7id;
        }
        String id_8id=  request.getParameter("id35");
        int id_8idTemp  = Integer.parseInt(id_8id);
        %>
        <jsp:useBean id="core1Film_2id" scope="session" class="core.Film" />
        <%
        core1Film_2id.setStars(stars_3idTemp);
        core1Film_2id.setReview(review_4idTemp);
        core1Film_2id.setDirector(director_5idTemp);
        core1Film_2id.setYear(year_6idTemp);
        core1Film_2id.setTitle(title_7idTemp);
        core1Film_2id.setId(id_8idTemp);
        boolean insertFilm20mtemp = sampleFilmDAOProxyid.insertFilm(core1Film_2id);
        String tempResultreturnp21 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(insertFilm20mtemp));
        %>
        <%= tempResultreturnp21 %>
        <%
break;
case 37:
        gotMethod = true;
        String id_9id=  request.getParameter("id40");
        int id_9idTemp  = Integer.parseInt(id_9id);
        core.Film[] getFilmByID37mtemp = sampleFilmDAOProxyid.getFilmByID(id_9idTemp);
if(getFilmByID37mtemp == null){
%>
<%=getFilmByID37mtemp %>
<%
}else{
        String tempreturnp38 = null;
        if(getFilmByID37mtemp != null){
        java.util.List listreturnp38= java.util.Arrays.asList(getFilmByID37mtemp);
        tempreturnp38 = listreturnp38.toString();
        }
        %>
        <%=tempreturnp38%>
        <%
}
break;
case 42:
        gotMethod = true;
        String id_10id=  request.getParameter("id45");
            java.lang.String id_10idTemp = null;
        if(!id_10id.equals("")){
         id_10idTemp  = id_10id;
        }
        boolean deleteFilm42mtemp = sampleFilmDAOProxyid.deleteFilm(id_10idTemp);
        String tempResultreturnp43 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(deleteFilm42mtemp));
        %>
        <%= tempResultreturnp43 %>
        <%
break;
case 47:
        gotMethod = true;
        core.Film[] getAllFilms47mtemp = sampleFilmDAOProxyid.getAllFilms();
if(getAllFilms47mtemp == null){
%>
<%=getAllFilms47mtemp %>
<%
}else{
        String tempreturnp48 = null;
        if(getAllFilms47mtemp != null){
        java.util.List listreturnp48= java.util.Arrays.asList(getAllFilms47mtemp);
        tempreturnp48 = listreturnp48.toString();
        }
        %>
        <%=tempreturnp48%>
        <%
}
break;
case 50:
        gotMethod = true;
        String stars_12id=  request.getParameter("stars55");
            java.lang.String stars_12idTemp = null;
        if(!stars_12id.equals("")){
         stars_12idTemp  = stars_12id;
        }
        String review_13id=  request.getParameter("review57");
            java.lang.String review_13idTemp = null;
        if(!review_13id.equals("")){
         review_13idTemp  = review_13id;
        }
        String director_14id=  request.getParameter("director59");
            java.lang.String director_14idTemp = null;
        if(!director_14id.equals("")){
         director_14idTemp  = director_14id;
        }
        String year_15id=  request.getParameter("year61");
        int year_15idTemp  = Integer.parseInt(year_15id);
        String title_16id=  request.getParameter("title63");
            java.lang.String title_16idTemp = null;
        if(!title_16id.equals("")){
         title_16idTemp  = title_16id;
        }
        String id_17id=  request.getParameter("id65");
        int id_17idTemp  = Integer.parseInt(id_17id);
        %>
        <jsp:useBean id="core1Film_11id" scope="session" class="core.Film" />
        <%
        core1Film_11id.setStars(stars_12idTemp);
        core1Film_11id.setReview(review_13idTemp);
        core1Film_11id.setDirector(director_14idTemp);
        core1Film_11id.setYear(year_15idTemp);
        core1Film_11id.setTitle(title_16idTemp);
        core1Film_11id.setId(id_17idTemp);
        boolean updateFilm50mtemp = sampleFilmDAOProxyid.updateFilm(core1Film_11id);
        String tempResultreturnp51 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(updateFilm50mtemp));
        %>
        <%= tempResultreturnp51 %>
        <%
break;
}
} catch (Exception e) { 
%>
Exception: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.toString()) %>
Message: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.getMessage()) %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>