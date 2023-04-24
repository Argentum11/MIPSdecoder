package argentum11.mipsdecoder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var machineCode:String
    private lateinit var editTextMIPS:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextMIPS = findViewById(R.id.machineCodeInput)

        val btn_decode:Button = findViewById(R.id.btn_decode)
        btn_decode.setOnClickListener { openDecodeActivity() }

        val btn_clear:Button = findViewById(R.id.btn_clear)
        btn_clear.setOnClickListener { emptyText() }

        val btn_add:Button = findViewById(R.id.btn_add)
        btn_add.setOnClickListener { setText(getString(R.string.add_machine_code_example)) }

        val btn_sub:Button = findViewById(R.id.btn_sub)
        btn_sub.setOnClickListener { setText(getString(R.string.sub_machine_code_example)) }

        val btn_lw:Button = findViewById(R.id.btn_lw)
        btn_lw.setOnClickListener { setText(getString(R.string.lw_machine_code_example)) }

        val btn_sw:Button = findViewById(R.id.btn_sw)
        btn_sw.setOnClickListener { setText(getString(R.string.sw_machine_code_example)) }

        val btn_and:Button = findViewById(R.id.btn_and)
        btn_and.setOnClickListener { setText(getString(R.string.and_machine_code_example)) }

        val btn_or:Button = findViewById(R.id.btn_or)
        btn_or.setOnClickListener { setText(getString(R.string.or_machine_code_example)) }

        val btn_nor:Button = findViewById(R.id.btn_nor)
        btn_nor.setOnClickListener { setText(getString(R.string.nor_machine_code_example)) }

        val btn_andi:Button = findViewById(R.id.btn_andi)
        btn_andi.setOnClickListener { setText(getString(R.string.andi_machine_code_example)) }

        val btn_ori:Button = findViewById(R.id.btn_ori)
        btn_ori.setOnClickListener { setText(getString(R.string.ori_machine_code_example)) }

        val btn_sll:Button = findViewById(R.id.btn_sll)
        btn_sll.setOnClickListener { setText(getString(R.string.sll_machine_code_example)) }

        val btn_srl:Button = findViewById(R.id.btn_srl)
        btn_srl.setOnClickListener { setText(getString(R.string.srl_machine_code_example)) }

        val btn_slt:Button = findViewById(R.id.btn_slt)
        btn_slt.setOnClickListener { setText(getString(R.string.slt_machine_code)) }

        val btn_jr:Button = findViewById(R.id.btn_jr)
        btn_jr.setOnClickListener { setText(getString(R.string.jr_machine_code_example)) }

        val btn_beq:Button = findViewById(R.id.btn_beq)
        btn_beq.setOnClickListener { setText(getString(R.string.beq_machine_code_example)) }

        val btn_bne:Button = findViewById(R.id.btn_bne)
        btn_bne.setOnClickListener { setText(getString(R.string.bne_machine_code_example)) }
    }

    private fun emptyText(){
        editTextMIPS.setText("")
    }

    private fun setText(machineCode:String){
        editTextMIPS.setText(machineCode)
    }
    private fun getText(): String {

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