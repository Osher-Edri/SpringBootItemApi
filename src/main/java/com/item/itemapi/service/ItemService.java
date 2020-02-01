package com.item.itemapi.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import com.item.itemapi.model.Item;
import com.item.itemapi.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> getAllItems(){
		List<Item> items = (List<Item>)itemRepository.findAll(); 
		return items;
	}
	
	public ResponseEntity<List<Item>> getItemByItemNo(Long itemNo) throws URISyntaxException{
		List<Item> it =  itemRepository.findByItemNo(itemNo);
		if(it.isEmpty()) {
			throw new URISyntaxException("The itemNo NOT found ", "Item");
		}
		return ResponseEntity.ok().body(itemRepository.findByItemNo(itemNo));
	}	
	
	public ResponseEntity<Item> createItem(Item item) throws URISyntaxException {	
		Long currentItemNo = item.getItemno();
		
		if(!itemRepository.findByItemNo(currentItemNo).isEmpty()) {
			ArrayList<Item>  ans =  (ArrayList<Item>) getItemByItemNo(currentItemNo).getBody();
			Integer newItemAmount = item.getAmount();
			Integer oldItemAmount = ans.get(0).getAmount();
			String newItemName = item.getName();
			String oldItemName = ans.get(0).getName();
			String oldInventoryCode = ans.get(0).getInventoryCode();
			Long oldItemId =  ans.get(0).getId();
			//if old item name equals to new item name update the item amount
			if(newItemName.equals(oldItemName)) {
				item.setId(oldItemId);
				item.setAmount(oldItemAmount + newItemAmount);
				item.setInventoryCode(oldInventoryCode);
				item.setItemno(currentItemNo);
				item.setName(oldItemName);
			}else {
				throw new URISyntaxException("This item.no already exist ", "Item");
			}
		}
		
		Item result = itemRepository.save(item);
		return ResponseEntity.created(new URI("/items/" + result.getItemno())).body(result);
	}

	//Need to sent all item details except id to change the item. (update by id)
	public ResponseEntity<Item> updateItem( Long id,Item itemDetails) throws URISyntaxException {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ResourceAccessException("item not found for this itemNo : " + id));
		System.out.println("item: "+item);
		item.setAmount(itemDetails.getAmount());
        item.setInventoryCode(itemDetails.getInventoryCode());
        item.setItemno(itemDetails.getItemno());
        item.setName(itemDetails.getName());
        Item result = itemRepository.save(item);
        return ResponseEntity.ok().body(result);
    } 
	
	public Map<String, Boolean> deleteItem(Long id){
		 itemRepository.findById(id).orElseThrow(() -> new ResourceAccessException("item not found for this ID : " + id));
		itemRepository.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
}
