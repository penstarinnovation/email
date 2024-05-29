package com.fsck.k9.hanvon.ui.home

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.util.setOnDebouncedItemClick
import com.fsck.k9.DI.get
import com.fsck.k9.hanvon.R
import com.fsck.k9.hanvon.databinding.RvItemAccountFolderBinding
import com.fsck.k9.hanvon.databinding.RvItemFolderBinding
import com.fsck.k9.mailstore.DisplayFolder
import com.fsck.k9.ui.folders.FolderNameFormatter

class AccountFoldersAdapter(val vm:AccountAndFoldersViewModel) : BaseQuickAdapter<AccountFolder,AccountFoldersAdapter.VH>() {

    private val folderNameFormatter: FolderNameFormatter = get()

    // 自定义ViewHolder类
    class VH(
        parent: ViewGroup,
        val binding: RvItemAccountFolderBinding = RvItemAccountFolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: AccountFolder?) {
        if (item!= null){
            holder.binding.apply {
                //展开折叠状态
                val expandStatus : Boolean = vm.expandCloseMap.getOrDefault(item.account.uuid,false)
                ivArrow.setImageResource(if (expandStatus) R.drawable.icon_arrpw_down else R.drawable.icon_right)
                rvFolders.visibility = if (expandStatus) View.VISIBLE else View.GONE
                tvAccount.text = item.account.displayName
                var folderAdapter = rvFolders.adapter
                if (folderAdapter==null){
                    folderAdapter = FolderAdapter(vm, folderNameFormatter).let {
                        setOnDebouncedItemClick(){adapter, view, position ->
                            var item1 = adapter.getItem(position) as DisplayFolder
                            vm.selectedFolderLiveData.value = item1;
                            //刷新列表
                            this@AccountFoldersAdapter.notifyDataSetChanged()
                        }
                    }
                    rvFolders.adapter =folderAdapter
                    (folderAdapter as FolderAdapter).submitList(item.folders)
                }else{
                    folderAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }
}

class FolderAdapter(val vm:AccountAndFoldersViewModel,private var folderNameFormatter:FolderNameFormatter) :BaseQuickAdapter<DisplayFolder,FolderAdapter.VH>(){

    class VH(
        parent: ViewGroup,
        val binding: RvItemFolderBinding = RvItemFolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: DisplayFolder?) {
        if (item!= null){
            holder.binding.apply {
                tvFolderName.text =folderNameFormatter.displayName(item.folder)
                tvEmailCount.text = if (item.allMessageCount>0)item.allMessageCount.toString() else ""
                //选中状态
                if (vm.selectedFolderLiveData.value==item){
                    viewSelected.visibility = View.VISIBLE
                    tvFolderName.typeface = Typeface.DEFAULT_BOLD
                }else{
                    viewSelected.visibility = View.INVISIBLE
                    tvFolderName.typeface = Typeface.DEFAULT
                }
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }
}
