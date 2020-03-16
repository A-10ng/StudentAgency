package com.example.studentagency.ui.fragment.StudentVerifyFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.studentagency.R;
import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.presenter.UnverifyFragmentBasePresenter;
import com.example.studentagency.mvp.view.UnverifyFragmentBaseView;
import com.example.studentagency.ui.widget.ChoosePicPopupWindow;
import com.example.studentagency.utils.DateUtils;
import com.example.studentagency.utils.FileUtils;
import com.example.studentagency.utils.ImageUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/17
 * desc:
 */
public class UnverifyFragment extends Fragment implements UnverifyFragmentBaseView {

    private static final String TAG = "UnverifyFragment";
    private static final int CAMERA = 200;
    private static final int WRITE_EXTERNAL_STORAGE = 201;
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final String IMAGE_FILE_NAME = "sa_user_verifypic.jpg";
    private View root;
    private UnverifyFragmentBasePresenter presenter = new UnverifyFragmentBasePresenter(this);
    private ChoosePicPopupWindow choosePicPopupWindow;

    //上传图
    private ImageView iv_verifyPic;
    private Bitmap defaultBitmap = null;
    private Bitmap currentBitmap = null;
    private Bitmap takePhotoBitmap = null;
    private Bitmap pickPhotoBitmap = null;
    private Uri userVerifyPicUri;//保存验证图的URI
    private boolean isPickPhoto = true;

    //上传按钮
    private Button btn_upload;

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
                //拍照
                case REQUEST_CODE_TAKE_PHOTO:
                    Log.i(TAG, "onActivityResult: REQUEST_CODE_TAKE_PHOTO");

                    takePhotoBitmap = null;
                    try {
                        int takePhotoDegree = ImageUtils.readPictureDegree(FileUtils.getFPUriToPath(getActivity(), userVerifyPicUri));

                        Log.i(TAG, "onActivityResult: degree>>>>>" + takePhotoDegree + "\n"
                                + "userAvatarUri>>>>>" + userVerifyPicUri + "\n"
                                + "userAvatarUriPath>>>>>" + FileUtils.getFPUriToPath(getActivity(), userVerifyPicUri));

                        //压缩图片
                        takePhotoBitmap = getCompressBitmap(userVerifyPicUri);

                        //旋转图片
                        takePhotoBitmap = ImageUtils.rotateBitmap(takePhotoDegree, takePhotoBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    iv_verifyPic.setImageBitmap(takePhotoBitmap);
                    break;
                //从相册中选择
                case REQUEST_CODE_PICK_IMAGE:
                    Log.i(TAG, "onActivityResult: REQUEST_CODE_PICK_IMAGE");

                    //返回选择的图片的URI
                    pickedImageUri = data.getData();

                    //通过URI方式返回，部分手机可能URI为空
                    pickPhotoBitmap = null;
                    if (pickedImageUri != null) {
                        Log.i(TAG, "onActivityResult: pickedImageUri != null \n " +
                                "pickedImageUri>>>>>" + pickedImageUri);

                        int pickPhotoDegree = ImageUtils.readPictureDegree(FileUtils.getRealPathFromURI(getActivity(), pickedImageUri));

                        Log.i(TAG, "onActivityResult: pickPhotoDegree>>>>>" + pickPhotoDegree);
                        //将返回的图片进行压缩，不然图片像素过大，可能导致OOM
                        try {
                            pickPhotoBitmap = getCompressBitmap(pickedImageUri);

                            pickPhotoBitmap = ImageUtils.rotateBitmap(pickPhotoDegree, pickPhotoBitmap);
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
                            pickPhotoBitmap = bundle.getParcelable("data");
                        }
                    }

                    iv_verifyPic.setImageBitmap(pickPhotoBitmap);
                    break;
            }
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult: CAMERA>>>>>SUCCESS");
                    takePhoto();
                } else {
                    Log.i(TAG, "onRequestPermissionsResult: CAMERA>>>>>FAIL");
                    LemonBubble.showError(this, "您已拒绝授权，请前往设置开启！", 1500);
                }
                break;
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
        root = inflater.inflate(R.layout.studentverify_fragment_unverify, container, false);

        initViews();

        return root;
    }

    private void initViews() {
        //上传图
        iv_verifyPic = root.findViewById(R.id.iv_verifyPic);
        defaultBitmap = ((BitmapDrawable) iv_verifyPic.getDrawable()).getBitmap();
        iv_verifyPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicPopupWindow();
            }
        });

        //上传按钮
        btn_upload = root.findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBitmap = ((BitmapDrawable) iv_verifyPic.getDrawable()).getBitmap();
                //如果用户还未选择上传图
                if (currentBitmap.equals(defaultBitmap)) {
                    Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
                    toast.setText("请选择图片！");
                    toast.show();
                }
                else {

                    LemonBubble.getRoundProgressBubbleInfo()
                            .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                            .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                            .setBubbleSize(200, 50)
                            .setProportionOfDeviation(0.1f)
                            .setTitle("上传中...")
                            .show(getActivity());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            File VerifyPicFile = null;
                            try {
                                VerifyPicFile = FileUtils.bitmapToFile(currentBitmap, DateUtils.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            presenter.uploadVerifyPic(VerifyPicFile);
                        }
                    }, 1500);
                }
            }
        });
    }

    private void showChoosePicPopupWindow() {
        choosePicPopupWindow = new ChoosePicPopupWindow(getActivity());
        choosePicPopupWindow.setClickItemListener(new ChoosePicPopupWindow.ClickItemListener() {
            @Override
            public void clickItem(int position) {
                if (0 == position) {
                    Log.i(TAG, "clickItem: 点击了拍照");
                    choosePicPopupWindow.dismiss();

                    isPickPhoto = false;

                    //首先判断是有拥有权限
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA);
                    } else {
                        //打开系统相机拍照
                        takePhoto();
                    }
                } else if (1 == position) {
                    Log.i(TAG, "clickItem: 点击了从相册中选择");
                    choosePicPopupWindow.dismiss();

                    isPickPhoto = true;

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
                } else {
                    Log.i(TAG, "clickItem: 点击了取消");
                    choosePicPopupWindow.dismiss();
                }
            }
        });
        choosePicPopupWindow.showAsDropDown(iv_verifyPic);
    }

    private void takePhoto() {
        Intent intent;

        //存放验证图的文件
        File verifyPicFile = new File(Environment.getExternalStorageDirectory() + File.separator + "StudentAgency", IMAGE_FILE_NAME);
        if (!verifyPicFile.getParentFile().exists()) {
            verifyPicFile.getParentFile().mkdirs();
        }
        //判断当前系统，Android7.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            userVerifyPicUri = FileProvider.getUriForFile(getActivity(), "com.example.studentagency.FileProvider", verifyPicFile);
            Log.i(TAG, "takePhoto: userVerifyPicUri>>>>>" + userVerifyPicUri.toString());
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            userVerifyPicUri = Uri.fromFile(verifyPicFile);
            Log.i(TAG, "takePhoto: userVerifyPicUri>>>>>" + userVerifyPicUri.toString());
        }

        //去拍照，拍照的结果存到userAvatarUri对应的路径中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, userVerifyPicUri);
        Log.i(TAG, "before takePhoto: " + userVerifyPicUri.toString());
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
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
    public void uploadVerifyPicSuccess(ResponseBean responseBean) {
        Log.i(TAG, "uploadVerifyPicSuccess: result>>>>>" + responseBean.getCode());

        //上传成功
        if (200 == responseBean.getCode()) {
            LemonBubble.showRight(this, "上传成功！", 1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().finish();
                }
            }, 1100);
        } else {
            LemonBubble.showError(this, "上传失败！", 1000);
        }
    }

    @Override
    public void uploadVerifyPicFail() {
        Log.i(TAG, "uploadVerifyPicFail: ");

        LemonBubble.showError(this, "网络异常，请重试！", 1000);
    }
}
