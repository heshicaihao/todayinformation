package com.heshicaihao.todayinformation.common;

/**
 * 拥有id的任务
 * 
 * @author heshicaihao 2015年4月30日
 */
public abstract class DataTask {

	protected int taskID;

	public DataTask(int id) {
		this.taskID = id;
	}

	/**
	 * 得到任务ID
	 * 
	 * @return
	 */
	public int getID() {
		return taskID;
	}
	
	public void run() {
	
	}
	
}
