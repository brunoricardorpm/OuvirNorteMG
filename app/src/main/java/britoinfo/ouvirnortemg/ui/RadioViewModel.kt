package britoinfo.ouvirnortemg.ui

import android.content.ComponentName
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import britoinfo.ouvirnortemg.R
import britoinfo.ouvirnortemg.model.Radio
import britoinfo.ouvirnortemg.service.RadioPlaybackService
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RadioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RadioUiState())
    val uiState: StateFlow<RadioUiState> = _uiState.asStateFlow()

    private var controllerFuture: ListenableFuture<MediaController>? = null
    private val controller: MediaController?
        get() = if (controllerFuture?.isDone == true) controllerFuture?.get() else null

    val radios = listOf(
        Radio(
            "1",
            "ClubFM",
            "https://8191.brasilstream.com.br/stream?1788213824671",
            R.drawable.clubfm,
            "https://images.unsplash.com/photo-1598488035139-bdbb2231ce04?q=80&w=2070&auto=format&fit=crop"
        ),
        Radio(
            "2",
            "Rio Pardo FM",
            "https://stm25.srvstm.com:7488/stream",
            R.drawable.riopardofmico,
            R.drawable.bkrpm
        ),
        Radio(
            "3",
            "Rádio Independente 93.7 FM",
            "https://server07.srvsh.com.br:7716/stream?1788146554588",
            R.drawable.logoradio2,
            "https://images.unsplash.com/photo-1478737270239-2fccd8c78619?q=80&w=2070&auto=format&fit=crop"
        ),
        Radio(
            "4",
            "Rádio Sinal Verde 93.7 FM",
            "https://audio.welltecnologia.com.br/radio/8020/radio.mp3?1788146407896",
            R.drawable.sinalverde,
            "https://images.unsplash.com/photo-1557683316-973673baf926?q=80&w=2029&auto=format&fit=crop"
        ),
    )

    fun initWithContext(context: Context) {
        val sessionToken = SessionToken(context, ComponentName(context, RadioPlaybackService::class.java))
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture?.addListener({
            setupController()
        }, MoreExecutors.directExecutor())
    }

    private fun setupController() {
        val controller = controller ?: return
        
        _uiState.value = _uiState.value.copy(isPlaying = controller.isPlaying)
        
        controller.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _uiState.value = _uiState.value.copy(isPlaying = isPlaying)
            }

            override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                _uiState.value = _uiState.value.copy(
                    currentTitle = mediaMetadata.title?.toString() ?: "",
                    currentSubtitle = mediaMetadata.artist?.toString() ?: mediaMetadata.albumTitle?.toString() ?: ""
                )
            }
        })
    }

    fun playRadio(radio: Radio) {
        val controller = controller ?: return
        
        val mediaItem = MediaItem.Builder()
            .setUri(radio.url)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(radio.name)
                    .build()
            )
            .build()

        controller.setMediaItem(mediaItem)
        controller.prepare()
        controller.play()
        
        _uiState.value = _uiState.value.copy(selectedRadio = radio)
    }

    fun togglePlayPause() {
        val controller = controller ?: return
        if (controller.isPlaying) {
            controller.pause()
        } else {
            controller.play()
        }
    }

    override fun onCleared() {
        super.onCleared()
        controllerFuture?.let {
            MediaController.releaseFuture(it)
        }
    }
}

data class RadioUiState(
    val isPlaying: Boolean = false,
    val selectedRadio: Radio? = null,
    val currentTitle: String = "",
    val currentSubtitle: String = ""
)
