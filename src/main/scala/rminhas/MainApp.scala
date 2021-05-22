package rminhas

object MainApp extends App {
  val cache = LRUCache[String, String](3)

  cache.set("a", "1")
  cache.set("b", "2")
  cache.set("c", "3")
  println(cache.get("a"))
  cache.set("d", "4")
  cache.printMap()
  cache.printList()
  println(cache.get("d"))
  println(cache.get("c"))
  cache.printMap()
  cache.printList()

}
