package com.zty.edu_equip.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.po.base.Util;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SysConfig;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.dao.EquipDao;
import com.zty.edu_equip.entity.EquipTypeGroup;
import com.zty.edu_equip.entity.Organ;
import com.zty.edu_equip.entity.OrganScal;
import com.zty.edu_equip.util.Common;

public class ReportFormAction extends ActionSupport {
	private static String[] X_SCHOOL = { "普通教室", "科学实验室", "科学探究实验室",
			"科学仪器室(含准备室、实验员室)", "多媒体网络电脑室", "电脑资料及工作室", "语言实验室", "音乐室", "舞蹈室",
			"音乐器材室", "美术室(含书法室)", "美术器材室", "多功能教室(综合电教室)", "电教器材室", "综合实践活动室",
			"综合实践活动器材室", "软件制作室", "生物园", "小气象站", "阶梯教室(综合电教室)", "图书馆藏书室(㎡)",
			"教师阅览室(㎡)", "学生阅览室(㎡)", "图书管理员工作室", "电子阅览室", "广播室(广播系统)",
			"演播室(闭路电视系统)", "生物标本室", "卫生(保健)室", "心理咨询室", "学生体质测试室", "体育器材室",
			"少先队部室", "校史(德育)展览室", "文印室" };
	private static String[] C_SCHOOL = { "普通教室", "物理实验室", "化学实验室", "生物实验室",
			"物理探究实验室", "化学探究实验室", "生物探究实验室", "物理准备室(含实验员室)", "化学准备室(含实验员室)",
			"生物准备室(含实验员室)", "物理仪器室", "化学仪器室", "生物仪器室", "药品室(化、生)", "危险药品室(化)",
			"培养室(生)", "多媒体网络电脑室", "电脑资料及工作室", "语言实验室", "音乐室", "舞蹈室", "器乐排练室",
			"音乐器材室", "美术室(含书法室)", "美术器材室", "历史室", "地理室", "综合电教室(㎡)", "电教器材室",
			"综合实践活动(劳技)室", "综合实践活动器材室", "软件制作室", "生物园", "地理园", "阶梯教室(综合电教室)",
			"图书馆藏书室(㎡)", "教师阅览室(㎡)", "学生阅览室(㎡)", "图书管理员工作室", "电子阅览室",
			"广播室(广播系统)", "体育器材室", "演播室(闭路电视系统)", "生物标本室", "心理咨询室", "学生体质测试室",
			"团队室", "卫生(保健)室", "校史(德育)展览室", "文印室" };
	private static String[] G_SCHOOL = { "普通教室", "物理实验室", "物理探究实验室",
			"物理仪器室(含教学仪器)", "物理准备室", "物理实验员室", "化学实验室", "化学探究实验室",
			"化学仪器室(含教学仪器)", "化学(通风)药品室", "化学危险药品室", "化学准备室", "化学实验员室", "生物实验室",
			"生物探究实验室", "生物仪器室(含教学仪器)", "生物准备室", "生物实验员室", "生物培养室", "多媒体网络电脑室",
			"语言实验室", "音乐室", "舞蹈室", "器乐排练室", "音乐器材室", "美术室(含书法室)", "美术器材室",
			"历史室", "地理室", "综合电教室(㎡)", "电教器材室", "通用技术与综合实践活动室",
			"通用技术与综合实践活动器材室", "软件制作室", "生物园", "地理园", "阶梯教室(综合电教室)",
			"图书馆藏书室(㎡)", "教师阅览室(㎡)", "学生阅览室(㎡)", "图书管理员工作室", "电子阅览室",
			"电子软件资料室", "广播室(广播系统)", "演播室(闭路电视系统)", "生物标本室", "卫生(保健)室", "心理咨询室",
			"学生体质测试室", "体育器材室", "社团办公室", "校史(德育)展览室", "文印室" };
	private Map map;
	private String OrganScal;
	private String OrganType;
	private final int standardRow_X = 328;// 表格的行数(小学)
	private final int standardRow_C = 452;// 表格的行数(初中)
	private final int standardRow_G = 455;// 表格的行数(高中)
	private String url;
	private String room_type_id;
	private String organID;

	public String jump() {
		return url;
	}

	/**
	 * 房间报表
	 * 
	 * @return
	 * @throws Exception
	 */
	/**
	 * 房间报表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String roomReportForm() throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		if (null == organID || "".equals(organID)) {
			organID = UtilAction.getUser().getOrganId() + "";
		}
		List list = baseDao.getObjsFromDbById(Organ.class,
				"ORGAN_TYPE='C' AND SCAL_ID IS NOT NULL AND ORGAN_ID="
						+ organID);
		map = new HashMap();
		String[] sortArr = null;
		if (!list.isEmpty()) {
			Organ organ = (Organ) list.get(0);
			OrganScal organScal = (OrganScal) baseDao.getObjFromDbById(
					OrganScal.class, organ.getScalId() + "");
			if ("A".equals(organScal.getOrganType())) {
				sortArr = X_SCHOOL;
			} else if ("A".equals(organScal.getOrganType())) {
				sortArr = C_SCHOOL;
			} else {
				sortArr = G_SCHOOL;
			}
			String sql = "select rt.ROOM_TYPE_ID,rs.ROOM_COUNT,rs.ROOM_COUNT2,rs.ROOM_AREA,rt.ROOM_TYPE_NAME,"
					+ " (select count(refRT.REF_ID) from room r,ref_room_type refRt "
					+ " where  refRt.ROOM_ID=r.ROOM_ID and r.organ_id= "
					+ organID
					+ " and refRt.room_type_id=rt.room_type_id) as actual "
					+ " from ROOM_STAND rs,ROOM_TYPE rt"
					+ " where rt.ROOM_TYPE_ID=rs.ROOM_TYPE_ID and  rs.SCAL_ID="
					+ organScal.getScalId();
			List data = baseDao.query(sql);
			// HashMap roomTypeMap = Common.getExcelRoomType();
			map.put("roomReportForm", Common.ConvertEmpty(sortComplianceList(
					sortArr, data, "ROOM_TYPE_NAME")));
		} else {
			map.put("msg", "当前机构没有定义规模，无法查看对应报表");
		}
		return SUCCESS;
	}

	private List sortComplianceList(String[] sortArr, List list, String key) {
		List newlist = new ArrayList();
		String str = null, str2 = null;
		for (int k = 0; k < sortArr.length; k++) {
			if (sortArr[k].indexOf("(") != -1)
				str = sortArr[k].replace(sortArr[k].substring(
						sortArr[k].indexOf("("), sortArr[k].indexOf(")") + 1),
						"");
			else
				str = sortArr[k];
			for (int j = 0; j < list.size(); j++) {
				HashMap datarow = (HashMap) list.get(j);
				str2 = datarow.get(key).toString().trim();
				if (str2.indexOf("(") != -1)
					str2 = str2.replace(str2.substring(str2.indexOf("("),
							str2.indexOf(")") + 1), "");
				if (str2.equals(str)) {
					newlist.add(list.get(j));
				}// System.out.println(str2+"======"+str);
					// 无法通过转换手段进行配置， 通过手动进行关联
				if (sortArr.length == C_SCHOOL.length) {
					if (str.equals("药品室") && str2.equals("化学药品室")) {
						newlist.add(list.get(j));

					} else if (str.equals("危险药品室") && str2.equals("化学危险药品室")) {
						newlist.add(list.get(j));

					} else if (str.equals("培养室") && str2.equals("生物培养室")) {
						newlist.add(list.get(j));

					} else if (str.equals("综合电教室") && str2.equals("多功能教室")) {
						newlist.add(list.get(j));

					}
				} else if (sortArr.length == G_SCHOOL.length) {
					if (str.equals("化学药品室") && str2.equals("化学通风药品室")) {
						newlist.add(list.get(j));

					} else if (str.equals("通用技术与综合实践活动器材室")
							&& str2.equals("综合实践活动器材室")) {
						newlist.add(list.get(j));

					} else if (str.equals("培养室") && str2.equals("生物培养室")) {
						newlist.add(list.get(j));

					}
				}
			}
		}

		return newlist;
	}

	/**
	 * 装备报表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String equipReportForm() throws Exception {
		HashMap data = new HashMap();
		String organ_id = UtilAction.getUser().getOrganId() + "";
		if (null == organID || "".equals(organID)) {
			organID = organ_id;
		}

		map = new HashMap();
		if ("1".equals(organID))
			map.put("boo", true);
		else
			map.put("boo", false);
		String[] summary = null;
		String str = null, str2 = null;
		String filename = null;
		List list = new ArrayList();
		String o_type = OrganScal;
		System.out.println(organID);
		HashMap OrganScalType = EquipDao.getOrganScalType(organID);

		try {
			if ("".equals(OrganScal) && null == OrganScalType) {
				map.put("msg", "该机构未设置规模");
				return SUCCESS;
			}

			if (null == OrganType || "".equals(OrganType)) {
				String organ_type = OrganScalType.get("TYPE") + "";
				OrganType = organ_type;
			}
			if (!"C".equals(OrganType)) {
				System.out.println(OrganType);
				map.put("boo", true);
				return SUCCESS;
			}

			if (null == OrganScal || "".equals(OrganScal)) {
				OrganScal = OrganScalType.get("SCAL").toString();
			}
			switch (OrganScal) {
			case "A":
				summary = X_SCHOOL;
				filename = "附件1—1：公办中小学校（小学）设备设施配置达标情况明细表.xlsx";
				break;
			case "B":
				summary = C_SCHOOL;
				filename = "附件1—2：公办中小学校（初中）设备设施配置达标情况明细表.xlsx";
				break;
			case "C":
				summary = G_SCHOOL;
				filename = "附件1—3：公办中小学校（高中）设备设施配置达标情况明细表.xlsx";
				break;
			}
			HashMap organ = EquipDao.getOrganName();
			HashMap Existorgan = new HashMap();
			list = sortEquipList(summary,
					EquipDao.getStandardEquip(o_type, organID, OrganScal),
					"ROOM_TYPE_NAME");

			if (null != o_type) {
				BigDecimal SUB_PRICE = new BigDecimal(0);
				BigDecimal ALL_PRICE = new BigDecimal(0);
				List organNameList = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					HashMap maps = (HashMap) list.get(i);
					String organ_name = (String) organ.get((BigDecimal) maps
							.get("ORGAN_ID"));
					String asset_type_name = (String) maps
							.get("EQUIP_TYPE_NAME");
					String room_type_name = (String) maps.get("ROOM_TYPE_NAME");
					BigDecimal ROOM_COUNT = (BigDecimal) maps.get("ROOM_COUNT");
					BigDecimal EQUIP_COUNT = ((BigDecimal) maps
							.get("EQUIP_COUNT"));
					BigDecimal EQUIPS_MIN = ((BigDecimal) maps
							.get("EQUIPS_MIN")).multiply(ROOM_COUNT);
					BigDecimal Need_count = new BigDecimal(0);

					if (EQUIPS_MIN != new BigDecimal(0)) {
						Need_count = EQUIPS_MIN.subtract(EQUIP_COUNT);
					}
					BigDecimal PRICE = ((BigDecimal) maps.get("UNIT_PRICE"))
							.multiply(Need_count);

					SUB_PRICE = (BigDecimal) data.get(organ_name + "~"
							+ room_type_name + "~" + "sub_price");
					if (null != SUB_PRICE) {
						SUB_PRICE = Util.addBigDecimal(SUB_PRICE, PRICE);
					} else {
						SUB_PRICE = new BigDecimal(0);
					}
					ALL_PRICE = (BigDecimal) data.get(organ_name + "~"
							+ "all_price");
					if (null != ALL_PRICE) {
						ALL_PRICE = Util.addBigDecimal(ALL_PRICE, SUB_PRICE);
					} else {
						ALL_PRICE = new BigDecimal(0);
					}
					if (null != organ_name && !"".equals(organ_name)) {
						// System.out.println(asset_type_name+"=="+EQUIP_COUNT);
						data.put(organ_name + "~" + room_type_name + "~"
								+ asset_type_name + "room_count", ROOM_COUNT);
						data.put(organ_name + "~" + room_type_name + "~"
								+ asset_type_name + "due_count", EQUIPS_MIN);
						data.put(organ_name + "~" + room_type_name + "~"
								+ asset_type_name + "real_count", EQUIP_COUNT);
						data.put(organ_name + "~" + room_type_name + "~"
								+ asset_type_name + "need_count", Need_count);
						data.put(organ_name + "~" + room_type_name + "~"
								+ asset_type_name + "price", PRICE);
						data.put(organ_name + "~" + room_type_name + "~"
								+ "sub_price", SUB_PRICE);
						data.put(organ_name + "~" + "all_price", ALL_PRICE);
						Existorgan.put(organ_name, organ_name);

					}
				}
				// 取数据中，所有学校名，放入data里
				Iterator it = Existorgan.keySet().iterator();
				for (int i = 0; it.hasNext(); i++) {
					String key = it.next().toString();
					String organ_name = (String) Existorgan.get(key);
					organNameList.add(organ_name);
				}
				data.put("organNameList", organNameList);
				createEquipExcel(filename, data);
			} else {
				HashMap roomtypename = new HashMap();
				for (int i = 0; i < list.size(); i++) {
					HashMap row = (HashMap) list.get(i);
					roomtypename.put(row.get("ROOM_TYPE_NAME"),
							row.get("ROOM_TYPE_NAME"));
				}
				Iterator iterator = roomtypename.keySet().iterator();
				List listdata = new ArrayList();
				for (int p = 0; p < summary.length; p++) {
					str = summary[p];
					List rows = new ArrayList();
					for (int i = 0; i < list.size(); i++) {
						HashMap row = (HashMap) list.get(i);
						if (str.equals(row.get("ROOM_TYPE_NAME").toString())) {
							rows.add(row);
						}
					}
					listdata.add(Common.ConvertEmpty(rows));
				}

				map.put("list", listdata);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String instrumentReportForm() throws Exception {

		List list = new ArrayList();
		if (null == organID || "".equals(organID)) {
			organID = UtilAction.getUser().getOrganId() + "";
		}
		list = EquipDao.getInstrument(room_type_id, organID);
		map = new HashMap();
		map.put("list", Common.ConvertEmpty(list));
		return SUCCESS;
	}

	private String createEquipExcel(String filename, HashMap data)
			throws Exception {
		// 选择模板文件：

		String realpath = SysConfig.getAttrValue("FileServerPath")
				+ File.separator;
		FileInputStream input = new FileInputStream(new File(realpath
				+ "moban/" + filename)); // 读取的文件路径

		System.out.println(filename);
		OutputStream os = new FileOutputStream(
				SysConfig.getAttrValue("FileServerPath") + File.separator
						+ "Excel/" + filename);
		System.out.println(filename);
		XSSFWorkbook wb = new XSSFWorkbook(new BufferedInputStream(input));
		System.out.println(filename);
		XSSFSheet sheet;
		System.out.println(filename);
		int SheetRow = 0;

		if ("附件1—1：公办中小学校（小学）设备设施配置达标情况明细表.xlsx".equals(filename)) {
			SheetRow = standardRow_X;
		}
		if ("附件1—2：公办中小学校（初中）设备设施配置达标情况明细表.xlsx".equals(filename)) {
			SheetRow = standardRow_C;
		}
		if ("附件1—3：公办中小学校（高中）设备设施配置达标情况明细表.xlsx".equals(filename)) {
			SheetRow = standardRow_G;
		}
		System.out.println("ssssssss");
		// System.out.println("呵呵"+sheet.getForceFormulaRecalculation());
		System.out.println("ssssssss");
		XSSFRow xrow;
		List organNameList = (List) data.get("organNameList");
		String room_type_name = null;
		for (int i = 0; i < organNameList.size(); i++) {
			String organ = (String) organNameList.get(i);
			if (i == 0) {
				sheet = wb.getSheetAt(0);
			} else {
				sheet = wb.cloneSheet(0);
			}
			wb.setSheetName(i, organ);
			sheet.setForceFormulaRecalculation(true);

			sheet.getRow(2).getCell(0).setCellValue(organ);
			String organ_name = sheet.getRow(2).getCell(0).toString().trim();
			for (int j = 0; j < SheetRow; j++) {
				xrow = sheet.getRow(j + 3);

				if (null != xrow.getCell(1)
						&& !"".equals(xrow.getCell(1).toString())) {
					room_type_name = xrow.getCell(1).toString().trim();
				}
				String asset_type_name = xrow.getCell(4).toString().trim();
				// System.out.println(organ_name+"~"+room_type_name+"~"+asset_type_name);

				if (null != data.get(organ_name + "~" + room_type_name + "~"
						+ asset_type_name + "room_count"))
					sheet.getRow(j + 3)
							.getCell(2)
							.setCellValue(
									((BigDecimal) data.get(organ_name + "~"
											+ room_type_name + "~"
											+ asset_type_name + "room_count"))
											.intValue());

				if (null != data.get(organ_name + "~" + room_type_name + "~"
						+ asset_type_name + "due_count"))
					sheet.getRow(j + 3)
							.getCell(7)
							.setCellValue(
									((BigDecimal) data.get(organ_name + "~"
											+ room_type_name + "~"
											+ asset_type_name + "due_count"))
											.intValue());

				if (null != data.get(organ_name + "~" + room_type_name + "~"
						+ asset_type_name + "real_count"))
					sheet.getRow(j + 3)
							.getCell(8)
							.setCellValue(
									((BigDecimal) data.get(organ_name + "~"
											+ room_type_name + "~"
											+ asset_type_name + "real_count"))
											.intValue());

				if (null != data.get(organ_name + "~" + room_type_name + "~"
						+ asset_type_name + "need_count"))
					sheet.getRow(j + 3)
							.getCell(9)
							.setCellValue(
									((BigDecimal) data.get(organ_name + "~"
											+ room_type_name + "~"
											+ asset_type_name + "need_count"))
											.intValue());

				if (null != data.get(organ_name + "~" + room_type_name + "~"
						+ asset_type_name + "price"))
					sheet.getRow(j + 3)
							.getCell(10)
							.setCellValue(
									((BigDecimal) data.get(organ_name + "~"
											+ room_type_name + "~"
											+ asset_type_name + "price"))
											.doubleValue());

			}
		}

		wb.write(os);
		input.close();
		os.close();
		HttpServletResponse response = null;
		OutputStream out = null;
		try {
			response = ServletActionContext.getResponse();
			response.setDateHeader("Expires", 0);
			response.setContentType("multipart/form-data");
			response.setHeader(
					"Content-Disposition",
					"attachment"
							+ "; filename=\""
							+ Common.toUtf8String("教育装备云管理-" + filename
									+ ".xls") + "\"");
			out = response.getOutputStream();
			File file = new File(SysConfig.getAttrValue("FileServerPath")
					+ File.separator + "Excel/" + filename);
			InputStream in = new FileInputStream(file);
			byte[] readed = new byte[1024];
			int len = in.read(readed);
			while (len > 0) {
				out.write(readed, 0, len);
				len = in.read(readed);
			}
			System.out.println("我的天");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	private List sortEquipList(String[] sortArr, List list, String key) {
		List newlist = new ArrayList();
		String str = null, str2 = null;
		for (int k = 0; k < sortArr.length; k++) {
			str = sortArr[k];
			for (int j = 0; j < list.size(); j++) {
				HashMap datarow = (HashMap) list.get(j);
				str2 = datarow.get(key).toString().trim();
				if (str2.equals(str)) {
					newlist.add(list.get(j));
				}// System.out.println(str2+"======"+str);
					// 无法通过转换手段进行配置， 通过手动进行关联
			}
		}

		return newlist;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOrganID() {
		return organID;
	}

	public void setOrganID(String organID) {
		this.organID = organID;
	}

	public String getOrganScal() {
		return OrganScal;
	}

	public void setOrganScal(String organScal) {
		OrganScal = organScal;
	}

	public String getOrganType() {
		return OrganType;
	}

	public void setOrganType(String organType) {
		OrganType = organType;
	}

	public String getRoom_type_id() {
		return room_type_id;
	}

	public void setRoom_type_id(String room_type_id) {
		this.room_type_id = room_type_id;
	}

}
