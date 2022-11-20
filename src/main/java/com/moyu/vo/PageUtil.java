package com.moyu.vo;

import lombok.Data;

@Data
public class PageUtil {

	private Integer limit = 10; // 当前页大小//每页行数
	private Integer total = 0; // 总记录数
	private Integer currentPage = 1; // 当前页
	private Integer totalPage = 0; // 总页数

	
	public PageUtil() {
		super();
	}

	public PageUtil(Integer limit, Integer currentPage, Integer total) {
		super();
		this.limit = limit;
		this.currentPage = currentPage;
		if (total > 0) {
			this.total = total;

			if (total % this.limit > 0) {
				this.totalPage = (total / this.limit) + 1;
			} else {
				this.totalPage = (total / this.limit);
			}
		} else {
			this.total = 0;
		}
	}

	public void setTotal(Integer total) {
		if (total > 0) {
			this.total = total;

			if (total % this.limit > 0) {
				this.totalPage = (total / this.limit) + 1;
			} else {
				this.totalPage = (total / this.limit);
			}
		} else {
			this.total = 0;
		}
	}


	@Override
	public String toString() {
		return "PageUtil{" +
				"limit=" + limit +
				", total=" + total +
				", currentPage=" + currentPage +
				", totalPage=" + totalPage +
				'}';
	}
}
