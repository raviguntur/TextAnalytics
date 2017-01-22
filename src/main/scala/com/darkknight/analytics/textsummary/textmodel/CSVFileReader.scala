package com.darkknight.analytics.textsummary.textmodel

import java.io.FileReader

import com.opencsv.CSVReader

import scala.collection.JavaConversions._

/**
  * Created by apple on 1/20/17.
  */
class CSVFileReader(fileName:String) {

  private[this] val dataWithHeader = getDataFromCSV(fileName)

  private[this ]
  def getDataFromCSV(fileName:String) : List[Array[String]] = {
    val csvReader = new CSVReader(new FileReader(fileName))
    val lines = csvReader.readAll()
    lines.toList
  }


  def getData() : List[Story]={
    val x = dataWithHeader.drop(1).map(l => {
      new Story(l(0).toInt,l(1))
    })
    x
  }

}
