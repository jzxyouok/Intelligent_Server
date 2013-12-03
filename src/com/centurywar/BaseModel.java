package com.centurywar;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

public class BaseModel {
	private int fromgameuid	= 0;
	protected String username = "wanbin";
	private int gameuid = 0;
	public static final int MAX = Integer.MAX_VALUE;
	public static final int MIN = (int) MAX / 2;
	private static JPushClient jpush = new JPushClient(
			"68c01e4660be175e19844c47", "550943fb320cf916b5a78c41");

	protected final static Log Log = LogFactory.getLog(BaseModel.class);

	public int getTime() {
		Date date = new Date();
		return (int) (date.getTime() / 1000);
	}

	/**
	 * 发送推送消息到手机客户端
	 * 
	 * @param gameuid
	 *            注意，这里写板子的id,里面会把所有手机的客户端都进行遍历的
	 * @param content
	 * @return
	 */
	public static boolean sendToPush(int gameuid, String title, String content) {
		UsersModel phone = new UsersModel(gameuid);
		if (phone.userName.length() > 0) {
			System.out.println("send Push to :" + phone.userName);
			MessageResult msgResult = jpush.sendNotificationWithAlias(
					getRandomSendNo(), phone.userName, title, content);
			if (null != msgResult) {
				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送成功， sendNo=" + msgResult.getSendno());
				} else {
					System.out.println("发送失败， 错误代码=" + msgResult.getErrcode()
							+ ", 错误消息=" + msgResult.getErrmsg());
					return false;
				}
			} else {
				System.out.println("无法获取数据");
				return false;
			}
		}
		return false;
	}

	public static int getRandomSendNo() {
		return (int) (MIN + Math.random() * (MAX - MIN));
	}

	public int getFromgameuid() {
		return fromgameuid;
	}

	public void setFromgameuid(int fromgameuid) {
		this.fromgameuid = fromgameuid;

	}

	public BaseModel() {
		// TODO Auto-generated constructor stub'
	}


	/**
	 * 返回数据集
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static JSONArray select(String sql) throws Exception {
		return JDBC.select(sql);
	}

	/**
	 * 返回一条记录
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static JSONObject selectOne(String sql) throws Exception {
		return JDBC.selectOne(sql);
	}

	/**
	 * 运行一条SQL update/delete
	 * 
	 * @param sql
	 * @return
	 */
	public static boolean query(String sql) {
		try {
			return JDBC.query(sql);
		} catch (Exception e) {
			Log.error(e.toString());
		}
		return false;
	}

	public static String getBehaver(int type, int pik, int commmand, int value) {
		return String.format("%d_%d_%d_%d", type, pik, commmand, value);
	}
}
