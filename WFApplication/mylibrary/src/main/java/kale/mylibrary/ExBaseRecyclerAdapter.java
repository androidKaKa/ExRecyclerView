package kale.mylibrary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * @author Jack Tony
 * @brief recycleView的基础适配器，处理了添加头和底的逻辑
 * @date 2015/4/10
 */
public abstract class ExBaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final String TAG = getClass().getSimpleName();

    public View customHeaderView = null;

    public View customFooterView = null;

    public ExRecyclerView.OnItemClickListener mOnItemClickListener;

    public ExRecyclerView.OnItemLongClickListener mOnItemLongClickListener;


    /**
     * view的基本类型，这里只有头/底部/普通，在子类中可以扩展
     */
     class VIEW_TYPES {

        public static final int HEADER = 5250;

        public static final int FOOTER = 7038;

        public static final int NORMAL = 3501;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.HEADER && customHeaderView != null) {
            return new SimpleViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.FOOTER && customFooterView != null) {
            return new SimpleViewHolder(customFooterView);
        }
        // 如果不是头类型或底类型，那么就交给子类去返回一个viewHolder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return onCreateViewHolder(parent, viewType, inflater);
    }

    /**
     * 得到一个viewholder对象
     *
     * @param parent   父控件
     * @param viewType 视图类型
     * @param layoutInflater 
     * @return
     */
    protected abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater layoutInflater);

    /**
     * 返回adapter中总共的item数目，包括头部和底部
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        int headerOrFooter = 0;
        if (customHeaderView != null) {
            headerOrFooter++;
        }
        if (customFooterView != null) {
            headerOrFooter++;
        }
        return getAdapterItemCount() + headerOrFooter;
    }

    /**
     * 返回adapter中不包括头和底view的item数目
     *
     * @return The number of items in the bound adapter
     */
    public abstract int getAdapterItemCount();


    @Override
    public int getItemViewType(int position) {
        if (customFooterView != null && position == getItemCount() - 1) {
            return VIEW_TYPES.FOOTER;
        } else if (customHeaderView != null && position == 0) {
            return VIEW_TYPES.HEADER;
        } else {
            return getItemViewType(position, true);
        }
    }

    /**
     * 根据位置得到view的类型
     *
     * @param nothing 无意义的参数
     */
    public abstract int getItemViewType(int position, boolean nothing);

    /**
     * 载入ViewHolder，这里仅仅处理header和footer视图的逻辑
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if ((customHeaderView != null && position == 0) || (customFooterView != null && position == getItemCount() - 1)) {
            // 如果是header或者是footer则不处理
        } else {
            if (customHeaderView != null) {
                position--;
            }
            onBindViewHolder(viewHolder, position, true);
            final int pos = position;
            // 设置点击事件  
            if (mOnItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(viewHolder.itemView, pos);
                    }
                });
            }
            // 设置长按事件
            if (mOnItemLongClickListener != null) {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return mOnItemLongClickListener.onItemLongClick(viewHolder.itemView, pos);
                    }
                });
            }
        }
    }

    /**
     * 载入viewHolder
     *
     * @param nothing 无意义的参数
     */
    public abstract void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position, boolean nothing);

    public void toggleSelection(int pos) {
        notifyItemChanged(pos);
    }


    public void clearSelection(int pos) {
        // 单条刷新
        notifyItemChanged(pos);
    }

    public void setSelected(int pos) {
        // 单条刷新
        notifyItemChanged(pos);
    }

    /**
     * 交换两个item的位置
     */
    public void swapPositions(List<?> list, int from, int to) {
        Collections.swap(list, from, to);
        notifyDataSetChanged();
    }

    /**
     * 加入一个item
     */
    public <T> void insert(List<T> list, T object, int position) {
        list.add(position, object);
        if (customHeaderView != null) {
            position++;
        }
        //notifyItemInserted(position);
        notifyDataSetChanged();
    }

    /**
     * 从适配器中移出某个位置的item
     */
    public void remove(List<?> list, int position) {
        list.remove(position);
        //notifyItemRemoved(customHeaderView != null ? position + 1 : position);
        notifyDataSetChanged();

    }

    /**
     * 删除适配器中的所有元素，不包括头部和底部
     */
    public void clear(List<?> list) {
        if (list == null) {
            return;
        }
        list.clear();
        // 移除一定范围的元素
        //notifyItemRangeRemoved(start, size);
        notifyDataSetChanged();
    }
}
