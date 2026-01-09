package com.example.todohabitlommepengetracker.ui.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todohabitlommepengetracker.R;
import com.example.todohabitlommepengetracker.data.TodoStorageHandler;
import com.example.todohabitlommepengetracker.model.TodoItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    private TodoStorageHandler storageHandler;
    private ArrayList<TodoItem> todoList;
    private TodoAdapter adapter;
    private ActivityResultLauncher<Intent> addTodoLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storageHandler = new TodoStorageHandler();
        todoList = storageHandler.load(requireContext());
        if (todoList == null) {
            todoList = new ArrayList<>();
        }

        addTodoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        String title = result.getData().getStringExtra(AddTodoActivity.EXTRA_TITLE);
                        double reward = result.getData().getDoubleExtra(AddTodoActivity.EXTRA_REWARD, 0);
                        if (title != null && !title.trim().isEmpty()) {
                            todoList.add(new TodoItem(title.trim(), reward));
                            adapter.notifyDataSetChanged();
                            storageHandler.save(requireContext(), todoList);
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        ListView listView = view.findViewById(R.id.listViewTodos);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);

        adapter = new TodoAdapter(requireContext(), todoList, storageHandler);
        listView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddTodoActivity.class);
            addTodoLauncher.launch(intent);
        });

        return view;
    }
}
