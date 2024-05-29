package com.fsck.k9.hanvon.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.k9mail.ui.utils.linearlayoutmanager.LinearLayoutManager
import com.fsck.k9.hanvon.databinding.FragmentEmailHomeBinding
import com.fsck.k9.ui.folders.FolderNameFormatter
import com.hvmail.common.base.BaseDataBindFragment
import org.koin.android.ext.android.inject

class EmailHomeFragment : BaseDataBindFragment<FragmentEmailHomeBinding>() {

    private val folderNameFormatter: FolderNameFormatter by inject()
    private val viewModel: EmailHomeViewModel by viewModels()
    private val accountAndFoldersViewModel: AccountAndFoldersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun bindView(){
        mBinding.title.navigationIcon.setOnClickListener { findNavController().navigateUp() };
        mBinding.ivRefresh.setOnClickListener(View.OnClickListener {
            //TODO 刷新

        })
        mBinding.llAddressBook.setOnClickListener(){
            //TODO 通讯录
        }
        mBinding.llEmailSetting.setOnClickListener(){
            //TODO 邮箱设置
        }
        mBinding.llAttachmentManage.setOnClickListener(){
            //TODO 附件管理
        }
        //账号和文件夹
        mBinding.rvAccount.layoutManager = LinearLayoutManager(context);
        mBinding.rvAccount.adapter = AccountFoldersAdapter(accountAndFoldersViewModel)
        accountAndFoldersViewModel.displayAccountsLiveData.observe(viewLifecycleOwner){ accounts->
            (mBinding.rvAccount.adapter as AccountFoldersAdapter).submitList(accounts)
        }
    }
}
