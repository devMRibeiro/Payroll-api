package com.mirasystems.payroll.model;

import java.util.Objects;

import com.mirasystems.payroll.enums.EnumStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_id_sequence")
  @SequenceGenerator(name = "order_id_sequence", sequenceName = "order_id_sequence", allocationSize = 1)
	private Integer id;
	private String description;
	private EnumStatus status;
	
	public Order() { }
	
	public Order(String description, EnumStatus status) {
		this.description = description;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EnumStatus getStatus() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

	@Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Order))
      return false;
    Order order = (Order) o;
    return Objects.equals(this.id, order.id) && Objects.equals(this.description, order.description)
        && this.status == order.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.description, this.status);
  }

  @Override
  public String toString() {
    return "Order{" + "id=" + this.id + ", description='" + this.description + '\'' + ", status=" + this.status + '}';
  }
}