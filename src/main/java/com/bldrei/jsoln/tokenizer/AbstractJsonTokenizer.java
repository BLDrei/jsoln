package com.bldrei.jsoln.tokenizer;

public abstract sealed class AbstractJsonTokenizer permits JsonArrayTokenizer, JsonObjectTokenizer {

  protected String remainingTxt;

  public AbstractJsonTokenizer(String txt) {
    this.remainingTxt = txt;
  }

}
