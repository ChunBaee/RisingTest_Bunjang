package com.jcorp.risingtest.src.main.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jcorp.risingtest.src.main.main.MainActivity
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentLoginUserBinding
import com.jcorp.risingtest.src.main.login.adapter.LoginActivityView
import com.jcorp.risingtest.src.main.login.adapter.LoginUserTelecomAdapter
import com.jcorp.risingtest.src.main.login.model.ChangeShopNameData
import com.jcorp.risingtest.src.main.login.model.LoginData
import com.jcorp.risingtest.src.main.login.model.LoginUserTelecomItem
import org.w3c.dom.Text

class LoginUserFragment : BaseFragment<FragmentLoginUserBinding>(
    FragmentLoginUserBinding::bind,
    R.layout.fragment_login_user
),
    View.OnClickListener, LoginActivityView {

    private var isName = false
    private var isId = false
    private var isPassword = false
    private var isPhone = false
    private var isShopName = false

    private lateinit var imm : InputMethodManager

    private lateinit var telecomSheetView : View
    private lateinit var telecomDialog : BottomSheetDialog
    private lateinit var telecomAdapter : LoginUserTelecomAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.loginUserBtnNext.setOnClickListener(this)
        binding.loginUserBtnTelecom.setOnClickListener(this)

        setView()
        setDialogOnSingle()
        checkName()
    }

    private fun setView() {
        imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        binding.loginUserBtnNext.alpha = 0.3F
        binding.loginUserBtnNext.isClickable = false
    }

    private fun setDialogOnSingle() {
        telecomSheetView = layoutInflater.inflate(R.layout.dialog_user_login_telecom, null)
        telecomDialog = BottomSheetDialog(requireActivity())
        telecomDialog.setContentView(telecomSheetView)
        telecomAdapter = LoginUserTelecomAdapter(requireActivity())

        telecomAdapter.setTelecomItem(setDialogItem())

        val rv = telecomSheetView.findViewById<RecyclerView>(R.id.login_user_telecom_rv)
        rv.hasFixedSize()
        rv.adapter = telecomAdapter

        telecomAdapter.clickListener(object : LoginUserTelecomAdapter.ClickListener {
            override fun onClick(view: View, position: Int) {
                binding.loginUserTxtTelecom.text = telecomAdapter.mItemList[position].title
                binding.loginUserTxtTelecom.setTextColor(resources.getColor(R.color.black))
                for(i in 0 until telecomAdapter.mItemList.size) {
                    telecomAdapter.mItemList[i].click = false
                }
                telecomAdapter.mItemList[position].click = true
                telecomAdapter.notifyDataSetChanged()

                telecomDialog.dismiss()
                checkPhoneNumber()
            }

        })
    }

    private fun setDialogItem() : MutableList<LoginUserTelecomItem> {
        val list = mutableListOf<LoginUserTelecomItem>()
        list.add(LoginUserTelecomItem("SKT", R.drawable.icon_radio_unclicked, false))
        list.add(LoginUserTelecomItem("KT", R.drawable.icon_radio_unclicked, false))
        list.add(LoginUserTelecomItem("LG U+", R.drawable.icon_radio_unclicked, false))
        list.add(LoginUserTelecomItem("SKT 알뜰폰", R.drawable.icon_radio_unclicked, false))
        list.add(LoginUserTelecomItem("KT 알뜰폰", R.drawable.icon_radio_unclicked, false))
        list.add(LoginUserTelecomItem("LG U+ 알뜰폰", R.drawable.icon_radio_unclicked, false))

        return list
    }

    private fun checkName() {
        binding.loginUserEdtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.loginUserEdtName.background.setTint(Color.BLACK)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.isNotEmpty()) {
                    binding.loginUserBtnNext.alpha = 1F
                    binding.loginUserBtnNext.isClickable = true

                    isName = true
                }
            }

        })
    }

    private fun checkId() {
        binding.loginUserEdtUserEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.isNotEmpty()) {
                    isId = true
                }
            }

        })
    }
    private fun checkPassword() {
        binding.loginUserEdtUserPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.isNotEmpty()) {
                    isPassword = true
                }
            }

        })
    }

    private fun checkPhone() {
        binding.loginUserBtnTelecom.visibility = View.VISIBLE
        binding.loginUserTxtChangeable1.text = "통신사를"
        binding.loginUserTxtChangeable2.text = "선택해주세요"
        imm.hideSoftInputFromWindow(binding.loginUserBtnTelecom.windowToken, 0)

        telecomDialog.show()
    }

    private fun checkPhoneNumber() {
        binding.loginUserLayoutPhoneNumber.visibility = View.VISIBLE
        binding.loginUserEdtPhoneNumber.requestFocus()
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.loginUserEdtPhoneNumber.addTextChangedListener ( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.length > 10) {
                    binding.loginUserBtnNext.alpha = 1F
                    binding.loginUserBtnNext.isClickable = true
                    imm.hideSoftInputFromWindow(binding.loginUserBtnTelecom.windowToken, 0)
                    isPhone = true
                } else {
                    binding.loginUserBtnNext.isClickable = false
                    isPhone = false
                }
            }

        })
    }

    private fun checkShopName() {
        binding.loginUserLayoutEmail.visibility = View.GONE
        binding.loginUserLayoutPassword.visibility = View.GONE
        binding.loginUserLayoutPhoneNumber.visibility = View.GONE
        binding.loginUserBtnTelecom.visibility = View.GONE
        binding.loginUserLayoutName.visibility = View.GONE
        binding.loginUserLayoutShopName.visibility = View.VISIBLE

        binding.loginUserTxtChangeable1.text = "마지막 단계입니다!"
        binding.loginUserTxtChangeable2.text = "상점명을 입력해주세요"

        binding.loginUserEdtShopName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.isNotEmpty()) {
                    isShopName = true
                }
            }

        })
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.login_user_btn_next -> {
                if(isShopName) {
                    LoginService(this).tryChangeShopName(
                        binding.loginUserEdtShopName.text.toString()
                    )
                }
                if(isPhone && !isShopName) {
                    //데이터 올려주기
                    LoginService(this).trySignIn(
                        binding.loginUserEdtName.text.toString(),
                        binding.loginUserEdtUserEmail.text.toString(),
                        binding.loginUserEdtUserPassword.text.toString(),
                        binding.loginUserEdtPhoneNumber.text.toString()
                    )
                }
                if(isPassword && !isPhone) {
                    checkPhone()
                }
                if(isId && !isPassword) {
                    binding.loginUserLayoutPassword.visibility = View.VISIBLE
                    binding.loginUserEdtUserPassword.requestFocus()
                    binding.loginUserTxtChangeable1.text = "비밀번호를"
                    checkPassword()
                }
                if (isName && !isId) {
                    binding.loginUserLayoutEmail.visibility = View.VISIBLE
                    binding.loginUserEdtUserEmail.requestFocus()
                    binding.loginUserTxtChangeable1.text = "아이디를"
                    checkId()
                }
            }

            R.id.login_user_btn_telecom -> checkPhone()
        }
    }

    override fun onGetDataSuccess(response: LoginData) {
        Log.d("----", "onGetDataSuccess: ${response.result.jwt}")
        ApplicationClass.mLoginSharedPreferences.edit().putString("X-ACCESS-TOKEN", response.result.jwt).apply()

        checkShopName()
    }

    override fun onChangeShopNameSuccess(response: ChangeShopNameData) {
        Log.d("----", "onChangeShopNameSuccess: ${response.code}")
        if(response.code == 1000) {

        }
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}