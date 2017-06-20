package com.ly.web.back;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ly.domain.Product;
import com.ly.exception.MsgException;
import com.ly.factory.BasicFactory;
import com.ly.service.ProductService;

public class BackProductAdd extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//定义map集合保存表单项信息
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", UUID.randomUUID().toString());
		
		//一、文件上传
		// 检查当前表单是否使用了multipart/form-data
				if (!ServletFileUpload.isMultipartContent(request)) {
					throw new RuntimeException("请设置表单属性enctype为'multipart/form-data'");
				}
				try {
					// 1.创建DiskFileItemFactory对象，并设置相关属性
					DiskFileItemFactory factory = new DiskFileItemFactory();
					// 1.1 设置缓存大小
					factory.setSizeThreshold(1024 * 10);// 10KB
					// 1.2 设置临时文件路径
					factory.setRepository(new File(getServletContext().getRealPath(
							"/WEB-INF/tmp")));
					// 2.创建ServletFileUpload对象并做相关操作
					ServletFileUpload fileUpload = new ServletFileUpload(factory);
					// 2.1 处理文件名乱码问题
					fileUpload.setHeaderEncoding("utf-8");
					// 2.2 设置单个文件大小设置
					fileUpload.setFileSizeMax(1024 * 1024 * 2);// 2MB
					// 2.3 设置整个form表单中整个文件大小的限制
					fileUpload.setSizeMax(1024 * 1024 * 2);
					//添加监听--进度条效果实现
					fileUpload.setProgressListener(new ProgressListener() {
						@Override
						public void update(long byteRead, long contentLength, int itemno) {
							
						}
					});
					// 3.解析request对象
					List<FileItem> list = fileUpload.parseRequest(request);
					//4.遍历集合
					for(FileItem item : list){
						//4.1判断是上传文件表单项还是普通表单项
						if(item.isFormField()){//true是普通表单项
							String name = item.getFieldName();//获取name属性对应的值
							String value = item.getString("utf-8");
							map.put(name, value);
							System.out.println(name+"---->"+value);
						}else{//false 上传表单项
							//IP时间戳：IP+时间戳+随机数
							//UUID.randomUUID().toString();
							//获取文件名称
							String fileName = item.getName();
//							System.out.println(fileName);
							if(fileName.indexOf("\\")!=-1){
								//从最后一次出现的位置开始截取
								fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
							}
							//避免文件重名
							fileName = UUID.randomUUID().toString()+"_"+fileName;
							//文件上传路径的问题
							String path = "/WEB-INF/upload";
							//将文件名的hashcode值转换为16进制的字符串
							String hashStr = Integer.toHexString(fileName.hashCode());
							char[] chs = hashStr.toCharArray();
							for(char c : chs){
								path = path+"/"+c;
							}
							//创建路径目录
							File file = new File(getServletContext().getRealPath(path));
							if(!file.exists()){
								file.mkdirs();
							}
							map.put(item.getFieldName(), path+"/"+fileName);
							//IO操作
							InputStream in = item.getInputStream();
							OutputStream out = new FileOutputStream(getServletContext().getRealPath(path+"/"+fileName));
							byte[] bt = new byte[1024];
							int len = -1;
							while((len = in.read(bt))!=-1){
								out.write(bt,0,len);
							}
							//关闭流
							out.close();
							in.close();
							//删除临时文件
							item.delete();
						}
					}
				} catch(SizeLimitExceededException see){
					response.getWriter().write("文件大小超过了2M");
					return;
				}catch (FileUploadException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}

				//将map对象转换到Product
				//二、调用Service层的添加方法
				//三、将商品信息保存到数据库
				try {
					Product prod = new Product();
					BeanUtils.populate(prod, map);
					ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
					System.out.println("!!!!!!!!"+service);
					service.addProduct(prod);
					//四、提示并跳转：提示商品添加成功，跳转到后台商品查询Serlvet
					response.getWriter().write("添加商品成功！");
					response.setHeader("Refresh", "2;url="+request.getContextPath()+"/back/manageAddProd.jsp");
				}catch(MsgException e){
					response.getWriter().write(e.getMessage());
					response.setHeader("Refresh","3;url="+request.getContextPath()+"/back/manageProd.jsp");
				}catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
			}
	}


