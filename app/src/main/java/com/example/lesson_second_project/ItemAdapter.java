package com.example.lesson_second_project;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {this.itemList=itemList;}

    class MyViewHolder extends RecyclerView.ViewHolder{
        public View view;
        private TextView tvname;
        private TextView tvdate;
        private TextView tvmemo;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            view = itemView;
            tvname=itemView.findViewById(R.id.itname);
            tvdate=itemView.findViewById(R.id.itdate);
            tvmemo=itemView.findViewById(R.id.itmemo);
        }

    }

    //insert
    public void addItem(Item item){
        itemList.add(item);
        notifyDataSetChanged(); // 새로고침
    }

    //update
    public void updateItem(Item item, int position) {
        Item i = itemList.get(position);
        i.setItdate(item.getItdate());
        i.setItmemo(item.getItmemo());
        i.setItname(item.getItname());
        i.setItposition(item.getItposition());
        i.setItuser(item.getItuser());
        notifyDataSetChanged();
    }

    //remove
    public void removeItem(int position) {
        itemList.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position){
        Item item=itemList.get(position);
        holder.tvname.setText(item.getItname());
        holder.tvdate.setText(item.getItdate());
        holder.tvmemo.setText(item.getItmemo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = v.inflate(v.getContext(), R.layout.layout_add, null);
                final EditText etName = dialogView.findViewById(R.id.etname);
                final EditText etDate = dialogView.findViewById(R.id.etdate);
                final EditText etPosition = dialogView.findViewById(R.id.etposition);
                final EditText etMemo = dialogView.findViewById(R.id.etmemo);
                final Button buttonStart = dialogView.findViewById(R.id.btnStart);
                final Button buttonCancel = dialogView.findViewById(R.id.btnCancel);
                buttonStart.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.INVISIBLE);


                etName.setText(item.getItname());
                etDate.setText(item.getItdate());
                etPosition.setText(item.getItposition());
                etMemo.setText(item.getItmemo());
                AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                dlg.setTitle("물품 수정");
                dlg.setView(dialogView);

                dlg.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (etName.getText().toString().equals("")) {
                            Toast.makeText(v.getContext(), "제품명을 입력하세요.", Toast.LENGTH_SHORT).show();
                        } else if (etDate.getText().toString().equals("")) {
                            Toast.makeText(v.getContext(), "유통기한을 입력하세요.", Toast.LENGTH_SHORT).show();
                        } else if (etMemo.getText().toString().equals("")) {
                            Toast.makeText(v.getContext(), "메모를 입력하세요.", Toast.LENGTH_SHORT).show();
                        } else if (etPosition.getText().toString().equals("")) {
                            Toast.makeText(v.getContext(), "냉장고안의 위치를 입력하세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            Item itemDto = new Item();
                            itemDto.setItname(etName.getText().toString());
                            itemDto.setItdate(etDate.getText().toString());
                            itemDto.setItposition(etPosition.getText().toString());
                            itemDto.setItmemo(etMemo.getText().toString());

                            ItemService itemService = ItemClient.getInstance().getItemService();
                            Call<Item> call = itemService.update(item.getId(), itemDto);
                            call.enqueue(new Callback<Item>() {
                                @Override
                                public void onResponse(Call<Item> call, Response<Item> response) {
                                    updateItem(response.body(), position);
                                }

                                @Override
                                public void onFailure(Call<Item> call, Throwable t) {

                                }
                            });
                        }
                    }
                });

                dlg.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ItemService itemService = ItemClient.getInstance().getItemService();
                        Call<Void> call = itemService.deleteById(item.getId());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                removeItem(position);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });
                dlg.setNeutralButton("Close", null);
                dlg.show();
            }
        });
    }

    @Override
    public int getItemCount() { return itemList==null ? 0 : itemList.size();    }
}
