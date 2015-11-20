package com.lvxingpai.morphia

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import com.lvxingpai.configuration.Configuration
import org.mongodb.morphia.Datastore

import scala.collection.JavaConversions._

/**
 * 示例:
 * config:
 * {
 *   mongo: {
 *     demo1: { user: "", password: "", database: "demo1", instance: "mongo" },
 *     demo2: { database: "demo2" }
 *   }
 * }
 *
 * services:
 * {
 *   mongo: { node1: { host: "localhost", port: 2379} }
 * }
 *
 * @param config: 存放数据库的用户名、密码等信息
 * @param services: 存放数据库的节点信息
 * Created by zephyre on 11/19/15.
 */
class MorphiaModule(config: Configuration, services: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    // 有哪些datastore? 从config的mongo字段读取
    val mongoConfig = config getConfig "mongo" getOrElse Configuration.empty
    mongoConfig.underlying.root().entrySet map (entry => {
      val key = entry.getKey
      val value = entry.getValue atKey "tmp" getConfig "tmp"
      bind(classOf[Datastore]) annotatedWith Names.named(key) toProvider new MorphiaProvider(Configuration(value), services)
    })
  }
}
