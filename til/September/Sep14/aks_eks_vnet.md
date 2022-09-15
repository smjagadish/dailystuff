aws -> concept of public and private subnets with outbound internet traffic by default stopped. subnets with a route to internet g/w are public while rest are private
azure -> concept of flat subnets with always allowed outbound internet traffic (unless modified)
aws -> vpc has 1 primary cidr at all times. node and pod ip assigned from this. secondary cidr can be added and with custom n/w one can assign pod ip from sec.cidr while using primary cide for node ip
azure -> vnet can potentially host multiple cidr with each tagged to a subnet. nodes (and pods) can be placed onto diff subnet/cidr . alternatively a cidr/subnet exclusively for pods is also possible . 
aws -> with pvt subnets, use nat g/w for outbound internet connection 
azure -> not striaght-forward to block outbound internet access . one way is to use azure firewall and UDR

