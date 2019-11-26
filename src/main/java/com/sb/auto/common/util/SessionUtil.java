package com.sb.auto.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;

/**
 * [LAMP] version [1.0]
 *
 * Copyright ⓒ [2018] kt corp. All rights reserved.
 *
 * This is a proprietary software of kt corp, and you may not use this file except in
 * compliance with license agreement with kt corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of kt corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 *
 * @author kt
 * @since 2018. 07. 20.
 * @Version 1.0
 * @see
 * @Copyright 2018 By KT corp. All rights reserved.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *  수정일               수정자                수정내용
 *  -------------        ----------       -------------------------
 *  2018. 07. 12.          이용준              최초생성
 */
@Slf4j
public class SessionUtil {

	/**
	 * Session에서 name의 Attribute 값을 리턴
	 * 
	 * @param name
	 * @return
	 */
	public static Object getAttribute(String name) {
		HttpSession session = RequestUtil.getSession(false);
		return session != null ? session.getAttribute(name) : null;
	}

	/**
	 * Session에서 name으로 object를 저장
	 * 
	 * @param name
	 * @param object
	 */
	public static void setAttribute(String name, Object object) {
		log.debug("setAttributes({}, {})", name, object);
		HttpSession session = RequestUtil.getSession(true);
		
		session.setAttribute(name, object);
	}

	/**
	 * Session에서 name의 Attribute 삭제
	 * 
	 * @param name
	 */
	public static void removeAttribute(String name) {
		log.debug("removeAttribute({})", name);
		HttpSession session = RequestUtil.getSession(true);
		session.removeAttribute(name);
	}
	
	/**
	 * 기존 세션 ID 무효화
	 */
	public static void invalidate() {
		HttpSession session = RequestUtil.getSession(false);
		if(session != null) {
			log.debug("invalidate(): sessionId[{}]", session.getId());
			session.invalidate();
			RequestUtil.getSession(true);
		}
	}
}
