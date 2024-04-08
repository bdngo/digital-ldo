// See README.md for license details.

package gcd

import chisel3._
// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage

class Comparator extends Module {
  val io = IO(new Bundle {
    val inn = Input(Bool())
    val inp = Input(Bool())
    val clk = Input(Clock())
    val out = Output(Bool())
  })

  val nand3_out1 = Wire(Bool())
  val nand3_out2 = Wire(Bool())
  val nor_out1 = Wire(Bool())
  val nor_out2 = Wire(Bool())

  nand3_out1 := !(io.inn && io.clk.asUInt.asBool && nand3_out2)
  nand3_out2 := !(io.inp && io.clk.asUInt.asBool && nand3_out1)

  nor_out1 := !(!nand3_out1 || nor_out2)
  nor_out2 := !(!nand3_out2 || nor_out1)

  io.out := nor_out1
}