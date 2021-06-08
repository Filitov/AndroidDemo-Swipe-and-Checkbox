package com.example.demo0608a.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.demo0608a.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment {

    private BlankViewModel mViewModel;

    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    List<Sample> mData;
    MyAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mData = new ArrayList<>();
        mData.add( new Sample("apple", false) );
        mData.add( new Sample("banana", false) );
        mData.add( new Sample("cat", false) );
        mData.add( new Sample("dog", false) );

        return inflater.inflate(R.layout.blank_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rView = view.findViewById(R.id.recyclerview);
        rView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mAdapter = new MyAdapter();
        rView.setAdapter(mAdapter);

        //滑動
        ItemTouchHelper.SimpleCallback simpleCallback
                = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();  // 滑在第幾筆上面
                if (direction == ItemTouchHelper.LEFT) {  // 往左滑
                    //???
                    Log.d("ooooo","我被往左滑了");
                    Sample x = mData.get(position);
                    x.setSelect( !x.getSelect() );
                    mAdapter.notifyItemChanged(position);
                }
                if (direction == ItemTouchHelper.RIGHT ){ // 往右滑
                    //????
                    Log.d("ooooo","我被往右滑了");
                    //預設的概念是，滑動完一定有某些操作，不會自動復原
                    //mAdapter.notifyItemChanged(position);   // 第 position 位置重新繪製
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rView);


//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback() {
//            @Override
//            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//            }
//        }).attachToRecyclerView(rView);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BlankViewModel.class);
        // TODO: Use the ViewModel
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        @NonNull
        @NotNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.myitem_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
            Sample x = mData.get(position);
            holder.tv.setText( x.getName() );
            holder.cb.setChecked( x.getSelect() );
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            CheckBox cb;
            public MyViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.textView);
                cb = itemView.findViewById(R.id.checkBox);

                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        Sample x = mData.get(position);
                        x.setSelect( ((CheckBox)v).isChecked() );
                        //如果後端還有資料庫的話，看要不要此時寫回
                    }
                });
            }
        }

    }


    class Sample {
        String name;
        Boolean select;

        Sample(String n, Boolean s){
            name = n;
            select = s;
        }

        public String getName() {
            return name;
        }

        public Boolean getSelect() {
            return select;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSelect(Boolean select) {
            this.select = select;
        }
    }

}