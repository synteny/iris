import org.deeplearning4j.eval.Evaluation
import ru.biocad.iris.{Dataset, IrisMLPModel}
/**
 * @author smirnovvs
 * @since 21.09.15
 */
object Main extends App {
  val output = new IrisMLPModel().train(Dataset()).predict(Dataset())
  val eval = new Evaluation(3)
  eval.eval(Dataset().getLabels, output)
  println(eval.stats())
}
