package com.example.service.qa.utils;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.SecureRandom;

/**
 * @ClassName: IdGenerator
 * @Description: snowflake算法 生成id(http://www.cnblogs.com/relucent/p/4955340.html, http://www.tuicool.com/articles/qmEBjeq) https://gist.github.com/xishuixixia/f0f8684805d0504289b7a40f3b327dd6
 * 		ID 生成规则: ID长达 64 bits
 * 		1 bits 未使用  | 41 bits: Timestamp (毫秒 41位的长度可以使用69年) | 5 bits: 区域（机房） | 5 bits: 机器编号 (10位的长度最多支持部署1024个节点) | 12 bits: 序列号 (12位的计数顺序号支持每个节点每毫秒产生4096个ID序号)
 * 		41 bits: Timestamp (毫秒) | 3 bits: 区域（机房） | 10 bits: 机器编号 | 10 bits: 序列号
 * 
 * 		id构成: 42位的时间前缀 + 10位的节点标识 + 12位的sequence避免并发的数字(12位不够用时强制得到新的时间前缀)
 * 		注意这里进行了小改动: snowkflake是5位的datacenter加5位的机器id; 这里变成使用10位的机器id
 * 
 * 		对系统时间的依赖性非常强，需关闭ntp的时间同步功能。当检测到ntp时间调整后，将会拒绝分配id			
 * 	
 * 		You should use NTP to keep your system clock accurate”. 而且最好把 NTP 配置成不会向后调整的模式
 * 
 * @author 杨鹏兵
 * @date 2017年2月14日-下午5:19:49
 * @version V1.0.0
 *
 */
public class IdGenerator {
	@Getter(lazy = true) private static final IdGenerator instance = new IdGenerator(); //单例
	
	private final long twepoch = 1288834974657L; //时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
	private final long datacenterIdBits = 5L; //数据中心标识位数
    private final long workerIdBits = 5L; //机器标识位数
    private final long sequenceBits = 12L; //毫秒内自增位
    
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits); //机器ID最大值
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); //数据中心ID最大值
    private final long workerIdShift = sequenceBits; //机器ID偏左移12位
    private final long datacenterIdShift = sequenceBits + workerIdBits; //数据中心ID左移17位
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; //时间毫秒左移22位
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    
    private long workerId; //机器标识id部分
    private long datacenterId; //数据中心标识id部分
    private long sequence = 0L; //并发控制
    private long lastTimestamp = -1L; //上次生产id时间戳
    
    private IdGenerator(){
    	this.datacenterId = getDatacenterId(maxDatacenterId);
    	this.workerId = getMaxWorkerId(datacenterId, maxWorkerId);
    }
    
    /**
     * @param workerId 机器标识id
     * @param datacenterId 数据中心标识id
     */
    private IdGenerator(long workerId, long datacenterId){
    	if(workerId > maxWorkerId || workerId < 0){
    		throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
    	}
    	
    	if(datacenterId > maxDatacenterId || datacenterId < 0){    		
    		throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
    	}
    	
    	this.workerId = workerId;
    	this.datacenterId = datacenterId;
    }
    
    /**
	 * @Title: getMaxWorkerId
	 * @Description: 获取 maxWorkerId(MAC + PID 的 hashcode 获取16个低位)
	 * @author 杨鹏兵
	 * @date 2017年2月14日-下午5:48:57
	 * @param datacenterId
	 * @param maxWorkerId
	 * @return
	 * 
	 */
	private long getMaxWorkerId(long datacenterId, long maxWorkerId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(datacenterId);
		
		String name = ManagementFactory.getRuntimeMXBean().getName();
		if(StringUtils.isNotBlank(name)){
			buffer.append(name.split("@")[0]);
		}
		
		return (buffer.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
	}

	/**
	 * @Title: getDatacenterId
	 * @Description: 数据标识id部分
	 * @author 杨鹏兵
	 * @date 2017年2月14日-下午5:48:54
	 * @param maxDatacenterId
	 * @return
	 * 
	 */
	private long getDatacenterId(long maxDatacenterId) {
		long datacenterId = 0L;
		try {
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			if(null == network){
				datacenterId = 1L;
			}else{
				byte[] mac = network.getHardwareAddress(); // 获取mac地址
				datacenterId = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
				
				datacenterId %= maxDatacenterId + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datacenterId;
	}

	/**
	 * @Title: nextId
	 * @Description: 获取下一个Id
	 * @author 杨鹏兵
	 * @date 2017年2月14日-下午6:03:04
	 * @return
	 *
	 */
    public synchronized long nextId(){
    	long timestamp = timeGen();
    	if(timestamp < lastTimestamp){
    		throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
    	}
    	
    	if(timestamp == lastTimestamp){
    		sequence = (sequence + 1) & sequenceMask;
    		if(sequence == 0){
    			timestamp = tilNextMillis(lastTimestamp);
    		}
    	}else{
    		sequence = new SecureRandom().nextInt(10);
    	}
    	
    	lastTimestamp = timestamp;
    	
    	return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

	/**
	 * @Title: tilNextMillis
	 * @Description: TODO
	 * @author 杨鹏兵
	 * @date 2017年2月14日-下午5:29:15
	 * @param lastTimestamp
	 * @return
	 * 
	 */
	private long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while(timestamp <= lastTimestamp){
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * @Title: timeGen
	 * @Description: 获取系统的时间戳
	 * @author 杨鹏兵
	 * @date 2017年2月14日-下午5:26:26
	 * @return
	 * 
	 */
	private long timeGen() {
		return System.currentTimeMillis();
	}
	
	public static void main(String[] args) {		
		IdGenerator idGenerator = new IdGenerator();		
		for(int i = 0; i<1000; i++){
			System.out.println(idGenerator.nextId());
		}
	}
}
