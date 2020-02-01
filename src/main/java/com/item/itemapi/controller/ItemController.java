package com.item.itemapi.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.item.itemapi.model.Item;
import com.item.itemapi.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@CrossOrigin(origins = "http://localhost:8080")
@Api(value = "Item Management")
@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@ApiOperation(value = "Return a list of available items", response = List.class)
    @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved list"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@GetMapping("/items")
	public List<Item> getAllItems(){
		return itemService.getAllItems();
	}
	
	@ApiOperation(value = "Return item by itemNo")
	@GetMapping("/items/{itemNo}")
	public ResponseEntity<List<Item>> getItemByItemNo(@PathVariable(value = "itemNo") Long itemNo) throws URISyntaxException{
		return itemService.getItemByItemNo(itemNo);
	}
	
	@ApiOperation(value = "Add an item")
	@PostMapping("/items")
	public ResponseEntity<Item> createItem(@RequestBody Item item) throws URISyntaxException {	
		return itemService.createItem(item);
	}
	
	@ApiOperation(value = "Update an item")
	@PutMapping("/items/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable(value = "id") Long id, @Valid @RequestBody Item itemDetails) throws URISyntaxException {
		return itemService.updateItem(id, itemDetails);
    } 
	
	@ApiOperation(value = "Delete an item")
	@DeleteMapping("/items/{id}")
	public Map<String, Boolean> deleteItem(@PathVariable(value = "id") Long id){
		return itemService.deleteItem(id);
	}
}
