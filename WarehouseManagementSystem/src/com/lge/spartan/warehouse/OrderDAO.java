package com.lge.spartan.warehouse;

import com.lge.spartan.customer.data.Order;

interface OrderDAO {
	void open();
	public void insert(Order order);
}
