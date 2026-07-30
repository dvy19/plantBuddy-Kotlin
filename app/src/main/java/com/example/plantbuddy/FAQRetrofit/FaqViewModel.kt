package com.example.plantbuddy.FAQRetrofit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class GetFaqState{
    object Idle:GetFaqState()
    object Loading:GetFaqState()
    data class Success(val data: FaqData):GetFaqState()
    data class Error(val message:String):GetFaqState()
}

class FaqViewModel():ViewModel(){

    private val repo=FaqRepo()

    private val _getFaqState= MutableStateFlow<GetFaqState>(GetFaqState.Idle)
    val getFaqState:StateFlow<GetFaqState> = _getFaqState.asStateFlow()

    fun getFaq(request: FaqRequest){

        viewModelScope.launch{

            _getFaqState.value=GetFaqState.Loading

            try{
                val response=repo.getPlantFaq(request)

                Log.d("m",response.toString())
                Log.d("m",response.body().toString())
                Log.d("m",response.isSuccessful.toString())
                Log.d("m",response.code().toString())
                Log.d("m",response.message())
                Log.d("m",response.errorBody().toString())



                if(response.isSuccessful && response.body()!=null){

                        _getFaqState.value=GetFaqState.Success(response.body()!!.data)
                    }

                else{
                    _getFaqState.value=GetFaqState.Error(response.message())
                }


                }catch(e:Exception){
                    _getFaqState.value=GetFaqState.Error(e.message ?: "Unknown Error")
                }


        }
    }



}