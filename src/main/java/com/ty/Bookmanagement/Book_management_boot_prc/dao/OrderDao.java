package com.ty.Bookmanagement.Book_management_boot_prc.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ty.Bookmanagement.Book_management_boot_prc.dto.Orders;
import com.ty.Bookmanagement.Book_management_boot_prc.repo.OrderRepo;

@Repository
public abstract class OrderDao {
	@Autowired
	private OrderRepo repo;
	
	public Orders saveOrder(Orders order)
	{
		return repo.save(order);
	}
	public Orders updateOrder(Orders order)
	{
		return repo.save(order);
	}
	
	public Orders findOrdersById(int id)
	{
		return repo.findById(id).get();
	}
	public String deleteOrders(Orders orders)
	{
		repo.delete(orders);
		return "deleted";
	}

}
