package ha.thanh.sidebarsmartapp.prgrs

interface UserNameInput {

    //    error message handling
    fun showError(messageId: Int)

    fun hideError()

    //    hint message handle
    fun showHint(messageId: Int)

    fun hideHint()

    //    validation online handle
    fun doOfflineValidation()

    fun onOfflineValidationFail(messageId: Int)
    fun onOfflineValidationSuccess()

}
