package com.vungn.luckywheeldemo

import android.content.res.Resources
import android.util.TypedValue

fun Resources.getDpOfFloat(dp: Float): Int {
    return Math.round(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, this.displayMetrics
        )
    )
}