package net.ceedubs.ficus.readers

import com.typesafe.config.Config

/**
  * This is a dummmy trait that used as an evidence that an implementation of a [[ValueReader]]
  * uses only [[com.typesafe.config.Config]]'s getConfig method on the given configuration object
  * in the read method.
  *
  * When this trait is applied to a [[ValueReader]], It means that the user may call the
  * [[net.ceedubs.ficus.FicusConfig.as()]] method using that value reader.
  */
trait ParentValueReader[A] {
  def value: ValueReader[A]

  /**
    * Calls [[read(config, path)]] with a new [[Config]] object constructed
    * from the given config object
    *
    * @param config The config object to read
    * @return       The value read
    */
  def read(config: Config): A =
    value.read(config.atKey(ParentValueReader.key), ParentValueReader.key)
}

object ParentValueReader {
  private val key = "key"

  /** Returns the implicit ParentValueReader[A] in scope.
    * `ParentValueReader[A]` is equivalent to `implicitly[ParentValueReader[A]]`
    */
  def apply[A](implicit reader: ParentValueReader[A]): ParentValueReader[A] = reader
}