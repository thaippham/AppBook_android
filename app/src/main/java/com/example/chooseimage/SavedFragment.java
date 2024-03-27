package com.example.chooseimage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class SavedFragment extends Fragment {
    private ListView listView;
    private Adapter_Book adapter;
    private DBImageTitle dbImageTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        listView = view.findViewById(R.id.lvbooksave);
        dbImageTitle = new DBImageTitle(getActivity());

        int currentUserId = UserSession.getInstance().getUserId();
        List<ImageTitle> savedBooks = dbImageTitle.getSavedBooksForUser(currentUserId);

        adapter = new Adapter_Book(getActivity(), R.layout.activity_adapter_book, savedBooks);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageTitle selectedBook = savedBooks.get(position);
                Intent intent = new Intent(getActivity(), MainRead.class);
                intent.putExtra("SELECTED_BOOK", selectedBook);
                startActivity(intent);
            }
        });

        return view;
    }

}