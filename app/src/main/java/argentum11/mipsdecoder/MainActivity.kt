package argentum11.mipsdecoder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var machineCode:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_decode:Button = findViewById(R.id.btn_decode)
        btn_decode.setOnClickListener { openDecodeActivity() }
    }
    private fun getText(): String {
        val editTextMIPS: EditText = findViewById(R.id.machineCode)
        return editTextMIPS.text.toString()
    }
    private fun is32Bits():Boolean{
        return if(machineCode.length < 32){
            Toast.makeText(this, R.string.less_than_32_bits, Toast.LENGTH_SHORT).show()
            false
        } else if(machineCode.length > 32){
            Toast.makeText(this, R.string.more_than_32_bits, Toast.LENGTH_SHORT).show()
            false
        } else true
    }

    private fun isBinary():Boolean{
        for ( i in 0..31){
            val currentChar:String = machineCode.substring(i,i+1)
            val number = currentChar.toInt()
            if(!(number==0 || number==1)){
                Toast.makeText(this, R.string.not_binary, Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun isValidMachineCode():Boolean{
        return is32Bits() && isBinary()
    }

    private fun openDecodeActivity(){
        machineCode = getText()
        if(isValidMachineCode()) {
            val intent = Intent()
            intent.setClass(this, display_assembly::class.java)
            intent.putExtra(display_assembly.MACHINE_CODE, machineCode)
            startActivity(intent)
        }
    }
}