# Implementation notes

* I chose to leave OrderBoardEntry as a mutable type for simplicity, but in a multi-threaded system it would be
preferable to modify this so that OrderBoardEntry was immutable and a new instance was created whenever a mutation was
required.  ConcurrentMap.merge() would be helpful for the atomic operation to mutate the board itself.

* I chose to keep both buy and sell orders in the same map and order them as sell followed by buy; this may not be
the desired ordering.  One might anticipate a requirement to be able to iterate the buy and sell sides separately and
this might be done either by keeping separate maps for each side of the market or perhaps less efficiently simply
using a stream with an added filter.

* It wasn't stated in the requirements but I assumed from the addition of the UserId that an order is to be considered
equivalent for cancellation if it has the same values for userid, quantity, price and type.  An alternative would be
for registerOrder() to return an OrderId and cancelOrder to take an OrderId; thus uniquely identifying an individual
order and allowing a single user to add the same logical order more than once.

* I chose to keep the orders in a single unordered collection in the OrderBoard as it is only required to establish
whether an Order is registered on the board or not (this wasn't actually a specified requirement but it seemed
a sensible defense).

