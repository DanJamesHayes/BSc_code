/**
 * Film.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package core;

public class Film  implements java.io.Serializable {
    private java.lang.String director;

    private int id;

    private java.lang.String review;

    private java.lang.String stars;

    private java.lang.String title;

    private int year;

    public Film() {
    }

    public Film(
           java.lang.String director,
           int id,
           java.lang.String review,
           java.lang.String stars,
           java.lang.String title,
           int year) {
           this.director = director;
           this.id = id;
           this.review = review;
           this.stars = stars;
           this.title = title;
           this.year = year;
    }


    /**
     * Gets the director value for this Film.
     * 
     * @return director
     */
    public java.lang.String getDirector() {
        return director;
    }


    /**
     * Sets the director value for this Film.
     * 
     * @param director
     */
    public void setDirector(java.lang.String director) {
        this.director = director;
    }


    /**
     * Gets the id value for this Film.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this Film.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the review value for this Film.
     * 
     * @return review
     */
    public java.lang.String getReview() {
        return review;
    }


    /**
     * Sets the review value for this Film.
     * 
     * @param review
     */
    public void setReview(java.lang.String review) {
        this.review = review;
    }


    /**
     * Gets the stars value for this Film.
     * 
     * @return stars
     */
    public java.lang.String getStars() {
        return stars;
    }


    /**
     * Sets the stars value for this Film.
     * 
     * @param stars
     */
    public void setStars(java.lang.String stars) {
        this.stars = stars;
    }


    /**
     * Gets the title value for this Film.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Film.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the year value for this Film.
     * 
     * @return year
     */
    public int getYear() {
        return year;
    }


    /**
     * Sets the year value for this Film.
     * 
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Film)) return false;
        Film other = (Film) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.director==null && other.getDirector()==null) || 
             (this.director!=null &&
              this.director.equals(other.getDirector()))) &&
            this.id == other.getId() &&
            ((this.review==null && other.getReview()==null) || 
             (this.review!=null &&
              this.review.equals(other.getReview()))) &&
            ((this.stars==null && other.getStars()==null) || 
             (this.stars!=null &&
              this.stars.equals(other.getStars()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            this.year == other.getYear();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDirector() != null) {
            _hashCode += getDirector().hashCode();
        }
        _hashCode += getId();
        if (getReview() != null) {
            _hashCode += getReview().hashCode();
        }
        if (getStars() != null) {
            _hashCode += getStars().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        _hashCode += getYear();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Film.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://core", "Film"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("director");
        elemField.setXmlName(new javax.xml.namespace.QName("http://core", "director"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://core", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("review");
        elemField.setXmlName(new javax.xml.namespace.QName("http://core", "review"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stars");
        elemField.setXmlName(new javax.xml.namespace.QName("http://core", "stars"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://core", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("http://core", "year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
