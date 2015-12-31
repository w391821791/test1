package com.zty.edu_equip.util;

import java.util.List;
import java.util.Map;

public class PageUtil {
		//当前页数
		private Integer page;
		//需要展示的行数 默认为30行
		private Integer lines;
		//总记录数
		private Integer count;
		//返回的数据
		private List list;
		//组装好的sqlWhere
		private String sqlWhere;
		//需要排序的字段
		private String orderField;
		//排序的方向 倒  正
		private String order;
		
		
		public Integer getPage() {
			return (page == null || page == 0 ? 1: page);
		}
		public void setPage(Integer page) {
			this.page = page;
		}
		public Integer getLines() {
			return (lines == null || lines == 0 ? 10: lines);
		}
		public void setLines(Integer lines) {
			this.lines=lines;
		}
		public Integer getCount() {
			return count;
		}
		public void setCount(Integer count) {
			this.count = count;
		}
		public List getList() {
			return list;
		}
		public void setList(List list) {
			this.list = list;
		}
		public String getSqlWhere() {
			return sqlWhere;
		}
		public void setSqlWhere(String sqlWhere) {
			this.sqlWhere = sqlWhere;
		}
		public String getOrderField() {
			return orderField;
		}
		public void setOrderField(String orderField) {
			this.orderField = orderField;
		}
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
		
		
}
