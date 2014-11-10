package com.blueteam.fbbutlerbackendservice.pojos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */
@Entity
@Table(name = "Restaurants")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Restaurants.findAll", query = "SELECT r FROM Restaurants r"),
    @NamedQuery(name = "Restaurants.findById", query = "SELECT r FROM Restaurants r WHERE r.id = :id"),
    @NamedQuery(name = "Restaurants.findByRestaurantName", query = "SELECT r FROM Restaurants r WHERE r.restaurantName = :restaurantName"),
    @NamedQuery(name = "Restaurants.findByButtonImage", query = "SELECT r FROM Restaurants r WHERE r.buttonImage = :buttonImage"),
    @NamedQuery(name = "Restaurants.findByAdvertisingImage", query = "SELECT r FROM Restaurants r WHERE r.advertisingImage = :advertisingImage"),
    @NamedQuery(name = "Restaurants.findByDescription", query = "SELECT r FROM Restaurants r WHERE r.description = :description")})
public class Restaurants implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 500)
    @Column(name = "restaurant_name")
    private String restaurantName;
    @Size(max = 500)
    @Column(name = "Button_Image")
    private String buttonImage;
    @Size(max = 500)
    @Column(name = "Advertising_Image")
    private String advertisingImage;
    @Size(max = 1000)
    @Column(name = "Description")
    private String description;
    @OneToMany(mappedBy = "restaurant")
    private Collection<Ingredients> ingredientsCollection;
    @JoinColumn(name = "hotel", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Hotels hotel;
    @OneToMany(mappedBy = "restaurant")
    private Collection<RestaurantPictures> restaurantPicturesCollection;
    @OneToMany(mappedBy = "restaurant")
    private Collection<Categories> categoriesCollection;

    public Restaurants() {
    }

    public Restaurants(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getButtonImage() {
        return buttonImage;
    }

    public void setButtonImage(String buttonImage) {
        this.buttonImage = buttonImage;
    }

    public String getAdvertisingImage() {
        return advertisingImage;
    }

    public void setAdvertisingImage(String advertisingImage) {
        this.advertisingImage = advertisingImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Ingredients> getIngredientsCollection() {
        return ingredientsCollection;
    }

    public void setIngredientsCollection(Collection<Ingredients> ingredientsCollection) {
        this.ingredientsCollection = ingredientsCollection;
    }

    public Hotels getHotel() {
        return hotel;
    }

    public void setHotel(Hotels hotel) {
        this.hotel = hotel;
    }

    @XmlTransient
    public Collection<RestaurantPictures> getRestaurantPicturesCollection() {
        return restaurantPicturesCollection;
    }

    public void setRestaurantPicturesCollection(Collection<RestaurantPictures> restaurantPicturesCollection) {
        this.restaurantPicturesCollection = restaurantPicturesCollection;
    }

    @XmlTransient
    public Collection<Categories> getCategoriesCollection() {
        return categoriesCollection;
    }

    public void setCategoriesCollection(Collection<Categories> categoriesCollection) {
        this.categoriesCollection = categoriesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Restaurants)) {
            return false;
        }
        Restaurants other = (Restaurants) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.blueteam.fbbutlerbackendservice.resources.Restaurants[ id=" + id + " ]";
    }
    
}
