Task - A simple poc to test communication between 2 network i/f ( same subnet for this poc, but could be diff subnet too if needed) on same host
Learnings:
* if the destination ip = one of the local i/f ip in same ns ( i.e. from ip route show table local) , then kernel will transit the packet throug loopback i/f
* this means that the packet is never really delivered to the nic ( remember the loopback is a virtudal entity not tied to any piece of h/w)
* above is the reason why a ping from ip of eth1 on a host to ip of eth2 on same host doesnt go through ( make sure you use ping -I and explicitly specify i/f to be used. if using just ping , of course the response will ne okay as the src add will be INADDR_ANY and it will end up picking the same ip for src and target)
* one way to get around this is by moving one of the i/f to a different network ns

command sequence for the poc:

sudo ip netns add ns1
sudo ip link set dev <eth1 or appropriate interface name> netns ns1
sudo ip netns exec ns1 ip link set <eth1 or appropriate interface name> up
after this, ping from global ns to ns1 and vice-versa will work

ping from global to ns1 (use relevant interface name) : ping -I eth0 10.8.1.15
ping from ns1 to global (use relevant interface name) : sudo ip netns exec ns1 ping -I eth1 10.8.1.5

after tests are done , to move back eth1 to global ns do the following
sudo ip netns exec ns1 ip link set <eth1 or appropriate interface name> netns 1
