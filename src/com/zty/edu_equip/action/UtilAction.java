package com.zty.edu_equip.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.po.base.Encrypt;
import com.po.base.IDbObj;
import com.po.base.Util;
import com.po.serverbase.BaseDao;
import com.po.serverbase.ImageVerify;
import com.po.serverbase.LogUtil;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SysConfig;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.entity.UserInfo;
import com.zty.edu_equip.listener.SessionListener;
import com.zty.edu_equip.util.Common;
import com.zty.edu_equip.util.PageUtil;
import com.zty.util.SealData;

public class UtilAction extends ActionSupport {
	private Map map;
	private String userCode;
	private String password;
	private static HashMap lastLoginRequestInfoOfUserCodeMap = new HashMap();
	private String verifyNo;
	private String verifyCode;
	private String organ_id;
	private String group_id;
	private String user_id;
	private Map<String, String[]> pageMap;
	private PageUtil pageUtil;
	private String room_id;
	private boolean boo;
	private String loginID;
	public static Map keyID = new HashMap();

	// JS ѧУ��������
	public String getOrganTree() throws Exception {
		map = new HashMap();
		map.put("dataorgan", Common.getOrganTree());
		return SUCCESS;
	}

	// ����װ���˵�����ĵ��� ����Ĭд�������ҷ���
	public String getEquipRoomForJS() throws Exception {
		map = new HashMap();
		map.put("room", Common.getEquipRoomName());
		return SUCCESS;
	}

	// ����װ���˵�����ĵ��� ����Ĭд�������ҷ���
	public String getInstrumentRoomForJS() throws Exception {
		map = new HashMap();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		map.put("room", Common.getInstrumentRoomName());
		return SUCCESS;
	}

	// ����װ���˵�����ĵ��� ����Ĭд�������ҷ������
	public String getInstrumentRoomTypeForJS() throws Exception {
		map = new HashMap();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		map.put("room_type", Common.getInstrumentRoomTypeName(organ_id));
		return SUCCESS;
	}
	
	/*public String getInstrumentRoomTypeTree() throws Exception{
		map = new HashMap();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		List list=Common.getInstrumentRoomTypeName();
		List roomTree=new ArrayList();
		HashMap row=null;
		HashMap hmap=null;
		for (int i=0;i<list.size();i++) {
			row=(HashMap) list.get(i);
			hmap.put("id", row.get("ROOM_TYPE_ID"));
			hmap.put("text", row.get("ROOM_TYPE_NAME"));
			roomTree.add(hmap);
		}
		map=new HashMap();
		map.put("roomTree", roomTree);
		return SUCCESS;
	}*/

	/*
	 * // js��ȡװ�����select public String getEquipTypeForJS() throws Exception {
	 * map = new HashMap(); map.put("equipType", Common.getEquipTypeforJS());
	 * return SUCCESS; }
	 * 
	 * // js��ȡ��������select public String getUserForJS() throws Exception { map =
	 * new HashMap(); BaseDao base = new BaseDao(SystemInfo.getMainJndi(),
	 * null); map.put("user", Common.getUserName(user_id)); return SUCCESS; } //
	 * js��ȡ�������select
	 * 
	 * public String getRoomForJS() throws Exception { map = new HashMap();
	 * map.put("room", Common.getRoomName()); return SUCCESS; }
	 */

	public String getRoomTypeTree() throws Exception {
		map = new HashMap();
		map.put("roomType", Common.getRoomType());
		return SUCCESS;
	}

	/**
	 * ��������Ҫ�������getGroupRoom Action����room�����װ����ʾ��������
	 * 
	 * @throws Exception
	 */
	public String getEquipByRoom() throws Exception {
		String sql = " select distinct e.equip_id,e.equip_name from equip e"
				+ " left join ref_equip_move_equip remq on  remq.EQUIP_ID=e.EQUIP_ID"
				+ " where e.EQUIP_STATUS='A'  and (remq.STATUS!='A' or remq.STATUS is null) and e.room_id="
				+ group_id;
		UserInfo user = getUser();
		if ("R_SCHOOL".equals(user.getUserPerms())) {
			sql += " and e.USER_ID=" + user.getPkValue();
		}
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		List list = baseDao.query(sql);
		HashMap row = null;
		HashMap itemMap = null;
		List equip = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			row = (HashMap) list.get(i);
			itemMap = new HashMap();
			itemMap.put("id", row.get("EQUIP_ID"));
			itemMap.put("text", row.get("EQUIP_NAME"));
			itemMap.put("parentid", group_id);
			equip.add(itemMap);
		}
		map = new HashMap();
		map.put("list", equip);
		return SUCCESS;
	}

	/**
	 * ��������Ҫ�������getGroupUser Action����user�����װ����ʾ��������
	 * 
	 * @throws Exception
	 */
	public String getEquipByUser() throws Exception {
		UserInfo user = getUser();
		String sql = null;
		if ("R_SCHOOL".equals(user.getUserPerms())) {
			if (!user.getPkValue().equals(group_id))
				return SUCCESS;
		}
		sql = " select distinct e.equip_id,e.equip_name from equip e"
				+ " left join ref_equip_move_equip remq on  remq.EQUIP_ID=e.EQUIP_ID"
				+ " where e.EQUIP_STATUS='A' and (remq.STATUS!='A' or remq.STATUS is null) and e.user_id="
				+ group_id;
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		List list = baseDao.query(sql);
		HashMap row = null;
		HashMap itemMap = null;
		List equip = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			row = (HashMap) list.get(i);
			itemMap = new HashMap();
			itemMap.put("id", row.get("EQUIP_ID"));
			itemMap.put("text", row.get("EQUIP_NAME"));
			itemMap.put("parentid", room_id);
			equip.add(itemMap);
		}
		map = new HashMap();
		map.put("list", equip);
		return SUCCESS;
	}

	/**
	 * �����ݿ��������userGroup ���� ��������װ ��������Ҫ�������getEquipByUser
	 * Action����user�����װ����ʾ��������
	 * 
	 * @throws Exception
	 */
	public String getGroupUser() throws Exception {
		String organ_id = UtilAction.getUser().getOrganId() + "";
		String sql = "select * from user_group where organ_id=" + organ_id;
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		HashMap attributes = new HashMap();
		attributes.put("url", "Util_getEquipByUser.action");
		List list = baseDao.query(sql);
		sql = "select user_id,user_name,user_group_id from user_info where organ_id="
				+ organ_id;
		List userlist = baseDao.query(sql);
		List userTree = new ArrayList();
		HashMap row = null;
		HashMap userMap = new HashMap();
		for (int j = 0; j < userlist.size(); j++) {
			row = (HashMap) userlist.get(j);
			HashMap itemMap = new HashMap();
			itemMap.put("id", row.get("USER_ID"));
			itemMap.put("text", row.get("USER_NAME"));
			itemMap.put("parentid", row.get("USER_GROUP_ID"));
			itemMap.put("attributes", attributes);
			itemMap.put("state", "closed");
			if (null == userMap.get(row.get("USER_GROUP_ID"))) {
				List children = new ArrayList();
				children.add(itemMap);
				userMap.put(row.get("USER_GROUP_ID"), children);
			} else {
				((List) userMap.get(row.get("USER_GROUP_ID"))).add(itemMap);
			}
		}
		for (int i = 0; null != list && i < list.size(); i++) {
			row = (HashMap) list.get(i);
			HashMap itemMap = new HashMap();
			itemMap.put("id", row.get("USER_GROUP_ID"));
			itemMap.put("text", row.get("GROUP_NAME"));
			itemMap.put("state", "closed");
			int count = 0;
			if (null != userMap.get(row.get("USER_GROUP_ID"))) {
				List children = (List) userMap.get(row.get("USER_GROUP_ID"));
				itemMap.put("children", children);
				count = children.size();
			}
			if (count != 0)
				userTree.add(itemMap);
		}
		// ���û���������ݵ���Ŀ¼
		for (int i = 0; i < userlist.size(); i++) {
			row = (HashMap) userlist.get(i);
			if (null == row.get("USER_GROUP_ID")) {
				HashMap itemMap = new HashMap();
				itemMap.put("id", row.get("USER_ID"));
				itemMap.put("text", row.get("USER_NAME"));
				itemMap.put("attributes", attributes);
				itemMap.put("state", "closed");
				userTree.add(itemMap);
			}
		}
		map = new HashMap();
		map.put("list", userTree);
		return SUCCESS;
	}

	/**
	 * �����ݿ��������roomType ���� ��������װ ��������Ҫ�������getEquipByRoom Action����room�����װ����ʾ��������
	 * 
	 * @throws Exception
	 */
	public String getGroupRoom() throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select room_type.room_type_id,room_type.room_type_name from room_type,organ,room_stand  where room_type.room_type_id =room_stand.room_type_id "
				+ " and organ.scal_id=room_stand.scal_id"
				+ " and organ_id="
				+ UtilAction.getUser().getOrganId()
				+ " order by room_type.REINDEX, convert(room_type.room_type_name using gbk) asc";
		List roomType = baseDao.query(sql);
		HashMap attributes = new HashMap();
		attributes.put("url", "Util_getEquipByRoom.action");
		HashMap addmap = new HashMap();
		addmap.put("ROOM_TYPE_ID", 82);
		addmap.put("ROOM_TYPE_NAME", "���ֽ�ѧ����");
		roomType.add(addmap);
		addmap = new HashMap();
		addmap.put("ROOM_TYPE_ID", 71);
		addmap.put("ROOM_TYPE_NAME", "������ѧ����");
		roomType.add(addmap);
		addmap = new HashMap();
		addmap.put("ROOM_TYPE_ID", 4);
		addmap.put("ROOM_TYPE_NAME", "������ѧ����");
		roomType.add(addmap);
		addmap = new HashMap();
		addmap.put("ROOM_TYPE_ID", 33);
		addmap.put("ROOM_TYPE_NAME", "��ѧ��ѧ����");
		roomType.add(addmap);
		addmap = new HashMap();
		addmap.put("ROOM_TYPE_ID", 60);
		addmap.put("ROOM_TYPE_NAME", "��ѧ�����ң���׼����ؼʵ��Ա�ң�");
		roomType.add(addmap);

		List roomTypeTree = new ArrayList();
		sql = "select r.ROOM_ID,r.room_name,refRt.ROOM_TYPE_ID from room r"
				+ " left join  ref_room_type refRt on refRt.ROOM_ID=r.ROOM_ID where  r.organ_id="
				+ UtilAction.getUser().getOrganId();
		List roomList = baseDao.query(sql);
		HashMap rtMap = new HashMap();
		// ��װ����
		for (int i = 0; i < roomList.size(); i++) {
			HashMap hmap = (HashMap) roomList.get(i);
			HashMap itemMap = new HashMap();
			itemMap.put("id", hmap.get("ROOM_ID"));
			itemMap.put("text", hmap.get("ROOM_NAME"));
			itemMap.put("parentid", hmap.get("ROOM_TYPE_ID"));
			itemMap.put("attributes", attributes);
			itemMap.put("state", "closed");
			if (null == rtMap.get(hmap.get("ROOM_TYPE_ID"))) {
				List list = new ArrayList();
				list.add(itemMap);
				rtMap.put(hmap.get("ROOM_TYPE_ID"), list);
				// etMap.put(hmap.get("GROUP_ID"),new ArrayList().add(itemMap));
			} else {
				((List) rtMap.get(hmap.get("ROOM_TYPE_ID"))).add(itemMap);
			}
		}
		// ��û�����ݵķ�����ӵ�������
		for (int i = 0; i < roomType.size(); i++) {
			HashMap map = (HashMap) roomType.get(i);
			HashMap itemMap = new HashMap();
			itemMap.put("id", map.get("ROOM_TYPE_ID"));
			int count = 0;
			if (null != rtMap.get(map.get("ROOM_TYPE_ID"))) {
				itemMap.put("children", rtMap.get(map.get("ROOM_TYPE_ID")));
				List list = (List) rtMap.get(map.get("ROOM_TYPE_ID"));
				count = list.size();
			}
			itemMap.put("text", map.get("ROOM_TYPE_NAME"));
			itemMap.put("state", "closed");
			if (count != 0)
				roomTypeTree.add(itemMap);
		}
		// ���û���������ݵ���Ŀ¼
		for (int i = 0; i < roomList.size(); i++) {
			HashMap hmap = (HashMap) roomList.get(i);
			if (null == hmap.get("ROOM_TYPE_ID")) {
				HashMap itemMap = new HashMap();
				itemMap.put("id", hmap.get("ROOM_ID"));
				itemMap.put("text", hmap.get("ROOM_NAME"));
				itemMap.put("attributes", attributes);
				itemMap.put("state", "closed");
				roomTypeTree.add(itemMap);
			}
		}
		map = new HashMap();
		map.put("list", roomTypeTree);
		return SUCCESS;
	}

	public String getUserTree() throws Exception {
		map = new HashMap();
		map.put("list", Common.getUserTree());
		return SUCCESS;
	}

	public String changePWD() throws Exception {
		UserInfo sessionUser = (UserInfo) ActionContext.getContext()
				.getSession().get("user");
		map = new HashMap();
		String oldPwd = pageMap.get("oldPwd")[0];
		System.out.println("oldPwd=" + oldPwd);
		String newPwd = pageMap.get("newPwd")[0];
		String newPwd2 = pageMap.get("newPwd2")[0];
		if (oldPwd.isEmpty()) {
			map.put("msg", "��¼��ԭ����");
		}
		if (newPwd.isEmpty() || !newPwd2.equals(newPwd)) {
			map.put("msg", "����������벻ƥ�䣬�������������룡");
		}
		if (newPwd.length() < 6) {
			map.put("msg", "���볤�Ȳ���С��6λ");
		}
		if (sessionUser.getUserCode().equals(newPwd)) {
			map.put("msg", "���벻�ܺ��û�����ͬ!");
		}
		// checkԭ�����Ƿ���ȷ
		if (!Encrypt.checkMd5Pw(
				sessionUser.getPkValue() + "/" + Encrypt.encrypt2MD5(oldPwd),
				sessionUser.getUserPwd())) {
			map.put("msg", "ԭ���������ע���Сд��");
		}
		newPwd = Encrypt.encrypt2MD5(sessionUser.getPkValue() + "/"
				+ Encrypt.encrypt2MD5(newPwd));
		sessionUser.setUserPwd(newPwd);
		sessionUser.setVersion(SeqenceGenerator.getNextOutranetVer());
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		baseDao.execute(sessionUser);
		return SUCCESS;
	}

	public String getEquipTypeRoot() throws Exception {
		map = new HashMap();
		map.put("list", Common.getEquipTypeRoot());
		return SUCCESS;
	}

	public String getEquipTypeByGroup() throws Exception {

		map = new HashMap();
		map.put("list", Common.getEquipTypeByGroup(group_id));
		return SUCCESS;
	}

	public String getRoomTreeByType() throws Exception {
		map = new HashMap();
		map.put("list", Common.getRoomByTypeTree(UtilAction.getUser()
				.getOrganId() + "", boo));
		return SUCCESS;
	}

	public String login() throws Exception {

		map = new HashMap();
		map.put("sec", 0);
		map.put("boo", false);
		// �������ƽ�
		Timestamp now = Util.getNow();
		Integer errorCount = (Integer) lastLoginRequestInfoOfUserCodeMap
				.get("ERROR_COUNT_" + userCode);
		if (errorCount == null) {
			errorCount = new Integer(0);
			lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_" + userCode,
					errorCount);
		}
		System.out.println("errorCount:" + errorCount);
		Timestamp lastRequestTime = (Timestamp) lastLoginRequestInfoOfUserCodeMap
				.get("LAST_REQUEST_TIME_" + userCode);
		lastLoginRequestInfoOfUserCodeMap.put("LAST_REQUEST_TIME_" + userCode,
				now);
		if (errorCount.intValue() >= 10
				&& (lastRequestTime == null || (now.getTime() - lastRequestTime
						.getTime()) < (errorCount.intValue() - 9) * 1000 * 10)) {
			lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_" + userCode,
					new Integer(errorCount.intValue() + 1));
			map.put("sec", (errorCount.intValue() - 8) * 10);
			map.put("msg", "ʧ�ܴ�������,���Ժ�����");
			return SUCCESS;
		}
		int verifyResult = ImageVerify.verifyCode(parseInt(verifyNo, -1),
				verifyCode);
		// 0����ȷ/1����֤����ʧЧ/2����Ų���ȷ/3������֤��/4����֤�벻��ȷ
		if (verifyResult == 1) {
			map.put("msg", "��֤����ʧЧ����ˢ��ҳ�������֤�룡");
			return SUCCESS;
		} else if (verifyResult == 2) {
			map.put("msg", "ҳ�������ˢ��ҳ�������֤�룡");
			return SUCCESS;
		} else if (verifyResult == 3) {
			map.put("msg", "��������֤�룬��ˢ��ҳ�������֤�룡");
			return SUCCESS;
		} else if (verifyResult == 4) {
			map.put("msg", "��֤�������ˢ��ҳ�������֤�룡");
			return SUCCESS;
		}
		try {

			BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
			System.out.println(keyID + "asdfaszzzz");
			System.out.println(loginID + "asdfaszzzz");
			if (null != keyID && !keyID.isEmpty()
					&& !"".equals(keyID.get("keyid"))
					&& !"cloud".equals(keyID.get("keyid"))
					&& null != keyID.get("keyid")) {
				System.out.println("jinru if1");
				if (!keyID.get("curDate").toString().contains(loginID)) {
					map.put("msg", "û�е�¼Ȩ��");
					return SUCCESS;
				} else {
					System.out.println("jinru else2");
					System.out.println(organ_id + "organ_id");
					if (null == organ_id || "" == organ_id) {
						System.out.println("jinru if3");

						organ_id = keyID.get("organ_id").toString();
						System.out.println("zzz" + organ_id);
					}

					List list = base.getObjsFromDbById(UserInfo.class,
							"user_code='" + userCode + "' and USER_STATUS='A'"
									+ " and ORGAN_ID='" + organ_id + "'"
									+ " and USER_FLAG = 'SCHOOL_USER'");
					if (null == list || list.size() < 1) {
						lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
								+ userCode, new Integer(
								errorCount.intValue() + 1));
						map.put("msg", "��ȷ���û����������Ƿ���ȷ");
						LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":��ȷ���û����������Ƿ���ȷ��"+":USER---zty1");
						return SUCCESS;
					}
					UserInfo user = (UserInfo) list.get(0);
					System.out.println("password=" + password);
					if (!Encrypt.encrypt2MD5(
							user.getPkValue() + "/"
									+ Encrypt.encrypt2MD5(password)).equals(
							user.getUserPwd())) {
						lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
								+ userCode, new Integer(
								errorCount.intValue() + 1));
						map.put("msg", "����������������룡ע���Сд��");
						LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":����������������룡ע���Сд��"+":USER---zty2");
						return SUCCESS;
					} else {
						if (null != user) {

							HttpSession session = ServletActionContext
									.getRequest().getSession();

							// �ж��û��Ƿ����ߣ�������߾͸ɵ���ǰ��
							// isLogonUser(user);
							session.setAttribute("user", user);

							String role = user.getUserPerms();

							Map powers = SysConfig
									.getMapByGroup("role_permmison");
							if (null != powers) {
								String power = (String) powers.get(role);
								session.setAttribute("userPower", power);
							}

						}
						System.out.println("jinru +=+");
						lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
								+ userCode, new Integer(0));
						map.put("msg", "��¼�ɹ�");
						map.put("boo", true);
						LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":��¼�ɹ�"+":ROOT---zty3");
						return SUCCESS;
					}
				}

			} else if("cloud".equals(keyID.get("keyid"))){
				List list = base.getObjsFromDbById(UserInfo.class,
						"user_code='" + userCode + "' and USER_STATUS='A'"
								+ " and USER_FLAG !='SCHOOL_USER'");
				if (null == list || list.size() < 1) {
					lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
							+ userCode, new Integer(errorCount.intValue() + 1));
					map.put("msg", "��ȷ���û����������Ƿ���ȷ");
					LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":��ȷ���û����������Ƿ���ȷ��"+":ROOT---zty4");
					return SUCCESS;
				}
				UserInfo user = null;
				int count = 0;
				for (int i = 0; i < list.size(); i++) {
					user = (UserInfo) list.get(i);
					System.out.println(Encrypt.encrypt2MD5(user.getPkValue() + "/" + Encrypt.encrypt2MD5(password))+"\t"+user.getUserPwd());
					System.out.println(Encrypt.encrypt2MD5(user.getPkValue() + "/" + Encrypt.encrypt2MD5(password)).equals(user.getUserPwd()));
					
					if (Encrypt.encrypt2MD5(
							user.getPkValue() + "/"
									+ Encrypt.encrypt2MD5(password)).equals(
							user.getUserPwd())) {
						count++;
					}
				}
				System.out.println("password=" + password);
				if (count == 0) {
					lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
							+ userCode, new Integer(errorCount.intValue() + 1));
					map.put("msg", "����������������룡ע���Сд��");
					LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":����������������룡ע���Сд��"+":ROOT---zty5");
					return SUCCESS;
				} else if (count > 1) {
					lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
							+ userCode, new Integer(errorCount.intValue() + 1));
					map.put("msg", "��ǰ���벻���á�����ϵ����Ա��������");
					LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":��ǰ���벻���á�����ϵ����Ա�������롣"+":ROOT---zty6");
					return SUCCESS;
				} else {
					if (null != user) {

						HttpSession session = ServletActionContext.getRequest()
								.getSession();

						// �ж��û��Ƿ����ߣ�������߾͸ɵ���ǰ��
						// isLogonUser(user);
						session.setAttribute("user", user);

						String role = user.getUserPerms();

						Map powers = SysConfig.getMapByGroup("role_permmison");
						if (null != powers) {
							String power = (String) powers.get(role);
							session.setAttribute("userPower", power);
						}

					}
					lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
							+ userCode, new Integer(0));
					map.put("msg", "��¼�ɹ�");
					LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":��¼�ɹ���"+":ROOT---zty7");
					map.put("boo", true);
					return SUCCESS;
				}

			}else{

				List list = base.getObjsFromDbById(UserInfo.class,
						"user_code='" + userCode + "' and USER_STATUS='A'"
								+ " and USER_FLAG !='SCHOOL_USER'");
				if (null == list || list.size() < 1) {
					lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
							+ userCode, new Integer(errorCount.intValue() + 1));
					map.put("msg", "ϵͳ���ϣ�����ϵ����Աȷ�Ϸ�����U���Ƿ�����");
					LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":ϵͳ���ϣ�����ϵ����Աȷ�Ϸ�����U���Ƿ�������"+":ROOT---zty8");
					return SUCCESS;
				}
				UserInfo user = null;
				int count = 0;
				for (int i = 0; i < list.size(); i++) {
					user = (UserInfo) list.get(i);
					System.out.println(Encrypt.encrypt2MD5(user.getPkValue() + "/" + Encrypt.encrypt2MD5(password))+"\t"+user.getUserPwd());
					System.out.println(Encrypt.encrypt2MD5(user.getPkValue() + "/" + Encrypt.encrypt2MD5(password)).equals(user.getUserPwd()));
					
					if (Encrypt.encrypt2MD5(
							user.getPkValue() + "/"
									+ Encrypt.encrypt2MD5(password)).equals(
							user.getUserPwd())) {
						count++;
					}
				}
				System.out.println("password=" + password);
				if (count == 0) {
					lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
							+ userCode, new Integer(errorCount.intValue() + 1));
					map.put("msg", "����������������룡ע���Сд��");
					LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":����������������룡ע���Сд��"+":ROOT---zty8");
					return SUCCESS;
				} else if (count > 1) {
					lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
							+ userCode, new Integer(errorCount.intValue() + 1));
					map.put("msg", "��ǰ���벻���á�����ϵ����Ա��������");
					LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":��ǰ���벻���á�����ϵ����Ա��������"+":ROOT---zty9");
					return SUCCESS;
				} else {
					if (null != user) {

						HttpSession session = ServletActionContext.getRequest()
								.getSession();

						// �ж��û��Ƿ����ߣ�������߾͸ɵ���ǰ��
						// isLogonUser(user);
						session.setAttribute("user", user);

						String role = user.getUserPerms();

						Map powers = SysConfig.getMapByGroup("role_permmison");
						if (null != powers) {
							String power = (String) powers.get(role);
							session.setAttribute("userPower", power);
						}

					}
					lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_"
							+ userCode, new Integer(0));
					map.put("msg", "��¼�ɹ�");
					LogUtil.info(SealData.class.getName(),userCode+":"+keyID.get("keyid")+":��¼�ɹ���"+":ROOT---zty11");
					map.put("boo", true);
					return SUCCESS;
				}

			
			}
		} catch (Exception e) {
			lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_" + userCode,
					new Integer(errorCount.intValue() + 1));
			map.put("msg", "���緱æ�����Ժ�����");
			return SUCCESS;
		} finally {

		}
	}

	public String webLogin() {

		map = new HashMap();
		map.put("sec", 0);
		map.put("boo", false);
		// �������ƽ�
		Timestamp now = Util.getNow();
		Integer errorCount = (Integer) lastLoginRequestInfoOfUserCodeMap
				.get("ERROR_COUNT_" + userCode);
		if (errorCount == null) {
			System.out.println("if");
			errorCount = new Integer(0);
			lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_" + userCode,
					errorCount);
		}
		System.out.println("errorCount:" + errorCount);
		Timestamp lastRequestTime = (Timestamp) lastLoginRequestInfoOfUserCodeMap
				.get("LAST_REQUEST_TIME_" + userCode);
		lastLoginRequestInfoOfUserCodeMap.put("LAST_REQUEST_TIME_" + userCode,
				now);
		if (errorCount.intValue() >= 10
				&& (lastRequestTime == null || (now.getTime() - lastRequestTime
						.getTime()) < (errorCount.intValue() - 9) * 1000 * 10)) {
			lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_" + userCode,
					new Integer(errorCount.intValue() + 1));
			map.put("sec", (errorCount.intValue() - 8) * 10);
			map.put("msg", "ʧ�ܴ�������,���Ժ�����");
			return SUCCESS;
		}
		try {
			BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
			List list = base.getObjsFromDbById(UserInfo.class, "user_code='"
					+ userCode + "' and USER_STATUS='A'");
			if (null == list || list.size() < 1) {
				lastLoginRequestInfoOfUserCodeMap.put(
						"ERROR_COUNT_" + userCode,
						new Integer(errorCount.intValue() + 1));
				map.put("msg", "�Ҳ������û��������µ�¼");
				return SUCCESS;
			}
			UserInfo user = (UserInfo) list.get(0);
			if (!Encrypt.encrypt2MD5(
					user.getPkValue() + "/" + Encrypt.encrypt2MD5(password))
					.equals(user.getUserPwd())) {
				lastLoginRequestInfoOfUserCodeMap.put(
						"ERROR_COUNT_" + userCode,
						new Integer(errorCount.intValue() + 1));
				map.put("msg", "����������������룡ע���Сд��");
				return SUCCESS;
			} else {
				if (null != user) {
					HttpSession session = ServletActionContext.getRequest()
							.getSession();
					// �ж��û��Ƿ����ߣ�������߾͸ɵ���ǰ��
					// isLogonUser(user);
					session.setAttribute("user", user);
					String role = user.getUserPerms();
					Map powers = SysConfig.getMapByGroup("role_permmison");
					String power = (String) powers.get(role);
					session.setAttribute("userPower", power);
				}
				lastLoginRequestInfoOfUserCodeMap.put(
						"ERROR_COUNT_" + userCode, new Integer(0));
				map.put("msg", "��¼�ɹ�");
				map.put("boo", true);
				return SUCCESS;
			}
		} catch (Exception e) {
			lastLoginRequestInfoOfUserCodeMap.put("ERROR_COUNT_" + userCode,
					new Integer(errorCount.intValue() + 1));
			map.put("msg", "���緱æ�����Ժ�����");
			return SUCCESS;
		}

	}

	private int parseInt(String as_Value, int defaultValue) {
		int result = defaultValue;
		try {
			result = Integer.parseInt(as_Value);
		} catch (Exception ee) {
			result = defaultValue;
		}
		return result;
	}

	public boolean isLogonUser(UserInfo user) {
		Set<HttpSession> keys = SessionListener.loginUser.keySet();
		for (HttpSession key : keys) {
			UserInfo sessionUser = (UserInfo) SessionListener.loginUser
					.get(key);
			if (sessionUser.getPkValue().equals(user.getPkValue())) {
				// ���
				key.invalidate();
				return true;
			}
		}
		return false;
	}

	public String closeUser() {
		map = new HashMap();
		Map session = ActionContext.getContext().getSession();
		if (session.size() > 0) {
			session.clear();
		}
		// request.getSession().invalidate()
		if (null != keyID && !keyID.isEmpty() && !"".equals(keyID.get("keyid"))
				&& null != keyID.get("keyid")) {
			System.out.println(keyID.get("keyid") + "...");
			System.out.println(keyID.get("curDate") + "...");
			map.put("keyid", keyID.get("keyid"));
			map.put("curDate", keyID.get("curDate"));
		}
		return "login";
	}

	public static UserInfo getUser() {
		return (UserInfo) ActionContext.getContext().getSession().get("user");
	}

	public static String getChildOrganId() throws Exception {
		String organ_id = getUser().getOrganId() + "";
		List datas = Common.sortChildItems(organ_id);
		for (int i = 1; i < datas.size(); i++) {
			HashMap map = (HashMap) datas.get(i);
			String organId = map.get("ORGAN_ID").toString();
			organ_id += "," + organId;
		}
		return " organ_id in (" + organ_id + ") ";
	}

	public String findEquipListForUse() throws Exception {
		map = new HashMap();
		String OrganId = UtilAction.getUser().getOrganId() + "";
		HttpServletRequest request = ServletActionContext.getRequest();
		String equipTypeId = request.getParameter("equipTypeId");
		String equipName = request.getParameter("equipName");
		String groupId = request.getParameter("groupId");
		String roomId = request.getParameter("roomId");
		String userId = request.getParameter("userId");
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select equip.*,equip_type.asset_type_name,room.room_name from REF_ROOM_TYPE,equip,equip_type,"
				+ " room where equip.equip_STATUS!='V' and REF_ROOM_TYPE.room_id=room.room_id and"
				+ " equip.equip_type_id=equip_type.equip_type_id "
				+ " and room.room_id = equip.room_id";
		if (null != equipTypeId && !"".equals(equipTypeId)) {
			sql += " and equip.equip_type_id=" + equipTypeId + " ";
		}
		if (null != groupId && !"".equals(groupId)) {
			sql += " and equip_type.group_id in ("
					+ Common.getChildGroup(Integer.parseInt(groupId)) + ")";
		}
		if (null != roomId && !"".equals(roomId)) {
			sql += " and ROOM.ROOM_ID = " + roomId + "";
		}
		if (null != organ_id && !"".equals(organ_id)) {
			sql += " and room.organ_id=" + organ_id + " ";
		} else {
			sql += " and room.organ_id=" + OrganId + "";
		}
		if (null != equipName && !"".equals(equipName)) {
			sql += " and equip.equip_name like '%" + equipName + "%' ";
		}
		if (null != userId && !"".equals(userId)) {
			sql += " and equip.user_id = " + userId + " ";
		}
		if (!pageUtil.getOrderField().isEmpty()) {
			sql += " order by " + pageUtil.getOrderField()
					+ pageUtil.getOrder();
		}
		map = base.getRowsWithPaging(sql, pageUtil.getPage() - 1,
				pageUtil.getLines());
		return SUCCESS;
	}

	public String showImport() throws Exception {
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String organ_id = getUser().getOrganId() + "";
		String sql = "select * from organ where organ_id=" + organ_id;
		List list = base.query(sql);
		HashMap row = (HashMap) list.get(0);
		String IMPORT_DATA = row.get("IMPORT_DATA") + "";
		map = new HashMap();
		map.put("key", IMPORT_DATA);
		return SUCCESS;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifyNo() {
		return verifyNo;
	}

	public void setVerifyNo(String verifyNo) {
		this.verifyNo = verifyNo;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getOrgan_id() {
		return organ_id;
	}

	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}

	public Map<String, String[]> getPageMap() {
		return pageMap;
	}

	public void setPageMap(Map<String, String[]> pageMap) {
		this.pageMap = pageMap;
	}

	public PageUtil getPageUtil() {
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	public void setBoo(boolean boo) {
		this.boo = boo;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

}
