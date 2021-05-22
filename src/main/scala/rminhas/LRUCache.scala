package rminhas

import scala.collection.mutable

case class LRUCache[K, V](size: Int) {
  assert(size > 0, "size must be greater than 0.")

  private var count = 0
  private val map = mutable.Map.empty[K, Node]
  private var head: Node = null
  private var tail: Node = null

  private def delete(node: Node): Unit = {
    if (count > 0) {
      if (node == head && node == tail) {
        head = null
        tail = null
      } else {
        if (node.pre != null)
          node.pre.next = node.next
        else
          head = node.next
        if (node.next != null) {
          node.next.pre = node.pre
        }
        tail = node.pre
      }
      count -= 1
    }
  }

  private def add(node: Node): Unit = {
    if (count >= size) {
      delete(tail)
    }
    if (count == 0) {
      head = node
      tail = node
    } else {
      node.next = head
      head.pre = node
      head = node
    }
    count += 1
  }

  def get(key: K): Option[V] = {
    if (map.contains(key)) {
      val node = map(key)
      delete(node)
      add(Node(key, node.value))
      Some(node.value)
    } else {
      None
    }
  }

  def set(key: K, value: V): Unit = {
    if (map.contains(key)) {
      val node = map(key)
      delete(node)
      val newNode = Node(key, value)
      add(newNode)
      map.put(key, newNode)
    } else {
      val node = Node(key, value)
      if (count < size) {
        add(node)
        map.put(key, node)
      } else {
        map.remove(tail.key)
        delete(tail)
        add(node)
        map.put(key, node)
      }
    }
  }

  def printMap(): Unit = {
    println("Map:" + map)
  }

  def printList(): Unit = {
    var h = head
    var l = List[Node]()
    while (h != null) {
      l = l :+ h
      h = h.next
    }
    println("List:" + l.mkString(" --> "))
  }

  private case class Node(key: K, value: V, var pre: Node = null, var next: Node = null) {
    override def toString: String = s"[$key,$value]"
  }
}
