-- 5mns
-- five minute noise set
--
-- ENC2 freq1
-- ENC3 freq2
-- KEY2 or KEY3 audio engage chopper

engine.name = "FiveMinuteNoiseSet"

local m

function init()
  params:add_control("freq1", "freq1", controlspec.new(40, 10000, 'exp', 1, 100))
  params:set_action("freq1", function(x) engine.freq1(x) end)
  
  params:add_control("freq2", "freq2", controlspec.new(40, 10000, 'exp', 1, 100))
  params:set_action("freq2", function(x) engine.freq2(x) end)
  
  params:add_control("chaos", "chaos", controlspec.new(0, 600, 'lin', 1, 100))
  params:set_action("chaos", function(x) engine.chaos(x) end)
  
  params:add_control("chopper", "chopper", controlspec.new(0, 1, 'lin', 1, 0))
  params:set_action("chopper", function(x) engine.chopper(x) end)
  
  params:bang()
  
  m = metro.init()
  m.time = 1.0 / 15
  m.event = function()
    redraw()
  end
  m:start()
end

function enc(n, d)
  if n == 1 then
    params:delta("chaos", d)
  elseif n == 2 then
    params:delta("freq1", d)
  elseif n == 3 then
    params:delta("freq2", d)
  end
  
  redraw()
end

function key(n, z)
  if n == 2 or n == 3 then
    params:set("chopper", z)
  end
end

function redraw()
  if math.random() > 0.95 then
    screen.clear()
  end
  
  screen.level(math.random(1, 15))
  screen.move(math.random(0, 120), math.random(0, 60))
  screen.text("[ 5 minute noise set]")
  
  screen.update()
end