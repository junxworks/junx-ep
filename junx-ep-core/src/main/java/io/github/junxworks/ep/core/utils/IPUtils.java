/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  IPUtils.java   
 * @Package io.github.junxworks.ep.core.utils   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:36   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

/**
 * {类的详细说明}.
 *
 * @ClassName:  IPUtils
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class IPUtils {
	
	/** logger. */
	private static Logger logger = LoggerFactory.getLogger(IPUtils.class);

	/**
	 * 返回 ip addr 属性.
	 *
	 * @param request the request
	 * @return ip addr 属性
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = null;
		try {
			ip = request.getHeader("x-forwarded-for");
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			logger.error("IPUtils ERROR ", e);
		}

		//        //使用代理，则获取第一个IP地址
		//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
		//			if(ip.indexOf(",") > 0) {
		//				ip = ip.substring(0, ip.indexOf(","));
		//			}
		//		}

		return ip;
	}

	public static String getHostName() throws UnknownHostException {
		Inet4Address ip = (Inet4Address) Inet4Address.getLocalHost();
		return ip.getHostName();
	}

	/** 常量 IP4Regex. */
	private static final String IP4Regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";

	public static String getLocalIPAddress() throws UnknownHostException, SocketException {
		// Before we connect somewhere, we cannot be sure about what we'd be bound to; however,
		// we only connect when the message where client ID is, is long constructed. Thus,
		// just use whichever IP address we can find.
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface current = interfaces.nextElement();
			if (!current.isUp() || current.isLoopback() || current.isVirtual())
				continue;
			String displayName = current.getDisplayName();
			if (!StringUtils.isBlank(displayName) && displayName.contains("Virtual Ethernet Adapter")) {
				continue;
			}
			Enumeration<InetAddress> addresses = current.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress addr = addresses.nextElement();
				if (addr.isLoopbackAddress())
					continue;
				String ipAddr = addr.getHostAddress();
				if (ipAddr.matches(IP4Regex)) {
					return ipAddr;
				} else {
					continue;
				}

			}
		}
		logger.warn("Can not initialize local IP address for current server.");
		return null;
	}

	/**
	 * Initialize server IP.
	 *
	 * @throws UnknownHostException the unknown host exception
	 * @throws SocketException the socket exception
	 */
	public static final void initializeServerIP() throws UnknownHostException, SocketException {
		String addr = IPUtils.getLocalIPAddress();//注册zookeeper，先初始化本地ip
		if (!StringUtils.isBlank(addr)) {//默认非空
			System.setProperty("server.ip", addr); //如果是空，则手动从启动参数中加入
		}
	}
}
