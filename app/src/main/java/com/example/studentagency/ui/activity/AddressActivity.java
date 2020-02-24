package com.example.studentagency.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.lemonhello.LemonHello;
import com.example.lemonhello.LemonHelloAction;
import com.example.lemonhello.LemonHelloInfo;
import com.example.lemonhello.LemonHelloView;
import com.example.lemonhello.interfaces.LemonHelloActionDelegate;
import com.example.studentagency.R;
import com.example.studentagency.Utils.DateUtils;
import com.example.studentagency.bean.AddressBean;
import com.example.studentagency.mvp.presenter.AddressActivityBasePresenter;
import com.example.studentagency.mvp.view.AddressActivityBaseView;
import com.example.studentagency.ui.adapter.AddressActivityRecyclerviewAdapter;
import com.example.studentagency.ui.widget.AddAddressPopupWindow;
import com.example.studentagency.ui.widget.EditAddressPopupWindow;

import java.util.ArrayList;
import java.util.List;

import static com.example.studentagency.ui.activity.MyApp.userId;

public class AddressActivity extends BaseActivity implements AddressActivityBaseView {

    private static final String TAG = "AddressActivity";
    private static final int REQUEST_EDIT_ADDRESS = 200;
    private AddressActivityBasePresenter presenter = new AddressActivityBasePresenter(this);

    private TextView tv_addAddress;

    //recyclerview
    private RecyclerView recyclerView;
    private AddressActivityRecyclerviewAdapter adapter;
    private int clickedPosition;
    private String changedTag, changedAddres;
    private int resource = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        initViews();

        initRecyclerview();

        presenter.getAddress();
    }

    private void initViews() {
        tv_addAddress = findViewById(R.id.tv_addAddress);
        tv_addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressPopupWindow popupWindow = new AddAddressPopupWindow(AddressActivity.this);
                popupWindow.setClickItemListener(new AddAddressPopupWindow.ClickItemListener() {
                    @Override
                    public void clickItem(String tag, String address) {
                        LemonBubble.getRoundProgressBubbleInfo()
                                .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                                .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                                .setBubbleSize(200, 50)
                                .setProportionOfDeviation(0.1f)
                                .setTitle("新建中...")
                                .show(AddressActivity.this);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "clickItem: tag>>>>>" + tag + " address>>>>>" + address);
                                presenter.addAddress(tag, address);
                            }
                        }, 1500);
                    }
                });
                popupWindow.showAsDropDown(tv_addAddress);
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
    }

    private void initRecyclerview() {
        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        List<Object> list = new ArrayList<>();
        list.add("暂无数据");
        adapter = new AddressActivityRecyclerviewAdapter(list);
        adapter.setClickItemListener(new AddressActivityRecyclerviewAdapter.OnClickItemListener() {
            @Override
            public void clickItem(int type, AddressBean addressBean, int position, TextView tv_editAddress) {
                if (type == 101) {
                    Intent intent = new Intent();
                    intent.putExtra("pickedAddress", addressBean.getAddress());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    clickedPosition = position;

                    EditAddressPopupWindow popupWindow = new EditAddressPopupWindow(AddressActivity.this, addressBean);
                    popupWindow.setClickItemListener(new EditAddressPopupWindow.ClickItemListener() {
                        @Override
                        public void clickItem(int type, int addressId, String tag, String address) {
                            if (type == 201) {//点击的是√和保存
                                changedTag = tag;
                                changedAddres = address;

                                saveAddress(addressId, tag, address);
                            } else {//点击的是删除
                                showEnsureDialog(addressId);
                            }
                        }
                    });
                    popupWindow.showAsDropDown(tv_editAddress);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void saveAddress(int addressId, String tag, String address) {
        LemonBubble.getRoundProgressBubbleInfo()
                .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                .setBubbleSize(200, 50)
                .setProportionOfDeviation(0.1f)
                .setTitle("保存中...")
                .show(AddressActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "clickItem: tag>>>>>" + tag + " address>>>>>" + address);
                presenter.editAddress(addressId, tag, address);
            }
        }, 1500);
    }

    private void showEnsureDialog(int addressId) {
        LemonHello.getInformationHello("提示", "您确定要删除该地址吗？")
                .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        Log.i(TAG, "onClick: 取消删除");
                        lemonHelloView.hide();
                    }
                }))
                .addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        Log.i(TAG, "onClick: 确定删除");
                        lemonHelloView.hide();

                        LemonBubble.getRoundProgressBubbleInfo()
                                .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                                .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                                .setBubbleSize(200, 50)
                                .setProportionOfDeviation(0.1f)
                                .setTitle("删除中...")
                                .show(AddressActivity.this);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                presenter.deleteAddress(addressId);
                            }
                        }, 1500);
                    }
                }))
                .show(AddressActivity.this);
    }

    @Override
    public void getAddressSuccess(List<AddressBean> addressBeans) {
        Log.i(TAG, "getAddressSuccess: ");

        if (!addressBeans.isEmpty()) {
            adapter.updateAddress(addressBeans);
        }
    }

    @Override
    public void getAddressFail() {
        Log.i(TAG, "getAddressFail: ");

        adapter.updateFail();
    }

    @Override
    public void addAddressSuccess(AddressBean addressBean) {
        Log.i(TAG, "addAddressSuccess: addressBean>>>>>" + addressBean.toString());

        LemonBubble.showRight(this, "新建成功！", 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (addressBean.getTag().equals("默认")) {
                    adapter.addAddress(addressBean, true);
                } else {
                    adapter.addAddress(addressBean, false);
                }
            }
        }, 1600);
    }

    @Override
    public void addAddressFail() {
        Log.i(TAG, "addAddressFail: ");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void editAddressSuccess(Integer result) {
        Log.i(TAG, "editAddressSuccess: result>>>>>" + result);

        if (0 == result) {
            LemonBubble.showError(this, "保存失败，请重试！", 1500);
        } else {
            LemonBubble.showRight(this, "保存成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "run: changedTag>>>>>" + changedTag + " changedAddress>>>>>" + changedAddres);
                    if (changedTag.equals("默认")) {
                        adapter.updateItem(changedTag, changedAddres, clickedPosition, true);
                    } else {
                        adapter.updateItem(changedTag, changedAddres, clickedPosition, false);
                    }
                }
            }, 1600);
        }
    }

    @Override
    public void editAddressFail() {
        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void deleteAddressSuccess(Integer result) {
        Log.i(TAG, "deleteAddressSuccess: result>>>>>" + result);

        if (0 == result) {
            LemonBubble.showError(this, "删除失败，请重试！", 1500);
        } else {
            LemonBubble.showRight(this, "删除成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.deleteAddress(clickedPosition);
                }
            }, 1600);
        }
    }

    @Override
    public void deleteAddressFail() {
        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }
}
