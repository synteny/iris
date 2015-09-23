package ru.biocad.iris

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet

/**
 * @author smirnovvs
 * @since 21.09.15
 */
trait Model {
  def train(data : DataSet) : Classifier

  trait Classifier {
    def predict(samples : DataSet) : INDArray
  }
}

