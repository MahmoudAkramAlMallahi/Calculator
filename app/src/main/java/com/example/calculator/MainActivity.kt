package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {
    lateinit var chipGroupTo:ChipGroup
    lateinit var chipGroupFrom:ChipGroup
    lateinit var textNumber:TextView
    lateinit var textResult:TextView
    lateinit var btnDeleteOneElement:AppCompatImageButton
    lateinit var converterImage:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        chipGroupFrom.setOnCheckedChangeListener { group, checkedIds ->

            converterImage.visibility = View.VISIBLE
            chipGroupTo.visibility = View.VISIBLE

            val chip = group.findViewById<Chip>(checkedIds)
            chooseTypeConvertFrom(chip)
            onChickDeleteAllNumber(findViewById(chip.id))
        }
        chipGroupTo.setOnCheckedChangeListener { group, checkedIds ->

            if(textNumber.text.isEmpty()){
                Toast.makeText(this,"The text is empty and does not contain numbers"
                    ,Toast.LENGTH_LONG).show()

            }else{
                val chip:Chip=group.findViewById(checkedIds)
                val chipConverterTo=chip.text
                val number=textNumber.text.toString()
                textResult.text = finalResult(number,chipConverterTo)
                textResult.visibility=View.VISIBLE
            }
        }
    }


    private fun initView(){
        chipGroupTo=findViewById(R.id.main_cg_to)
        chipGroupFrom=findViewById(R.id.main_cg_from)
        textNumber=findViewById(R.id.main_tv_number)
        textResult=findViewById(R.id.main_tv_result)
        btnDeleteOneElement=findViewById(R.id.main_btn_deleteOneElement)
        converterImage=findViewById(R.id.main_iv_restart)
    }
    private fun chooseTypeConvertFrom(chip:Chip){

        val btnList = listOf(
            findViewById<Button>(R.id.main_btn_0),
            findViewById<Button>(R.id.main_btn_1),
            findViewById<Button>(R.id.main_btn_2),
            findViewById<Button>(R.id.main_btn_3),
            findViewById<Button>(R.id.main_btn_4),
            findViewById<Button>(R.id.main_btn_5),
            findViewById<Button>(R.id.main_btn_6),
            findViewById<Button>(R.id.main_btn_7),
            findViewById<Button>(R.id.main_btn_8),
            findViewById<Button>(R.id.main_btn_9),
            findViewById<Button>(R.id.main_btn_A),
            findViewById<Button>(R.id.main_btn_B),
            findViewById<Button>(R.id.main_btn_C),
            findViewById<Button>(R.id.main_btn_D),
            findViewById<Button>(R.id.main_btn_E),
            findViewById<Button>(R.id.main_btn_F)
        )

        when (chip.text) {
            "Bin" -> {
                btnList.forEachIndexed { index, button ->
                    button.isEnabled = index in 0..1
                }
            }
            "Dec" -> {
                btnList.forEachIndexed { index, button ->
                    button.isEnabled = index in 0..9
                }
            }
            "Oct" -> {
                btnList.forEachIndexed { index, button ->
                    button.isEnabled = index in 0..7
                }
            }
            "Hex" -> {
                btnList.forEach { it.isEnabled = true }
            }
        }
    }
    private fun finalResult(number:String,chipConverterTo: CharSequence):String{
        return when{
            chipConverterTo.equals("Bin") ->{
                when(chipGroupFrom.checkedChipId){
                    R.id.main_chip_bin -> number
                    R.id.main_chip_dec -> number.toString().toInt().toString(2)
                    R.id.main_chip_oct ->number.toString().toInt(8).toString(2)
                    else -> number.toString().toInt(16).toString(2)
                }
            }
            chipConverterTo.equals("Dec") -> {
                when(chipGroupFrom.checkedChipId){
                    R.id.main_chip_bin ->Integer.parseInt(number,2).toString()
                    R.id.main_chip_dec ->number
                    R.id.main_chip_oct ->number.toInt(8).toString()
                    else ->number.toInt(16).toString()
                }
            }
            chipConverterTo.equals("Oct") -> {
                when(chipGroupFrom.checkedChipId){
                    R.id.main_chip_bin ->number.toInt(2).toString(8)
                    R.id.main_chip_dec ->number.toInt().toString(8)
                    R.id.main_chip_oct ->number
                    else ->number.toInt(16).toString(8)
                }
            }
            else ->{
                when(chipGroupFrom.checkedChipId){
                    R.id.main_chip_bin ->number.toInt(2).toString(16)
                    R.id.main_chip_dec -> number.toInt().toString(16)
                    R.id.main_chip_oct ->number.toInt(8).toString(16)
                    else ->number

                }
            }
        }
    }
    fun onClickNumber(view:View){
        val newNumber=(view as Button).text.toString()
        val oldNumber=textNumber.text.toString()
        val resultText=oldNumber + newNumber
        textNumber.text=resultText

    }
    fun onChickDeleteOneNumber(view: View){
        var oldText=textNumber.text.toString()
        var newText=""
        if (textNumber.text.isNotEmpty()) newText=textNumber.text.substring(0,oldText.length-1)
        textNumber.text=newText
    }
    fun onChickDeleteAllNumber(view: View){
        textNumber.text=""
        textResult.visibility=View.GONE
    }

}