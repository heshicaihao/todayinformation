package com.heshicaihao.todayinformation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.heshicaihao.todayinformation.base.SuperActivity;
import com.heshicaihao.todayinformation.bean.User;
import com.heshicaihao.todayinformation.utils.CharCheckUtils;
import com.heshicaihao.todayinformation.R;


public class LoginActivity extends SuperActivity implements OnClickListener {

    private View view;
    private EditText phone;
    private EditText vCode;
    private TextView getVCode;
    private TextView login;
    private ImageView delete;            // 输入的删除按钮
    private ImageView check;

    private boolean flag = true;

    private int countSeconds = 60;        // 倒数秒数

    @SuppressLint("HandlerLeak")
    private Handler mCountHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (countSeconds > 0) {
                --countSeconds;
                getVCode.setText("发送验证码" + "(" + countSeconds + ")");
                mCountHanlder.sendEmptyMessageDelayed(0, 1000);
            } else {
                countSeconds = 60;
                getVCode.setText("发送验证码");
            }

        }
    };

    // 开始倒计时
    private void startCountBack() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getVCode.setText(countSeconds + "");
                mCountHanlder.sendEmptyMessage(0);
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(mContext, R.layout.activity_login, null);
        setContentView(view);

        phone = (EditText) view.findViewById(R.id.input_phone);
        vCode = (EditText) view.findViewById(R.id.code);
        login = (TextView) view.findViewById(R.id.login);
        getVCode = (TextView) view.findViewById(R.id.get_code);
        ImageView exit = (ImageView) view.findViewById(R.id.exit);
        delete = (ImageView) view.findViewById(R.id.input_delete);
        exit.setOnClickListener(this);
        delete.setOnClickListener(this);
        getVCode.setOnClickListener(this);
        login.setOnClickListener(this);
        delete.setVisibility(View.GONE);

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    delete.setVisibility(View.VISIBLE);
                } else {
                    delete.setVisibility(View.GONE);
                }
            }
        });

        check = (ImageView) this.findViewById(R.id.check);
        check.setOnClickListener(this);
        this.findViewById(R.id.user_tx).setOnClickListener(this);
        this.findViewById(R.id.user_ll).setOnClickListener(this);
        this.findViewById(R.id.login_qq).setOnClickListener(this);
        this.findViewById(R.id.login_weibo).setOnClickListener(this);

    }

    @Override
    public void retry() {

    }

    @Override
    public void netError() {

    }

    @Override
    public void pwdError() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_code:                    // 获取手机号发送请求向手机发送验证码
                hideSoftInput(this, phone);
                if (countSeconds != 60) {
                    showToast("您的验证码已发送!");
                    return;
                }
                String phoneStr = phone.getText().toString();
                if (CharCheckUtils.isPhoneNum(phoneStr)) {
                    showProgressDialog();
                    showToast("发送成功");
                    startCountBack();
                } else {
                    showToast("输入的手机号码有误!");
                }
                break;

            case R.id.login:
                hideSoftInput(this, vCode);
                if (LoginCheck()) {
                    User user = new User();
                    user.name = "资讯爱好者";
                    user.uId = "111842201";
                    user.gender = 1;
                    user.birthday = System.currentTimeMillis() / 1000;
                    user.phone = phone.getText().toString();
                    Toast.makeText(mContext, "您好，" + user.name, Toast.LENGTH_SHORT).show();
                    mUserController.saveUserInfo(user);
                    Intent intent = new Intent(mContext, MyInfoActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.exit:
                finish();
                break;
            case R.id.input_delete:
                phone.setText("");
                delete.setVisibility(View.GONE);
                break;

            case R.id.check:                    //是否勾选用户协议
                if (flag) {
                    flag = false;
                    login.setEnabled(false);
                    login.setBackgroundResource(R.color.C8C8C8);
                    check.setImageResource(R.drawable.check_2);
                } else {
                    flag = true;
                    login.setEnabled(true);
                    login.setBackgroundColor(Color.WHITE);
                    check.setImageResource(R.drawable.check_1);
                }
                break;

            case R.id.user_ll:                    //用户协议
            case R.id.user_tx:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.login_qq:                    //QQ登录
                break;
            case R.id.login_weibo:                //微博登录
                break;
            default:
                break;
        }

    }


    /**
     * 登录参数校验
     *
     * @return false || true
     */
    public boolean LoginCheck() {
        if (!CharCheckUtils.isPhoneNum(phone.getText().toString())) {
            showToast("您输入的手机号码有误，请重新输入!");
            return false;
        }
        if (!CharCheckUtils.isAllDigit(vCode.getText().toString())) {
            showToast("您输入的验证码有误，请重新输入!");
            return false;
        }
        return true;
    }

    @Override
    protected void obtainInfo() {

    }

}
