# Major_Project
BE Computer Engineering Final Year Project                                                                                                                                         
                                                            ORDER ROUTING SYSTEM                                                                                                  
Order Routing is the ability to correctly and efficiently route an order by choosing the best execution destination based on factors like price, costs, speed, quantity, etc. Order Routing takes decisions based on certain rules that are defined by the broker or the brokerage firm.

The whole system can be broken down into 3 components:-
Client-side application
The server (main Order Routing Server)
Dummy Exchanges (3rd party data vendors)

Client-side:-
GUI application for placing orders.
Brokers will be able to select stock ticker, Quantity of shares, order side(Buy or Sell), etc.
Broker(client) can also add rules with respect to type of order (limit order, stop order, stop limit order, etc.)
Client can also monitor the status of order whether an order has reached to a desired exchange.                                                                                  
Order Routing System:
Order Routing server will be provided with Market data from mock exchanges. 
The incoming order from the client is fed into the ORS as a FIX  message.
Server will use this market data and apply certain rules defined by the end user (broker) to route incoming orders to the venue (stock exchange) with the best price.

Data Storage:
All the event history / order routing history is stored in a database. When an order reaches a particular destination the record of it (routed order) will be stored in a database.
This records can be retrieved by the client to monitor the status of an order.
                                                                                                                                                                                  
(3) FIX (Financial Information Exchange) Protocol:-
Order Routing Server will send orders received from clients to a best execution venue(mock stock exchange) through FIX engine which utilizes FIX messages. FIX messages reduce time and complexity involved in connecting to multiple trading partners and exchanges around the world. This is because FIX Protocol has been developed through the collaboration of banks, exchanges, brokers and information providers from around the world.
                                                                                                                                                                                 
                                                                                                                                                                                 
FIX is a session based protocol. Session is a communication or information exchange between two parties. FIX communication involves two parties:-
Initiator / Client - initiates the communication
Acceptor / Server - receives connection request from initiator and validates client request using login message.
                                                                                                                                                                                                                                                                                                                                                                


























