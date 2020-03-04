package example

import java.io.File

object SensorReport extends App {
 
val okFileExtensions = List("csv")
val files = getListOfFiles(new File("C:/Users/vivek.kale/workspace/SensorReport/csvfiles"), okFileExtensions)

def getListOfFiles(dir: File, extensions: List[String]): List[File] = {
    dir.listFiles.filter(_.isFile).toList.filter { file =>
        extensions.exists(file.getName.endsWith(_))
    }
  }

val sensordata = files.map(f => new CsvReader(f.getPath).readSensor()).flatten
val failedMeasurements = sensordata.filter(p => p.humidity.toUpperCase == "NAN")

def parseNaN(x: String) = {
	if(x.toUpperCase() == "NAN") -1 else x.toInt
	}

def transformSdata(l: List[Int])={
	if(l.isEmpty)("NaN","NaN","NaN") else(l.min.toString(),(l.sum/l.length).toString,l.max.toString)
	} 

val res = sensordata.groupBy(f => f.id)
.map(f =>(f._1, f._2.map(f=> parseNaN(f.humidity))))
.map(f => (f._1,f._2.filterNot(p => p<0)))
.map(f => analysedSensoryData(f._1,transformSdata(f._2)._1,transformSdata(f._2)._2,transformSdata(f._2)._3))

println("Num of processed files:"+files.length)
println("Num of processed measurements:"+sensordata.length)
println("Num of failedMeasurements:"+failedMeasurements.length)
res.map(f=> println(f.id+" "+f.min+" "+f.avg+" "+f.max))
}

case class analysedSensoryData(id: String,min: String,avg: String ,max: String )