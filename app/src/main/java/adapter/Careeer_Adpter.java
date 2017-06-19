package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.infomanav.astaguru.JustifiedTextView;
import com.infomanav.astaguru.Model_Careeer;
import com.infomanav.astaguru.R;

import java.util.List;

/**
 * Created by fox-2 on 11/30/2016.
 */

public class Careeer_Adpter extends ArrayAdapter<Model_Careeer> {

    Context mContext;
    public boolean is_us=false;
    List<Model_Careeer> objects;
    LinearLayout layout;
    ImageView iv_expand;
    ScrollView sc_form;
    ListView listView;
    public Careeer_Adpter(Context context, int textViewResourceId, List<Model_Careeer> objects, ScrollView sc_form, ListView listView) {
        super(context, textViewResourceId, objects);
        mContext = context;
        this.objects=objects;
        this.sc_form=sc_form;
        this.listView=listView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View curView = convertView;

        if (curView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.career_single_item, null);
        }

        final Model_Careeer cp = getItem(position);


        TextView JobTtile = (TextView) curView.findViewById(R.id.JobTtile);
         iv_expand = (ImageView) curView.findViewById(R.id.iv_expand);
//        TextView tv_job_desc = (TextView) curView.findViewById(R.id.tv_job_desc);
        TextView businessUnit= (TextView) curView.findViewById(R.id.businessUnit);
        JustifiedTextView jobResponsibility= (JustifiedTextView) curView.findViewById(R.id.jobResponsibility);
        JustifiedTextView functionalSkills= (JustifiedTextView) curView.findViewById(R.id.functionalSkills);
        JustifiedTextView technicalSkills= (JustifiedTextView) curView.findViewById(R.id.technicalSkills);
        JustifiedTextView joiningTime= (JustifiedTextView) curView.findViewById(R.id.joiningTime);
        JustifiedTextView jobSalary= (JustifiedTextView) curView.findViewById(R.id.jobSalary);
        JustifiedTextView jobExperience= (JustifiedTextView) curView.findViewById(R.id.jobExperience);
        JustifiedTextView Responsibilities= (JustifiedTextView) curView.findViewById(R.id.Responsibilities);
        RelativeLayout rel_row_single = (RelativeLayout) curView.findViewById(R.id.rel_row_single);
        Button btn_apply = (Button) curView.findViewById(R.id.btn_apply);
         layout = (LinearLayout) curView.findViewById(R.id.layout);

        boolean is_close =cp.getIsClose();

        if(is_close)
        {
            iv_expand.setImageResource(R.drawable.minus);
            layout.setVisibility(View.VISIBLE);

           // notifyDataSetChanged();
        }
        else
        {
            iv_expand.setImageResource(R.drawable.plus);
            layout.setVisibility(View.GONE);
           // notifyDataSetChanged();
        }




        JobTtile.setText(cp.getJob_title());
//        tv_job_desc.setText(cp.getJob_desc());
        businessUnit.setText(cp.getBusinessUnit().replace("\r\n",""));
         jobResponsibility.setText(cp.getJobResponsibility().replace("\r\n",""));
        functionalSkills.setText(cp.getFunctionalSkills().replace("\r\n",""));
         technicalSkills.setText(cp.getTechnicalSkills().replace("\r\n",""));
        joiningTime.setText(cp.getJoiningTime().replace("\r\n",""));
         jobSalary.setText(cp.getJobSalary().replace("\r\n",""));
        jobExperience.setText(cp.getJobExperience().replace("\r\n",""));
        Responsibilities.setText(cp.getJob_desc().replace("\r\n",""));

        iv_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cp.getIsClose())
                {
                    cp.setIsClose(false);

//                    iv_expand.setImageResource(R.drawable.minus);
                    notifyDataSetChanged();
                }
                else
                {
                    cp.setIsClose(true);

//                    iv_expand.setImageResource(R.drawable.plus);
                    notifyDataSetChanged();
                }
            }
        });

        rel_row_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cp.getIsClose())
                {
                    cp.setIsClose(false);

//                    iv_expand.setImageResource(R.drawable.minus);
                    notifyDataSetChanged();
                }
                else
                {
                    cp.setIsClose(true);

//                    iv_expand.setImageResource(R.drawable.plus);
                    notifyDataSetChanged();
                }
            }
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listView.setVisibility(View.GONE);
                sc_form.setVisibility(View.VISIBLE);

            }
        });



        return curView;
    }
}

