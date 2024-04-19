// Generated by CIRCT firtool-1.62.0
module DigitalLDOLogic(
  input        clock,
               reset,
               io_in,
  output [3:0] io_out
);

  reg [3:0] lastVoltage;
  always @(posedge clock) begin
    if (reset)
      lastVoltage <= 4'hF;
    else if (io_in)
      lastVoltage <= {lastVoltage[2:0], 1'h1};
    else
      lastVoltage <= {1'h0, lastVoltage[3:1]};
  end // always @(posedge)
  assign io_out = lastVoltage;
endmodule

