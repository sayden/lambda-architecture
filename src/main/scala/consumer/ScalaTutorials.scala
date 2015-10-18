package consumer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Promise, Future}
import scala.util.{Failure, Random, Success}

class ScalaTutorials {

  def futures(): Unit = {
    var i = 500

    def sleep(duration: Int) = {
      i += 11
      Thread.sleep(duration)
    }

    println("Starting sleep in a Future")
    val f = Future {
      val duration: Int = Random.nextInt(i)
      sleep(duration)
      i
    }

    println("Before onComplete")
    f.onComplete{
      case Success(value) => printf("Got the callback, meaning = %s\n", value)
      case Failure(e) => e.printStackTrace
    }

    println("Do the rest")
    println("A") ; sleep(500)
    println("B") ; sleep(500)
    println("C") ; sleep(500)
  }

  def promises(): Unit = {
    val taxcut: Promise[TaxCut] = Promise()
    val taxcutF: Future[TaxCut] = taxcut.future

    println("Establishing promise")
    taxcutF.onComplete{
      case Success(taxcut: TaxCut) => printf("The taxcut is %s\n", taxcut.reduction)
      case Failure(e) => e.printStackTrace
    }

    println("Fullfilling the promise")
    taxcut.success(TaxCut(20))

    // We have to main the main thread to wait a bit for the promise, if not
    // the program exists before it is fulfilled
    Thread.sleep(50)
  }

  def inheritance(): Unit ={
    val myFirstClass: MyFirstClass = new MyFirstClass("Nombre de clase0")
    myFirstClass.mustImplementThis()
    myFirstClass.thisIsAlreadyImplemented()

    val mySecondClass: MySecondClass = new MySecondClass("Nombre de clase1", "A message")
    mySecondClass.mustImplementThis()
    mySecondClass.thisIsAlreadyImplemented()

    var interfacedClass: MyInterface = new MyFirstClass("Nombre de la clase2")
    interfacedClass.mustImplementThis()
    interfacedClass.thisIsAlreadyImplemented()

    interfacedClass = new MySecondClass("Nombre de la clase3", "Another message")
    interfacedClass.mustImplementThis()
    interfacedClass.thisIsAlreadyImplemented()
  }

  case class TaxCut(reduction: Int)

  trait MyInterface{
    val interfaceValue: String = "A value"

    def mustImplementThis(): Unit

    def thisIsAlreadyImplemented(): Unit = {
      println("Implemented method")
    }
  }

  class MyFirstClass(className: String) extends MyInterface {
    override def mustImplementThis(): Unit = {
      println("Overriden method with name " + className)
    }
  }
  class MySecondClass(className: String, msg: String) extends MyInterface {
    override def mustImplementThis(): Unit = {
      println(msg)
    }
  }
}
