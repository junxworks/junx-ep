package io.github.junxworks.ep.core.orm;

/**
 * 雪花算法ID生成
 * The Class SnowFlake.
 */
public class SnowFlake {

	/** The worker id. */
	//下面两个每个5位，加起来就是10位的工作机器id
	private long workerId; //工作id

	/** The datacenter id. */
	private long datacenterId; //数据id
	//12位的序列号

	/** The sequence. */
	private long sequence;

	/** The twepoch. */
	//初始时间戳
	private long twepoch = 1288834974657L;

	/** The worker id bits. */
	//长度为5位
	private long workerIdBits = 5L;

	/** The datacenter id bits. */
	private long datacenterIdBits = 5L;

	/** The max worker id. */
	//最大值
	private long maxWorkerId = -1L ^ (-1L << workerIdBits);

	/** The max datacenter id. */
	private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

	/** The sequence bits. */
	//序列号id长度
	private long sequenceBits = 12L;

	/** The sequence mask. */
	//序列号最大值
	private long sequenceMask = -1L ^ (-1L << sequenceBits);

	/** The worker id shift. */
	//工作id需要左移的位数，12位
	private long workerIdShift = sequenceBits;

	/** The datacenter id shift. */
	//数据id需要左移位数 12+5=17位
	private long datacenterIdShift = sequenceBits + workerIdBits;

	/** The timestamp left shift. */
	//时间戳需要左移位数 12+5+5=22位
	private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

	/** The last timestamp. */
	//上次时间戳，初始值为负数
	private long lastTimestamp = -1L;

	/**
	 * Instantiates a new snow flake.
	 *
	 * @param workerId the worker id
	 * @param datacenterId the datacenter id
	 * @param sequence the sequence
	 */
	public SnowFlake(long workerId, long datacenterId, long sequence) {
		// sanity check for workerId
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
		}
		System.out.printf("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d", timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

		this.workerId = workerId;
		this.datacenterId = datacenterId;
		this.sequence = sequence;
	}

	public long getWorkerId() {
		return workerId;
	}

	public long getDatacenterId() {
		return datacenterId;
	}

	public long getTimestamp() {
		return System.currentTimeMillis();
	}

	/**
	 * Next id.
	 *
	 * @return the long
	 */
	//下一个ID生成算法
	public synchronized long nextId() {
		long timestamp = timeGen();

		//获取当前时间戳如果小于上次时间戳，则表示时间戳获取出现异常
		if (timestamp < lastTimestamp) {
			System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
			throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		//获取当前时间戳如果等于上次时间戳（同一毫秒内），则在序列号加一；否则序列号赋值为0，从0开始。
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}

		//将上次时间戳值刷新
		lastTimestamp = timestamp;

		/**
		  * 返回结果：
		  * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
		  * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
		  * (workerId << workerIdShift) 表示将工作id左移相应位数
		  * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
		  * 因为个部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
		*/
		return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
	}

	/**
	 * Til next millis.
	 *
	 * @param lastTimestamp the last timestamp
	 * @return the long
	 */
	//获取时间戳，并与上次时间戳比较
	private long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * Time gen.
	 *
	 * @return the long
	 */
	//获取系统时间戳
	private long timeGen() {
		return System.currentTimeMillis();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	//---------------测试---------------
	public static void main(String[] args) {
		SnowFlake worker = new SnowFlake(1, 1, 1);
		for (int i = 0; i < 30; i++) {
			System.out.println(worker.nextId());
		}
	}

}
