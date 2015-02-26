package complex

import net.yested.div
import net.yested.bootstrap.row
import net.yested.bootstrap.Medium
import net.yested.bootstrap.pageHeader
import net.yested.toFixed
import net.yested.Div
import kotlin.browser.document
import kotlin.js.dom.html.window
import net.yested.HTMLComponent
import net.yested.Span
import net.yested.with
import net.yested.compareByValue
import net.yested.bootstrap.smartgrid.SmartGrid
import net.yested.bootstrap.smartgrid.GridColumn
import net.yested.bootstrap.InputField
import kotlin.js.dom.html.HTMLElement
import jquery.jq
import net.yested.utils.on
import net.yested.removeAllContent
import net.yested.repeatWithDelayUntil
import net.yested.utils.isIncludedInDOM
import net.yested.utils.keypress
import net.yested.TextInput
import net.yested.bootstrap.smartgrid.CellEditorFactory
import net.yested.bootstrap.BtsButton
import net.yested.Component
import net.yested.bootstrap.ButtonSize
import net.yested.bootstrap.Small
import net.yested.bootstrap.ExtraSmall

data class MarketData(val ticker:String, var price:Double, val move:Double,
                      val min:Double, val max:Double, val avg:Double, val quantity:Double,
                      val fair:Double, val ask:Double,
                      val col1:Double, val col2:Double, val col3:Double, val col4:Double, val col5:Double,
                      val col6:Double, val col7:Double, val col8:Double, val col9:Double, val col10:Double,
                      val col11:Double, val col12:Double, val col13:Double, val col14:Double, val col15:Double)

fun HTMLComponent.coloredNumber(value:Double) {
    +(Span() with {
        "style".."color: ${if (value>0) "green" else "red"}"
        +"${value.toFixed(2)}"
    })
}

native fun HTMLElement.focus() : Unit = noImpl

public class DoubleEditor<TYPE>(
        val getValue:(TYPE)->Double?,
        val saveValue:(TYPE, Double)->Unit) : CellEditorFactory<TYPE> {

    override fun createEditor(width: String, item: TYPE, closeHandler: () -> Unit): HTMLElement {

        val inputField = TextInput() with {
            element.setAttribute("style", "max-width: ${width}")
        }

        repeatWithDelayUntil(
                check = { isIncludedInDOM(inputField.element) },
                millisecondInterval = 100,
                run = { inputField.element.focus() })

        jq(inputField.element).on("focusout", {
            closeHandler()
        })

        jq(inputField.element).keypress( { event ->
            val d = safeParseDouble(inputField.data)
            if (event.which == 13 && d != null) {
                saveValue(item, d)
                closeHandler()
            }
        })

        inputField.data = "${getValue(item)?.toFixed(2)}"

        return inputField.element
    }

}

fun createGrid() =
    SmartGrid<MarketData, String>(
            getKey = {it.ticker},
            maxHeight = "300px",
            defaultSortColumn = "ticker",
            defaultSortOrderAsc = true,
            columns = array(
                GridColumn(id = "ticker", width = "80px", label = "Ticker", render = { +it.ticker }, sortFunction = compareByValue<MarketData, String> { it.ticker }),
                GridColumn(id = "price",  width = "80px", label = "Price", render = { +"${it.price.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.price },
                        editor = DoubleEditor(getValue = {it.price}, saveValue = {item, value->item.price = value} ) ),
                GridColumn(id = "move", width = "80px", label = "Move", render = { coloredNumber(it.move) }, sortFunction = compareByValue<MarketData, Double> { it.move }),
                GridColumn(id = "min", width = "80px", label = "Min", render = { +"${it.min.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.min }),
                GridColumn(id = "max", width = "80px", label = "Max", render = { +"${it.max.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.max }),
                GridColumn(id = "avg", width = "80px", label = "Avg", render = { +"${it.avg.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.avg }),
                GridColumn(id = "fair", width = "80px", label = "Fair", render = { +"${it.fair.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.fair }),
                GridColumn(id = "ask", width = "80px", label = "Ask", render = { +"${it.ask.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.ask }),
                GridColumn(id = "quantity", width = "80px", label = "Quantity", render = { +"${it.quantity.toFixed(0)}" }, sortFunction = compareByValue<MarketData, Double> { it.quantity }),
                GridColumn(id = "col1", width = "80px", label = "Col 1", render = { +"${it.col1.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col1 }),
                GridColumn(id = "col2", width = "80px", label = "Col 2", render = { +"${it.col2.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col2 }),
                GridColumn(id = "col3", width = "80px", label = "Col 3", render = { +"${it.col3.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col3 }),
                GridColumn(id = "col4", width = "80px", label = "Col 4", render = { +"${it.col4.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col4 }),
                GridColumn(id = "col5", width = "80px", label = "Col 5", render = { +"${it.col5.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col5 }),
                GridColumn(id = "col6", width = "80px", label = "Col 6", render = { +"${it.col6.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col6 }),
                GridColumn(id = "col7", width = "80px", label = "Col 7", render = { +"${it.col7.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col7 }),
                GridColumn(id = "col8", width = "80px", label = "Col 8", render = { +"${it.col8.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col8 }),
                GridColumn(id = "col9", width = "80px", label = "Col 9", render = { +"${it.col9.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col9 }),
                GridColumn(id = "col10", width = "80px", label = "Col 10", render = { +"${it.col10.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col10 }),
                GridColumn(id = "col11", width = "80px", label = "Col 11", render = { +"${it.col11.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col11 }),
                GridColumn(id = "col12", width = "80px", label = "Col 12", render = { +"${it.col12.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col12 }),
                GridColumn(id = "col13", width = "80px", label = "Col 13", render = { +"${it.col13.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col13 }),
                GridColumn(id = "col14", width = "80px", label = "Col 14", render = { +"${it.col14.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col14 }),
                GridColumn(id = "col15", width = "80px", label = "Col 15", render = { +"${it.col15.toFixed(2)}" }, sortFunction = compareByValue<MarketData, Double> { it.col15 })
            ))

fun generateData() =
        (0..100)
                .map {
                    MarketData(
                            ticker= "A${it}",
                            price = Math.random()*100,
                            min = Math.random()*100,
                            max = Math.random()*100,
                            avg = Math.random()*100,
                            fair = Math.random()*100,
                            ask = Math.random()*100,
                            quantity = Math.random()*1000,
                            move = Math.random()*10-5,
                            col1 = Math.random()*100,
                            col2 = Math.random()*100,
                            col3 = Math.random()*100,
                            col4 = Math.random()*100,
                            col5 = Math.random()*100,
                            col6 = Math.random()*100,
                            col7 = Math.random()*100,
                            col8 = Math.random()*100,
                            col9 = Math.random()*100,
                            col10 = Math.random()*100,
                            col11 = Math.random()*100,
                            col12 = Math.random()*100,
                            col13 = Math.random()*100,
                            col14 = Math.random()*100,
                            col15 = Math.random()*100) }
                .toArrayList()

/**
 * Created by jean on 7.2.2015.
 */
class CustomizableGridSection: Component {

    override val element: HTMLElement
        get() = content.element

    var timerId : Number? = null
    val data = generateData()
    val grid = createGrid()
    val tickingToggleButton = BtsButton(label = { +"Toggle ticking"}, size = ButtonSize.SMALL, onclick = { toogleTicking() })

    val content = div {
        row {
            col(Medium(12)) {
                pageHeader { h3 { +"Smart Grid" } }
            }
        }
        row {
            col(Small(6)) {
                +"Smart Grid is a fully customizable grid with a lot of fancy features."
                br()
                br()
                emph { +"Features:" }
                ul {
                    li { +"Mobile (small screens) support." }
                    li { +"Support vertical and horizontal scrolling." }
                    li { +"Supports fast, high performing updates." }
                    li { +"Supports fully customizable inline cell editors." }
                    li { +"Cell can contains any content, grid supports fully customizable cell renderers." }
                    li { +"Sorting (optional per cell) is enabled via provided sorting functions." }
                    li { +"Columns can be re-sorted via Drag&amp;Drop." }
                    li { +"Columns visibility is set in a dedicated Configuration Dialog." }

                }
            }
            col(Small(6)) {
                emph { +"Please note!" }
                ul {
                    li { +"Grid does not supports Virtual Renderring (yet), therefore it only supports up-to a 1000 rows." }
                    li { +"When update is delivered into a grid, it disables sorting due to performance reasons." }
                }
                br()
            }
        }
        row {
            col(Medium(12)) {
                h4 { +"Demo" }
                a(href = "https://github.com/jean79/yested/blob/master/src/main/docsite/complex/smartgrid.kt") { +"Source code is deployed on GitHub" }
                br()
                +tickingToggleButton
                br()
                +" Click the button to start ticking of Move column."
                br()
                +" Click any cell in a Price column to edit it."
                +grid
            }
        }
    };

    {
        grid.list = data
    }

    fun updateRandomValues() {
        (0..(data.size()/6)).forEach {
            val item = data.get((Math.random()*data.size()).toInt())
            val modified = item.copy(move = Math.random()*10-5)
            grid.updateItem(modified, listOf("move"))
        }
    }

    fun toogleTicking() {
        if (timerId == null) {
            startTicking()
            tickingToggleButton.active = true
        } else {
            stopTicking()
            tickingToggleButton.active = false
        }
    }

    fun startTicking() {
        timerId = window.setInterval( { updateRandomValues() }, 1000)
    }

    fun stopTicking() {
        if (timerId != null) {
            window.clearInterval(timerId!!)
            timerId = null
        }
    }

}
