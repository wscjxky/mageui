package com.mage.magemata.circle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mage.magemata.R;
import com.mage.magemata.circle.card.CircleActivity;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;

/**
 * Created by Administrator on 2017/4/24.
 */
@ContentView(R.layout.fragment_circle)
public class CircleFragment extends Fragment {

    private Activity activity;
    @ViewInject(R.id.fg_circle_rv)
    private RecyclerView recyclerView;
    @ViewInject(R.id.fgcircle_menu)
    private FloatingActionsMenu floatingActionsMenu;

    private CircleFragAdapter circleFragAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        this.activity = getActivity();
        return x.view().inject(CircleFragment.this,inflater,container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
//
        try {
            setAdapter();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


//
//    @Event(R.id.fgcircle_btn_addcircle)
//    private void addCircle(View view){
//        AddCircleActivity.actionStart(activity);
//        floatingActionsMenu.collapse();
//    }
//    @Event(R.id.fgcircle_btn_mycircle)
//    private void myCircle(View view){
//        MyCircleActivity.actionStart(activity);
//        floatingActionsMenu.collapse();
//
//    }
//    @Event(R.id.fgcircle_btn_search)
//    private void search(View view){
//        new MaterialDialog.Builder(activity)
//                .title("搜索")
//                .inputType(InputType.TYPE_CLASS_TEXT )
//                .input("", "哈利波特0", new MaterialDialog.InputCallback() {
//                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//                    @Override
//                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
//                        List<Circle> data=circleFragAdapter.getData();
//                        for(Circle item : data){
//                            if(Objects.equals(item.getTitle(), input + "")){
//                                data.clear();
//                                data.add(item);
//                                break;
//                            }
//                        }
//                        circleFragAdapter.notifyDataSetChanged();
//                        dialog.dismiss();
//                    }
//                }).show();
//        floatingActionsMenu.collapse();
//
//    }
//
    private void setAdapter() throws DbException {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

         circleFragAdapter  = new CircleFragAdapter();
        circleFragAdapter.openLoadAnimation(SCALEIN);
        circleFragAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        CircleActivity.actionstart(activity);

            }
        });
        recyclerView.setAdapter(circleFragAdapter);

    }


//
//    @Override
//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }
}
