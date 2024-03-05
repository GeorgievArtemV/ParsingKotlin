package com.example.parsingkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.parsingkotlin.databinding.FragmentBlankBinding
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class CoreFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBlankBinding.inflate(inflater,container,false)

        binding.editText.text
        binding.buttonStart.setOnClickListener {
            //binding.textView2.text = getWeb(binding.editText.text.toString())
            val singleOnSubscribe = SingleOnSubscribe<String> {
                it.onSuccess(getWeb(binding.editText.text.toString()))
            }
            val single = Single.create(singleOnSubscribe)
            single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { x: String? -> binding.textView2.text = x }

            /*val singleOnSubscribe = SingleOnSubscribe<String?> { emitter: SingleEmitter<String?> ->
                emitter.onSuccess(getWeb())

            }
            val single = Single.create(singleOnSubscribe)
            single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { x: String? ->
                    binding.descCountry.setText(
                        x
                    )
                }*/

            /*val singleOnSubscribe = SingleOnSubscribe<String?> { emitter: SingleEmitter<String?> ->
                emitter.onSuccess(getWeb())
                binding.progress.setVisibility(View.INVISIBLE)
            }*/
        }
        return binding.root
    }
    private fun getWeb(urlString: String): String {
        try {
            val doc: Document = Jsoup.connect(urlString).get()
            return doc.body().html().toString()
        } catch (e: Exception){
            throw RuntimeException(e)
        }
    }
}