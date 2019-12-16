package android.vn.lcd.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adomino.ddsdb.R

abstract class BaseFragment : Fragment() {

  abstract fun getLayoutResource(): Int

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    getBaseActivity().setActionBarTitle(getString(R.string.app_name))
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(getLayoutResource(), container, false)
  }

  fun getBaseActivity(): BaseActivity {
    return activity as BaseActivity
  }
}