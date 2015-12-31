package com.zty.edu_equip.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

public class SessionListener implements HttpSessionListener,
		HttpSessionAttributeListener {

	// ���浱ǰ��¼�������û�
	public static Map<HttpSession, Object> loginUser = new HashMap<HttpSession, Object>();

	// �������Ϊsession�е�key

	public static String SESSION_LOGIN_NAME = "user";

	// session����ʱ�����������
	public void sessionCreated(HttpSessionEvent arg0) {
	}

	// SessionʧЧ���߹��ڵ�ʱ����õ��������,
	public void sessionDestroyed(HttpSessionEvent se) {
		// ���session��ʱ, ���map���Ƴ�����û�
		try {
			loginUser.remove(se.getSession());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ִ��setAttribute��ʱ��, ��������Ա�����������Session��ʱ, �����������.
	public void attributeAdded(HttpSessionBindingEvent se) {
		// �����ӵ��������û���, �����map��
		if (se.getName().equals(SESSION_LOGIN_NAME)) {
			loginUser.put(se.getSession(),se.getValue());
		}

	}

	// ��ִ��removeAttributeʱ���õķ���
	public void attributeRemoved(HttpSessionBindingEvent se) {
		// ����Ƴ����������û���, ���map���Ƴ�
		if (se.getName().equals(SESSION_LOGIN_NAME)) {
			try {
				loginUser.remove(se.getSession());
			} catch (Exception e) {
			}
		}

	}

	// ��ִ��setAttributeʱ ,�����������Ѿ�����, �������Ե�ʱ��, �����������
	public void attributeReplaced(HttpSessionBindingEvent se) {
		// ����ı���������û���, ����Ÿı�map
		if (se.getName().equals(SESSION_LOGIN_NAME)) {
			loginUser.put(se.getSession(),se.getValue());
		}
	}

	public boolean isLogonUser(Long userId) {
		Set<HttpSession> keys = SessionListener.loginUser.keySet();
		for (HttpSession key : keys) {
			if (SessionListener.loginUser.get(key).equals(userId)) {
				return true;
			}
		}
		return false;
	}
}