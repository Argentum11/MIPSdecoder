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
        resultTextView.text = decodeMachineCode()
    }

    private fun decodeOPCode(opcode:String): String{
        if(opcode == "000000"){
            return getString(R.string.r_type)
        }
        else return getString(R.string.unknown_format)
    }

    private fun decodeRegister(registerBinary:String):String{
        when (val registerNum = registerBinary.toInt(2)) {
            0 -> {
                return "zero"
            }
            31->{
                return "ra"
            }
            in 8..15 -> { // temporary registers 1
                return "t${registerNum-8}"
            }
            in 24..25 -> { // temporary registers 2
                return "t${registerNum-16}"
            }
            in 16..23 -> { // saved variables
                return "s${registerNum-16}"
            }
            else -> {
                return "invalid reg"
                //return registerNum.toString()
            }
        }
    }

    private fun decodeFunctionCode(functionCode:String):String{
        return when (functionCode) {
            "100000" -> getString(R.string.add)
            "100010" -> getString(R.string.subtract)
            "100100" -> getString(R.string.and)
            "100101" -> getString(R.string.or)
            "100111" -> getString(R.string.nor)
            "000000" -> getString(R.string.shift_left_logical)
            "000010" -> getString(R.string.shift_right_logical)
            "101010" -> getString(R.string.set_if_less_than)
            "001000" -> getString(R.string.jump_register)
            //else -> functionCode
            else -> "invalid function code"
        }
    }

    private fun decodeMachineCode():String{
        opcode = decodeOPCode(machineCode.substring(0, 6))
        if (opcode == getString(R.string.r_type)){
            val rs = decodeRegister(machineCode.substring(6, 11))
            val rt = decodeRegister(machineCode.substring(11, 16))
            val rd = decodeRegister(machineCode.substring(16, 21))
            val shiftAmount = machineCode.substring(21, 26).toInt(radix = 2)
            return when (val functionCode = decodeFunctionCode(machineCode.substring(26, 32))) {
                getString(R.string.jump_register) -> {
                    "jr \$$rs"
                }
                getString(R.string.shift_left_logical), getString(R.string.shift_right_logical) -> {
                    "$functionCode \$$rd, \$$rt, $shiftAmount"
                }
                else -> {
                    "$functionCode \$$rd, \$$rs, \$$rt"
                }
            }
        }
        else{
            return "invalid machine code"
        }

    }

}