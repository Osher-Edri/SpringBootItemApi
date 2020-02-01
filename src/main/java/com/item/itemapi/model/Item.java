package com.item.itemapi.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "All details about the Item. ")
@Entity
@Table(name = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(notes = "The database generated item id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private Long itemNo;
    private String name;
    private Integer amount;
    private String inventoryCode;    

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
    
    @ApiModelProperty(notes = "The item itemNo")
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
    
    @ApiModelProperty(notes = "The item name")
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty(notes = "The item amount")
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
    
    @ApiModelProperty(notes = "The item inventory code")
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

