
package example

import scala.io.Source
class CsvReader(val filePath: String) extends SensorReader{
  override def readSensor():Seq[SensorData] ={
    for {
      line <- Source.fromFile(filePath).getLines().drop(1).toVector
      values = line.split(",").map(_.trim)
    } yield SensorData(values(0), values(1))
  }
 
}

  
