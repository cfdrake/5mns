Engine_FiveMinuteNoiseSet : CroneEngine {
  var <synth;

  *new { arg context, doneCallback;
  ^super.new(context, doneCallback);
  }

  alloc {
    synth = {
      arg out, freq1 = 100, freq2 = 100, chaos = 100, chopper = 0;
      
    	var fb = LocalIn.ar(1);
    	var osc1 = Saw.ar(freq1 + (chaos*fb));
    	var osc2 = Saw.ar(freq2 + (chaos*osc1));
    	
    	var pan1 = Pan2.ar(osc1, Lag.ar(LFNoise0.ar(3)));
    	var pan2 = Pan2.ar(osc2, Lag.ar(LFNoise0.ar(3)));
    	
    	var ch = Pulse.ar(LFNoise0.ar(4, 12), LFNoise0.ar(4));
    	
    	pan1 = SelectX.ar(chopper, [pan1, pan1 * ch]);
    	pan2 = SelectX.ar(chopper, [pan2, pan2 * ch]);
    	
    	LocalOut.ar(osc2);
      Out.ar(out, Limiter.ar(Fold.ar(Mix.ar([pan1, pan2]), -0.5, 0.5)) * 0.25);
    }.play(args: [\out, context.out_b], target: context.xg);

    this.addCommand("freq1", "f", { arg msg;
      synth.set(\freq1, msg[1]);
    });
    
    this.addCommand("freq2", "f", { arg msg;
      synth.set(\freq2, msg[1]);
    });
    
    this.addCommand("chaos", "f", { arg msg;
      synth.set(\chaos, msg[1]);
    });
    
    this.addCommand("chopper", "i", { arg msg;
      synth.set(\chopper, msg[1]);
    });
  }

  free {
    synth.free;
  }
}