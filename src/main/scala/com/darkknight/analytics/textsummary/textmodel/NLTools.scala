package com.darkknight.analytics.textsummary.textmodel

import opennlp.tools.cmdline.parser.ParserTool
import opennlp.tools.parser.{Parse, ParserFactory, ParserModel}
import opennlp.tools.sentdetect.{SentenceDetectorME, SentenceModel}

/**
  * Created by apple on 1/20/17.
  */
object  NLTools extends Serializable{
  private val sentenceModel = new SentenceModel(this.getClass().getResourceAsStream("/en-sent.bin"));
  val sentenceDetector = new SentenceDetectorME(sentenceModel);
  val parserModel = new ParserModel(this.getClass.getResourceAsStream("/en-parser-chunking.bin"))
  val parser = ParserFactory.create(parserModel)

  def getSentences(s:String) : List[(Int,String)] = {
    sentenceDetector.sentDetect(s).zipWithIndex.map(a => (a._2,a._1)).toList
  }

  def getPhrases(s:String) : List[Phrase]= {
    var res = List[Phrase]()
    val parse = ParserTool.parseLine(s,parser,1)

    //extracting noun phrases
    val xx = extractPhraseTypes(parse,List[Phrase](),List("NP"))
    xx
  }

  //performs a recursive search of the phrase tree to exract phrases of the
  //giben type
  def extractPhraseTypes(ptree:Array[Parse],ph:List[Phrase],phType:List[String]): List[Phrase] ={
    var localList = List[Phrase]()
    ptree.foreach(f=>{
      if(f.getChildCount == 0) {
        if (phType.contains(f.getType))
          localList = localList :+ new Phrase(f.getType, f.getCoveredText)
      }
      else {
          if(phType.contains(f.getType))
            localList = localList :+ new Phrase(f.getType,f.getCoveredText)
        localList = localList ::: extractPhraseTypes(f.getChildren, ph,phType)
      }
    })
    localList
  }

}
