package com.zty.edu_equip.action;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.opensymphony.xwork2.ActionSupport;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.entity.Equip;
import com.zty.edu_equip.util.Common;
import com.zty.edu_equip.util.PageUtil;


public class EquipLabelAction extends ActionSupport {
	private String equip_id;
	private String equip_name;
	private String room_name;
	private String room_no;
	private String check_type;
	private String card_no;
	private PageUtil pageUtil;
	private Map map;
	private String url;
	public static String[] QRData = new String[] { "17", "23", "54", "56",
			"79", "98", "108", "120", "8", "10", "18", "19", "21", "29", "31",
			"76", "80", "83", "92", "104", "109", "139", "140", "145", "174",
			"267", "3", "9", "28", "37", "86", "88", "93", "95", "103", "114",
			"124", "126", "127", "135", "151", "176", "183", "192", "207",
			"214", "222", "236", "242", "251", "254", "259", "265", "269",
			"270", "11", "39", "48", "49", "59", "62", "72", "115", "156",
			"134", "165", "166", "168", "186", "187", "199", "232", "250",
			"253", "255", "257", "258", "261", "266", "274" };
	public String jump(){
		map=new HashMap();
		return url;
	}
	public String verifyEquip() throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = " SELECT a.*,r.ROOM_NAME,r.ROOM_NO FROM EQUIP a"
				+ " LEFT JOIN ROOM r ON a.ROOM_ID=r.ROOM_ID"
				+ " WHERE a.EQUIP_STATUS='A' AND r.ORGAN_ID="
				+ UtilAction.getUser().getOrganId();
		if (null != equip_name && !"".equals(equip_name))
			sql += " AND EQUIP_NAME like '%" + equip_name + "%'";
		if (null != room_no && !"".equals(room_no))
			sql += " AND ROOM_NO like '%" + room_no + "%'";
		if (null != room_name && !"".equals(room_name))
			sql += " AND r.ROOM_NAME LIKE '%" + room_name + "%'";
		if (null != card_no && !"".equals(card_no))
			sql += " AND a.CARD_NO='" + card_no + "'";
		if (!"ALL".equals(check_type))
			sql += " AND a.CARD_STATUS='" + check_type + "'";

		if (!pageUtil.getOrderField().isEmpty())
			sql += " order by " + pageUtil.getOrderField()
					+ pageUtil.getOrder();
		else
			sql += " ORDER BY r.ROOM_NAME,r.ROOM_NO,a.EQUIP_NAME ";
		// 查询方法
		map = baseDao.getRowsWithPaging(sql, pageUtil.getPage() - 1,
				pageUtil.getLines());
		return SUCCESS;
	}

	public String exportExcel() {
		String organId = UtilAction.getUser().getOrganId() + "";
		try {
			// 设置装备编号
			setEquipCardNo(organId);
			setRoomCardNo(organId);
			List equipList = Common.ConvertEmpty(getExeclEquip(organId));
			List roomList = Common.ConvertEmpty(getExcelRoom(organId));
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet equipSheet = wb.createSheet();
			HSSFSheet roomSheet = wb.createSheet();
			HSSFRow row = equipSheet.createRow(0);
			row.createCell(0).setCellValue("装备名称");
			row.createCell(1).setCellValue("标签编号");
			row.createCell(2).setCellValue("装备类别");
			row.createCell(3).setCellValue("房间名称");
			row.createCell(4).setCellValue("房间地址");
			row.createCell(5).setCellValue("规格型号");
			row.createCell(6).setCellValue("购置日期");
			row.createCell(7).setCellValue("报废年限");
			row.createCell(8).setCellValue("学校名称");
			int count = 1;
			String old_room = null;
			String old_no = "";
			String count_no = "";
			String[] data = null;
			String organ_name = Common.getOrganName(organId);
			List list = new ArrayList();
			for (int i = 0; i < equipList.size(); i++) {
				HashMap hmap = (HashMap) equipList.get(i);
				row = equipSheet.createRow(count++);
				count_no = hmap.get("CARD_NO") + "";
				if (old_room == null) {
					old_room = hmap.get("ROOM_NAME") + "";
					old_no = hmap.get("CARD_NO") + "";
				}
				if ((old_room != null && !old_room
						.equals(hmap.get("ROOM_NAME")))) {
					HashMap rhmap = (HashMap) equipList.get(i - 1);
					data = new String[9];
					data[0] = "***" + "\t" + rhmap.get("ROOM_NO") + "\t"
							+ old_room + "***";
					data[1] = old_no;
					data[2] = "";
					data[3] = "";
					data[4] = "";
					data[5] = rhmap.get("CARD_NO") + "";
					data[6] = "";
					data[7] = "";
					data[8] = organ_name;
					list.add(data);
					old_room = hmap.get("ROOM_NAME") + "";
					old_no = hmap.get("CARD_NO") + "";
				}
				data = new String[9];
				data[0] = hmap.get("EQUIP_NAME") + "";
				data[1] = hmap.get("CARD_NO") + "";
				data[2] = hmap.get("ASSET_TYPE_NAME") + "";
				data[3] = hmap.get("ROOM_NAME") + "";
				data[4] = hmap.get("ROOM_NO") + "";
				data[5] = hmap.get("EQUIP_SPECIFY") + "";
				if (!"".equals(hmap.get("PURCHASE_DATE")))
					data[6] = DateFormat.getDateInstance().format(
							hmap.get("PURCHASE_DATE"));
				else
					data[6] = "";
				if (!"".equals(hmap.get("LIFE_YEAR"))) {
					data[7] = hmap.get("LIFE_YEAR") + "年";
				} else {
					data[7] = "";
				}
				data[8] = organ_name;
				list.add(data);
			}
			data = new String[9];
			data[0] = "***" + old_room + "***";
			data[1] = old_no;
			data[2] = "";
			data[3] = "";
			data[4] = "";
			data[5] = count_no;
			data[6] = "";
			data[7] = "";
			data[8] = organ_name;
			list.add(data);
			for (int i = 0; i < list.size(); i++) {
				row = equipSheet.createRow(i + 1);
				data = (String[]) list.get(i);
				for (int j = 0; j < data.length; j++) {
					row.createCell(j).setCellValue(data[j]);
				}
			}
			// sheet2 roomsheet
			row = roomSheet.createRow(0);
			row.createCell(0).setCellValue("学校");
			row.createCell(1).setCellValue("名称");
			row.createCell(2).setCellValue("地点");
			row.createCell(3).setCellValue("编号");
			row.createCell(4).setCellValue("面积");
			for (int i = 0; i < roomList.size(); i++) {
				HashMap hmap = (HashMap) roomList.get(i);
				row = roomSheet.createRow(i + 1);
				row.createCell(0).setCellValue(organ_name);
				row.createCell(1).setCellValue(hmap.get("ROOM_NAME") + "");
				row.createCell(2).setCellValue(hmap.get("ROOM_NO") + "");
				row.createCell(3).setCellValue(hmap.get("CARD_NO") + "");
				row.createCell(4).setCellValue(
						!"".equals(hmap.get("AREA") + "") ? hmap.get("AREA")
								+ "m²" : "");
			}
			ServletOutputStream sos = null;
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ URLEncoder.encode("打印数据.xls", "UTF-8"));
			sos = response.getOutputStream();
			wb.write(sos);
			sos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List getExcelRoom(String organId) throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select * from room where organ_id=" + organId;
		return baseDao.query(sql);
	}

	public List getExeclEquip(String organ_id) throws Exception {
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		StringBuffer str = new StringBuffer();
		str.append(" and e.equip_type_id in (");
		for (int i = 0; i < QRData.length; i++) {
			if (i != 0)
				str.append(",");
			str.append(QRData[i]);
		}
		str.append(")");
		String sql = " select  e.EQUIP_ID,e.EQUIP_NAME,e.PURCHASE_DATE,e.EQUIP_SPECIFY,"
				+ " e.CARD_NO,et.LIFE_YEAR,et.asset_type_name,r.room_name,r.room_no from equip e "
				+ " left join EQUIP_TYPE et on  e.EQUIP_TYPE_ID=et.EQUIP_TYPE_ID"
				+ " left join room r on r.room_id=e.room_id "
				+ " WHERE e.EQUIP_STATUS='A' and e.CARD_NO is not null and r.organ_id="
				+ organ_id
				+ str
				+ " order by r.room_no,r.room_name,e.EQUIP_NAME";
		List list = base.query(sql);
		return list;
	}

	public void setEquipCardNo(String organId) throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		StringBuffer str = new StringBuffer();
		str.append(" and e.equip_type_id in (");
		for (int i = 0; i < QRData.length; i++) {
			if (i != 0)
				str.append(",");
			str.append(QRData[i]);
		}
		str.append(")");
		String sql = " select  e.EQUIP_ID from equip e "
				+ " left join room r on r.room_id=e.room_id "
				+ " WHERE e.EQUIP_STATUS='A' and e.CARD_NO is null and r.organ_id="
				+ organId + str;
		List list = baseDao.query(sql);
		for (int i = 0; i < list.size(); i++) {
			HashMap row = (HashMap) list.get(i);
			sql = "UPDATE EQUIP set CARD_NO="
					+ SeqenceGenerator.getNextOutranetVer()
					+ ",CARD_STATUS='C' " + "WHERE EQUIP_ID="
					+ row.get("EQUIP_ID");
			baseDao.addSql4Execute(sql);
		}
		baseDao.execute();
	}

	public void setRoomCardNo(String organId) throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "UPDATE room set CARD_NO=room_id where organ_id="
				+ organId;
		baseDao.execute(sql);
	}

	public String setCardStatus() throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		Equip equip = (Equip) baseDao.getObjFromDbById(Equip.class, equip_id);
		if (null == check_type || "".equals(check_type))
			equip.setCardStatus("A");
		else
			equip.setCardStatus(check_type);
		System.out.println("check_type=" + check_type);
		System.out.println(null == check_type);
		System.out.println("null".equals(check_type));
		baseDao.execute(equip);
		return SUCCESS;
	}

	public String findPrintEquip() throws Exception {
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
		String organ_id = UtilAction.getUser().getOrganId() + "";
		String sql = " select  e.EQUIP_ID from equip e "
				+ " left join room r on r.room_id=e.room_id "
				+ " WHERE e.EQUIP_STATUS='A' and e.CARD_NO is null and equip_id in("+equip_id+")";
		List list = baseDao.query(sql);
		for (int i = 0; i < list.size(); i++) {
			HashMap row = (HashMap) list.get(i);
			sql = "UPDATE EQUIP set CARD_NO="
					+ SeqenceGenerator.getNextOutranetVer()
					+ ",CARD_STATUS='C' " + "WHERE EQUIP_ID="
					+ row.get("EQUIP_ID");
			baseDao.addSql4Execute(sql);
		}
		baseDao.execute();
		sql = "select equip_id,equip_name,e.card_no,EQUIP_SPECIFY,et.LIFE_YEAR,PURCHASE_DATE "
				+ "from equip e "
				+ "left join equip_type et on et.equip_type_id=e.equip_type_id "
				+ "where equip_id in("+equip_id+")";
		list=Common.ConvertEmpty(baseDao.query(sql));
		map=new HashMap();
		map.put("list", list);
		map.put("organ_name", Common.getOrganName(organ_id));
		return SUCCESS;
	}
	public String getQRImg() throws IOException, WriterException{
		ServletOutputStream stream = null;
		HttpServletResponse resp=ServletActionContext.getResponse();
        try {
            int size=129;
            stream = resp.getOutputStream();
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix m = writer.encode(card_no, BarcodeFormat.QR_CODE, size, size);
            MatrixToImageWriter.writeToStream(m, "JPEG", stream);
        } catch (WriterException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
        return null;
	}
	public void createQRImg(){
	}
	
	public PageUtil getPageUtil() {
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	public String getEquip_name() {
		return equip_name;
	}

	public void setEquip_name(String equip_name) {
		this.equip_name = equip_name;
	}

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public String getRoom_no() {
		return room_no;
	}

	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}

	public String getCheck_type() {
		return check_type;
	}

	public void setCheck_type(String check_type) {
		this.check_type = check_type;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getEquip_id() {
		return equip_id;
	}

	public void setEquip_id(String equip_id) {
		this.equip_id = equip_id;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
