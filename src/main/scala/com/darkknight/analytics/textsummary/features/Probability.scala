package com.darkknight.analytics.textsummary.features

import com.darkknight.analytics.textsummary.textmodel.Phrase

/**
  * Created by apple on 1/21/17.
  */
object Probability {

  def buildDistribution(tokens: List[String]) : List[ProbElem] = {
    val freq = tokens.groupBy(f=>f).map(f=>(f._1,f._2.length))
    val total=freq.map(_._2).foldLeft(0.0)( (a,b) => a+b)
    if(total != 0)
      return freq.map(f => new ProbElem(f._1,f._2/total.toFloat)).toList.sortBy(-_.prob)
    else
      return List[ProbElem]()
  }

}

case class ProbElem(elem:Any,prob:Float)
