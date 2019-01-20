package ha.thanh.sidebarsmartapp.prgrs

/**
 *
 */
interface UserNameInput {

    /**
     *
     */
    fun showError(messageId: Int)

    /**
     *
     */
    fun hideError()

    /**
     *
     */
    fun showHint(messageId: Int)

    /**
     *
     */
    fun hideHint()


    fun setStatusType(visibleStatus: Int)
}