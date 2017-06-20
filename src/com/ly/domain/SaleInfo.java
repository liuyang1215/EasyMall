package com.ly.domain;

import java.io.Serializable;

public class SaleInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String prod_id;
	private String prod_name;
	private int sale_num;
	@Override
	public String toString() {
		return prod_id+","+prod_name+","+sale_num;
	};
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public int getSale_num() {
		return sale_num;
	}
	public void setSale_num(int sale_num) {
		this.sale_num = sale_num;
	}
	
}
