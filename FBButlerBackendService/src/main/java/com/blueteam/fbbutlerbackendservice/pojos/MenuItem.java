package com.blueteam.fbbutlerbackendservice.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */
@Entity
@Table(name = "MenuItems")
@XmlRootElement
public class MenuItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 500)
    @Column(name = "name")
    private String name;
    
    @Size(max = 2000)
    @Column(name = "description")
    private String description;
    
    @Size(max = 500)
    @Column(name = "picture_file")
    private String pictureFile;
    
    @Column(name = "price")
    private BigDecimal price;
    
    @Size(max = 500)
    @Column(name = "review_image_location")
    private String reviewImageLocation;
    
    @JoinColumn(name = "category", referencedColumnName = "id")
    @ManyToOne
    private Category category;
    
    @ManyToMany(mappedBy = "menuItemsList")
    private List<Ingredient> ingedientsList;
    

    public MenuItem() {
    }

    public MenuItem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String pictureFile) {
        this.pictureFile = pictureFile;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getReviewImageLocation() {
        return reviewImageLocation;
    }

    public void setReviewImageLocation(String reviewImageLocation) {
        this.reviewImageLocation = reviewImageLocation;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addIngredient(Ingredient ingredient) {
        if (!getIngredients().contains(ingredient))
        {
            getIngredients().add(ingredient);
        }
        if(!ingredient.getMenuItems().contains(this))
        {
            ingredient.getMenuItems().add(this);
        }
    }
    
    public List<Ingredient> getIngredients() {
        return this.ingedientsList;
    }
    
    public void setIngredient(List<Ingredient> ingredients) {
        this.ingedientsList = ingredients;
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
        if (!(object instanceof MenuItem)) {
            return false;
        }
        MenuItem other = (MenuItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.blueteam.fbbutlerbackendservice.pojos.MenuItems[ id=" + id + " ]";
    }
    
}
