package com.fsck.k9.hanvon.ui.home

import android.util.ArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fsck.k9.Account
import com.fsck.k9.DI.get
import com.fsck.k9.mailstore.DisplayFolder
import com.fsck.k9.mailstore.FolderRepository
import com.fsck.k9.preferences.AccountManager
import com.fsck.k9.ui.account.DisplayAccount
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class AccountAndFoldersViewModel() :ViewModel(){

    //记录展开折叠状态
    public val expandCloseMap:ArrayMap<String,Boolean> = ArrayMap();
    //记录选中
    public val selectedFolderLiveData:MutableLiveData<DisplayFolder> = MutableLiveData<DisplayFolder>()


    private val accountManager: AccountManager = get()
    private val folderRepository: FolderRepository = get()

    private val displayAccountFlow: Flow<List<AccountFolder>> = accountManager.getAccountsFlow()
        .flatMapLatest { accounts ->
            //查找该账号下所有文件夹
            val foldersFlows: List<Flow<List<DisplayFolder>>> = accounts.map { account ->
                folderRepository.getDisplayFoldersFlow(account)
            }
            //账号和文件夹组合
            combine(foldersFlows) { foldersList ->
                foldersList.mapIndexed { index, folder ->
                    //设置默认选中的folder
                    if (index==0 && folder.isNotEmpty() && selectedFolderLiveData.value==null){
                        selectedFolderLiveData.postValue(folder[0])
                    }
                    AccountFolder(
                        account = accounts[index],
                        folders=folder,
                    )
                }
            }
        }

    val displayAccountsLiveData: LiveData<List<AccountFolder>> = displayAccountFlow.asLiveData()
}

data class AccountFolder(val account:Account,var folders: List<DisplayFolder>)
