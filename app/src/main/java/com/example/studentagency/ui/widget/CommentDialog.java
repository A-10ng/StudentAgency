package com.example.studentagency.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.studentagency.R;
import com.example.studentagency.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/09
 * desc:
 */
public class CommentDialog extends Dialog {

    private Context context;
    private float width;
    private float height;
    private onEnsureClickListener ensureClickListener;
    private onCancelClickListener cancelClickListener;

    public CommentDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CommentDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected CommentDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_indent_activity_comment,null,false);
        setContentView(dialogView);

        final EditText et_content = dialogView.findViewById(R.id.et_content);
        Button btn_ensure = dialogView.findViewById(R.id.btn_ensure);
        Button btn_cacel = dialogView.findViewById(R.id.btn_cancel);
        btn_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ensureClickListener){
                    String content = et_content.getText().toString();
                    ensureClickListener.clickEnsure(content);
                }
            }
        });
        btn_cacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != cancelClickListener){
                    cancelClickListener.clickCancel();
                }
            }
        });

        Window window = getWindow();
        if (width == 0 || height == 0) throw new NullPointerException("宽高未设置！");
        window.setLayout(Utils.dp2px(context,width),Utils.dp2px(context,height));
        window.setBackgroundDrawableResource(R.color.transparent);
    }

    public void setDialogSize(float width,float height){
        this.width = width;
        this.height = height;
    }

    public void setEnsureClickListener(onEnsureClickListener ensureClickListener) {
        this.ensureClickListener = ensureClickListener;
    }

    public void setCancelClickListener(onCancelClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }

    public interface onEnsureClickListener{
        void clickEnsure(String content);
    }

    public interface onCancelClickListener{
        void clickCancel();
    }
}
