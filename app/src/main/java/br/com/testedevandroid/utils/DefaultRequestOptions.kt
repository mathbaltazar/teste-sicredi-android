package br.com.testedevandroid.utils

import br.com.testedevandroid.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class DefaultRequestOptions {
    companion object {
        private var instance: RequestOptions? = null

        fun instance() = instance ?:
            RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.image_not_supported)
                .centerCrop().also { instance = it }

    }
}