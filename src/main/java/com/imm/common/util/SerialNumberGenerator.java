package com.imm.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public final class SerialNumberGenerator {

	private static DateFormat dateStrPattern = new SimpleDateFormat("yyyyMMddHHmmss");
	private static int RANDOM_BOUND = 9999;
	private static int POSTFIX_STR_LENGTH = 4;

	private static String PREFIX_ORDER = "10";
	private static String PREFIX_PAYORDER = "20";
	private static String PREFIX_USERACCOUNT = "30";
	private static String PREFIX_P2P_REQUEST = "40";
	private static String PREFIX_GAME_REQUEST = "50";

	final static Random random = new Random();

	/**
	 * Create invest order no.
	 * 
	 * @return
	 */
	public static String generateOrderNo() {
		synchronized (LockOrder.class) {
			String uuid = PREFIX_ORDER + dateStrPattern.format(new Date());
			return uuid + getRandom();
		}
	}

	/**
	 * Create payment order no.
	 * 
	 * @return
	 */
	public static String generatePaymentNo() {
		synchronized (LockPaymentOrder.class) {
			String uuid = PREFIX_PAYORDER + dateStrPattern.format(new Date());
			return uuid + getRandom();
		}
	}

	/**
	 * Create user account no.
	 * 
	 * @return
	 */
	public static String generateUserAccountNo() {
		synchronized (LockUserAccount.class) {
			String uuid = PREFIX_USERACCOUNT + dateStrPattern.format(new Date());
			return uuid + getRandom();
		}
	}

	/**
	 * Create request id before going to P2P website.
	 * 
	 * @return
	 */
	public static String generateRequestIdToP2P() {
		synchronized (LockP2PRequest.class) {
			String uuid = PREFIX_P2P_REQUEST + dateStrPattern.format(new Date());
			return uuid + getRandom();
		}
	}

	public static String generateRequestIdToGame() {
		synchronized (LockGameRequest.class) {
			String uuid = PREFIX_GAME_REQUEST + dateStrPattern.format(new Date());
			return uuid + getRandom();
		}
	}

	public static String getRandom() {
		return getRandom(POSTFIX_STR_LENGTH, RANDOM_BOUND);
	}

	public static String getRandomCaptcha() {
		return getRandom(6, 999999);
	}

	// 返回固定长度的随机正数对应的字符串
	public static String getRandom(int size, int randomLength) {
		return getFixedLengthString(size, random.nextInt(randomLength));
	}

	// 返回固定长度的字符串
	public static String getFixedLengthString(int size, int initValue) {
		String postfix = "" + initValue;
		int len = postfix.length();
		while (len < size) {
			postfix = "0" + postfix;
			len = postfix.length();
		}
		return postfix;
	}

	private static class LockOrder {
		// nothing
	}

	private static class LockPaymentOrder {
		// nothing
	}

	private static class LockUserAccount {
		// nothing
	}

	private static class LockP2PRequest {
		// nothing
	}

	private static class LockGameRequest {

	}
    // 传入区间值，获取 之间的随机数
	public static int rand(int start, int end) {
		int number = random.nextInt(100);
		while (number < start || number > end) {
			number = random.nextInt(100);

		}
		return number;
	}

	public static void main(String[] args) {

		System.out.println(rand(80, 100));
	}
}
