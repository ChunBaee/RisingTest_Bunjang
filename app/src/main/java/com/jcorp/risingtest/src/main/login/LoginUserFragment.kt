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
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jcorp.risingtest.src.main.main.MainActivity
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentLoginUserBinding
import com.jcorp.risingtest.src.main.login.adapter.LoginPermissionAdapter
import com.jcorp.risingtest.src.main.login.util.LoginActivityView
import com.jcorp.risingtest.src.main.login.adapter.LoginUserTelecomAdapter
import com.jcorp.risingtest.src.main.login.model.ChangeShopNameData
import com.jcorp.risingtest.src.main.login.model.LoginData
import com.jcorp.risingtest.src.main.login.model.LoginPermissionData
import com.jcorp.risingtest.src.main.login.model.LoginUserTelecomItem
import com.jcorp.risingtest.src.main.login.util.LoginService

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

    private lateinit var permissionSheetView : View
    private lateinit var permissionDialog : BottomSheetDialog
    private lateinit var permissionAdapter : LoginPermissionAdapter
    var isAllClicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.loginUserBtnNext.setOnClickListener(this)
        binding.loginUserBtnTelecom.setOnClickListener(this)

        setView()
        setDialogOnSingle()
        checkName()
        focusListeners()
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

        telecomAdapter.setTelecomItem(setTelecomDialogItem())

        val telecomRv = telecomSheetView.findViewById<RecyclerView>(R.id.login_user_telecom_rv)
        telecomRv.hasFixedSize()
        telecomRv.adapter = telecomAdapter

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

        permissionSheetView = layoutInflater.inflate(R.layout.dialog_user_login_permission, null)
        permissionDialog = BottomSheetDialog(requireActivity())
        permissionDialog.setContentView(permissionSheetView)
        permissionAdapter = LoginPermissionAdapter()
        permissionAdapter.setList(setPermissionDialogItem())

        val permissionRv = permissionSheetView.findViewById<RecyclerView>(R.id.dialog_permission_list)
        permissionRv.hasFixedSize()
        permissionRv.adapter = permissionAdapter

        permissionAdapter.clickListener(object : LoginPermissionAdapter.ClickListener {
            override fun onClick(view: View, position: Int) {
                permissionAdapter.permissionList[position].isClicked = !permissionAdapter.permissionList[position].isClicked
                permissionAdapter.notifyDataSetChanged()
                if(permissionAdapter.permissionList[0].isClicked && permissionAdapter.permissionList[1].isClicked&& permissionAdapter.permissionList[2].isClicked) {
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).alpha = 1F
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).isClickable = true
                } else {
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).alpha = 0.3F
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).isClickable = false
                }
            }
        })
        permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_accept_all).setOnClickListener {
            if(isAllClicked) {
                permissionSheetView.findViewById<ImageView>(R.id.dialog_permission_accept_all_img).setImageResource(R.drawable.btn_pay_agree_unchecked)
                for(i in 0 until permissionAdapter.permissionList.size) {
                    permissionAdapter.permissionList[i].isClicked = false
                }
                permissionAdapter.notifyDataSetChanged()
                if(permissionAdapter.permissionList[0].isClicked && permissionAdapter.permissionList[1].isClicked&& permissionAdapter.permissionList[2].isClicked) {
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).alpha = 1F
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).isClickable = true
                } else {
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).alpha = 0.3F
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).isClickable = false
                }
                isAllClicked = false
            }
            else {
                permissionSheetView.findViewById<ImageView>(R.id.dialog_permission_accept_all_img).setImageResource(R.drawable.btn_pay_agree_checked)
                for(i in 0 until permissionAdapter.permissionList.size) {
                    permissionAdapter.permissionList[i].isClicked = true
                }
                permissionAdapter.notifyDataSetChanged()
                if(permissionAdapter.permissionList[0].isClicked && permissionAdapter.permissionList[1].isClicked&& permissionAdapter.permissionList[2].isClicked) {
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).alpha = 1F
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).isClickable = true
                } else {
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).alpha = 0.3F
                    permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).isClickable = false
                }
                isAllClicked = true
            }
        }
        permissionSheetView.findViewById<ConstraintLayout>(R.id.dialog_permission_next).setOnClickListener {
            permissionDialog.dismiss()
            LoginService(this).trySignIn(
                binding.loginUserEdtName.text.toString(),
                binding.loginUserEdtUserEmail.text.toString(),
                binding.loginUserEdtUserPassword.text.toString(),
                binding.loginUserEdtPhoneNumber.text.toString()
            )

        }
    }

    private fun setTelecomDialogItem() : MutableList<LoginUserTelecomItem> {
        val list = mutableListOf<LoginUserTelecomItem>()
        list.add(LoginUserTelecomItem("SKT", R.drawable.icon_radio_unclicked, false))
        list.add(LoginUserTelecomItem("KT", R.drawable.icon_radio_unclicked, false))
        list.add(LoginUserTelecomItem("LG U+", R.drawable.icon_radio_unclicked, false))
        list.add(LoginUserTelecomItem("SKT 알뜰폰", R.drawable.icon_radio_unclicked, false))
        list.add(LoginUserTelecomItem("KT 알뜰폰", R.drawable.icon_radio_unclicked, false))
        list.add(LoginUserTelecomItem("LG U+ 알뜰폰", R.drawable.icon_radio_unclicked, false))

        return list
    }

    private fun setPermissionDialogItem() : MutableList<LoginPermissionData> {
        val list = mutableListOf<LoginPermissionData>()
        list.add(LoginPermissionData("번개장터 이용약관 (필수)", false))
        list.add(LoginPermissionData("개인정보 수집 이용 동의 (필수)", false))
        list.add(LoginPermissionData("휴대폰 본인확인서비스 (필수)", false))
        list.add(LoginPermissionData("개인정보 수집 이용 동의 (선택)", false))
        list.add(LoginPermissionData("위치정보 이용약관 동의 (선택)", false))
        list.add(LoginPermissionData("마케팅 수신 동의 (선택)", false))
        list.add(LoginPermissionData("개인정보 광고활용 동의 (선택)", false))

        return list
    }

    private fun focusListeners() {
        binding.loginUserEdtName.setOnFocusChangeListener { view, b ->
            if(b) {
                binding.loginUserNameDivider.setBackgroundColor(requireActivity().resources.getColor(R.color.black))
            } else {
                binding.loginUserNameDivider.setBackgroundColor(requireActivity().resources.getColor(R.color.login_user_edittext_unfocused_tint))
            }
        }
        binding.loginUserEdtUserEmail.setOnFocusChangeListener { view, b ->
            if(b) {
                binding.loginUserEmailDivider.setBackgroundColor(requireActivity().resources.getColor(R.color.black))
            } else {
                binding.loginUserEmailDivider.setBackgroundColor(requireActivity().resources.getColor(R.color.login_user_edittext_unfocused_tint))
            }
        }
        binding.loginUserEdtUserPassword.setOnFocusChangeListener { view, b ->
            if(b) {
                binding.loginUserPasswordDivider.setBackgroundColor(requireActivity().resources.getColor(R.color.black))
            } else {
                binding.loginUserPasswordDivider.setBackgroundColor(requireActivity().resources.getColor(R.color.login_user_edittext_unfocused_tint))
            }
        }
        binding.loginUserEdtPhoneNumber.setOnFocusChangeListener { view, b ->
            if(b) {
                binding.loginUserPhoneDivider.setBackgroundColor(requireActivity().resources.getColor(R.color.black))
            } else {
                binding.loginUserPhoneDivider.setBackgroundColor(requireActivity().resources.getColor(R.color.login_user_edittext_unfocused_tint))
            }
        }
        binding.loginUserEdtShopName.setOnFocusChangeListener { view, b ->
            if(b) {
                binding.loginUserShopNameDivider.setBackgroundColor(requireActivity().resources.getColor(R.color.black))
            } else {
                binding.loginUserShopNameDivider.setBackgroundColor(requireActivity().resources.getColor(R.color.login_user_edittext_unfocused_tint))
            }
        }
    }

    private fun checkName() {
        binding.loginUserEdtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
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

    private fun checkEmail() {
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
                if(isShopName && isPhone) {
                    LoginService(this).tryChangeShopName(
                        binding.loginUserEdtShopName.text.toString()
                    )
                }
                if(isPhone && !isShopName) {
                    permissionDialog.show()
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
                    checkEmail()
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