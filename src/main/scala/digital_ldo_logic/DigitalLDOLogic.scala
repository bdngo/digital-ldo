package digital_ldo_logic

import chisel3._
import chisel3.util.Cat
// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage

class DigitalLDOLogic(numPFET: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(Bool())
    val out = Output(UInt(numPFET.W))
  })

  val lastVoltage = RegInit(~(0.U(numPFET.W)))

  when (io.in) {
    lastVoltage := Cat(lastVoltage(numPFET - 2, 0), 1.U)
  }.otherwise {
    lastVoltage := Cat(0.U, lastVoltage(numPFET - 1, 1))
  }
  io.out := lastVoltage
}

object DigitalLDOLogic extends App {
  ChiselStage.emitSystemVerilogFile(
    new DigitalLDOLogic(4),
    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
  )
}
