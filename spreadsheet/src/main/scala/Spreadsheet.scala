import scala.collection.immutable.{IndexedSeq, HashMap}

class Spreadsheet {

    private val validColumns = Array("A", "B", "C", "D", "E", "F", "G", "H")
    private var data = HashMap[(String,Int), Double]()

    def getCellValue(column: String, row: Int): String = {
        data get(column, row) match {
            case Some(value) => value.toString
            case None => ""
        }
    }

    def setCellValue(column: String, row: Int, value: Double) {
        cellMustBeWithinGridRange(column, row)
        data += (column, row) -> value
    }

    def emptyCell(column: String, row: Int) {
        cellMustBeWithinGridRange(column, row)
        data -= ((column, row))
    }

    /*
      With more time I could make the display adjust to the size of the number of decimal places
      in each cell. For now I assumed max 2dp
     */
    def display = {
        val headers = validColumns map(_ + "      ") mkString ""
        val cellValues = for(r <- 1 to 10; c <- validColumns) yield getCellValue(c, r) padTo(7, " ") mkString("")
        val rowValues = cellValues grouped(8) map(_.mkString("")) toIndexedSeq
        val fullRows = for(i <- 0 to rowValues.length - 1) yield s"${i + 1}  ${rowValues(i)}"
        "   " ++ headers ++ sys.props("line.separator") ++ (fullRows mkString sys.props("line.separator"))
    }

    private def cellMustBeWithinGridRange(column: String, row: Int) {
        val isInRange = validColumns.contains(column) && row >= 1 && row <= 10
        if(!isInRange) throw new GridCellOutOfRange
    }
}

class GridCellOutOfRange extends Exception
