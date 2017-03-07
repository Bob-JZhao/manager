package com.imm.common.util;

import java.util.HashMap;
import java.util.Map;

 
public final class LockUtil {

	private static Map<String, Object> lockMap = new HashMap<String, Object>();
	
	/**
	 * 获得锁对象.
	 * @param lockMapKey
	 * @return
	 */
	public static synchronized Object getLock(String lockMapKey) {
		Object lockObj = lockMap.get(lockMapKey);
		if (lockObj == null) {
			lockMap.put(lockMapKey, lockMapKey);
			lockObj = lockMapKey;
		}
		return lockObj;
	}

	/**
	 * 解除锁对象.
	 * @param lockMapKey
	 * @return
	 */
	public static Object removeLock(String lockMapKey) {
		return lockMap.remove(lockMapKey);
	}
}
