package com.lvxingpai.morphia

import com.google.inject.Provider
import com.lvxingpai.configuration.Configuration
import com.lvxingpai.database.MorphiaFactoryImpl
import org.mongodb.morphia.Datastore

import scala.collection.JavaConversions._

/**
 * Created by zephyre on 11/19/15.
 */
class MorphiaProvider(config: Configuration, services: Configuration) extends Provider[Datastore] {

  lazy val get: Datastore = {
    (for {
      instance <- config getString "instance" orElse Some("mongo")
      // 找到所有的节点名称
      nodeKeys <- services getConfig instance orElse Some(Configuration.empty) map (_.underlying.root().keySet().toSeq)
      database <- config getString "database" orElse Some("local")
      validation <- config getBoolean "validation" orElse Some(false)
    } yield {
      // 找到所有节点的地址
      val addresses = nodeKeys map (nodeKey => {
        for {
          host <- services getString s"$instance.$nodeKey.host"
          port <- services getInt s"$instance.$nodeKey.port"
        } yield {
          host -> port
        }
      }) filter (_.nonEmpty) map (_.get)

      val user = config getString "user"
      val password = config getString "password"
      val adminSource = config getString "adminSource"

      MorphiaFactoryImpl.newInstance(addresses, database, adminSource, user, password, validation = validation)
    }).get
  }
}
