package com.example.service.qa.utils;


import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;



@Slf4j
public class CommonUtils {

    /**
     * 判断是否是jar包启动的标志
     */
    private static final String BOOT_INFO_PATH = "BOOT-INF";

	private static final Pattern birthday = Pattern.compile("(\\d){4}-(\\d){2}-(\\d){2}");

    public static <T> boolean isNull(T... objects) {
        if (null == objects){
            return true;
        }
        for (T obj : objects) {
            if (null == obj) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean notNull(T... objects) {
        if (null == objects){
            return false;
        }
        for (T obj : objects) {
            if (null == obj) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(String... objects) {

        if (isNull(objects)){
            return true;
        }
        for (String obj : objects) {
            if (StringUtils.isNotBlank(obj)) {
                return false;
            }
        }
        return true;
    }


    public static boolean notBlank(String... objects) {
        if (isNull(objects)){
            return false;
        }
        for (String obj : objects) {
            if (StringUtils.isBlank(obj)) {
                return false;
            }
        }
        return true;
    }

    public static boolean notEmpty(Collection collection) {

        if (isNull(collection)) {
            return false;
        }
        return collection.size() > 0;
    }

    public static boolean isEmpty(Collection collection) {
        if (isNull(collection)) {
            return true;
        }
        return collection.size() <= 0;
    }

    public static <T extends Collection> boolean hasTheSameItem(T main, T second) {
        if (CommonUtils.notEmpty(main) && CommonUtils.notEmpty(second)) {
            for (Object objects : main) {
                if (second.contains(objects)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getFileldValue(Object obj, String methodName) {
        try {
            Method[] methods = obj.getClass().getDeclaredMethods();
            Method[] var3 = methods;
            int var4 = methods.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Method method = var3[var5];
                if (methodName.equals(method.getName())) {
                    Object result = method.invoke(obj);
                    if (CommonUtils.notNull(new Object[]{result})) {
                        return result.toString();
                    }

                    return null;
                }
            }

            return null;
        } catch (Exception var8) {
            log.error("反射获取属性值异常", var8);
            return null;
        }
    }




    /**
     * 对String类型的数据做非空判断,如果为空，返回空字符串
     *
     * @param value
     * @return
     */
    public static String getAndSetDefaultStr(String value) {

        return Optional.ofNullable(value)
                .filter(CommonUtils::notBlank)
                .orElse("");
    }

    /**
     * 对bigdecimal类型的数据做非空判断,如果值小于0,则返回0
     *
     * @param value
     * @return
     */
    public static BigDecimal getAndSetBigThenZero(BigDecimal value) {

        return Optional.ofNullable(value)
                .filter(s -> s.compareTo(BigDecimal.ZERO) > 0)
                .orElse(BigDecimal.ZERO);
    }

    /**
     * 对Long类型的数据做非空判断,如果值小于0,则返回0
     *
     * @param value
     * @return
     */
    public static Long getAndSetBigThenZero(Long value) {

        return Optional.ofNullable(value)
                .filter(s -> value > BigDecimal.ZERO.longValue())
                .orElse(BigDecimal.ZERO.longValue());
    }

    /**
     * 对Long类型的数据做非空判断,如果值小于0,则返回0
     *
     * @param value
     * @return
     */
    public static Integer getAndSetBigThenZero(Integer value) {

        return Optional.ofNullable(value)
                .filter(s -> value > BigDecimal.ZERO.longValue())
                .orElse(BigDecimal.ZERO.intValue());
    }

    /**
     * 检测传递的值是否为空或者小于0,如果为空或者小于0 则返回false
     *
     * @param value
     * @return
     */
    public static boolean verifyBigThanZero(BigDecimal value) {

        return Optional.ofNullable(value)
                .map(s -> s.compareTo(BigDecimal.ZERO) > BigDecimal.ZERO.intValue())
                .orElse(Boolean.FALSE);
    }

    /**
     * 检测传递的值是否为空或者小于0,如果为空或者小于0 则返回false
     *
     * @param value
     * @return
     */
    public static boolean verifyBigThanZero(Double value) {

        return Optional.ofNullable(value)
                .map(s -> s.compareTo(BigDecimal.ZERO.doubleValue()) > BigDecimal.ZERO.intValue())
                .orElse(Boolean.FALSE);
    }

    /**
     * 检测传递的值是否为负数
     *
     * @param value
     * @return
     */
    public static boolean isNegative(BigDecimal value) {
        return Optional.ofNullable(value)
                .map(v -> v.compareTo(BigDecimal.ZERO) < BigDecimal.ZERO.intValue())
                .orElse(Boolean.FALSE);
    }



    /**
     * 检测传递的Boolean对象是否为true
     *
     * @param value
     * @return
     */
    public static boolean verifiyBooleanValue(Boolean value) {

        return Optional.ofNullable(value)
                .filter(s -> s)
                .orElse(Boolean.FALSE);
    }

    /**
     * 检测传递的值是否为空或者小于0,如果为空或者小于0 则返回false
     *
     * @param value
     * @return
     */
    public static boolean verifyBigThanZero(Long value) {

        return Optional.ofNullable(value)
                .map(s -> s.longValue() > BigDecimal.ZERO.longValue())
                .orElse(Boolean.FALSE);
    }

    /**
     * 检测传递的值是否不小于0
     *
     * @param value
     * @return
     */
    public static boolean isNoLessThanZero(Long value) {

        return Optional.ofNullable(value)
                .map(v -> v.longValue() > BigDecimal.ZERO.longValue() || v.longValue() == BigDecimal.ZERO.longValue())
                .orElse(Boolean.FALSE);
    }

    /**
     * 检测传递的值是否不小于0
     *
     * @param value
     * @return
     */
    public static boolean isNoLessThanZero(Integer value) {

        return Optional.ofNullable(value)
                .map(v -> v.longValue() > BigDecimal.ZERO.longValue() || v.longValue() == BigDecimal.ZERO.longValue())
                .orElse(Boolean.FALSE);
    }



    /**
     * 检测传递的值是否为空或者小于0,如果为空或者小于0 则返回false
     *
     * @param value
     * @return
     */
    public static boolean verifyBigThanZero(Integer value) {

        return Optional.ofNullable(value)
                .map(s -> s.longValue() > BigDecimal.ZERO.longValue())
                .orElse(Boolean.FALSE);
    }


    /**
     * 检测传递的值是否为空或者小于0,如果为空或者小于0 则返回false
     *
     * @param value
     * @return
     */
    public static boolean verifyBigThanAndEqualZero(Integer value) {

        return Optional.ofNullable(value)
                .map(s -> s.longValue() >= BigDecimal.ZERO.longValue())
                .orElse(Boolean.FALSE);
    }


    /**
     * 检测传递的值是否为空或者小于0,如果为空或者小于0 则返回false
     *
     * @param value
     * @return
     */
    public static boolean verifyLittleThanZero(BigDecimal value) {

        return Optional.ofNullable(value)
                .map(s -> s.compareTo(BigDecimal.ZERO) < BigDecimal.ZERO.intValue())
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断输入的验证码是否为空,如果不为空随机生成验证码返回
     *
     * @param authCode
     * @return
     */
    public static String generateAuthCode(String authCode) {

        return Optional.ofNullable(authCode)
                .orElseGet(() -> {
                    DecimalFormat df = new DecimalFormat("######");
                    return df.format(Math.random() * 9999);
                });

    }


    /**
     * 对bigdecimal类型的数据做非空处理，如果不为空则返回,为空则赋值为0
     *
     * @param value
     * @return
     */
    public static BigDecimal getAndSetValue(BigDecimal value) {

        return Optional.ofNullable(value)
                .filter(s -> notNull(s))
                .orElse(BigDecimal.ZERO);
    }


    /**
     * 获取当前unix时间
     * @return 当前unix时间
     */
    public static Long getNowUnixTime(){
        return System.currentTimeMillis() / 1000L;
    }


//    public static String getAbsolutePath(String fileName) {
//        String absolutePath = FileUtil.getAbsolutePath("");
//        // 用jar包启动
//        String[] splitArr = absolutePath.split(SLASH);
//        Stream<String> stream = Stream.of(splitArr);
//        String baseUrl = "";
//        if (absolutePath.contains(BOOT_INFO_PATH)) {
//            baseUrl =  stream.limit(splitArr.length - 3).collect(Collectors.joining(SLASH));
//        } else {
//            // 其他方式启动
//            baseUrl =  stream.limit(splitArr.length - 2).collect(Collectors.joining(SLASH));
//        }
//        return baseUrl.concat(SLASH).concat(fileName);
//    }

	/**
	 * 根据birthdayType转换日期， 如果是阴历，需要将日期转成阳历，不区分闰年
	 * @param birthdayType
	 * @param customerBirthday
	 * @return
	 */
	public static String genBirthday(Boolean birthdayType, String customerBirthday){
		if (StringUtils.isBlank(customerBirthday)) {
			return StringUtils.EMPTY;
		}

		if (Objects.isNull(birthdayType)) {
			birthdayType = Boolean.FALSE;
		}

		// 阳历
		if (!birthdayType) {
			return genBirthday(customerBirthday);
		}

		// 阴历
		LunarSolarConverter.Lunar lunar = validateBirthdayIfNecessary(customerBirthday);
		if (Objects.isNull(lunar)) {
			return StringUtils.EMPTY;
		}

		LunarSolarConverter.Solar solar = LunarSolarConverter.lunarToSolar(lunar);
		DateTime dateTime = new DateTime(solar.getSolarYear(), solar.getSolarMonth(), solar.getSolarDay(), 0, 0);

		return dateTime.toString("MMdd");
	}

	/**
	 * 根据传递的生日参数获取四位数字的生日信息
	 * @param customerBirthday
	 * @return
	 */
	public static String genBirthday(String customerBirthday){

		if (notBlank(customerBirthday) && customerBirthday.indexOf("-") > -1){
			String[] birthdays = customerBirthday.split("-");
			if (birthdays.length == 3 ){
				String month = birthdays[1].length() == 2 ?  birthdays[1] : ConstantUtils.ZERO+birthdays[1];
				String day = birthdays[2].length() == 2 ?  birthdays[2] : ConstantUtils.ZERO+birthdays[2];
				return month + day;
			}
		}

		if (notBlank(customerBirthday) && customerBirthday.indexOf("-") == -1 && customerBirthday.length()==8) {
			String month = customerBirthday.substring(4,6);
			String day = customerBirthday.substring(6);
			return month + day;
		}

		return "";
	}

	/**
	 *
	 * 阴历转化阳历，阴历以当年为准，阳历以本年为准
	 * @param customerBirthday
	 * @return 本年的阳历日期
	 */
	private static LunarSolarConverter.Lunar validateBirthdayIfNecessary(String customerBirthday) {
		if (!birthday.matcher(customerBirthday).matches()) {
			return null;
		}

		if (!customerBirthday.contains("-") && customerBirthday.length() == 8) {
			customerBirthday =
					customerBirthday.substring(0, 4) + "-" + customerBirthday.substring(4, 6) + "-" + customerBirthday
							.substring(6);
		}

		List<String> strs = Splitter.on("-").trimResults().splitToList(customerBirthday);
		if (!CollectionUtils.isEmpty(strs) && strs.size() == 3) {
			LunarSolarConverter.Lunar lunar = new LunarSolarConverter.Lunar();
			int year = LocalDate.now().getYear();
			lunar.setLunarYear(year);
			lunar.setLunarMonth(Integer.parseInt(strs.get(1)));

			String day = strs.get(2);
			if (Integer.parseInt(day) >= 31) {
				day = "30";
			}

			lunar.setLunarDay(Integer.parseInt(day));

			return lunar;
		}

		return null;
	}

	public static String getIP() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (Objects.nonNull(sra)) {
			HttpServletRequest request = sra.getRequest();
			return getIP(request);
		}

		return StringUtils.EMPTY;
	}

    /**
     * 获取请求中的ip
     *
     * */
    public static String getIP(HttpServletRequest request){
        final String ipName = "unknown";
        String ip=request.getHeader("x-forwarded-for");
        if(ip==null || ip.length()==0 || ipName.equalsIgnoreCase(ip)){
            ip=request.getHeader("Proxy-Client-IP");
        }
        if(ip==null || ip.length()==0 || ipName.equalsIgnoreCase(ip)){
            ip=request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null || ip.length()==0 || ipName.equalsIgnoreCase(ip)){
            //X-Real-IP：nginx服务代理
            ip=request.getHeader("X-Real-IP");
        }
        if(ip==null || ip.length()==0 || ipName.equalsIgnoreCase(ip)){
            ip=request.getRemoteAddr();
            if("127.0.0.1".equals(ip)){
                //根据网卡取本机配置的IP
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

	/**
	 * 对bigdecimal类型的数据做非空判断
	 */
	public static BigDecimal getAndSetDefaultBigDecimal(BigDecimal value) {

		return Optional.ofNullable(value)
				.orElse(BigDecimal.ZERO);
	}
}
