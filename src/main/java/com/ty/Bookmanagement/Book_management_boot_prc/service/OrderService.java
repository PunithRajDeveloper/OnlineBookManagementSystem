package com.ty.Bookmanagement.Book_management_boot_prc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.Bookmanagement.Book_management_boot_prc.dao.CartDao;
import com.ty.Bookmanagement.Book_management_boot_prc.dao.OrderDao;
import com.ty.Bookmanagement.Book_management_boot_prc.dto.Cart;
import com.ty.Bookmanagement.Book_management_boot_prc.dto.Orders;
import com.ty.Bookmanagement.Book_management_boot_prc.exception.NoSuchIdFoundException;
import com.ty.Bookmanagement.Book_management_boot_prc.exception.UnableToDeleteException;
import com.ty.Bookmanagement.Book_management_boot_prc.exception.UnableToUpdateException;
import com.ty.Bookmanagement.Book_management_boot_prc.util.ResponseStructure;

@Service
public class OrderService {
	@Autowired
	private OrderDao dao;
	@Autowired
	private CartDao cartDao;

	public ResponseEntity<ResponseStructure<Orders>> saveOrder(Orders orders, int id) {
		Cart cart = cartDao.getCartById(id).get();
		orders.setCart(cart);

		ResponseStructure<Orders> responseStructure = new ResponseStructure<Orders>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Data Saved");
		cartDao.saveCart(orders.getCart());
		responseStructure.setData(dao.saveOrder(orders));
		return new ResponseEntity<ResponseStructure<Orders>>(responseStructure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Orders>> updateOrder(Orders orders, int id) {

		Optional<Orders> orders1 = dao.findOrdersById(id);
		ResponseStructure<Orders> responseStructure = new ResponseStructure<Orders>();
		if (orders1.isPresent()) {
Orders o1=orders1.get();
			orders.setId(id);
			orders.setCart(o1.getCart());
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Updated");
			responseStructure.setData(dao.updateOrder(orders));
			return new ResponseEntity<ResponseStructure<Orders>>(responseStructure, HttpStatus.OK);
		}
		throw new UnableToUpdateException("UnableToUpdate");

	}

	public ResponseEntity<ResponseStructure<Orders>> getOrderById(int id) {
		Optional<Orders> orders1 = dao.findOrdersById(id);
		ResponseStructure<Orders> responseStructure = new ResponseStructure<Orders>();
		if (orders1.isPresent()) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Received");
			responseStructure.setData(dao.findOrdersById(id).get());
			return new ResponseEntity<ResponseStructure<Orders>>(responseStructure, HttpStatus.OK);

		}
		throw new NoSuchIdFoundException();

	}

	public ResponseEntity<ResponseStructure<String>> deleteOrderById(int id) {
		Optional<Orders> orders1 = dao.findOrdersById(id);
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();

		if (orders1.isPresent()) {
			
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Deleted");
			responseStructure.setData(dao.deleteOrders(id));
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);

		}
		throw new UnableToDeleteException("UnableToDelete");

	}

}
