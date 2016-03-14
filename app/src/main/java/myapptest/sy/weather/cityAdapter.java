package myapptest.sy.weather;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sy on 2016/3/12.
 */
public class cityAdapter extends BaseAdapter {
    private Context context = null;
    private List<cityName> mList = null;
    private LayoutInflater mInflater = null;
    private Handler mHandler = null;

    public cityAdapter(List<cityName> mList,Context context,Handler mHandler){
        this.context = context;
        this.mList = mList;
        this.mHandler = mHandler;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class viewHolder{
        public TextView tv_cityname;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new viewHolder();
            convertView = mInflater.inflate(R.layout.iteam,null);
            viewHolder.tv_cityname = (TextView) convertView.findViewById(R.id.tv_cityname);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (cityAdapter.viewHolder) convertView.getTag();
        }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity main = new MainActivity();
                            main.setHttpArg(mList.get(position).getName());

                            Message msg = new Message();
                            msg.what = 0x001;
                            mHandler.sendMessage(msg);
                        }
                    });
                }
            });

            viewHolder.tv_cityname.setText(mList.get(position).getName());
        return convertView;
    }
}
