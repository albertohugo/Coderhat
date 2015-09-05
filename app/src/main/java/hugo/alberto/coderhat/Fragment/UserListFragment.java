package hugo.alberto.coderhat.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hugo.alberto.coderhat.Adapter.UserCustomAdapter;
import hugo.alberto.coderhat.Handler.DatabaseHandler;
import hugo.alberto.coderhat.Handler.JsonParseHandler;
import hugo.alberto.coderhat.Model.ListDataModel;
import hugo.alberto.coderhat.R;

public class UserListFragment extends Fragment {
    private ListView listView;
    private ArrayList<ListDataModel> listdatamodel;
    private UserCustomAdapter adapter;
    private ProgressDialog progressDialog;
    private DatabaseHandler db;
    private static final String URL = "http://jsonplaceholder.typicode.com/posts/";
    private JsonParseHandler jsonParseHandler;
    private Fragment fragment = null;

    public UserListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        listdatamodel = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());
        db = new DatabaseHandler(getActivity());
        jsonParseHandler = new JsonParseHandler();
        new DataFetch().execute();

        List<ListDataModel> users = db.getAllUsers();
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

        adapter = new UserCustomAdapter(listdatamodel, getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                Fragment fragment = new BookListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("user_id", String.valueOf(posicao + 1)); //any string to be sent
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });
        return rootView;
    }

    private void readJsonParse(String json_data) {
        try {

            JSONArray livros = new JSONArray(json_data);

            for (int i = 0; i < livros.length(); i++) {
                JSONObject contactObj = livros.getJSONObject(i);

                String id = contactObj.getString("id");
                String userId = contactObj.getString("userId");
                String title = contactObj.getString("title");
                String body = contactObj.getString("body");

                Log.d("data is", id + "," + userId + "," + title + "," + body);
                db.addUsers(new ListDataModel(id, userId, title, body));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
            readJsonParse(json_data);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }

}
