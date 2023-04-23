package argentum11.mipsdecoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class display_assembly : AppCompatActivity() {
    companion object{
        const val MACHINE_CODE:String = "machine code"
    }
    private lateinit var opcode:String
    private lateinit var  machineCode:String
    private lateinit var resultTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_assembly)
        machineCode = intent.getStringExtra(MACHINE_CODE).toString()
        resultTextView = findViewById(R.id.result)
        decodeCommand()
    }

    private fun decodeOPCode(opcode:String): String{
        if(opcode == "000000"){
            return getString(R.string.r_format)
        }
        else return getString(R.string.unknown_format)
    }

    private fun decodeCommand(){
        opcode = decodeOPCode(machineCode.substring(0, 6))
        resultTextView.text = opcode
    }

}