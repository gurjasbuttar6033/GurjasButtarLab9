package gurjas.buttar.n01562033.js;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Gurjas1Fragment extends Fragment {


    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<String> itemList;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_LIST = "MyList";

    public Gurjas1Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gurjas1, container, false);

        recyclerView = view.findViewById(R.id.gurrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button addButton = view.findViewById(R.id.guraddButton);
        Button deleteButton = view.findViewById(R.id.gurdeleteButton);

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        itemList = loadData();

        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> addItem());
        deleteButton.setOnClickListener(v -> deleteAllItems());

        return view;
    }

    private void addItem() {
        itemList.add("Item " + (itemList.size() + 1));
        saveData();
        adapter.notifyDataSetChanged();
    }

    private void deleteAllItems() {
        if (itemList.isEmpty()) {
            Toast.makeText(getContext(), "No data to delete", Toast.LENGTH_SHORT).show();
        } else {
            itemList.clear();
            saveData();
            adapter.notifyDataSetChanged();
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemList);
        editor.putString(KEY_LIST, json);
        editor.apply();
    }

    private List<String> loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_LIST, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return json == null ? new ArrayList<>() : gson.fromJson(json, type);
    }
}