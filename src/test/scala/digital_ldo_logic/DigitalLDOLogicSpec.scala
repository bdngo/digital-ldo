package digital_ldo_logic

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

class DigitalLDOLogicSpec extends AnyFreeSpec with ChiselScalatestTester {
  "LDO logic adds as long as comparator is low" in {
    test(new DigitalLDOLogic(8)).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.io.in.poke(true.B)
      for (i <- 255 to 0 by -1) {
        dut.io.out.expect(i.U)
        dut.clock.step()
      }
      
      dut.io.in.poke(false.B)
      for (i <- 0 to 255) {
        dut.io.out.expect(i.U)
        dut.clock.step()
      }
    }
  }
}
