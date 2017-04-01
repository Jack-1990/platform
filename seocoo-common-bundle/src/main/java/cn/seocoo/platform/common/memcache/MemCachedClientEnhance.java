package cn.seocoo.platform.common.memcache;

//biz.wss.cache
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;

/**
 * MemCachedClient Enhance. <br>
 * <br>
 * Because of the memcached item data which more than 1MB, can not be stored. <br>
 * so will split the big data into small blocks, and cached them.
 * 
 */
public class MemCachedClientEnhance {
	private static final Logger logger = Logger.getLogger("biz.wss.cache");

	// per block size
	private static final int BLOCK_SIZE = 1024 * 512;

	private static final String BLOCK_COUNT = "_block_count";
	private static final String BLOCK = "_block_";

	private MemCachedClient memcachedClient;

	public MemCachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemCachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	/**
	 * MemCachedClient set method enhance.
	 * 
	 * @param key
	 * @param value
	 * @param expiry
	 * @return
	 */
	public boolean set(String key, Object value, Date expiry) {
		logger.debug("set memcached. key=" + key + ", value="
				+ value.getClass() + ", expiry=" + expiry);

		if (value == null)
			return false;

		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			// Read value into mem
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(value);
			oos.flush();
			bos.flush();

			// Some declarations.
			byte[] data = bos.toByteArray();
			int dataLen = data.length;
			int blockCount = 0, mod = 0;

			// When dataLen more than specified block size,
			// will split big-data into small-blocks, and cached them.
			// If dataLen less then specified block size, will cached the value
			// directly.
			logger.debug("set memcached. key=" + key + ", data length="
					+ dataLen + ", DefBlockSize=" + BLOCK_SIZE);

			if (dataLen > BLOCK_SIZE) {
				logger.debug("Cache data by blocks. key=" + key);

				blockCount = dataLen / BLOCK_SIZE;
				mod = dataLen % BLOCK_SIZE;
				if (mod != 0) {
					blockCount += 1;
				}

				for (int i = 1; i <= blockCount; i++) {
					int begin = (i - 1) * BLOCK_SIZE;
					int end = i * BLOCK_SIZE;
					if (end >= dataLen)
						end = dataLen;

					// Cache block data
					String blockKey = key + BLOCK + i;
					memcachedClient.set(blockKey,
							getBlockData(data, begin, end), expiry,
							blockKey.hashCode());
				}

				// Cache Block-count
				String blockCountKey = key + BLOCK_COUNT;
				return memcachedClient.set(blockCountKey, blockCount, expiry,
						blockCountKey.hashCode());
			} else {
				// Cache origin value directly
				logger.debug("Cache origin value directly. key=" + key);
				return memcachedClient.set(key, value, expiry, key.hashCode());
			}
		} catch (Exception e) {
			logger.error("set memcached exception.", e);
			return false;
		} finally {
			try {
				if (bos != null)
					bos.close();
			} catch (Exception e1) {/* nothing can do. */
			}
			try {
				if (oos != null)
					oos.close();
			} catch (Exception e2) {/* nothing can do. */
			}
		}
	}

	private byte[] getBlockData(byte[] data, int begin, int end) {
		if (end < begin)
			return null;

		byte[] blockData = new byte[end - begin];
		for (int i = 0; begin < end; i++) {
			blockData[i] = data[begin++];
		}

		return blockData;
	}

	/**
	 * MemCachedClient get method enhance.
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		logger.debug("get from memcache. key=" + key);

		// Get block count from cache.
		int blockCount = 0;
		try {
			String blockCountKey = key + BLOCK_COUNT;
			blockCount = (Integer) memcachedClient.get(blockCountKey,
					blockCountKey.hashCode());
		} catch (Exception e) {
			logger.warn("get blockCount from memcache. blockCount="
					+ blockCount);
		}

		logger.debug("get blockCount from memcache. blockCount=" + blockCount);

		// No blocks, get cache by origin key directly.
		if (blockCount <= 0) {
			logger.debug("get from memcache by origin key directly. key=" + key);
			return memcachedClient.get(key, key.hashCode());
		}

		// Has blocks, Rebuild Object from cached-block-data.
		logger.debug("get from memcache, Rebuild Object from cached-block-data. key="
				+ key);

		ByteArrayOutputStream bos = null;
		ObjectInputStream ois = null;
		try {
			// Write all block data into memory
			bos = new ByteArrayOutputStream();
			for (int i = 1; i <= blockCount; i++) {
				String blockKey = key + BLOCK + i;
				Object obj = memcachedClient.get(blockKey, blockKey.hashCode());

				if (obj != null && (obj instanceof byte[])) {
					bos.write((byte[]) obj);
				}
			}
			bos.flush();

			// Read object from mem
			ois = new ObjectInputStream(new ByteArrayInputStream(
					bos.toByteArray()));
			Object obj = ois.readObject();
			logger.debug("readObject from memory, obj info="
					+ (obj == null ? "null" : obj.getClass().getName()));
			return obj;
		} catch (Exception e) {
			logger.error("get from memcache. key=" + key + " exception.", e);
			return null;
		} finally {
			try {
				if (bos != null)
					bos.close();
			} catch (Exception e1) {/* nothing can do. */
			}
			try {
				if (ois != null)
					ois.close();
			} catch (Exception e2) {/* nothing can do. */
			}
		}
	}
}