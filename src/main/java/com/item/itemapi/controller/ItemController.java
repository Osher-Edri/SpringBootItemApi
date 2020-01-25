package com.item.itemapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.item.itemapi.model.Item;
import com.item.itemapi.repository.ItemRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping("/items")
	public List<Item> getAllItems(){
		System.out.println();
		return (List<Item>) itemRepository.findAll();
	}
	
	//Find by item no
	@GetMapping("/items/{itemNo}")
	public ResponseEntity<List<Item>> getItemByItemNo(@PathVariable(value = "itemNo") Long itemNo) throws URISyntaxException{
		List<Item> it =  itemRepository.findByItemNo(itemNo);
		if(it.isEmpty()) {
			throw new URISyntaxException("The itemNo NOT found ", "Item");
		}
		return ResponseEntity.ok().body(itemRepository.findByItemNo(itemNo));
	}
	
	@PostMapping("/items")
	public ResponseEntity<Item> createItem(@RequestBody Item item) throws URISyntaxException {	
		Long currentItemNo = item.getItemno();
		ResponseEntity findItem =  getItemByItemNo(currentItemNo);
		Integer newItemAmount = item.getAmount();
		
		//if response not null the item found
		if(findItem != null) {
			ArrayList<Item>  ans =  (ArrayList<Item>) findItem.getBody();
			Integer oldItemAmount = ans.get(0).getAmount();
			String newItemName = item.getName();
			String oldItemName = ans.get(0).getName();
			String oldInventoryCode = ans.get(0).getInventoryCode();
			Long oldItemId =  ans.get(0).getId();
			//if old item name equals to new item name upsate the item amount
			if(newItemName.equals(oldItemName)) {
				item.setId(ans.get(0).getId());
				item.setAmount(oldItemAmount + newItemAmount);
				item.setInventoryCode(oldInventoryCode);
				item.setItemno(currentItemNo);
				item.setName(oldItemName);
			}else {
				throw new URISyntaxException("This item.no already exist ", "Item");
			}
		}
		if (item != null) {
			Item result = itemRepository.save(item);
			return ResponseEntity.created(new URI("/items/" + result.getItemno())).body(result);
		}
		throw new URISyntaxException("A new item cannot already have an ", "Item");
	}
	
	@PutMapping("/items/{itemNo}")
	public ResponseEntity<Item> updateItem(@PathVariable(value = "itemNo") Long itemNo, @Valid @RequestBody Item itemDetails) throws URISyntaxException {
		Item item = itemRepository.findById(itemNo)
				.orElseThrow(() -> new ResourceAccessException("item not found for this itemNo : " + itemNo));
		item.setAmount(itemDetails.getAmount());
        item.setInventoryCode(itemDetails.getInventoryCode());
        item.setItemno(itemDetails.getItemno());
        item.setName(itemDetails.getName());
        Item result = itemRepository.save(item);
        return ResponseEntity.ok().body(result);
    } 
	
	@DeleteMapping("/items/{id}")
	public Map<String, Boolean> deleteItem(@PathVariable(value = "id") Long id){
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ResourceAccessException("item not found for this ID : " + id));
		itemRepository.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
	

}
