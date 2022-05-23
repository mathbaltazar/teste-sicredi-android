package br.com.testedevandroid.ui.checkin

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.testedevandroid.R
import br.com.testedevandroid.viewmodels.EventsViewModel
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CheckInFragment : Fragment(), View.OnClickListener {

    private val viewModel: EventsViewModel by activityViewModels()

    private lateinit var progressIndicator: LinearProgressIndicator
    private lateinit var textlayoutNomeCheckin: TextInputLayout
    private lateinit var textinputNomeCheckin: TextInputEditText
    private lateinit var textlayoutEmailCheckin: TextInputLayout
    private lateinit var textinputEmailCheckin: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressIndicator = view.findViewById(R.id.checkin_progress_indicator)
        textlayoutNomeCheckin = view.findViewById(R.id.textlayout_nome_checkin)
        textinputNomeCheckin = view.findViewById(R.id.textinput_nome_checkin)
        textlayoutEmailCheckin = view.findViewById(R.id.textlayout_email_checkin)
        textinputEmailCheckin = view.findViewById(R.id.textinput_email_checkin)

        view.findViewById<Button>(R.id.button_send_checkin).setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (v?.id == R.id.button_send_checkin) {
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            val email = textinputEmailCheckin.text.toString()
            val name = textinputNomeCheckin.text.toString()

            textlayoutNomeCheckin.error = null
            textlayoutEmailCheckin.error = null

            if (TextUtils.isEmpty(name)) {
                textlayoutNomeCheckin.error = "Preencha o nome"
                return
            }

            if (TextUtils.isEmpty(email)) {
                textlayoutEmailCheckin.error = "Preencha o email"
                return
            }

            lifecycleScope.launch {
                progressIndicator.visibility = View.VISIBLE
                delay(2000) // Apenas pra visualizar o progress

                if (viewModel.fazerCheckInEmEvento(
                    eventId = viewModel.eventoDetalhado.value!!.id,
                    nome = name,
                    email = email
                )) {
                    confirmationCheckIn()
                } else {
                    Toast.makeText(requireContext(), "Falha ao fazer o checkin", Toast.LENGTH_SHORT)
                        .show()
                }
                progressIndicator.visibility = View.GONE
            }
        }
    }

    private fun confirmationCheckIn() {
        AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle("Concluído!")
            .setMessage("Checkin realizado com sucesso! =)")
            .setPositiveButton("OK") { _, _ ->
                findNavController().navigate(R.id.action_checkInFragment_to_eventsFragment)
            }
            .show()
    }
}