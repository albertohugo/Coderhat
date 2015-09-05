package hugo.alberto.coderhat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hugo.alberto.coderhat.Model.ListDataModel;
import hugo.alberto.coderhat.R;

public class DetailCustomAdapter extends BaseAdapter {
    private Activity activity;
    LayoutInflater inflater;
    private ArrayList<ListDataModel> listdata;

    public DetailCustomAdapter(ArrayList<ListDataModel> listdata, Activity activity) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            view = inflater.inflate(R.layout.detail_view, null);

        TextView detail_body = (TextView) view.findViewById(R.id.detail_body);
        ListDataModel data = listdata.get(position);
        detail_body.setText(data.getBody());

        return view;
    }
}
