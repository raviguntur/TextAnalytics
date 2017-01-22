package com.darkknight.analytics.textsummary.textmodel

import scala.collection.immutable.HashSet

/**
  * Created by apple on 1/21/17.
  */
object  Commons {

  val StopList = HashSet("a","an","and","all",
    "be", "become","became",
    "can","cannot","could","cant",
    "do","done","dont","did",
    "find","follow","following","go","gone","him","has","had","how","it","just","know","let",
    "lot","was","would","who","their","them","the","that","those"
  )
}

case class Story(index : Int, story : String)
case class Phrase(kind: String, phrase: String)