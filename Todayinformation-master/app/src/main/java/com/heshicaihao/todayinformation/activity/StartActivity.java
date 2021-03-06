package com.heshicaihao.todayinformation.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.heshicaihao.todayinformation.MainActivity;
import com.heshicaihao.todayinformation.R;
import com.heshicaihao.todayinformation.base.BaseActivity;
import com.heshicaihao.todayinformation.utils.SharedUtils;

/**
 * 启动界面
 * @author heshicaihao 2015年4月13日
 */
public class StartActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);

		isNormalStart = true;

		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {

					public void run() {
						if (SharedUtils.getGuided(mContext)) {
							Intent intent = new Intent(mContext,
									MainActivity.class);
							startActivity(intent);
							StartActivity.this.finish();
						} else {
							Intent intent = new Intent(StartActivity.this,
									WelcomeActivity.class);
							StartActivity.this.startActivity(intent);
							StartActivity.this.finish();
						}

					}
				});
			}
		}, 2000);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void retry() {
	}

	@Override
	public void netError() {
	}

	@Override
	protected void obtainInfo() {
	}

}
