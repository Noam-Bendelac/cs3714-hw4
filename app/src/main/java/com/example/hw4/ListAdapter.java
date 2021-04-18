package com.example.hw4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * A custom adapter implementation that binds views to items of a list
 * @param <I> type of list item (data)
 */
public class ListAdapter<I> extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
  
  /**
   * functional interface that takes the itemView to bind, and the I item (data) to bind to;
   * and binds itemView to reflect the I item's data
   * @param <I> type of data item
   */
  public interface ItemBinder<I> {
    void bind(View itemView, I item);
  }
  
  
  private final Context mCtx;
  private final List<I> mList;
  private final int mItemResId;
  private final ItemBinder<I> mBind;
  
  /**
   * 
   * @param ctx parent context
   * @param list list of I items
   * @param itemResId resource id of item's layout
   * @param bind function that takes the itemView to bind, and the I item (data) to bind to;
   *             and binds itemView to reflect the I item's data
   */
  public ListAdapter(Context ctx, List<I> list, int itemResId, ItemBinder<I> bind) {
    mCtx = ctx;
    mList = list;
    mItemResId = itemResId;
    mBind = bind;
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(mCtx).inflate(mItemResId, parent, false);
    return new ViewHolder(itemView);
  }
  
  @Override
  public int getItemCount() {
    return mList.size();
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    I item = mList.get(position);
    mBind.bind(holder.mItemView, item);
  }
  
  
  public static class ViewHolder extends RecyclerView.ViewHolder {
    
    private final View mItemView;
  
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      mItemView = itemView;
    }
    
  }
  
  
}
