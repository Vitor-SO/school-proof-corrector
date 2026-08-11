package vitorcode.school_proof_corrector.data.local

import android.content.Context
import androidx.core.content.edit
import com.auth0.jwt.JWT
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import java.util.Base64
import java.util.UUID
import javax.inject.Inject

private const val PREF_NAME = "authentication"
private const val KEY_TOKEN = "access_token"

class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val prefs = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    fun saveAccessToken(accessToken: String) {
        prefs.edit {
            putString(KEY_TOKEN, accessToken)
        }
    }

    fun getToken(): String? {
        val token = prefs.getString(KEY_TOKEN, null) ?: return null

        return if (isExpired(token)) {
            clearToken()
            null
        } else {
            token
        }
    }

    fun clear() {
        prefs.edit {
            remove(KEY_TOKEN)
        }
    }

    private fun clearToken() {
        prefs.edit {
            remove(KEY_TOKEN)
        }
    }

    private fun isExpired(token: String): Boolean {
        return try {
            val parts = token.split(".")

            if (parts.size < 2) {
                return true
            }

            val payload = String(
                Base64.getUrlDecoder().decode(parts[1])
            )

            val json = JSONObject(payload)

            val exp = json.optLong("exp", 0L)

            if (exp == 0L) {
                return true
            }

            // exp vem em segundos
            val expirationMillis = exp * 1000

            // margem de 5 minutos
            System.currentTimeMillis() >= (expirationMillis - 5 * 60 * 1000)

        } catch (_: Exception) {
            true
        }
    }

    fun getUserId(): UUID {
        val token = getToken() ?: return UUID(0, 0)
        val jwt = JWT()
        return UUID.fromString(
            jwt.decodeJwt(token)
                .getClaim("id")
                .asString()
        )
    }
}