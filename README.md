# morphia-guice
Dependency Injection container for Morphia datastore instances (Guice)

## Usage

### application.conf

```hcon
etcdStore: {
  host: "192.168.100.3"
  port: 2389
  serviceKeys: [
    {
      key: "mongo-production"
      alias: "mongo"
    }
  ],
  confKeys: [
    "idgen"
  ]
}
```

### Usage

```scala
import scala.concurrent.ExecutionContext.Implicits.global

// Get etcd configuration
val injector = Guice.createInjector(new EtcdStoreModule(Configuration.load()))
val conf = injector.getInstance(Key.get(classOf[Configuration], Names.named("etcd")))


val mongoConf = conf.getConfig("mongo").get
val serviceConf = conf.getConfig("services").get

val injector2 = injector.createChildInjector(new MorphiaModule(mongoConf, serviceConf))
val ds = injector2.getInstance(Key.get(classOf[Datastore], Names.named("demo")))
```
