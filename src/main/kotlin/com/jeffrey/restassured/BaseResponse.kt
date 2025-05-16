package com.jeffrey.restassured

class BaseResponse<D>(
    val code: Int,
    val message: String?,
    val data: D?
) {
    companion object {
        fun <D> ofSuccess(): BaseResponse<D> {
            return BaseResponse(200, SuccessMessage.SUCCESS_MSG, null)
        }

        fun <D> ofSuccess(data: D): BaseResponse<D> {
            return BaseResponse(200, SuccessMessage.SUCCESS_MSG, data)
        }

        fun <D> ofSuccess(message: String?, data: D): BaseResponse<D> {
            return BaseResponse(200, message, data)
        }

        fun <D> ofError(message: String?): BaseResponse<D> {
            return BaseResponse(400, message, null)
        }

        fun <D> ofError(message: String?, data: D): BaseResponse<D> {
            return BaseResponse(400, message, data)
        }

        fun <D> ofError(code: Int, message: String?): BaseResponse<D> {
            return BaseResponse(code, message, null)
        }

        fun <D> ofError(code: Int, message: String?, data: D): BaseResponse<D> {
            return BaseResponse(code, message, data)
        }
    }
}
