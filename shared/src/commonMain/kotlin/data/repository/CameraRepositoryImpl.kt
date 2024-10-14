package data.repository

import data.Api
import data.model.ApiException
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.datetime.Clock
import ui.repository.CameraRepository

class CameraRepositoryImpl(
    private val httpClient: HttpClient
) : CameraRepository {

    override suspend fun uploadImage(meetingId: Long, image: ByteArray): Result<Unit> =
        runCatching {
            httpClient.submitFormWithBinaryData(
                url = Api.MOIMS_PHOTO(meetingId),
                formData = formData {
                    append("file", image, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpg")
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"$meetingId-${Clock.System.now()}.jpg\""
                        )
                    })
                }
            ).also { if (it.status.value != 200) throw ApiException(it.status) }
        }
}
