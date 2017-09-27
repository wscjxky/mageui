package com.mage.magemata.more;


import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;

import org.xutils.view.annotation.Event;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class GpaActivity extends BaseActivity {
    private ListView listView;
    private FloatingActionButton button;
    private GpaAdapter gpaAdapter;
    private ArrayList<Integer> scoreList = new ArrayList<Integer>();
    private ArrayList<Integer> creditList = new ArrayList<Integer>();

    private FloatingActionsMenu floatingActionsMenu;
    private EditText editText;
    private EditText editText1;
    private EditText tempText;
    private EditText tempText1;
    private ArrayList<Integer> tscoreList = new ArrayList<Integer>();
    private ArrayList<Integer> tcreditList = new ArrayList<Integer>();


    @Event(R.id.gpa_btn_addinput)
    private void addinput(View  view){
        floatingActionsMenu.collapse();
        for (int i = 0; i < listView.getChildCount(); i++) {
            View mview = listView.getChildAt(i);
            tempText = (EditText) mview.findViewById(R.id.gpa_et_score);
            tempText1 = (EditText) mview.findViewById(R.id.gpa_et_credit);
            if (!Objects.equals(tempText1.getText().toString(), "")) {
                tscoreList.add(Integer.parseInt(tempText.getText().toString()));
                tcreditList.add(Integer.parseInt(tempText1.getText().toString()));
            }
            else
                break;
        }
        gpaAdapter.add("xiixi");
        gpaAdapter.notifyDataSetChanged();
//        Log.e("num",listView.getChildCount()+"");
//        Log.e("numa",tcreditList.get(0).toString());
//        View tview = listView.getChildAt(2);
//        tempText = (EditText) tview.findViewById(R.id.gpa_et_score);
//        tempText1 = (EditText) tview.findViewById(R.id.gpa_et_credit);

    }

    @Event(R.id.gpa_btn_five)
    private void five(View  view){


        submit(5);

    }
    @Event(R.id.    gpa_btn_recovery)
    private void recovery(View  view){
        for (int i = 0; i < tcreditList.size(); i++) {
            View tview = listView.getChildAt(i);
            tempText = (EditText) tview.findViewById(R.id.gpa_et_score);
            tempText1 = (EditText) tview.findViewById(R.id.gpa_et_credit);
            if (!Objects.equals(tcreditList.get(i).toString(), "")) {
                tempText.setText(tscoreList.get(i).toString());
                tempText1.setText(tcreditList.get(i).toString());
            }
            else{
                break;
            }

        }
        tscoreList.clear();
        tcreditList.clear();


    }
    @Event(R.id.gpa_btn_four)
    private void four(View  view){
        submit(4);

    }
    @Event(R.id.gpa_btn_peking)
    private void peking(View  view){
        submit(3);

    }

    private void submit(int type) {
        floatingActionsMenu.collapse();
        for (int i = 0; i < listView.getChildCount(); i++) {
            View view = listView.getChildAt(i);
             editText = (EditText) view.findViewById(R.id.gpa_et_score);
             editText1 = (EditText) view.findViewById(R.id.gpa_et_credit);

            if (!Objects.equals(editText1.getText().toString(), "")) {
                scoreList.add(Integer.parseInt(editText.getText().toString()));
                creditList.add(Integer.parseInt(editText1.getText().toString()));
            }
            editText.setText("");
            editText1.setText("");
        }
        if(scoreList.size()!=0)
            getGpa(scoreList, creditList, type);
        scoreList.clear();
        creditList.clear();



    }

    private void getGpa(ArrayList<Integer> scoreList, ArrayList<Integer> creditList, int type) {
        ArrayList<Double> new_arr = new ArrayList<Double>();
        Double total_credit=0.0;
        Double dividend=0.0;
        switch (type) {
            case 3:
                for (int i = 0; i < scoreList.size(); i++) {
                    int value = scoreList.get(i);
                    if (value >= 90 && value <= 100)
                        new_arr.add(4.0);
                    else if (value >= 85 && value <= 89)
                        new_arr.add(3.7);
                    else if  (value >= 82 && value <= 84)
                        new_arr.add(3.3);
                    else if  (value >= 78 && value <= 81)
                        new_arr.add(3.0);
                    else if  (value >= 75 && value <= 77)
                        new_arr.add(2.7);
                    else if  (value >= 72 && value <= 74)
                        new_arr.add(2.3);
                    else if  (value >= 68 && value <= 71)
                        new_arr.add(2.0);
                    else if  (value >= 64 && value <= 67)
                        new_arr.add(1.5);
                    else if  (value >= 60 && value <= 63)
                        new_arr.add(1.0);
                    else {
                        new_arr.add(0.0);
                    }
                }
                break;
            case 4:
                for (int i = 0; i < scoreList.size(); i++) {
                    int value = scoreList.get(i);
                    if (value >= 90 && value <= 100)
                        new_arr.add(4.0);
                    else if (value >= 80 && value <= 89)
                        new_arr.add(3.0);
                    else if  (value >= 70 && value <= 79)
                        new_arr.add(2.0);
                    else if  (value >= 60 && value <= 69)
                        new_arr.add(1.0);

                    else {
                        new_arr.add(0.0);
                    }
                }
                break;
            case 5:
                for (int i = 0; i < scoreList.size(); i++) {
                    int value = scoreList.get(i);
                    if (value <= 60) {
                        new_arr.add(0.0);
                    } else {
                        new_arr.add((value - 50) / 10.0);
                    }
                }
                break;
            default:
                break;
        }
        for (int i = 0; i < creditList.size(); i++) {
            total_credit+=creditList.get(i);
        }
        for (int i = 0; i < creditList.size(); i++) {
            dividend+=creditList.get(i)*new_arr.get(i);
        }

        BigDecimal bd = new BigDecimal(dividend/total_credit);

        showSuccDialog("最终绩点为："+bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_gpa);
        initToolbar("绩点计算",true);
    }

    @Override
    public void initData() {
        listView = (ListView) findViewById(R.id.gpa_lv);
        gpaAdapter = new GpaAdapter(this, R.layout.item_gpa);
        gpaAdapter.add("ban1");
        gpaAdapter.add("ban1");
        gpaAdapter.add("ban1");
        gpaAdapter.add("ban1");
        gpaAdapter.add("ban1");
        gpaAdapter.add("ban1");
        gpaAdapter.add("ban1");
        gpaAdapter.add("ban1");

        listView.setAdapter(gpaAdapter);
        listView.setFocusable(false);
        floatingActionsMenu = (FloatingActionsMenu)findViewById(R.id.multiple_actions);
    }

    @Override
    public void loadData() {

    }


    class GpaAdapter extends ArrayAdapter<String> {
        private int mResourceId;

        public GpaAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.mResourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, @Nullable ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();

            View view = inflater.inflate(mResourceId, null);
            return view;
        }
    }



}
