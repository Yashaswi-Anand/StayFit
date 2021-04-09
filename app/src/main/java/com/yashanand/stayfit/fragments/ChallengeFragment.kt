package com.yashanand.stayfit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yashanand.stayfit.R
import com.yashanand.stayfit.adapters.DailyExerciseAdapter
import com.yashanand.stayfit.adapters.MyListener

class ChallengeFragment : Fragment(), MyListener {

    private lateinit var RVChallege:RecyclerView
    companion object{
        private const val RC_SIGN_IN = 120
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_challenge, container, false)
        RVChallege = view.findViewById(R.id.RVChallengeFrg)
        RVChallege.layoutManager = LinearLayoutManager(context)
        val items = fetchData()
        RVChallege.adapter = DailyExerciseAdapter(activity!!,this,items)

        return view
    }

    private fun fetchData(): List<String> {
        val list= ArrayList<String>()
        for (i in 1..30){
            if(i%6==0){
                list.add("Day $i,Today is Rest.")
            }else {
                list.add("Day $i")
            }
        }
        return list
    }

    override fun onItemClicked() {
        //Toast.makeText(activity,"My first interface implementation",Toast.LENGTH_SHORT).show()
        val bottomSheetDialog: BottomSheetDialog? = context?.let { BottomSheetDialog(it,R.style.BottomSheetDialogTheme) }

        val bottomSheetView = LayoutInflater.from(context).inflate(
            R.layout.payment_gateway_layout,
            null
        )
        bottomSheetView.findViewById<View>(R.id.debitCard).setOnClickListener {
            Toast.makeText(context,"pay with card...",Toast.LENGTH_SHORT).show()
            bottomSheetView.findViewById<View>(R.id.cc).visibility = View.VISIBLE
            bottomSheetView.findViewById<View>(R.id.gpay_method).visibility = View.GONE
            bottomSheetView.findViewById<View>(R.id.patm_method).visibility = View.GONE
            bottomSheetView.findViewById<View>(R.id.debitCard).visibility = View.GONE
            bottomSheetView.findViewById<View>(R.id.txtInfoToPay).visibility = View.GONE
        }
        //dialog?.cancel()
        bottomSheetDialog?.setContentView(bottomSheetView)
        bottomSheetDialog?.show()
    }

}