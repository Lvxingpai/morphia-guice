name := """morphia-guice"""

organization := "com.lvxingpai"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.4"

crossScalaVersions := "2.10.4" :: "2.11.4" :: Nil

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "4.0",
  "com.lvxingpai" %% "etcd-store-guice" % "0.1.1-SNAPSHOT",
  "com.lvxingpai" %% "morphia-factory" % "0.2.0",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

