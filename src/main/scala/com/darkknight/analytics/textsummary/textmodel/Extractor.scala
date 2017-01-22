package com.darkknight.analytics.textsummary.textmodel

import com.darkknight.analytics.textsummary.features.ProbElem

/**
  * Created by apple on 1/21/17.
  */
object Extractor {

  def titleExtractor(sModel:StoryModel) : (List[(String,Int)],List[String])= {

    //get high probablity words
    val highProbWords = sModel.phraseWordDistribution.take(10)

    //get a score for phrases with high probability words and take a few
    val topPhrases = phraseScore(highProbWords,sModel.phraseDistribution).take(10)

    val xx = topPhrases.toMap
    val curatedSentencesIndex = sModel.phrasesPerSentence.map(s => (s._1,cumulativeScore(s._2.map(_.phrase),xx))).sortBy(-_._2).take(1)

    //index score
    val yy = curatedSentencesIndex.toMap
    val zz = sModel.sentencesWithIndex.toMap

    //choose top phrases that are within a length range
    val phrasesAndLength = topPhrases.map(p => (p._1,p._1.split(" ").length)).filter(q => q._2 > 3 && q._2 < 11).sortBy(-_._2)

    val shortTitles = phrasesAndLength.take(2)

    val longTitles = yy.map(a => zz(a._1))
    (shortTitles,longTitles.toList)
  }


  def summaryExtractor(sModel:StoryModel) : List[String] = {

    //get high probablity words
    val highProbWords = sModel.phraseWordDistribution.take(10)

    //get a score for phrases with high probability words and take a few
    val topPhrases = phraseScore(highProbWords, sModel.phraseDistribution).take(10)

    val xx = topPhrases.toMap
    val curatedSentencesIndex = sModel.phrasesPerSentence.map(s => (s._1, cumulativeScore(s._2.map(_.phrase), xx))).sortBy(_._2).take(5).map(_._1)

    val sentMap = sModel.sentencesWithIndex.toMap
    var extract = List[String]()
    curatedSentencesIndex.foreach(i => extract = extract :+ sentMap(i))
    extract
  }

    def phraseScore(highProbWords: List[ProbElem],phraseProb:List[ProbElem]) : List[(String,Float)] = {

    //probability that the phrase occurs in the story = PS
    //score of high probability words that this phrase has CHP
    //phrase score = PS*CHP

    phraseProb.map(ph => (ph.elem.toString,ph.prob*countWord(highProbWords,ph.elem.toString))).sortBy(-_._2)

  }

  //returns the score of high probability words found in a string/phrase
  def countWord(highProbWords: List[ProbElem],ph:String) : Float = {
    var count = 0.0.toFloat ;
    highProbWords.foreach(w => {
      if(ph.contains(w.elem.toString))
        count+=w.prob
    })
    count
  }

  def cumulativeScore(A:List[String],B:Map[String,Float]): Float = {
    var score = 0.0.toFloat
    A.foreach(p => {
      if(B.contains(p)){
        score +=B(p)
      }
    })
    score
  }

}
