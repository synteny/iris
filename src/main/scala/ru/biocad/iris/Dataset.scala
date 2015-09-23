package ru.biocad.iris

import org.deeplearning4j.datasets.iterator.impl.IrisDataSetIterator
import org.nd4j.linalg.dataset.DataSet

/**
 * @author smirnovvs
 * @since 21.09.15
 */
object Dataset {
  private lazy val _data = {
    val d = new IrisDataSetIterator(BATCH_SIZE, NUM_SAMPLES).next()
    d.normalizeZeroMeanZeroUnitVariance()
    d
  }

  def apply() : DataSet = _data
}
