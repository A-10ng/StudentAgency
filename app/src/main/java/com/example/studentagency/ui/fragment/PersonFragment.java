package com.example.studentagency.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.lemonhello.LemonHello;
import com.example.lemonhello.LemonHelloAction;
import com.example.lemonhello.LemonHelloInfo;
import com.example.lemonhello.LemonHelloView;
import com.example.lemonhello.interfaces.LemonHelloActionDelegate;
import com.example.studentagency.R;
import com.example.studentagency.Utils.ActivityCollector;
import com.example.studentagency.Utils.BlurUtils;
import com.example.studentagency.Utils.DateUtils;
import com.example.studentagency.asyncTask.GetBitmapTask;
import com.example.studentagency.bean.UserBean;
import com.example.studentagency.mvp.presenter.PersonFragmentBasePresenter;
import com.example.studentagency.mvp.view.PersonFragmentBaseView;
import com.example.studentagency.ui.activity.CreditScoreRecordActivity;
import com.example.studentagency.ui.activity.LoginActivity;
import com.example.studentagency.ui.activity.ModifyPwdActivity;
import com.example.studentagency.ui.activity.MyApp;
import com.example.studentagency.ui.activity.PersonIndentActivity;
import com.example.studentagency.ui.activity.PersonalInfoActivity;
import com.example.studentagency.ui.activity.PublishActivity;
import com.example.studentagency.ui.widget.ChoosePicPopupWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.studentagency.ui.activity.MyApp.hadLogin;
import static com.example.studentagency.ui.activity.MyApp.userId;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/11/02
 * desc:
 */
public class PersonFragment extends Fragment implements View.OnClickListener, PersonFragmentBaseView {

    private static final String TAG = "PersonFragment";
    private static final int WRITE_EXTERNAL_STORAGE = 200;
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final String IMAGE_FILE_NAME = "sa_user_avatar.jpg";
    private SmartRefreshLayout smartRefreshLayout;
    private PersonFragmentBasePresenter presenter = new PersonFragmentBasePresenter(this);
    private Uri userAvatarUri;//保存用户头像的URI

    //根视图
    private View root;

    //头像及背景
    private ChoosePicPopupWindow choosePicPopupWindow;
    private ImageView iv_avatar, iv_avatar_bg;
    private TextView tv_username, tv_balance, tv_creditScore;

    //个人订单
    private RelativeLayout layout_personalIndent;

    //信誉积分
    private RelativeLayout layout_creditScoreRecord;

    //修改个人信息
    private RelativeLayout layout_personalInfo;

    //修改密码
    private RelativeLayout layout_modifyPwd;

    //退出登录
    private RelativeLayout layout_exitLogin;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
                if (hadLogin) {
                    //弹窗相册或相机popupwindow
                    showChoosePicPopupWindow();
                } else {
                    //还未登录则前往登录
                    goToLogin();
                }
                break;
            case R.id.layout_personalIndent:
                startActivity(new Intent(getActivity(), PersonIndentActivity.class));
                break;
            case R.id.layout_creditScoreRecord:
                startActivity(new Intent(getActivity(), CreditScoreRecordActivity.class));
                break;
            case R.id.layout_personalInfo:
                startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
                break;
            case R.id.layout_modifyPwd:
                startActivity(new Intent(getActivity(), ModifyPwdActivity.class));
                break;
            case R.id.layout_exitLogin:
                showEnsureExitLoginDialog();
                break;
        }
    }

    private void showChoosePicPopupWindow() {
        choosePicPopupWindow = new ChoosePicPopupWindow(getActivity());
        choosePicPopupWindow.setClickItemListener(new ChoosePicPopupWindow.ClickItemListener() {
            @Override
            public void clickItem(int position) {
                if (0 == position) {
                    Log.i(TAG, "clickItem: 点击了拍照");
                    choosePicPopupWindow.dismiss();
                }
                else if (1 == position) {
                    Log.i(TAG, "clickItem: 点击了从相册中选择");
                    choosePicPopupWindow.dismiss();

                    //首先判断是否拥有权限
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //如果权限还未授权，进行申请
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
                    } else {
                        //打开系统相册
                        openSystemAlbum();
                    }
                }
                else {
                    Log.i(TAG, "clickItem: 点击了取消");
                    choosePicPopupWindow.dismiss();
                }
            }
        });
        choosePicPopupWindow.showAsDropDown(iv_avatar);
    }

    private void goToLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private void showEnsureExitLoginDialog() {
        LemonHello.getInformationHello("提示", "您确定要退出登录吗？")
                .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        Log.i(TAG, "onClick: 取消退出登录");
                        lemonHelloView.hide();
                    }
                }))
                .addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        Log.i(TAG, "onClick: 确定退出登录");
                        lemonHelloView.hide();

                        ActivityCollector.finishAll();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                }))
                .show(getActivity());
    }

    public void openSystemAlbum() {
        //如果已经授权
        Intent readAlbumIntent = new Intent(Intent.ACTION_PICK);
        readAlbumIntent.setType("image/*");
        //判断系统中是否有处理该Intent的activity
        if (readAlbumIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(readAlbumIntent, REQUEST_CODE_PICK_IMAGE);
        } else {
            LemonBubble.showError(this, "未找到图片查看器", 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri pickedImageUri;

        //resultCode参数为返回的信息,RESULT_CANCELED表示失败
        if (resultCode == RESULT_CANCELED) {
            Log.i(TAG, "onActivityResult: 当前未选择任何图片");
        }
        //RESULT_OK表示成功
        else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //从相册中选择
                case REQUEST_CODE_TAKE_PHOTO:
                    Log.i(TAG, "onActivityResult: REQUEST_CODE_TAKE_PHOTO");
                    break;
                //拍照
                case REQUEST_CODE_PICK_IMAGE:
                    Log.i(TAG, "onActivityResult: REQUEST_CODE_PICK_IMAGE");

                    //返回选择的图片的URI
                    pickedImageUri = data.getData();

                    //通过URI方式返回，部分手机可能URI为空
                    Bitmap bitmap = null;
                    if (pickedImageUri != null) {
                        //将返回的图片进行压缩，不然图片像素过大，可能导致OOM
                        try {
                            bitmap = getCompressBitmap(pickedImageUri);
                        } catch (IOException e) {
                            Log.i(TAG, "onActivityResult: IOException");
                            e.printStackTrace();
                        }
                    }
                    //部分手机返回的URI为空时
                    else {
                        //部分手机可能直接存放在bundle中
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            bitmap = bundle.getParcelable("data");
                        }
                    }
                    initAvatarAndBG(bitmap);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult: WRITE_EXTERNAL_STORAGE>>>>>SUCCESS");
                    openSystemAlbum();
                } else {
                    Log.i(TAG, "onRequestPermissionsResult: WRITE_EXTERNAL_STORAGE>>>>>FAIL");
                    LemonBubble.showError(this, "您已拒绝授权，请前往设置开启！", 1500);
                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_person, container, false);

        initAllViews();

        initSmartRefreshLayout();

        if (hadLogin) {
            //发送请求
            presenter.getPersonFragmentInfo(MyApp.userId);
        } else {
            initAvatarAndBG(R.drawable.icon_upload_avatar);
        }


        return root;
    }

    private void initAllViews() {
        smartRefreshLayout = root.findViewById(R.id.smartRefreshLayout);

        //头像区
        iv_avatar = root.findViewById(R.id.iv_avatar);
        iv_avatar.setOnClickListener(this);

        iv_avatar_bg = root.findViewById(R.id.iv_avatar_bg);
        tv_username = root.findViewById(R.id.tv_username);
        tv_balance = root.findViewById(R.id.tv_balance);
        tv_creditScore = root.findViewById(R.id.tv_creditScore);

        //个人订单
        layout_personalIndent = root.findViewById(R.id.layout_personalIndent);
        layout_personalIndent.setOnClickListener(this);

        //信誉积分
        layout_creditScoreRecord = root.findViewById(R.id.layout_creditScoreRecord);
        layout_creditScoreRecord.setOnClickListener(this);

        //修改个人信息
        layout_personalInfo = root.findViewById(R.id.layout_personalInfo);
        layout_personalInfo.setOnClickListener(this);

        //修改密码
        layout_modifyPwd = root.findViewById(R.id.layout_modifyPwd);
        layout_modifyPwd.setOnClickListener(this);

        //退出登录
        layout_exitLogin = root.findViewById(R.id.layout_exitLogin);
        layout_exitLogin.setOnClickListener(this);
    }

    private void initSmartRefreshLayout() {
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (hadLogin) {
                    //发送请求
                    presenter.getPersonFragmentInfo(MyApp.userId);
                } else {
                    initAvatarAndBG(R.drawable.icon_upload_avatar);
                    smartRefreshLayout.finishRefresh();
                }
            }
        });
    }

    private void initAvatarAndBG(int resId) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(getActivity())
                .load(resId)
                .placeholder(R.drawable.placeholder_pic)
                .apply(requestOptions)
                .into(iv_avatar);

        iv_avatar_bg.setBackground(new ColorDrawable(0x1296db));
        iv_avatar_bg.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.themeColor));
        iv_avatar_bg.setImageBitmap(null);
    }

    private Bitmap getCompressBitmap(Uri pickedImageUri) throws IOException {
        InputStream input = getActivity().getContentResolver().openInputStream(pickedImageUri);

        //这一段代码是不加载文件到内存中也得到bitmap的真实宽高，主要是设置inJustDecodeBounds为true
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;//不加载到内存
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;

        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        input = getActivity().getContentResolver().openInputStream(pickedImageUri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    private void initAvatarAndBG(Bitmap bitmap) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(getActivity())
                .load(bitmap)
                .placeholder(R.drawable.placeholder_pic)
                .apply(requestOptions)
                .into(iv_avatar);

        //设置头像背景虚化
//                Bitmap bitmapBackground = ((BitmapDrawable) iv_avatar_gone.getDrawable()).getBitmap();
        iv_avatar_bg.setBackgroundColor(Color.TRANSPARENT);
        iv_avatar_bg.setImageBitmap(BlurUtils.fastBlur(bitmap, 15));

    }

    private Bitmap compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            if (options <= 0)
                break;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap resultBitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return resultBitmap;
    }

    @Override
    public void getPersonFragmentInfoSuccess(UserBean userBean) {
        Log.i(TAG, "getPersonInfoSuccess: userBean>>>>>" + userBean.toString());

        initAvatarAndBG(userBean.getAvatar());

        tv_username.setText(userBean.getUsername());
        tv_balance.setText("余额：" + userBean.getBalance());
        tv_creditScore.setText("信誉积分：" + userBean.getCreditScore());

        smartRefreshLayout.finishRefresh();
    }

    private void initAvatarAndBG(String picPath) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(getActivity())
                .load(picPath)
                .placeholder(R.drawable.placeholder_pic)
                .apply(requestOptions)
                .into(iv_avatar);

        //获取bitmap
        GetBitmapTask getBitmapTask = new GetBitmapTask();
        getBitmapTask.setGetBitmapListener(new GetBitmapTask.GetBitmapListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                //设置头像背景虚化
//                Bitmap bitmapBackground = ((BitmapDrawable) iv_avatar_gone.getDrawable()).getBitmap();
                iv_avatar_bg.setBackgroundColor(Color.TRANSPARENT);
                iv_avatar_bg.setImageBitmap(BlurUtils.fastBlur(bitmap, 15));
            }
        });
        List<Object> params = new ArrayList<>();
        params.add(getActivity());
        params.add(picPath);
        getBitmapTask.execute(params);

    }

    @Override
    public void getPersonFragmentInfoFail() {
        Log.i(TAG, "getPersonInfoFail: ");

        initAvatarAndBG(R.drawable.avatar_male);

        smartRefreshLayout.finishRefresh();
    }
}
