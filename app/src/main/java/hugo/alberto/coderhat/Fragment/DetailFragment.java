package hugo.alberto.coderhat.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hugo.alberto.coderhat.Adapter.DetailCustomAdapter;
import hugo.alberto.coderhat.Handler.DatabaseHandler;
import hugo.alberto.coderhat.Model.ListDataModel;
import hugo.alberto.coderhat.R;

public class DetailFragment extends Fragment {
    private ListView listView;
    private ArrayList<ListDataModel> listdatamodel;
    private DetailCustomAdapter adapter;
    private DatabaseHandler db;
    private String title;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        listView = (ListView) rootView.findViewById(R.id.detail_view);
        listdatamodel = new ArrayList<>();
        db = new DatabaseHandler(getActivity());
        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }

        List<ListDataModel> users = db.getDetail(title);
        for (ListDataModel data : users) {
            String log = "UserId: " + data.getUserId() + " ,Id: " + data.getId() + " ,Title: " + data.getTitle() + " ,Body: " + data.getBody();

            ListDataModel item = new ListDataModel();
            item.setId(data.getId());
            item.setTitle(data.getTitle());
            item.setUserId(data.getUserId());
            item.setBody(data.getBody());

            listdatamodel.add(item);
            Log.d("Id: ", log);
        }

        adapter = new DetailCustomAdapter(listdatamodel, getActivity());
        listView.setAdapter(adapter);

        return rootView;
    }

}
