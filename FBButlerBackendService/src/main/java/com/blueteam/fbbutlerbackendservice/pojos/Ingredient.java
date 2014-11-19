package com.blueteam.fbbutlerbackendservice.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */
@Entity
@Table(name = "Ingredients")
@XmlRootElement
public class Ingredient implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 2000)
    @Column(name = "name")
    private String name;
    
    @Column(name = "in_stock")
    private Boolean inStock;
    
    @JoinColumn(name = "restaurant", 
            referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;
    
    @ManyToMany(mappedBy = "ingredientsList", 
            cascade={CascadeType.PERSIST, CascadeType.MERGE}, 
            targetEntity = MenuItem.class, 
            fetch = FetchType.EAGER)
    private List<MenuItem> menuItemsList;

    public Ingredient() {
        menuItemsList = new ArrayList<MenuItem>();
    }

    public Ingredient(Integer id) {
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

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    public void addMenuItem(MenuItem menuItem) {
        if (!getMenuItems().contains(menuItem))
        {
            getMenuItems().add(menuItem);
        }
        if (!menuItem.getIngredients().contains(this))
        {
            menuItem.getIngredients().add(this);
        }
    }
    
    @XmlTransient
    public List<MenuItem> getMenuItems() {
        return menuItemsList;
    }
    
    public void setMenuItem(List<MenuItem> menuItems) {
        this.menuItemsList = menuItems;
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
        if (!(object instanceof Ingredient)) {
            return false;
        }
        Ingredient other = (Ingredient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.blueteam.fbbutlerbackendservice.pojos.Ingredients[ id=" + id + " ]";
    }
    
}
