package ru.biocad.iris

/**
 * @author smirnovvs
 * @since 23.09.15
 */
trait IrisEnsembleModel[M <: Model] extends Model {
  protected def factory() : M
}
