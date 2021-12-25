package com.yoond.iiyy.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SupplementListDecoration(
    private val default: Int,
    private val last: Int
): RecyclerView.ItemDecoration() {
    var pos: Int = 0
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        pos = parent.getChildAdapterPosition(view)

        // FAB이 마지막 아이템을 가리지 않도록 하기 위해 bottom에 공간 설정
        if (pos == state.itemCount - 1) {
            outRect.bottom = last
        }
        outRect.top = default
        outRect.left = default
        outRect.right = default

    }
}