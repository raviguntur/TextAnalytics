package com.darkknight.analytics.textsummary.textmodel

/**
  * Created by apple on 1/20/17.
  */
object Main {

  def main(args: Array[String]): Unit = {
    val stories = new CSVFileReader("/Users/apple/PROJECTS/TextProcessing/articles.csv")
    //each story --> break into sentences --> phrases

    //this is a list of stories from the csv file
    val corpus = stories.getData().take(20)
    // this is a list of stories in the form of basic components from which
    //features shall be built
    val storyModels = corpus.map(c => new StoryModel(c))

    //execute the tile extractor for each story
    storyModels.foreach(s => {
      println("-----------------------------------------------")
      println("Story number:"+s.index)
      val (shortTitle,longTitle) = Extractor.titleExtractor(s)
      println("short title candidates")
      shortTitle.foreach(t => {println("\t----- "+t)})
      println("\n longer title candidates")
      longTitle.foreach(t => {println("--- "+t)})

      val extract = Extractor.summaryExtractor(s)
      println("\n ------------- Extract ----------")
      extract.foreach(e => println(e))
      println("*********************************")
      })


  }
}