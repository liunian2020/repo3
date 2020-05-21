package com.strive.springcloud.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class StringUtil {
	
	private static StringUtil instanceObject;
	
	/**
	 * @alias 数字大写
	 */
	private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍",
			"陆", "柒", "捌", "玖" };
	/**
	 * @alias 金额位数名称
	 */
	private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰",
			"仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
	private static final String[] DUNIT = { "角", "分", "厘" };

	public StringUtil() {
	}
	
	public static synchronized StringUtil getInstance() {
		if (instanceObject == null) {
			instanceObject = new StringUtil();
		}
		return instanceObject;
	}
	/**
	 * 字符串非空判断
	 * @param object
	 * @param message
	 */
	public static void notNullString(Object object, String message) {
		if (object == null || object.toString().trim().length() <= 0) {
			throw new IllegalArgumentException(message);
		}
	}
	public static boolean isNull(Object data) {
		if(null == data || data.toString().length()<=0){
			return true;
		}
		return false;
	}
	public static BigDecimal getNumberValue(Object data) {
		if(isNull(data)) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(data.toString());
	}
	
	 /**
     * 日期偏移天数
     * @param t Timestamp
     * @param l long
     * @return Timestamp
     */
    public static Timestamp rollDate(Timestamp t, long l)
    {
        return new Timestamp(t.getTime() + l * 24 * 60 * 60 * 1000);
    }
    
    public static String getString(Timestamp d, String format)
    {
        if (d == null)
            return "";
        
        return getString(new Date(d.getTime()), format);
    }
    
    public static String getString(Date d, String format)
    {
        if (d == null)
            return "";
        try
        {
            return new SimpleDateFormat(format).format(d);
        } catch (Exception e)
        {
            return "";
        }
    }
    /**
     * 根据数据字符和日期格式获得时间
     * @return
     */
    public static Timestamp getTimestamp(String s, String format)
    {
        return getTimestamp(getDate(s, format));
    }
    public static Timestamp getTimestamp(Date d)
    {
        if (d == null)
            return null;
        return new Timestamp(d.getTime());
    }
    /**
     * 字符串转换为日期
     * @param s String
     * @param format String (yyyy/MM/dd HH:mm:ss)
     * @return Date
     */
    public static Date getDate(String s, String format)
    {
        if(s == null || s.length() == 0)
            return null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date d = sdf.parse(s);
            return d;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    
	/**
	 * 金额默认值为0
	 * @param clazz
	 * @param object
	 * @throws Exception
	 */
	public static void getDefaultAmtValue(Class clazz,Object object) throws Exception {
			// 获得类
			// 获取实体类的所有属性信息，返回Field数组
			Field[] fields = clazz.getDeclaredFields();
			//String name = "";
			for (Field field : fields) {
				String type = field.getGenericType().getTypeName().toString();
				if (type.equals("java.math.BigDecimal")) {
					field.setAccessible(true);  
					field.set(object,BigDecimal.ZERO);
				}
			}
	}
	
	/**
	 * 作废金额冲负使用
	 * @param clazz
	 * @param object
	 * @throws Exception
	 */
	public static void getDefaultAmtNegativeValue(Class clazz,Object object) throws Exception {
			// 获得类
			// 获取实体类的所有属性信息，返回Field数组
			Field[] fields = clazz.getDeclaredFields();
			//String name = "";
			for (Field field : fields) {
				String type = field.getGenericType().getTypeName().toString();
				if (type.equals("java.math.BigDecimal")) {
					field.setAccessible(true);  
					field.set(object,BigDecimal.ZERO);
				}
			}
	}
	
	/**
	 * 作废金额冲负使用
	 * @param clazz
	 * @param object
	 * @throws Exception
	 */
	public static void getReAmtValue(Class clazz,Object object) throws Exception {
			// 获得类
			// 获取实体类的所有属性信息，返回Field数组
			Field[] fields = clazz.getDeclaredFields();
			//String name = "";
			BigDecimal num = null;
			for (Field field : fields) {
				String type = field.getGenericType().getTypeName().toString();
				if (type.equals("java.math.BigDecimal")) {
					field.setAccessible(true);  
					Object value = field.get(object); // 调用getter方法获取属性值
					if(value != null) {
						num = new BigDecimal(value.toString());
						if(num.compareTo(BigDecimal.ZERO) != 0)
							field.set(object,num.multiply(new BigDecimal(-1)));
					}
				}
			}
	}
	/** 
     * 将map转变成Class 
     * @param map
     * @return 
     * @throws Exception 
     */  
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
		 if (map == null)  
	            return null; 
	  
	        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass); // 获取类属性    
	        Object obj = beanClass.newInstance(); // 创建 JavaBean 对象    
	    
	        // 给 JavaBean 对象的属性赋值    
	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();    
	        for (int i = 0; i< propertyDescriptors.length; i++) {    
	            PropertyDescriptor descriptor = propertyDescriptors[i];    
	            String propertyName = descriptor.getName();    
	            String type  =descriptor.getPropertyType().getTypeName();
	    
	            if (map.containsKey(propertyName)) {    
	                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。    
	                Object value = map.get(propertyName);    
	    
	                Object[] args = new Object[1];    
	                  
	                if(type.equals("java.math.BigDecimal")) {
	                	if(isNull(value) || value.equals("null")) {
	                		 args[0] = BigDecimal.ZERO;
	                	}else {
	                		 args[0] = new BigDecimal(value.toString()); 
	                	}
	                }
	                else if(type.equals("java.util.Date") && null != value){
		                continue;
	                }
	                else {
	                	args[0] = value;
	                }
	                descriptor.getWriteMethod().invoke(obj, args);    
	            }    
	        }    
	        return obj;   
   }
	/**
	 * 取数字转大写
	 * 
	 * @param num
	 *            double
	 * @return String
	 */
	public String numberToWord(double num) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		boolean flg;
		if (num == 0) {
			return "零元整";
		}
		if (num < 0) {
			num = 0 - num;
			flg = true;
		} else
			flg = false;
		String str=df.format(num);
		str = str.replaceAll(",", "");// 去掉","
		String integerStr;// 整数部分数字
		String decimalStr;// 小数部分数字

		// 初始化：分离整数部分和小数部分
		if (str.indexOf(".") > 0) {
			integerStr = str.substring(0, str.indexOf("."));
			decimalStr = str.substring(str.indexOf(".") + 1);
		} else if (str.indexOf(".") == 0) {
			integerStr = "";
			decimalStr = str.substring(1);
		} else {
			integerStr = str;
			decimalStr = "";
		}
		// integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
		if (!integerStr.equals("")) {
			integerStr = Long.toString(Long.parseLong(integerStr));
			if (integerStr.equals("0")) {
				integerStr = "";
			}
		}
		// overflow超出处理能力，直接返回
		if (integerStr.length() > IUNIT.length) {
			System.out.println(str + ":超出处理能力");
			return str;
		}

		int[] integers = toArray(integerStr);// 整数部分数字
		boolean isMust5 = isMust5(integerStr);// 设置万单位
		int[] decimals = toArray(decimalStr);// 小数部分数字
		String result=getChineseInteger(integers, isMust5)
		+ getChineseDecimal(decimals);
		if (flg)
			return "负" + result.toString() + "整";
		else
			if(result.toString().indexOf("分")!=-1){
				return result.toString();
			}else{
				return result.toString() + "整";
			}
	}
	
	private static int[] toArray(String number) {
		int[] array = new int[number.length()];
		for (int i = 0; i < number.length(); i++) {
			array[i] = Integer.parseInt(number.substring(i, i + 1));
		}
		return array;
	}
	
	private static boolean isMust5(String integerStr) {
		int length = integerStr.length();
		if (length > 4) {
			String subInteger = "";
			if (length > 8) {
				// 取得从低位数，第5到第8位的字串
				subInteger = integerStr.substring(length - 8, length - 4);
			} else {
				subInteger = integerStr.substring(0, length - 4);
			}
			return Integer.parseInt(subInteger) > 0;
		} else {
			return false;
		}
	}
	
	private static String getChineseInteger(int[] integers, boolean isMust5) {
		StringBuffer chineseInteger = new StringBuffer("");
		int length = integers.length;
		for (int i = 0; i < length; i++) {
			// 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
			// 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)
			String key = "";
			if (integers[i] == 0) {
				if ((length - i) == 13)// 万(亿)(必填)
					key = IUNIT[4];
				else if ((length - i) == 9)// 亿(必填)
					key = IUNIT[8];
				else if ((length - i) == 5 && isMust5)// 万(不必填)
					key = IUNIT[4];
				else if ((length - i) == 1)// 元(必填)
					key = IUNIT[0];
				// 0遇非0时补零，不包含最后一位
				if ((length - i) > 1 && integers[i + 1] != 0)
					key += NUMBERS[0];
			}
			chineseInteger.append(integers[i] == 0 ? key
					: (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
		}
		return chineseInteger.toString();
	}
	
	private static String getChineseDecimal(int[] decimals) {
		StringBuffer chineseDecimal = new StringBuffer("");
		for (int i = 0; i < decimals.length; i++) {
			// 舍去3位小数之后的
			if (i == 3)
				break;
			chineseDecimal.append(decimals[i] == 0 ? ""
					: (NUMBERS[decimals[i]] + DUNIT[i]));
		}
		return chineseDecimal.toString();
	}
	
	/**
     * 得到系统时间Date类型
     * @return 
	 * @throws
     */
    public static Timestamp getDate() {
    	//创建一个时间对象，获取到当前的时间
		Date date= new Date();
		//设置时间显示格式
		SimpleDateFormat dangqianDateGeShiHua = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dangqianDateGeShiHua.format(date);
		return Timestamp.valueOf(str); 
    }
	
	
	/**
	 * 票号加一方法
	 * @param testStr
	 * @return
	 */
	public static String addOne(String testStr){
	    String[] strs = testStr.split("[^0-9]");//根据不是数字的字符拆分字符串
	    String numStr = strs[strs.length-1];//取出最后一组数字
	    if(numStr != null && numStr.length()>0){//如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
	        int n = numStr.length();//取出字符串的长度
	        BigInteger b = new BigInteger(numStr);
			String added = String.valueOf(b.add(BigInteger.ONE));
	        n = Math.min(n, added.length());
	        //拼接字符串
	        return testStr.subSequence(0, testStr.length()-n)+added;
	    }else{
	        throw new NumberFormatException();
	    }
	}
	/**  
	 * JavaBean对象转化成Map对象    
	 * @param javaBean    
	 * @return    
	 * @author   
	 */
	public static Map java2Map(Object javaBean) {
		Map map = new HashMap();
		try {
			// 获取javaBean属性
			BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			if (propertyDescriptors != null && propertyDescriptors.length > 0) {
				String propertyName = null; // javaBean属性名
				Object propertyValue = null; // javaBean属性值
				for (PropertyDescriptor pd : propertyDescriptors) {
					propertyName = pd.getName();
					if (!propertyName.equals("class")) {
						Method readMethod = pd.getReadMethod();
						propertyValue = readMethod.invoke(javaBean, new Object[0]);
						map.put(propertyName, propertyValue);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	

}
