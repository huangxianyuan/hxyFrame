/**
 * Copyright &copy; 2015-2020 <a href="http://www.scqiming.cn/">Qiming</a> All rights reserved.
 */
package com.hxy.base.page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * 
 * @ClassName: Page 
 * @Description: 分页类
 * @author HH
 * @date 2017年3月13日 下午4:27:17 
 * 
 * @param <T>
 */
public class Page<T> {
	 private int pageNum;  
     private int pageSize;  
     private int startRow;  
     private int endRow;  
     private int total;  
     private int pages;
     private int first =1;

     private List<T> result;  
     private int length = 5;// 显示页面长度
     private int slider = 1;// 前后显示页面长度
     private String funcName = "page"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。
     private String funcParam = ""; // 函数的附加参数，第三个参数值。
     
     public Page(int pageNum, int pageSize) {  
         this.pageNum = pageNum;  
         this.pageSize = pageSize;  
         this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;  
         this.endRow = pageNum * pageSize;  
     }

	/**
	 * 分页
	 * @param result        列表数据
	 * @param total  总记录数
	 * @param pageSize    每页记录数
	 * @param pageNum    当前页数
	 */
	public Page(List<T> result, int total, int pageSize, int pageNum) {
		this.result = result;
		this.total = total;
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
		this.endRow = pageNum * pageSize;
		this.pages=total/pageSize+((total%pageSize == 0) ? 0:1);
	}

     public List<T> getResult() {  
         return result;  
     }  

     public void setResult(List<T> result) {  
         this.result = result;  
     }  

     public int getPages() {  
         return pages;  
     }  

     public void setPages(int pages) {  
         this.pages = pages;  
     }  

     public int getEndRow() {  
         return endRow;  
     }  

     public void setEndRow(int endRow) {  
         this.endRow = endRow;  
     }  

     public int getPageNum() {  
         return pageNum;  
     }  

     public void setPageNum(int pageNum) {  
         this.pageNum = pageNum;  
     }  

     public int getPageSize() {  
         return pageSize;  
     }  

     public void setPageSize(int pageSize) {  
         this.pageSize = pageSize;  
     }  

     public int getStartRow() {  
         return startRow;  
     }  

     public void setStartRow(int startRow) {  
         this.startRow = startRow;  
     }  

     public long getTotal() {  
         return total;  
     }  

     public void setTotal(int total) {  
         this.total = total;  
     }  

     public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getSlider() {
		return slider;
	}

	public void setSlider(int slider) {
		this.slider = slider;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncParam() {
		return funcParam;
	}

	public void setFuncParam(String funcParam) {
		this.funcParam = funcParam;
	}


	@Override  
 	public String toString() {
		StringBuilder sb = new StringBuilder();
		int startNum = startRow + 1;
		endRow = endRow <= total? endRow : total;
		sb.append("<div class=\"fixed-table-pagination\" style=\"display: block;\">");
		sb.append("<div class=\"pull-left pagination-detail\" style=\"margin: 12px 0;\">");
		sb.append("<span class=\"pagination-info\">显示第 "+startNum+" 到第 "+ endRow +" 条记录，总共 "+total+" 条记录</span>");
		sb.append("<span class=\"page-list\">每页显示 <span class=\"dropup\">"+pageSize);//btn-white btn-group
// 		sb.append("<button type=\"button\" class=\"btn btn-white btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\">");
// 		sb.append("<span class=\"page-size\">"+pageSize+"</span> <span class=\"caret\"></span>");
// 		sb.append("</button>");
// 		sb.append("<ul class=\"dropdown-menu\" role=\"menu\">");
// 		sb.append("<li class=\""+getSelected(pageSize,10)+ "\"><a href=\"javascript:"+funcName+"("+pageNum+",10);\">10</a></li>");
// 		sb.append("<li class=\""+getSelected(pageSize,25)+ "\"><a href=\"javascript:"+funcName+"("+pageNum+",25);\">25</a></li>");
// 		sb.append("<li class=\""+getSelected(pageSize,50)+ "\"><a href=\"javascript:"+funcName+"("+pageNum+",50);\">50</a></li>");
// 		sb.append("<li class=\""+getSelected(pageSize,100)+ "\"><a href=\"javascript:"+funcName+"("+pageNum+",100);\">100</a></li>");
// 		sb.append("</ul>");
		sb.append("</span> 条记录</span>");
		sb.append("</div>");
 		
 		sb.append("<div class=\"pull-right pagination-roll\">");
 		sb.append("<ul class=\"pagination pagination-outline\">");
 		if (pageNum == first) {// 如果是首页
 			sb.append("<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"></i></a></li>\n");
 			sb.append("<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"></i></a></li>\n");
 		} else {
 			sb.append("<li class=\"paginate_button previous\"><a href=\"javascript:\" onclick=\""+funcName+"("+first+","+pageSize+");\"><i class=\"fa fa-angle-double-left\"></i></a></li>\n");
 			sb.append("<li class=\"paginate_button previous\"><a href=\"javascript:\" onclick=\""+funcName+"("+getPrev()+","+pageSize+");\"><i class=\"fa fa-angle-left\"></i></a></li>\n");
 		}

 		int begin = pageNum - (length / 2);

 		if (begin < first) {
 			begin = first;
 		}

 		int end = begin + length - 1;

 		if (end >= pages) {
 			end = pages;
 			begin = end - length + 1;
 			if (begin < first) {
 				begin = first;
 			}
 		}

 		if (begin > first) {
 			int i = 0;
 			for (i = first; i < first + slider && i < begin; i++) {
 				sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+");\">"
 						+ (i + 1 - first) + "</a></li>\n");
 			}
 			if (i < begin) {
 				sb.append("<li class=\"paginate_button disabled\"><a href=\"javascript:\">...</a></li>\n");
 			}
 		}

 		for (int i = begin; i <= end; i++) {
 			if (i == pageNum) {
 				sb.append("<li class=\"paginate_button active\"><a href=\"javascript:\">" + (i + 1 - first)
 						+ "</a></li>\n");
 			} else {
 				sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+");\">"
 						+ (i + 1 - first) + "</a></li>\n");
 			}
 		}

 		if (pages - end > slider) {
 			sb.append("<li class=\"paginate_button disabled\"><a href=\"javascript:\">...</a></li>\n");
 			end = pages - slider;
 		}

 		for (int i = end + 1; i <= pages; i++) {
 			sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+");\">"
 					+ (i + 1 - first) + "</a></li>\n");
 		}
 		if (pageNum == pages || total == 0) {
 			sb.append("<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"></i></a></li>\n");
 			sb.append("<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\" fa fa-angle-double-right\"></i></a></li>\n");
 		} else {
 			sb.append("<li class=\"paginate_button next\"><a href=\"javascript:\" onclick=\""+funcName+"("+getNext()+","+pageSize+");\">"
 					+ "<i class=\"fa fa-angle-right\"></i></a></li>\n");
 			sb.append("<li class=\"paginate_button next\"><a href=\"javascript:\" onclick=\""+funcName+"("+pages+","+pageSize+");\">"
 					+ "<i class=\" fa fa-angle-double-right\"></i></a></li>\n");
 		}
         sb.append("</ul>");
         sb.append("</div>");
         sb.append("</div>");
 		return sb.toString();
 	}

	/**
	 * 上一页索引值
	 * @return
	 */
	@JsonIgnore
	public int getPrev() {
		if (pageNum == 1) {
			return pageNum;
		} else {
			return pageNum - 1;
		}
	}

	/**
	 * 下一页索引值
	 * @return
	 */
	public int getNext() {
		if (pageNum == pages) {
			return pageNum;
		} else {
			return pageNum + 1;
		}
	}
	
}
