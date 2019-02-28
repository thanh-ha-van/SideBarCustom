package ha.thanh.sidebarsmartapp.prgrs

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import ha.thanh.sidebarsmartapp.R
import kotlinx.android.synthetic.main.view_pin_input.view.*


class UserNameInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr), UserNameInput {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {

        View.inflate(context, R.layout.view_pin_input, this)
        tvInputField.setSelection(tvInputField.length())

        val ta = context.obtainStyledAttributes(attrs, R.styleable.UserNameInputView)
        try {
            val laborText = ta.getString(R.styleable.UserNameInputView_input_labor)
            tvLabor.text = laborText
            val hintText = ta.getString(R.styleable.UserNameInputView_input_hint_message)
            tvHint.text = hintText
            val visibleStatus = ta.getInteger(R.styleable.UserNameInputView_visible_status, 1)
            changeStatus(visibleStatus)
        } finally {
            ta.recycle()
        }
    }

    private fun changeStatus(visibleStatus: Int) {
        when (visibleStatus) {
            1 -> {
                hideProgressAndShowSuccess()
            }
            2 -> {
                hideProgressAndShowError()
            }
            3 -> {
                showProgressAndHideIcon()
            }
        }
    }

    private fun showErrorIcon() {
        statusIcon.setImageResource(R.drawable.ic_error)
        statusIcon.animate()
            .alpha(1f)
            .setDuration(200).start()
    }

    private fun showSuccessIcon() {
        statusIcon.setImageResource(R.drawable.ic_checked)
        statusIcon.animate()
            .alpha(1f)
            .setDuration(200).start()
    }

    private fun hideIcon() {
        statusIcon.setImageResource(R.drawable.ic_checked)
        statusIcon.animate()
            .alpha(0.0f)
            .setDuration(200).start()
    }

    private fun hideProgressAndShowSuccess() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressAndShowError() {
        progressBar.animate()
            .alpha(0.0f)
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    showErrorIcon()
                }
            })
    }

    private fun showProgressAndHideIcon() {
        progressBar.animate()
            .alpha(1f)
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    hideIcon()
                }
            })
    }

    override fun showError(messageId: Int) {
        tvError.text = context.resources.getString(id)
        if (tvError.visibility != View.VISIBLE) {
            tvError.visibility = View.VISIBLE
        }
    }

    override fun hideError() {
        tvError.visibility = View.INVISIBLE
    }


    override fun showHint(messageId: Int) {
        tvHint.text = context.resources.getString(id)
        if (tvHint.visibility != View.VISIBLE) {
            tvHint.visibility = View.VISIBLE
        }
    }

    override fun hideHint() {
        tvHint.visibility = View.GONE
    }

    override fun setStatusType(visibleStatus: Int) {
        changeStatus(visibleStatus)
    }
}