package ha.thanh.sidebarsmartapp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_loading.view.*

class LoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {

        View.inflate(context, R.layout.view_loading, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        try {

            viewLoading.background = context.getDrawable(R.drawable.semi_circle)
            viewLoading.background.level = 5000

        } finally {
            ta.recycle()
        }

    }

    public fun doAnimation() {
        viewLoading.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate))
    }
}