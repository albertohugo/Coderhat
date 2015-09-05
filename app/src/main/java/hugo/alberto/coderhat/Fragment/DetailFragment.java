package hugo.alberto.coderhat.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import hugo.alberto.coderhat.Handler.JsonParseHandler;
import hugo.alberto.coderhat.Model.ListDataModel;
import hugo.alberto.coderhat.R;

public class DetailFragment extends Fragment {
    private ListView listView;
    private ArrayList<ListDataModel> listdatamodel;
    private DetailCustomAdapter adapter;
    private ProgressDialog progressDialog;
    private DatabaseHandler db;
    private static final String URL = "http://jsonplaceholder.typicode.com/posts/";
    private JsonParseHandler jsonParseHandler;
    private Fragment fragment = null;

    private String userID;
    private String title;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        listView = (ListView) rootView.findViewById(R.id.detail_view);
        listdatamodel = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());
        db = new DatabaseHandler(getActivity());
        jsonParseHandler = new JsonParseHandler();
        new DataFetch().execute();

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

    public class DataFetch extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json_data = jsonParseHandler.serviceCall(URL, JsonParseHandler.GET);
            Log.d("in inBG()", "fetch data" + json_data);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }

}
