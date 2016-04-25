package cn.studyjams.s1.sj56.zhangshengli;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vic on 2016/4/21 0021.
 */
public class CurtCounterActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.aGrade)
    TextView aGrade;
    @Bind(R.id.a1Btn)
    Button a1Btn;
    @Bind(R.id.a2Btn)
    Button a2Btn;
    @Bind(R.id.a3Btn)
    Button a3Btn;
    @Bind(R.id.bGrade)
    TextView bGrade;
    @Bind(R.id.b1Btn)
    Button b1Btn;
    @Bind(R.id.b2Btn)
    Button b2Btn;
    @Bind(R.id.b3Btn)
    Button b3Btn;
    @Bind(R.id.reset)
    Button reset;
    private int teamA = 0;
    private int teamB = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_counter;
    }

    public void addA(int tmp) {
        aGrade.setText(String.valueOf(teamA+=tmp));
    }

    public void addB(int tmp) {
        bGrade.setText(String.valueOf(teamB+=tmp));
    }

    public void reset(){
        aGrade.setText(String.valueOf(teamA=0));
        bGrade.setText(String.valueOf(teamB=0));
    }

    @OnClick({R.id.a1Btn,R.id.a2Btn,R.id.a3Btn,R.id.b1Btn,R.id.b2Btn,R.id.b3Btn,R.id.reset})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.a1Btn:
                addA(1);
                break;
            case R.id.a2Btn:
                addA(2);
                break;
            case R.id.a3Btn:
                addA(3);
                break;
            case R.id.b1Btn:
                addB(1);
                break;
            case R.id.b2Btn:
                addB(2);
                break;
            case R.id.b3Btn:
                addB(3);
                break;
            case R.id.reset:
                reset();
                break;
        }
    }
}
