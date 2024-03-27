package com.example.chooseimage;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class BookFragment extends Fragment {
    private DBImageTitle dbImageTitle;
    ListView lvbook;
    private List<ImageTitle> originalData = new ArrayList<>();
    List<ImageTitle> data_book = new ArrayList<>();
    ArrayAdapter adapter_book;
    private void DocDL() {
        data_book.clear();
        List<ImageTitle> books = dbImageTitle.DocDL();
        if (books != null) {
            data_book.addAll(books);
        }
        adapter_book.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);
        lvbook = rootView.findViewById(R.id.lvbook);
        adapter_book = new Adapter_Book(getActivity(), R.layout.activity_adapter_book, data_book);
        lvbook.setAdapter(adapter_book);
        dbImageTitle = new DBImageTitle(getActivity());
        DocDL();
        lvbook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageTitle selectedBook = data_book.get(position);
                Intent intent = new Intent(getActivity(), MainRead.class);
                intent.putExtra("SELECTED_BOOK", selectedBook);
                startActivity(intent);
            }
        });
        originalData.addAll(data_book);
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void filterData(String query) {
        data_book.clear();
        for (ImageTitle book : originalData) {
            if (book.getTitleimg().toLowerCase().contains(query.toLowerCase())) {
                data_book.add(book);
            }
        }
        adapter_book.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}