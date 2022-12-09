package com.example.passwordmanager

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class AddPassFragment : DialogFragment() {
    internal lateinit var listener: AddTableListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var nameText : EditText
        var passText : EditText


        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            var view : View = inflater.inflate(R.layout.add_dialog,null)

            builder.setView(view)

                .setPositiveButton(R.string.okButton, null)
                .setNegativeButton(R.string.cancelButton
                ) { dialog, id ->
                    getDialog()?.cancel()
                }

            val diaL = builder.create()

            if (view.getParent() != null) {
                (view.getParent() as ViewGroup).removeView(view)
            }
            view = inflater.inflate(R.layout.add_dialog,null)
            diaL.setView(view)
            diaL.show()

            diaL.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener { v ->

                nameText = view.findViewById(R.id.passName) as EditText
                passText = view.findViewById(R.id.passwordValue) as EditText

                var nameVar = nameText.text
                var passVar = passText.text

                when{

                    nameVar.toString() == "" -> {
                        nameText.setHint(R.string.emptyName)
                        nameText.setHintTextColor(Color.RED)
                    }

                    else -> {
                        listener.onDialogPositiveClick(this, nameVar.toString(), passVar.toString())
                        diaL.dismiss()
                    }
                }

            }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as AddTableListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement Listener"))
        }
    }

    interface AddTableListener{
        fun onDialogPositiveClick(dialog: DialogFragment, name: String, pass: String)
    }

}


