package com.gerija.giphy.presentation

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gerija.giphy.R
import com.gerija.giphy.data.remote.api.dto.Data
import com.gerija.giphy.databinding.ActivitySingleGifBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class SingleGifActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleGifBinding
    private lateinit var gestureDetector: GestureDetectorCompat

    @Inject
    lateinit var viewModelFactory: GifsViewModelFactory

    private val viewModel: GifsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GifsViewModel::class.java]
    }
    private val component by lazy {
        (application as MyApplication).component
    }

    companion object {
        const val MIN_DISTANCE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleGifBinding.inflate(layoutInflater)
        component.inject(this)

        setContentView(binding.root)
        startAndSetIntents() //получаю и устанавливаю значение из intent
        deleteGifs() //слушатель на кнопку удаление gif

        gestureDetector = GestureDetectorCompat(this, GestureListener())
        binding.imLogo.setOnClickListener { finish() }
    }


    /**
     * Получаю интент и задаю их значение
     */
    private fun startAndSetIntents() {
        if (viewModel.firstVisitSingleAct) {
            viewModel.gifsPosSAct = intent.getIntExtra("position", 0)
            viewModel.gifsListSAct = intent.getSerializableExtra("gifsList") as ArrayList<Data>

            Glide.with(this).load(
                viewModel.gifsListSAct[viewModel.gifsPosSAct]
                    .images?.original?.url
            ).into(binding.imSingleGif)

            viewModel.firstVisitSingleAct = false
        } else {
            Glide.with(this).load(
                viewModel.gifsListSAct[viewModel.gifsPosSAct]
                    .images?.original?.url
            ).into(binding.imSingleGif)
        }
    }

    /**
     * Устанавливаю слушатель на уделание картинки
     */
    private fun deleteGifs() {
        binding.imageDelete.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {

                viewModel.deleteItem(viewModel.gifsListSAct[viewModel.gifsPosSAct])

                lifecycleScope.launch {
                    delay(200)
                    viewModel.gifsListSAct.removeAt(viewModel.gifsPosSAct)
                    binding.imageDelete.setImageResource(R.drawable.ic_delete_precced)

                    lifecycleScope.launch {
                        delay(200)
                        Glide.with(this@SingleGifActivity).load(
                            viewModel.gifsListSAct
                                    [viewModel.gifsPosSAct].images?.original?.url
                        ).into(binding.imSingleGif)
                        binding.imageDelete.setImageResource(R.drawable.ic_delete_normal)
                    }
                }
            }
        }
    }

    /**
     * Класс для слайдера
     */
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        /**
         * Действия при скроле(настраиваю слайдер)
         */
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {

            val diffX = e2.x - e1.x
            if (Math.abs(diffX) > MIN_DISTANCE) {

                if (e2.x > e1.x) {
                    //уменьшаю позицию с которой будет браться gif и меняю цвет стрелки для эффекта
                    if (viewModel.gifsPosSAct != 0) {
                        viewModel.gifsPosSAct--
                        binding.imLeft.setImageResource(R.drawable.ic_left_pressed)
                    }
                    //ставлю 2 мил/сек задержку, что бы отобразить изменение цвета на стрелке, при клике
                    lifecycleScope.launch {
                        delay(200)
                        binding.imLeft.setImageResource(R.drawable.ic_left_normal)
                    }
                    Glide.with(this@SingleGifActivity).load(
                        viewModel.gifsListSAct[viewModel.gifsPosSAct]
                            .images?.original?.url
                    ).into(binding.imSingleGif)

                } else {
                    //увеличиваю позицию с которой будет браться gif и меняю цвет стрелки дял эффекта
                    if (viewModel.gifsPosSAct < viewModel.gifsListSAct.size - 1) {
                        viewModel.gifsPosSAct++
                        binding.imRight.setImageResource(R.drawable.ic_right_pressed)
                    }
                    Glide.with(this@SingleGifActivity).load(
                        viewModel.gifsListSAct
                                [viewModel.gifsPosSAct].images?.original?.url
                    ).into(binding.imSingleGif)
                    //ставлю 2 мил/сек задержку, что бы отобразить изменение цвета на стрелке, при клике
                    lifecycleScope.launch {
                        delay(200)
                        binding.imRight.setImageResource(R.drawable.ic_right_normal)
                    }
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}