1. Stateful Join

Fork with combination of stateful join nodes.
Every fork has a join key set which is consumbed by exactly one join node.
The output arrows of the Fork have to end up as (possibly only a subset of) the input arrows of the
Join node.

It is possible for many Fork nodes to use the same joinKey for one target
joinNode.
