package myapplication.second.workinghourmanagement.dto

data class ResultBnumCheck (
    val request_cnt: Int?,
    val match_cnt: Int?,
    val status_code: String,
    val data: List<BNumCheckData>?
)
data class BNumCheckData(
    val b_no: String,
    val b_stt: String,
    val b_stt_cd: String,
    val tax_type: String,
    val tax_type_cd: String,
    val end_dt: String,
    val utcc_yn: String,
    val tax_type_change_dt: String,
    val invoice_apply_dt: String
)