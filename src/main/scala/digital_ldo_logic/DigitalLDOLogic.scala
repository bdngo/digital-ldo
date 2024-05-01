package digital_ldo_logic

import chisel3._
import chisel3.util._
// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage

class DigitalLDOLogic(numPFET: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(Bool())
    val out = Output(UInt(numPFET.W))
  })

  // object Mode extends ChiselEnum {
  //   val EXP_INC, EXP_DEC, EXP_INC_MED, EXP_DEC_MED, LIN_INC, LIN_DEC = Value
  // }

  val pFETMask = RegInit(0.U(numPFET.W))
  // val inReg = RegNext(io.in)
  // val state = RegInit(Mode.EXP_INC)

  // switch (state) {
  //   is (Mode.EXP_INC) {
  //     state := Mux(io.in, Mode.EXP_INC, Mode.LIN_DEC)
  //     pFETMask := Mux(pFETMask < ~0.U(numPFET.W), pFETMask << 1.U, pFETMask)
  //   }
  //   is (Mode.EXP_DEC) {
  //     state := Mux(io.in, Mode.LIN_INC, Mode.EXP_DEC)
  //     pFETMask := Mux(pFETMask > 0.U, pFETMask >> 1.U, pFETMask)
  //   }
  //   is (Mode.EXP_INC_MED) {
  //     state := Mux(io.in, Mode.EXP_INC, Mode.LIN_DEC)
  //     pFETMask := Mux(pFETMask < ~0.U(numPFET.W), pFETMask + 1.U, pFETMask)
  //   }
  //   is (Mode.EXP_DEC_MED) {
  //     state := Mux(io.in, Mode.LIN_INC, Mode.EXP_DEC)
  //     pFETMask := Mux(pFETMask > 0.U, pFETMask - 1.U, pFETMask)
  //   }
  //   is (Mode.LIN_INC) {
  //     state := Mux(io.in, Mode.EXP_INC_MED, Mode.LIN_DEC)
  //     pFETMask := Mux(pFETMask < ~0.U(numPFET.W), pFETMask + 1.U, pFETMask)
  //   }
  //   is (Mode.LIN_DEC) {
  //     state := Mux(io.in, Mode.LIN_INC, Mode.EXP_DEC_MED)
  //     pFETMask := Mux(pFETMask > 0.U, pFETMask - 1.U, pFETMask)
  //   }
  // }

  when (io.in && pFETMask < ~0.U(numPFET.W)) {
    pFETMask := pFETMask + 1.U
  }.elsewhen(!io.in && pFETMask > 0.U) {
    pFETMask := pFETMask - 1.U
  }
  io.out := ~pFETMask
}

object DigitalLDOLogic extends App {
  ChiselStage.emitSystemVerilogFile(
    new DigitalLDOLogic(6),
    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
  )
}
