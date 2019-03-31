package com.jl.mis.utils;


/**
 * datatables实体类，用于传递参数
 *
 */
public class PageTools {

	private Integer iTotalRecords;// 数据库中的结果总行数

	private Integer iDisplayStart = Integer.valueOf(0);// 起始行数

	private Integer pageDisplayLength = Integer.valueOf(10);// 每页显示条数

	private Integer pageCount;// 总页数

	private Integer currentPage = Integer.valueOf(1);// 当前页数

	public Object data;// 结果集

	public String queryParams; //查询条件

	public Integer getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(Integer iTotalRecords) {
		if(null != iTotalRecords){
			if(iTotalRecords <= 0){
				this.pageCount = Integer.valueOf(1);
			}else {
				this.iTotalRecords = iTotalRecords;
			/*
				如果总条数能整除每页显示条数
					总页数 = 总条数 / 每页显示条数
				如果总条数不能整除每页显示条数
					总页数 = 总条数 / 每页显示条数 + 1
			 */
				if(iTotalRecords % this.pageDisplayLength == 0){
					this.pageCount = iTotalRecords / this.pageDisplayLength;
				}else {
					this.pageCount = iTotalRecords / this.pageDisplayLength + 1;
				}
			}
		}
	}

	public Integer getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(Integer iDisplayStart) {
		if(iDisplayStart < 0){
			this.iDisplayStart = 0;
		}else {
			this.iDisplayStart = iDisplayStart;
		}
	}

	public Integer getPageDisplayLength() {
		return pageDisplayLength;
	}

	public void setPageDisplayLength(Integer pageDisplayLength) {
		this.pageDisplayLength = pageDisplayLength;
	}

	public Integer getPageCount() {
		return pageCount;
	}


	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		if(null != currentPage){
			/*
				如果当前页数小于等于零
					当前页数 = 1
				如果当前页数大于总页数
					当前页数 = 总页数
				正常状态
					正常赋值
			 */
			if(currentPage <= 0){
				this.currentPage = Integer.valueOf(1);
			}else if(currentPage > this.pageCount){
				this.currentPage = this.pageCount;
			}else {
				this.currentPage = currentPage;
			}
		}
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}
}
