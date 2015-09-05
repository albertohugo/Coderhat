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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hugo.alberto.coderhat.Adapter.BookCustomAdapter;
import hugo.alberto.coderhat.Handler.DatabaseHandler;
import hugo.alberto.coderhat.Handler.JsonParseHandler;
import hugo.alberto.coderhat.Model.ListDataModel;
import hugo.alberto.coderhat.R;

public class BookListFragment extends Fragment {
    private ListView listView;
    private ArrayList<ListDataModel> listdatamodel;
    private BookCustomAdapter adapter;
    private ProgressDialog progressDialog;
    private DatabaseHandler db;
    private static final String URL = "http://jsonplaceholder.typicode.com/posts/";
    private JsonParseHandler jsonParseHandler;
    private Fragment fragment = null;

    private String userID;

    public BookListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.book_list_view);
        listdatamodel = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());
        db = new DatabaseHandler(getActivity());
        jsonParseHandler = new JsonParseHandler();
        new DataFetch().execute();

        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            userID = getArguments().getString("user_id");
        }

        List<ListDataModel> users = db.getUsersBooks(userID);
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

        adapter = new BookCustomAdapter(listdatamodel, getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                TextView v = (TextView) view.findViewById(R.id.title_book);
                String itemStr = (String) v.getText();

                Fragment fragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", itemStr);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });

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
