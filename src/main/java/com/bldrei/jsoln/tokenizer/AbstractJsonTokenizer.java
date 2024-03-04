package com.bldrei.jsoln.tokenizer;

public abstract class AbstractJsonTokenizer {

  protected String remainingTxt;

  public AbstractJsonTokenizer(String txt) {
    this.remainingTxt = txt;
  }

}
