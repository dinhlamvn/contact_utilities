package com.adomino.ddsdb.ui.listcontact.uimodel

import com.adomino.ddsdb.recyclerview.XModel

class EmptyResultUiModel(id: String) : XModel(id) {

  companion object {
    const val VIEW_TYPE = 1
  }

  override fun viewType(): Int {
    return VIEW_TYPE
  }

  override fun hashId(): Int {
    return hashCode()
  }
}