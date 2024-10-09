import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yoga_app.dao.YogaClassDao
import com.example.yoga_app.viewmodel.YogaClassViewModel

class YogaClassViewModelFactory(private val yogaClassDao: YogaClassDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YogaClassViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return YogaClassViewModel(yogaClassDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}