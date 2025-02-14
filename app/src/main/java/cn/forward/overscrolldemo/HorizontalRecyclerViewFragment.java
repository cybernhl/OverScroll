package cn.forward.overscrolldemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.forward.overscroll.IOffsetChangeListener;
import cn.forward.overscroll.IOverScrollView;

/**
 * @author ziwei huang
 */
public class HorizontalRecyclerViewFragment extends Fragment {

    public static int[] COLORS = new int[]{0xff00ff00, 0xffffffff, 0xff00ffff, 0xffffff00, 0xffff00ff, 0xff8FBC8F};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recyclerview_horizontal, container, false);
        initHorizontal(view);
        return view;
    }

    public static void initHorizontal(View view) {
        final View iconHeaderView = view.findViewById(R.id.icon_header);
        final View iconFooterView = view.findViewById(R.id.icon_footer);

        IOverScrollView overScrollView = view.findViewById(R.id.overscroll_view);
        overScrollView.addOffsetChangeListener(new IOffsetChangeListener() {
            @Override
            public void onOffsetChanged(View child, int offset) {
                if (child.getHeight() == 0) {
                    return;
                }

                int absOffset = Math.abs(offset);
                float scale = 3 * absOffset * 1f / child.getHeight();
                if (offset >= 0) {
                    iconHeaderView.setPivotX(0);
                    iconHeaderView.setPivotY(iconHeaderView.getHeight() / 2);
                    iconHeaderView.setScaleX(scale);
                    iconHeaderView.setScaleY(scale);

                    iconFooterView.setScaleX(0);
                    iconFooterView.setScaleY(0);
                } else {
                    iconFooterView.setPivotX(iconFooterView.getWidth());
                    iconFooterView.setPivotY(iconFooterView.getHeight() / 2);
                    iconFooterView.setScaleX(scale);
                    iconFooterView.setScaleY(scale);

                    iconHeaderView.setScaleX(0);
                    iconHeaderView.setScaleY(0);
                }
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.overscroll_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater factory = LayoutInflater.from(parent.getContext());
                View item = factory.inflate(R.layout.item_horizontal, parent, false);
                return new RecyclerView.ViewHolder(item) {
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                TextView textView = holder.itemView.findViewById(R.id.text);
                textView.setText("" + (1 + position));

                View container = holder.itemView.findViewById(R.id.container);
                container.setBackgroundColor(COLORS[position % COLORS.length]);
            }

            @Override
            public int getItemCount() {
                return 6;
            }
        });

    }
}
