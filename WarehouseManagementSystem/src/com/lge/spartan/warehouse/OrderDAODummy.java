package com.lge.spartan.warehouse;

import com.lge.spartan.customer.data.Order;

public class OrderDAODummy implements OrderDAO {

	@Override
	public void open() {

	}

	@Override
	public void insert(Order order) {
		System.out.println("Storing order: " + order.toString());
	}

}
