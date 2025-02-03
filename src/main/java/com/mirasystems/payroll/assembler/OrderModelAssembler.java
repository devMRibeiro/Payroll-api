package com.mirasystems.payroll.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.mirasystems.payroll.controller.OrderController;
import com.mirasystems.payroll.enums.EnumStatus;
import com.mirasystems.payroll.model.Order;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

	@Override
	public EntityModel<Order> toModel(Order order) {
		EntityModel<Order> orderModel = EntityModel.of(order,
				linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
				linkTo(methodOn(OrderController.class).all()).withRel("order"));
		
		if (order.getStatus() == EnumStatus.IN_PROGRESS) {
			orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
			orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
		}
		
		return orderModel;
	}
}