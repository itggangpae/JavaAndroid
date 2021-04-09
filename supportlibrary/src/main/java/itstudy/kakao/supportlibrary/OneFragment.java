package itstudy.kakao.supportlibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.google.android.material.snackbar.Snackbar;

public class OneFragment extends ListFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] datas={"Encapsulation", "Inheritance", "Polymorphism"};
        ArrayAdapter<String> aa=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datas);
        setListAdapter(aa);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Snackbar.make(v, (String)l.getAdapter().getItem(position), Snackbar.LENGTH_LONG).show();
    }

}