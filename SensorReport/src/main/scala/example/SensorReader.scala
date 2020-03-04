package example

case class SensorData(id:String, humidity: String)
trait SensorReader {
  def readSensor():Seq[SensorData]
}


