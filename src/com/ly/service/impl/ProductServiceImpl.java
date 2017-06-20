package com.ly.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import com.ly.dao.ProductDao;
import com.ly.domain.Product;
import com.ly.exception.MsgException;
import com.ly.factory.BasicFactory;
import com.ly.service.ProductService;
import com.ly.utils.Page;

public class ProductServiceImpl implements ProductService {
	private ProductDao prodDao=BasicFactory.getFactory().getInstance(ProductDao.class);

	@Override
	public void addProduct(Product prod) {
		int row = prodDao.addProduct(prod);
		if(row != 1){
			throw new MsgException("添加失败，请重新添加！");
		}
	}

	@Override
	public List<Product> findAll() {
	   return prodDao.findAll();
	}

	@Override
	public void changePnum(String id, int pnum) {
		int row = prodDao.changePnum(id,pnum);
		if(row <= 0 ){
			throw new MsgException("修改失败！");
		}
	}

	@Override
	public void deleteProductById(String id, ServletContext sc) {
		//首先删除商品图片
		Product prod = prodDao.findProdById(id);
		//System.out.println(prod.getImgurl());
		if(prod.getImgurl()!=null && !"".equals(prod.getImgurl())){
			File file = new File(sc.getRealPath(prod.getImgurl()));
			if(file.exists()){
				file.delete();
			}
		}
		int row = prodDao.deleteProduct(id);
		if(row<0){
			throw new MsgException("删除失败！");
		}
		
	}

	@Override
	public Page pageList(int thispage, int rowperpage, String name, String category, double min, double max) {
		Page<Product> page = new Page<Product>();
		page.setThispage(thispage);
		page.setRowperpage(rowperpage);
		//设置总行数
		page.setCountrow(prodDao.getProdKeyCount(name,category,min,max));
		//计算总页数
		int tmp = page.getCountrow()%rowperpage==0?0:1;
		int countpage = page.getCountrow()/rowperpage+tmp;
		page.setCountpage(countpage);
		page.setPrepage(thispage==1?thispage:thispage-1);
		page.setNextpage(thispage==page.getCountpage()?thispage:thispage+1);
		/** 
		 * 0   （1-1）*10
		 * 10  (2-1)*10
		 * 20  (3-1)*10
		 * 30  4
		 */
		page.setList(prodDao.findProdsByKeyLimit((thispage-1)*rowperpage,
				rowperpage,name,category,min,max));
		return page;
	}

	@Override
	public Product findProductById(String pid) {
		return prodDao.findProdById(pid);
	}

}
