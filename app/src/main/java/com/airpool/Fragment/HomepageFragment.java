package com.airpool.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.airpool.Adapter.UserGroupsAdapter;
import com.airpool.GlobalUser;
import com.airpool.HomepageActivity;
import com.airpool.Model.Group;
import com.airpool.Model.User;
import com.airpool.R;
import com.airpool.SearchActivity;
import com.airpool.ViewGroupActivity;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.widget.LoginButton;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Maury on 11/15/14.
 */
public class HomepageFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomepageFragment";

    LoginButton logoutButton;
    Button searchButton;
    ListView userGroupList;
    ArrayAdapter<Group> userGroupListAdapter;
    ArrayList<Group> userGroups;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_homepage, container, false);

        userGroupList = (ListView) rootView.findViewById(R.id.user_groups);

        searchButton = (Button) rootView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);

        logoutButton = (LoginButton) rootView.findViewById(R.id.facebook_login_button);
        logoutButton.setSessionStatusCallback(((HomepageActivity) getActivity()).callback);

        // Get the groups associated with this user.
        userGroups = new ArrayList<Group>();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.search_button:
                Log.i("HomepageActivity", "clicked search button");
                Intent clickSearch = new Intent(getActivity(), SearchActivity.class);
                startActivity(clickSearch);
                break;
        }
    }

    public void noNetwork() {
        // Indicate to the user that there is no network.
        userGroupList.setEmptyView((TextView)
                getView().findViewById(R.id.noNetwork));
    }

    public void populateGroups() {
        userGroups.clear();

        Session session = Session.getActiveSession();
        new Request(
                session,
                "/me",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
                        query.whereEqualTo("facebookID", response.getGraphObject().getProperty("id"));
                        try {
                            ParseObject user = query.getFirst();
                            ((GlobalUser) getActivity().getApplicationContext())
                                    .setCurrentUser((User) user);
                            assertNotNull(user);

                            // Then, get the associated groups.
                            ParseQuery<ParseObject> groupQuery = ParseQuery.getQuery("Group");
                            groupQuery.whereEqualTo("users", user);
                            groupQuery.whereEqualTo("isActive", true);

                            List<ParseObject> groups = groupQuery.find();

                            // Do not know of any other way to cast list of objects.
                            for(ParseObject group : groups) {
                                userGroups.add((Group) group);
                            }
                        } catch (ParseException exception) {
                            Log.e(TAG, "Error getting user: " + exception.toString());
                        }

                        if (!userGroups.isEmpty()) {
                            // Populate the list view.
                            userGroupListAdapter = new UserGroupsAdapter(getActivity(), R.layout.item_user_groups,
                                    userGroups);
                            userGroupList.setAdapter(userGroupListAdapter);

                            // View a group when you click on a list item.
                            userGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                    Intent viewGroup = new Intent(getActivity(), ViewGroupActivity.class);
                                    viewGroup.putExtra("groupId", userGroups.get(position).getObjectId());
                                    startActivity(viewGroup);
                                }
                            });
                        } else {
                            // Indicate to the user that there are no groups that they've joined.
                            userGroupList.setEmptyView((TextView)
                                    getView().findViewById(R.id.no_search_results));
                        }

                    }
                }
        ).executeAsync();
    }
}
