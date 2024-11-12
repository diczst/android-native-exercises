package com.neonusa.exercises

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ExerciseGridDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // Item position
        val column = position % spanCount // Item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount // Spacing on the left
            outRect.right = (column + 1) * spacing / spanCount // Spacing on the right

            if (position < spanCount) { // Top edge for the first row
                outRect.top = spacing
            }
            outRect.bottom = spacing // Bottom spacing for all items
        } else {
            outRect.left = column * spacing / spanCount // Spacing on the left without edge
            outRect.right = spacing - (column + 1) * spacing / spanCount // Spacing on the right without edge
            if (position >= spanCount) {
                outRect.top = spacing // Top spacing for all rows except the first
            }
        }
    }
}