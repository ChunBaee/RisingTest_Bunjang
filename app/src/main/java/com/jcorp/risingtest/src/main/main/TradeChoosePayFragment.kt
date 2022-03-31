package com.jcorp.risingtest.src.main.main

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jcorp.risingtest.R
import com.jcorp.risingtest.config.BaseFragment
import com.jcorp.risingtest.databinding.FragmentChooseHowToPayBinding
import com.jcorp.risingtest.src.MyViewModel
import com.jcorp.risingtest.src.main.main.adapter.ChoosePaymentAdapter
import com.jcorp.risingtest.src.main.main.model.ChoosePaymentData

class TradeChoosePayFragment : BaseFragment<FragmentChooseHowToPayBinding>(
    FragmentChooseHowToPayBinding::bind,
    R.layout.fragment_choose_how_to_pay
),
    View.OnClickListener {

    private lateinit var paymentAdapter: ChoosePaymentAdapter
    private val viewModel by activityViewModels<MyViewModel>()
    var mPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chooseHowBtnFinish.setOnClickListener(this)

        setRv()
        observe()
    }

    private fun setRv() {
        paymentAdapter = ChoosePaymentAdapter(requireActivity())
        binding.chooseHowRv.hasFixedSize()
        binding.chooseHowRv.adapter = paymentAdapter
        paymentAdapter.setPaymentList(setChoosePayList())

        paymentAdapter.clickListener(object : ChoosePaymentAdapter.ClickListener {
            override fun onClick(view: View, position: Int) {
                for (i in 0 until paymentAdapter.paymentsList.size) {
                    paymentAdapter.paymentsList[i].isChecked = false
                }
                paymentAdapter.paymentsList[position].isChecked = true
                mPosition = position
                viewModel.curLiveChoosePayment.value = position
                paymentAdapter.notifyDataSetChanged()
            }
        })
        binding.txtNotionInfo.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun observe() {
        viewModel.curLiveChoosePayment.observe(requireActivity(), Observer {
            when (it) {
                0 -> {
                    binding.chooseHowTxtNotion.text = "신용/체크카드 안내"
                    binding.txtNotion1.text = "ㆍ결제시 BC카드 TOP포인트, 현대카드 M포인트 사용가능"
                    binding.txtNotion2.text = "ㆍ체크카드는 카드사 점검시간에 따라 이용이 불가"
                    binding.txtNotion3.text =
                        "ㆍ카드사별 무이자 할부 지원 : BC카드, NH농협카드, KB국민카드, 삼성카드, 신한카드, 하나카드, 롯데카드, 현대카드, 우리카드"
                    binding.txtNotion4.text = "ㆍ일부 상품은 무이자 할부 및 혜택 적용이 불가"
                    binding.txtNotionInfo.text = "포인트/무이자 할부"
                    binding.chooseHowBtnChooseCompany.visibility = View.VISIBLE
                }
                1 -> {
                    binding.chooseHowTxtNotion.text = "카카오페이 안내"
                    binding.txtNotion1.text =
                        "ㆍ카카오페이에 계좌 또는 카드를 등록하여 간편하게 결제할 수 있는 서비스입니다. (카드 등록 시 휴대폰과 카드 명의자가 일치해야 합니다.)"
                    binding.txtNotion2.text = ""
                    binding.txtNotion3.text = ""
                    binding.txtNotion4.text = ""
                    binding.txtNotionInfo.text = ""
                    binding.chooseHowBtnChooseCompany.visibility = View.GONE
                }

                2 -> {
                    binding.chooseHowTxtNotion.text = "토스 안내"
                    binding.txtNotion1.text =
                        "ㆍToss 는 간편송금/결제 서비스며, 번개장터에서는 등록된 계좌와 토스머니를 통해 쉽게 결제할 수 있습니다."
                    binding.txtNotion2.text = "결제 및 사용관련 문의는 Toss 고객센터 (1599-4905)를 이용바랍니다."
                    binding.txtNotion3.text = ""
                    binding.txtNotion4.text = ""
                    binding.txtNotionInfo.text = ""
                    binding.chooseHowBtnChooseCompany.visibility = View.GONE
                }

                3 -> {
                    binding.chooseHowTxtNotion.text = "차이 안내"
                    binding.txtNotion1.text = "ㆍ차이는 은행 계좌만 등록하면 간편하게 결제할 수 있는 모바일 결제 서비스입니다."
                    binding.txtNotion2.text = "ㆍ은행 점검시간인 23:30 ~ 00:30 까지는 이용 불가합니다."
                    binding.txtNotion3.text = ""
                    binding.txtNotion4.text = ""
                    binding.txtNotionInfo.text = ""
                    binding.chooseHowBtnChooseCompany.visibility = View.GONE
                }

                4 -> {
                    binding.chooseHowTxtNotion.text = "간편계좌결제 안내"
                    binding.txtNotion1.text = "ㆍ내 계좌를 미리 등록하여 간편하게 결제 할 수 있는 간편 결제 서비스 입니다."
                    binding.txtNotion2.text = "ㆍ간편계좌결제서비스는 최대 200만원까지 결제 가능 합니다."
                    binding.txtNotion3.text = ""
                    binding.txtNotion4.text = ""
                    binding.txtNotionInfo.text = ""
                    binding.chooseHowBtnChooseCompany.visibility = View.GONE
                }

                5 -> {
                    binding.chooseHowTxtNotion.text = "휴대폰결제 안내"
                    binding.txtNotion1.text = "ㆍ휴대폰 결제일 경우 결제 당월에만 결제수단 취소가 가능합니다."
                    binding.txtNotion2.text =
                        "ㆍ휴대폰 결제 한도는 1회 30만원, 월 30만원까지 가능하며, 통신사 수납/가입상태 및 결제 대행사에 따라 한도가 상이할 수 있습니다."
                    binding.txtNotion3.text = ""
                    binding.txtNotion4.text = ""
                    binding.txtNotionInfo.text = ""
                    binding.chooseHowBtnChooseCompany.visibility = View.GONE
                }

                6 -> {
                    binding.chooseHowTxtNotion.text = "편의점결제 안내"
                    binding.txtNotion1.text = "ㆍCU / GS25 / 세븐일레븐 / 이마트24 편의점에서 현금으로 결제 가능합니다."
                    binding.txtNotion2.text = "ㆍ편의점 결제 환불의 경우 결제한 편의점에서만 가능 합니다."
                    binding.txtNotion3.text = ""
                    binding.txtNotion4.text = ""
                    binding.txtNotionInfo.text = "결제방법 자세히 보기"
                    binding.chooseHowBtnChooseCompany.visibility = View.GONE
                }
            }
        })
    }

    private fun setChoosePayList(): MutableList<ChoosePaymentData> {
        var list = mutableListOf<ChoosePaymentData>()
        list.add(ChoosePaymentData("신용/체크카드", false))
        list.add(ChoosePaymentData("카카오페이", false))
        list.add(ChoosePaymentData("토스", false))
        list.add(ChoosePaymentData("차이", false))
        list.add(ChoosePaymentData("간편계좌결제", false))
        list.add(ChoosePaymentData("휴대폰결제", false))
        list.add(ChoosePaymentData("편의점결제", false))

        return list
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.choose_how_btn_finish -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@TradeChoosePayFragment).commit()
                requireActivity().supportFragmentManager.popBackStack()
                viewModel.curChoosePayment = mPosition
            }
        }
    }
}