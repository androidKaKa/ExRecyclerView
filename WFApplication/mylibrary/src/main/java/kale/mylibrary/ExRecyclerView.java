package kale.mylibrary;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

/**
 * @author Jack Tony
 * @brief 增强型RecyclerView，可以设置头底视图
 * @attention 必须在设置adapter前设置header或footer, clickListener
 * @date 2015/4/11
 */
public class ExRecyclerView extends RecyclerView {

    protected final String TAG = getClass().getSimpleName();

    private View mHeaderView = null;

    private View mFooterView = null;

    public ExRecyclerView(Context context) {
        super(context);
    }

    public ExRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(final LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            lm.setSpanSizeLookup(new GridSpanSizeLookup(lm.getSpanCount()));
        } else if (layoutManager instanceof ExStaggeredGridLayoutManager) {
            ExStaggeredGridLayoutManager lm = (ExStaggeredGridLayoutManager) layoutManager;
            lm.setSpanSizeLookup(new GridSpanSizeLookup(lm.getSpanCount()));
        }
    }

    /**
     * 设置Grid/StaggeredGrid LayoutManager的布局样式
     */
    private class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private int mSpanSize = 1;

        public GridSpanSizeLookup(int spanSize) {
            mSpanSize = spanSize;
        }

        @Override
        public int getSpanSize(int position) {
            BaseRecyclerAdapter adapter = (BaseRecyclerAdapter) getAdapter();
            // 如果是头或底的类型，那么就设置横跨所有列
            if (adapter.getItemViewType(position) == BaseRecyclerAdapter.VIEW_TYPES.HEADER ||
                    adapter.getItemViewType(position) == BaseRecyclerAdapter.VIEW_TYPES.FOOTER) {
                return mSpanSize;
            }
            return 1;
        }
    }

    /**
     * Set the header view of the adapter.
     */
    public void addHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    /**
     * @return recycle的头部视图
     */
    public View getHeaderView() {
        return mHeaderView;
    }


    /**
     * 设置底部的视图
     */
    public void addFooterView(View footerView) {
        mFooterView = footerView;
    }

    /**
     * 得到底部的视图
     */
    public View getFooterView() {
        return mFooterView;
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof BaseRecyclerAdapter) {
            ((BaseRecyclerAdapter) adapter).mOnItemClickListener = mOnItemClickListener;
            ((BaseRecyclerAdapter) adapter).mOnItemLongClickListener = mOnItemLongClickListener;
            ((BaseRecyclerAdapter) adapter).customHeaderView = mHeaderView;
            ((BaseRecyclerAdapter) adapter).customFooterView = mFooterView;
        }
    }

    /**
     * 平滑滚动到某个位置
     *
     * @param isAbsolute position是否是绝对的，如果是绝对的，那么header的位置就是0
     *                   如果是相对的，那么position就是相对内容的list的位置
     */
    public void smoothScrollToPosition(int position, boolean isAbsolute) {
        if (!isAbsolute && mHeaderView != null) {
            position++;
        }
        smoothScrollToPosition(position);
    }

    /**
     * 设置item的点击事件
     */
    private static AdapterView.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 设置item的长按事件
     */
    public static AdapterView.OnItemLongClickListener mOnItemLongClickListener = null;

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }
}
