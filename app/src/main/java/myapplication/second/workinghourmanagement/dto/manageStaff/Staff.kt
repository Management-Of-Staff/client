package myapplication.second.workinghourmanagement.dto.manageStaff

data class Staff(
    val employmentId: Int,
    val staffId: Int,
    val staffName: String,
    val workingStatus: String,
    val profilePath: String?,
    val healthCertificateCheck: Boolean,    //보건증유무
    val workTimeRequests: List<WorkTimeRequest>
)