package com.mathbot.pay.lightning

import org.scalatest.FunSuite
import play.api.libs.json.Json

class PaymentTest extends FunSuite {

  val stream = getClass.getResourceAsStream("/payments.json")
  val lines = io.Source.fromInputStream(stream).getLines
  val json = Json.parse(lines.mkString)
  stream.close()
  val r = json.validate[Seq[Payment]]
  test("json reads") {
    assert(r.isSuccess)
  }

  test("json writes") {
    val payment = r.get.head
    val j = Json.toJson(payment)
    val created_at = j("created_at").as[Long]
    assert(created_at === payment.created_at.getEpochSecond)
  }
}
