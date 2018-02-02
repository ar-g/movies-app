package arg.movies.pagination

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import java.util.ArrayList
import java.util.Collections

import io.reactivex.Observable

class RvPager2(
        private val recyclerView: RecyclerView,
        private val isDirectionDown: Boolean = true,
        private val downThreshold: Int = DOWN_THRESHOLD,
        private val itemsPerPage: Int = ITEMS_PER_PAGE
) {

    private fun getItemCount(): Int = recyclerView.adapter.itemCount

    private fun isNextPage(): Boolean {
        val position = getVisibleItem()

        return if (isDirectionDown) {
            val itemCount = getItemCount()
            val updatePosition = itemCount - downThreshold
            position >= updatePosition
        } else {
            position <= UP_THRESHOLD
        }
    }

    private fun isItemCountNotEmpty(): Boolean = recyclerView.layoutManager.itemCount != 0

    private fun getVisibleItem(): Int {
        val recyclerViewLMClass = recyclerView.layoutManager.javaClass
        if (recyclerViewLMClass == LinearLayoutManager::class.java || LinearLayoutManager::class.java.isAssignableFrom(recyclerViewLMClass)) {

            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

            return if (!isDirectionDown) {
                linearLayoutManager.findFirstVisibleItemPosition()
            } else {
                linearLayoutManager.findLastVisibleItemPosition()
            }

        } else if (recyclerViewLMClass == StaggeredGridLayoutManager::class.java || StaggeredGridLayoutManager::class.java.isAssignableFrom(recyclerViewLMClass)) {

            val staggeredGridLayoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager

            val into: IntArray
            if (!isDirectionDown) {
                into = staggeredGridLayoutManager.findFirstVisibleItemPositions(null)

            } else {
                into = staggeredGridLayoutManager.findLastVisibleItemPositions(null)
            }

            val intoList = ArrayList<Int>()
            for (i in into) {
                intoList.add(i)
            }
            return Collections.max(intoList)
        }
        throw IllegalStateException("Unknown LayoutManager class: " + recyclerViewLMClass.toString())
    }


    fun pageEvents(): Observable<Int> {
        return scrollEvents()
                .filter { isItemCountNotEmpty() && isNextPage() }
                .map { getItemCount() }
                .distinctUntilChanged()
                .map { itemsCount -> itemsCount / itemsPerPage + 1 }
    }

    private fun scrollEvents(): Observable<RecyclerViewScrollEvent> {
        return RxRecyclerView.scrollEvents(recyclerView)
    }

    companion object {
        private val ITEMS_PER_PAGE = 20
        private val DOWN_THRESHOLD = 14
        private val UP_THRESHOLD = 2
    }
}



