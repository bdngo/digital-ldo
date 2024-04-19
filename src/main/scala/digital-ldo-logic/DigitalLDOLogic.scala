package DigitalLDOLogic

import chisel3._
// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage
import chisel3.util.HasBlackBoxResource

class DigitalLDOLogic(pfetWidth: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(Bool())
    val inb = Input(Bool())
    val out = Output(UInt(pfetWidth.W))
  })

  val lastVoltage = RegInit(~0.U(io.out.getWidth.W))

  lastVoltage := ~(lastVoltage + io.in.asUInt - io.inb.asUInt)
  io.out := lastVoltage
}

object DigitalLDOLogic extends App {
  ChiselStage.emitSystemVerilogFile(
    new DigitalLDOLogic(4),
    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
  )
}
