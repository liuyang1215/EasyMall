package com.ly.service;

import java.util.List;

import javax.servlet.ServletContext;

import com.ly.domain.Product;
import com.ly.utils.Page;

public interface ProductService extends Service{
	/**
	 * 商品添加方法
	 * @param prod：封装了商品信息的Product实体类的对象
	 */
	void addProduct(Product prod);
	
	/**后台查询全部商品
	 * @return 全部商品的集合对象List<Product>
	 */
	List<Product> findAll();
	
	/**
	 * 修改商品数量
	 * @param id
	 * @param pnum
	 */
	void changePnum(String id, int pnum);
	
	/**
	 * 删除商品
	 */
	void deleteProductById(String id, ServletContext sc);
	
	/**
	 * 带有查询条件的分页
	 * @param thispage:当前第几页
	 * @param rowperpage：m每页显示的行数
	 * @param name：商品名称
	 * @param category：商品分类
	 * @param min：价格区间最小值
	 * @param max：价格区间的最大值
	 * @return  封装了当前页的相关信息
	 */
	Page pageList(int thispage, int rowperpage, String name, String category,double min, double max);

	/**根据商品id查询商品详情
	 * @param pid商品的id
	 * @return商品详情Product对象
	 */
	Product findProductById(String pid);
}
