package com.ly.dao;

import java.util.List;

import com.ly.domain.Product;

public interface ProductDao extends Dao {
	/**
	 * 商品添加的方法
	 * @param prod
	 */
	int addProduct(Product prod);
	/**
	 * 查找所有商品
	 * @return
	 */
	List<Product> findAll();
	
	/**
	 * 修改商品库存
	 */
	int changePnum(String id, int pnum);
	
	/**
	 * 删除商品
	 */
	int deleteProduct(String id);
	
	/**
	 * 根据商品ID查询商品信息
	 * @param id
	 * @return
	 */
	Product findProdById(String id);
	
	/**分页查询之：查询符合条件的商品数量
	 * @param name：商品名称
	 * @param category分类
	 * @param min 价格区间的最小值
	 * @param max价格区间的最大值
	 * @return 符合条件的商品总数量
	 */
	int getProdKeyCount(String name, String category, double min, double max);
	
	/**分页查询之：查询符合条件的商品集合
	 * @param start：从哪一个开始查询
	 * @param rowperpage：查询多少条
	 * @param name：商品名
	 * @param category分类
	 * @param min价格区间的最小值
	 * @param max价格区间的最大值
	 * @return 符合条件的商品集合
	 */
	List<Product> findProdsByKeyLimit(int start, int rowperpage, String name,
			String category, double min, double max);
}
