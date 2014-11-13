package com.blueteam.fbbutlerbackendservice.pojos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Restaurant implements Serializable {
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
    private List<Ingredient> ingredientsList;
    
    @JoinColumn(name = "hotel", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Hotel hotel;
    
    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantPicture> restaurantPicturesList;
    
    @OneToMany(mappedBy = "restaurant")
    private List<Category> categoriesList;

    public Restaurant() {
    }

    public Restaurant(Integer id) {
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
    public List<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @XmlTransient
    public List<RestaurantPicture> getRestaurantPicturesList() {
        return restaurantPicturesList;
    }

    public void setRestaurantPicturesList(List<RestaurantPicture> restaurantPicturesList) {
        this.restaurantPicturesList = restaurantPicturesList;
    }

    @XmlTransient
    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
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
        if (!(object instanceof Restaurant)) {
            return false;
        }
        Restaurant other = (Restaurant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.blueteam.fbbutlerbackendservice.pojos.Restaurants[ id=" + id + " ]";
    }
    
}
