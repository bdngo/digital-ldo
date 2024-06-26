// Generated by CIRCT firtool-1.62.0
module DigitalLDOLogic(
  input        clock,
               reset,
               io_in,
  output [5:0] io_out
);

  reg [5:0] pFETMask;
  always @(posedge clock) begin
    if (reset)
      pFETMask <= 6'h0;
    else if (io_in & pFETMask != 6'h3F)
      pFETMask <= pFETMask + 6'h1;
    else if (~io_in & (|pFETMask))
      pFETMask <= pFETMask - 6'h1;
  end // always @(posedge)
  assign io_out = ~pFETMask;
endmodule

