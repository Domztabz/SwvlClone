package com.andromeda.swvlclone

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_payment_methods.*
import java.io.Serializable


data class PaymentMethod(val link: Int, val name: String) : Serializable

class PaymentMethodsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_methods)


        val items = ArrayList<PaymentMethod>()

        val item = PaymentMethod(R.drawable.mpesa, "MPESA")

        items.add(item)

        payment_method_recycler?.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = PaymentMethodRecyclerAdapter(items, this, {method: PaymentMethod -> chosenMethod(method)})
        }

//        pesapal?.setOnClickListener {
//            makePayments()
//
//        }
    }

    fun chosenMethod(paymentMethod: PaymentMethod) {
//        val intent = Intent()
//        intent.putExtra(METHOD, paymentMethod)
//        setResult(Activity.RESULT_OK, intent)
//        finish()

        startActivity(Intent(this, MPESAActivity::class.java))

    }

    val METHOD = "METHOD"


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        val bundle: Bundle? = data?.getExtras();
//        if (bundle != null) {
//            for (key in bundle!!.keySet()) {
//                if (bundle.get(key) != null) {
//                    Log.e(TAG, key + " : " + bundle!!.get(key));
//
//                } else {
//                    Log.e(TAG, key + " : " + "NULL");
//
//                }
//            }
//        }

        if(requestCode == PAYMENT_ACTIVITY_REQUEST_CODE && data != null) {
            val payment : String = data.getStringExtra("payment")
            Log.d(TAG, "PAyment: $payment")

        }

    }

    val SETTINGS_ACTIVITY_REQUEST_CODE = 100
    val PAYMENT_ACTIVITY_REQUEST_CODE = 101
    val TAG = "PaymentMethodsActivity"
}
