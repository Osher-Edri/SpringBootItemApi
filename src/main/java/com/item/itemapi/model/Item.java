package com.item.itemapi.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    private Long itemNo;
    private String name;
    private Integer amount;
    private String inventoryCode;
    //CHECK: need to be Long but it's get error java.lang.NumberFormatException: For input string: "00FF123"
    


    public Item() {
    	
    }
    
    public Item(Long itemNo, String name, Integer amount, String inventoryCode) {
    	this.itemNo = itemNo;
    	this.name = name;
    	this.amount = amount;
    	this.inventoryCode = inventoryCode;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name = "item_no", nullable = false)
    public Long getItemno() {
        return itemNo;
    }

//    public Item itemno(Long itemno) {
//        this.itemNo = itemno;
//        return this;
//    }

    public void setItemno(Long itemno) {
        this.itemNo = itemno;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

 

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "amount", nullable = false)
    public Integer getAmount() {
        return amount;
    }

//    public Item amount(Integer amount) {
//        this.amount = amount;
//        return this;
//    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    @Column(name = "inventory_code", nullable = false)
    public String getInventoryCode() {
        return inventoryCode;
    }

//    public Item code(String code) {
//        this.inventoryCode = code;
//        return this;
//    }

    public void setInventoryCode(String code) {
        this.inventoryCode = code;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Item)) {
//            return false;
//        }
//        return id != null && id.equals(((Item) o).id);
//    }

//    @Override
//    public int hashCode() {
//        return 31;
//    }

    @Override
    public String toString() {
        return 
            "id=" + getId() +
            ", itemno=" + getItemno() +
            ", name='" + getName() + "'" +
            ", amount=" + getAmount() +
            ", code='" + getInventoryCode() + "'" ;
    }
}

