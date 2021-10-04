package com.ibrahimethem.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.ibrahimethem.artbooktesting.R
import com.ibrahimethem.artbooktesting.databinding.FragmentArtDetailsBinding
import com.ibrahimethem.artbooktesting.util.Status
import com.ibrahimethem.artbooktesting.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//resim secicegimiz için glide ihtiyacımız olucak
@AndroidEntryPoint //sonradan ekledim github
class ArtDetailsFragment @Inject constructor(
   val glide : RequestManager
) : Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding : FragmentArtDetailsBinding? = null

    lateinit var viewModel : ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObserves()

        //ImageView'a tıklandığında api kısmına gidicez
        binding.artImageView.setOnClickListener {
            //findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        //geri tuşuna basıldığında ne yapılacak onu yazıyoruz callback yazıcaz
        //normalde de bu işlem yapılıyor ama biz ekstradan mesaj/image göndereceğimiz için yazıyoruz
        val callback = object : OnBackPressedCallback(true){
            //object kullanmamızın sebebi OnBackPressedCallBack bir abstract class
            override fun handleOnBackPressed() {
                viewModel.setSelectedImage("") //github
                findNavController().popBackStack()
                //popBackStack -> bir önceki stack de ne varsa oraya git burayı kapat
            }
        }

        //callback'i ekliyoruz
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(
                binding.nameText.text.toString(),
                binding.artistText.text.toString(),
                binding.yearText.text.toString()
            )
        }
    }

    //secili resim
    private fun subscribeToObserves(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                //başarılı olursa
                Status.SUCCESS -> {
                    //requireActivity yerinde requireContext vardı github
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp() //geri gidecek -> navigeUp yerinde popBackStack vardı github
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    //view-binding de kullanılması öneriliyor
    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}