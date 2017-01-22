package com.darkknight.analytics.textsummary.textmodel

import com.darkknight.analytics.textsummary.features.Probability

/**
  * Created by apple on 1/20/17.
  */
class StoryModel(val data : Story) {
  val index = data.index
  val story = data.story

  val sentencesWithIndex = NLTools.getSentences(story)
  val phrasesPerSentence = sentencesWithIndex.map(s => (s._1,NLTools.getPhrases(s._2)))
  val phraseDistribution = Probability.buildDistribution(phrasesPerSentence.flatMap(f=>f._2).map(_.phrase))
  val phraseWordsInStory = phrasesPerSentence.map(p=>p._2.map(q=>q.phrase.split(" ")).flatten)
    .flatten
    .map(_.trim)
      .filterNot(_.length < 5)
    .filterNot(Commons.StopList.contains(_))
  val phraseWordDistribution = Probability.buildDistribution(phraseWordsInStory)

}
