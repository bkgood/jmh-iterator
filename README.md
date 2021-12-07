# jmh-iterator
JMH benchmarking various methods of iterating through an ArrayList

In another project in different time and place, I needed to meet some strict
time goals in a routine that was replacing another one (without being more
expensive).

Chasing profiler output, one avenue I took was replacing enhanced `for` loops
as I saw `hasNext()` and `next()` invocations popping up, and I figured that
there wasn't really any harm in writing a C-style `for` loop.

Later, someone challenged me on whether or not this really makes a difference.
So I wrote this.

After a bunch of runs (on an AMD EPYC 7281-based VM), it appears (if I've done
everything right) that there is some reliable improvement in both throughput
and variance of the C-style `for` loop over enhanced for. I'm guessing the
variance improvement is due to reduced heap churn; with no Iterator allocations
GC pauses should be very rare (to non-existent) over the course of the
benchmark runs.

Some day I'll (maybe) get hotspot to dump disassembled output of the code it's
emitting so I can sort out how different the code is otherwise (or maybe even
look at gc logs to verify my claims are correct), but for now I need to push
this so I can stop paying for this VM :)
