organization := "ru.biocad"
name := "dl4j-iris"
version := "1.0-SNAPSHOT"
resolvers += "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository"

libraryDependencies ++= Seq(
  "org.deeplearning4j" % "deeplearning4j-core" % "0.4-rc3",
  "org.slf4j" % "slf4j-simple" % "1.7.12",
  "io.dropwizard.metrics" % "metrics-core" % "3.1.2",
  "org.jblas" % "jblas" % "1.2.4",
  "org.nd4j" % "nd4j-jblas" % "0.4-rc3",
  "org.scalatest" %% "scalatest" % "2.2.5" % Test
)

version := "1.0"

scalaVersion := "2.11.7"
