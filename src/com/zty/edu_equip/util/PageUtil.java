package com.zty.edu_equip.util;

import java.util.List;
import java.util.Map;

public class PageUtil {
		//��ǰҳ��
		private Integer page;
		//��Ҫչʾ������ Ĭ��Ϊ30��
		private Integer lines;
		//�ܼ�¼��
		private Integer count;
		//���ص�����
		private List list;
		//��װ�õ�sqlWhere
		private String sqlWhere;
		//��Ҫ������ֶ�
		private String orderField;
		//����ķ��� ��  ��
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
