package com.ly.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ly.dao.ProductDao;
import com.ly.domain.Product;
import com.ly.utils.BeanHandler;
import com.ly.utils.BeanListHandler;
import com.ly.utils.DBUtils;
import com.ly.utils.ResultSetHandler;

public class ProductDaoImpl implements ProductDao {

	@Override
	public int addProduct(Product prod) {
		String sql = "insert into products(id,name,price,category,pnum," +
				"imgurl,description) values(?,?,?,?,?,?,?)";
		 int row = DBUtils.update(sql, prod.getId(),prod.getName(),prod.getPrice(),
				prod.getCategory(),prod.getPnum(),prod.getImgurl(),prod.getDescription());
		 return row;
	}

	@Override
	public List<Product> findAll() {
		String sql = "select * from products";
		return DBUtils.query(sql, new BeanListHandler<Product>(Product.class));
		
	}

	@Override
	public int changePnum(String id, int pnum) {
		String sql = "update products set pnum=? where id=?";
		return DBUtils.update(sql, pnum,id);
	}

	@Override
	public int deleteProduct(String id) {
		String sql = "delete from products where id = ?";
		return DBUtils.update(sql, id);
	}

	@Override
	public Product findProdById(String id) {
		String sql = "select * from products where id = ?";
		return DBUtils.query(sql, new BeanHandler<Product>(Product.class), id);
	}

	@Override
	public int getProdKeyCount(String name, String category, double min, double max) {
		String sql = "select count(*) from products where name like ? and category like ? and price>=? and price<=?";
		return DBUtils.query(sql, new ResultSetHandler<Integer>() {
			public Integer handler(ResultSet rs) throws Exception {
				rs.next();
				return rs.getInt(1);
			}
			
		}, "%"+name+"%","%"+category+"%",min,max);
	}

	@Override
	public List<Product> findProdsByKeyLimit(int start, int rowperpage,
			String name, String category, double min, double max) {
		System.out.println("DAO!!!"+start+","+rowperpage+","+name+","+category+","+min+","+max);
		String sql = "select * from products where name like ? and category like ? and price>=? and price<=? limit ?,?";
		 List<Product> list = DBUtils.query(sql, new BeanListHandler<Product>(Product.class), 
					"%"+name+"%","%"+category+"%",min,max,start,rowperpage);
//		 System.out.println("-------->"+list);
		return list;
	}
	
	

}
