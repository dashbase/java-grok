package io.thekraken.grok.api;

public interface IConverter<T> {
  T convert(CharSequence value);

  default IConverter<T> newConverter(String param, Object... params) {
    return this;
  }
}
