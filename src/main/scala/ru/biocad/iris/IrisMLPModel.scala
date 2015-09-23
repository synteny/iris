package ru.biocad.iris

import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction

/**
 * @author smirnovvs
 * @since 22.09.15
 */
class EnsembleMLPModel extends IrisEnsembleModel[IrisMLPModel] {
  override protected def factory() : IrisMLPModel = ???

  override def train(data : DataSet) : Classifier = ???
}

class IrisMLPModel extends Model {
  private val SEED = 451
  private val ITERATIONS = 100
  private val LEARNING_RATE = 1e-3
  private val L1_REGULARIZATION = 0.3
  private val L2_REGULARIZATION = 1e-3
  private val INPUT_DIM = 4
  private val LAYER_SIZES = Seq(128, 64, 3)

  private def getMlp() : MultiLayerNetwork = {
    val conf = new NeuralNetConfiguration.Builder()
      .seed(SEED)
      .iterations(ITERATIONS)
      .learningRate(LEARNING_RATE)
      .l1(L1_REGULARIZATION).regularization(true).l2(L2_REGULARIZATION)
      .constrainGradientToUnitNorm(true)
      .list(3)
      .layer(0, new DenseLayer.Builder().nIn(INPUT_DIM).nOut(LAYER_SIZES(0)).activation("tanh").weightInit(WeightInit.XAVIER).build)
      .layer(1, new DenseLayer.Builder().nIn(LAYER_SIZES(0)).nOut(LAYER_SIZES(1)).activation("tanh").weightInit(WeightInit.XAVIER).build)
      .layer(2, new OutputLayer.Builder(LossFunction.MCXENT).weightInit(WeightInit.XAVIER).activation("softmax").nIn(LAYER_SIZES(1)).nOut(LAYER_SIZES(2)).build)
      .backprop(true)
      .pretrain(false)
      .build

    val model = new MultiLayerNetwork(conf)
    model.init

    model
  }

  override def train(data : DataSet) : Classifier = {
    assert(data.getFeatures.columns() == INPUT_DIM)

    val mlp = getMlp()
    mlp.fit(data)

    new PerceptronClassifier(mlp)
  }

  private class PerceptronClassifier(mlp : MultiLayerNetwork) extends Classifier {
    override def predict(samples : DataSet) : INDArray = mlp.output(samples.getFeatureMatrix)
  }
}
